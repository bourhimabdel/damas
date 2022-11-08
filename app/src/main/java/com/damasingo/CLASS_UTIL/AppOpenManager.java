package com.damasingo.CLASS_UTIL;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
//import androidx.lifecycle.ProcessLifecycleOwner;

import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.bluetooth_match.bluetooth_game_space;
import com.damasingo.local_match.local_game_space;
import com.damasingo.local_match.local_game_space_robot;
import com.damasingo.open_app_ads;
import com.damasingo.play_onlinge.game_space;
import com.damasingo.randomly.game_space_randomly;
import com.damasingo.sign_in;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import java.util.Date;

/** Prefetches App Open Ads. */
public class AppOpenManager  implements LifecycleObserver,Application.ActivityLifecycleCallbacks{
    private static final String LOG_TAG = "AppOpenManager";
    private static final String AD_UNIT_ID = "ca-app-pub-2065152596342192/1500371665";
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private Activity currentActivity;
    private long loadTime = 0;
    private sharedpreferences sharedpreferences;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private final open_app_ads myApplication;


    /** Constructor */
    public AppOpenManager(open_app_ads myApplication) {
       this.myApplication = myApplication;
       this.myApplication.registerActivityLifecycleCallbacks(this);
       sharedpreferences=new sharedpreferences(myApplication);
       ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /** LifecycleObserver methods */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
         if(!(currentActivity instanceof local_game_space ||
                 currentActivity instanceof local_game_space_robot  || currentActivity instanceof game_space_randomly
                 ||currentActivity instanceof bluetooth_game_space || currentActivity instanceof game_space
                 || currentActivity instanceof sign_in)) {
             showAdIfAvailable();
         }
    }


    /** Shows the ad if one isn't already showing. */
    public void showAdIfAvailable() {
         // Only show ad if there is not already an app open ad currently showing
         // and an ad is available.
         if (!isShowingAd && isAdAvailable()) {
             Log.d(LOG_TAG, "Will show ad.");
//
             FullScreenContentCallback fullScreenContentCallback =
                     new FullScreenContentCallback() {
                         @Override
                         public void onAdDismissedFullScreenContent() {
                             // Set the reference to null so isAdAvailable() returns false.
                             AppOpenManager.this.appOpenAd = null;
                             isShowingAd = false;
                             fetchAd();
                         }
//
                         @Override
                         public void onAdFailedToShowFullScreenContent(AdError adError) {}
//
                         @Override
                         public void onAdShowedFullScreenContent() {
                             isShowingAd = true;
                         }
                     };
//
             appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
             appOpenAd.show(currentActivity);
//
         } else {
             Log.d(LOG_TAG, "Can not show ad.");
             fetchAd();
          }
    }

    /** Request an ad */
    public void fetchAd() {
         // Have unused ad, no need to fetch another.
         if (isAdAvailable()) {
             return;
         }
//
         loadCallback =
                 new AppOpenAd.AppOpenAdLoadCallback() {
                     /**
                      * Called when an app open ad has loaded.
                      *
                      * @param ad the loaded app open ad.
                      */
                     @Override
                     public void onAdLoaded(AppOpenAd ad) {
                         AppOpenManager.this.appOpenAd = ad;
                         AppOpenManager.this.loadTime = (new Date()).getTime();
//
                     }
//
                     /**
                      * Called when an app open ad has failed to load.
                      *
                      * @param loadAdError the error.
                      */
                     @Override
                     public void onAdFailedToLoad(LoadAdError loadAdError) {
                         // Handle the error.
                     }
//
                 };
         AdRequest request = getAdRequest();
         AppOpenAd.load(
                 myApplication, AD_UNIT_ID, request,
                 AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    /** Creates and returns ad request. */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /** Utility method to check if ad was loaded more than n hours ago. */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));

    }

    /** Utility method that checks if ad exists and can be shown. */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }



    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {


        if(myApplication.str==2 ) {
            if(!new sharedpreferences(myApplication).get_setting_type("user_is_not_sign_in"))
                new connect_to_firebase().incrument_online(myApplication);
        }
         currentActivity = activity;
        sharedpreferences.put_app_state("start");
        myApplication.w=0;
        myApplication.str=0;
    }

    @Override
    public void onActivityResumed(Activity activity) {
         currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {

        //currentActivity = null;
    }
}