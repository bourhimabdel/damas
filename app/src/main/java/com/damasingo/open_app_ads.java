package com.damasingo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDexApplication;

import com.damasingo.CLASS_UTIL.AppOpenManager;
import com.damasingo.CLASS_UTIL.HomeWatcher;
import com.damasingo.CLASS_UTIL.OnHomePressedListener;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.home_fragments.local;
import com.damasingo.home_fragments.online;
import com.damasingo.randomly.selecto_fragment;
import com.google.android.gms.ads.MobileAds;



public class open_app_ads extends MultiDexApplication {

    private static AppOpenManager appOpenManager;
    private static sharedpreferences sharedpreferences;
    public int w = 0;
    public int str = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(
                this, initializationStatus -> {});
        appOpenManager = new AppOpenManager(this);

       //


        sharedpreferences =new sharedpreferences(this);


        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if(w ==0) {
                    w++;
                    // new connect_to_firebase().desincrument_online();
                    if(!new sharedpreferences(open_app_ads.this).get_setting_type("user_is_not_sign_in"))
                        new connect_to_firebase().desincrument_online(open_app_ads.this);
                    sharedpreferences.put_app_state("pause");
                    str=2;
                }
                // do something here...
            }
            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }


}
