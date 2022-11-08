package com.damasingo;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.CLASS_UTIL.account;

import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.periodic_reward.YourService;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;

import pl.pawelkleczkowski.customgauge.CustomGauge;

import static android.content.Context.MODE_PRIVATE;


@SuppressLint("DefaultLocale")
public class fragment_add_coins extends Fragment {


    private static final long COUNT_TIME_LOW_ADS_IN_MILLIS = 18100000
            ,COUNT_TIME_LOGO_ADS_IN_MILLIS = 600000; //600000




    ImageButton btn_info;


    TextView txt_second1,txt_second2,txt_second3,txt_state1,txt_state2,txt_state3,txt_chrono_logo;
    ImageView img_state1,img_state2,img_state3,img_lock_low,img_lock_normal,img_lock_top;
    Button btn_1,btn_2,btn_3,btn_collect;
    View view_line_1,view_line_2,view_line_3;
    LottieAnimationView lot_1,lot_2,lot_3;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;




    /// low_ads ////////////////////////////
    private static final String code_rewardedAd_low="ca-app-pub-2065152596342192/6952008891";
    RewardedAd rewardedAd_low;
    private CountDownTimer mCountDownTimer_low;
    private boolean mTimerRunning_low;
    private long mTimeLeftInMillis_low;
    private long mEndTime_low;
    private int type_wait_low=0;
    /// low ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /// normal_ads ////////////////////////////
    private static final String code_rewardedAd_normal="ca-app-pub-2065152596342192/6952008891";
    RewardedAd rewardedAd_normal;
    public boolean mTimerRunning_normal;
    private int type_wait_normal=0;
    /// normal ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /// top_ads ////////////////////////////
    private static final String code_rewardedAd_top="ca-app-pub-2065152596342192/6952008891";
    RewardedAd rewardedAd_top;
    public boolean mTimerRunning_top;
    private int type_wait_top=0;
    /// top ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /// logo_ads ////////////////////////////
    private CountDownTimer mCountDownTimer_logo;
    private boolean mTimerRunning_logo;
    private long mTimeLeftInMillis_logo;
    private long mEndTime_logo;
    /// top ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\

    float dX, dY,first_X,first_Y;
    boolean touch_logo=false;
    boolean logo_is_hiden=false;
    private Animation animFade;
    String next_to_load="low";
    sql_manager db_offline;
    Context context;
    sharedpreferences msharedpreferences;
    Praincipal m_Mother_activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_coins, container, false);

        initial_view(v);
        click_listner();
        set_money_value();

        return v;
    }

    private void initial_view(View view) {
        db_offline=new sql_manager(getContext());
        msharedpreferences = new sharedpreferences(getContext());
        context=getContext();
        m_Mother_activity= (Praincipal) getActivity();

        //txt_eCPM1=view.findViewById(R.id.txt_ecpm_low_ads_valeur);
        //txt_eCPM2=view.findViewById(R.id.txt_ecpm_normal_ads_valeur);
        //txt_eCPM3=view.findViewById(R.id.txt_ecpm_top_ads_valeur);

        txt_second1=view.findViewById(R.id.txt_chrono_low_ads);
        txt_second2=view.findViewById(R.id.txt_chrono_normal_ads);
        txt_second3=view.findViewById(R.id.txt_chrono_top_ads);
        btn_1=view.findViewById(R.id.btn_low_ads);
        btn_2=view.findViewById(R.id.btn_normal_ads);
        btn_3=view.findViewById(R.id.btn_top_ads);
        view_line_1=view.findViewById(R.id.line_s_1);
        view_line_2=view.findViewById(R.id.line_s_1_normal);
        view_line_3=view.findViewById(R.id.line_s_1_top);
        lot_1=view.findViewById(R.id.lottie_low);
        lot_2=view.findViewById(R.id.lottie_normal);
        lot_3=view.findViewById(R.id.lottie_top);
        nestedScrollView=view.findViewById(R.id.sc);
        txt_state1=view.findViewById(R.id.txt_state_low);
        txt_state2=view.findViewById(R.id.txt_state_normal);
        txt_state3=view.findViewById(R.id.txt_state_top);
        img_state1=view.findViewById(R.id.img_state_low);
        img_state2=view.findViewById(R.id.img_state_normal);
        img_state3=view.findViewById(R.id.img_state_top);
        txt_chrono_logo=view.findViewById(R.id.txt_chrono_logo);
        btn_collect=view.findViewById(R.id.btn_collect);
        progressBar=view.findViewById(R.id.progressbar);

        img_lock_low=view.findViewById(R.id.img_lock_low);
        img_lock_normal=view.findViewById(R.id.img_lock_normal);
        img_lock_top=view.findViewById(R.id.img_lock_top);
        btn_info=view.findViewById(R.id.btn_info);

        String unicode = "U+1F381";
        btn_collect.setText(getResources().getString(R.string.Rewar)+"\t"+new aide().getEmojiByUnicode(Integer.parseInt(unicode.substring(2), 16)));

    }


    private void set_money_value() {
        if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in"))
            m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(getContext()).get_score_offline(),context));
        else
            m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
    }

    @Override
    public void onPause() {
        super.onPause();

        Activity activity=getActivity();

        assert activity != null;
        SharedPreferences prefs_low =  activity.getSharedPreferences("prefs_low", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = prefs_low.edit();
        editor_low.putLong("millisLeft_low", mTimeLeftInMillis_low);
        editor_low.putBoolean("timerRunning_low", mTimerRunning_low);
        editor_low.putLong("endTime_low", mEndTime_low);
        editor_low.apply();
        if (mCountDownTimer_low != null) {
            mCountDownTimer_low.cancel();
        }

        SharedPreferences prefs_next =  getActivity().getSharedPreferences("ads_role", MODE_PRIVATE);
        SharedPreferences.Editor editor_next = prefs_next.edit();
        editor_next.putString("name",next_to_load);
        editor_next.apply();

        SharedPreferences prefs_logo =  getActivity().getSharedPreferences("prefs_logo", MODE_PRIVATE);
        SharedPreferences.Editor editor_logo = prefs_logo.edit();
        editor_logo.putLong("millisLeft_logo", mTimeLeftInMillis_logo);
        editor_logo.putBoolean("timerRunning_logo", mTimerRunning_logo);
        editor_logo.putLong("endTime_logo", mEndTime_logo);
        editor_logo.apply();
        if (mCountDownTimer_logo != null) {
            mCountDownTimer_logo.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        get_time(current_time -> {

            Activity activity=getActivity();
            assert activity != null;
            SharedPreferences ads = activity.getSharedPreferences("ads_role", MODE_PRIVATE);
            String ads_name = ads.getString("name","low");


            switch (ads_name) {
                case "low":

                    on_ADS_lock("normal");
                    on_ADS_lock("top");

                    SharedPreferences prefs_low = getActivity().getSharedPreferences("prefs_low", MODE_PRIVATE);
                    mTimeLeftInMillis_low = prefs_low.getLong("millisLeft_low", COUNT_TIME_LOW_ADS_IN_MILLIS);
                    mTimerRunning_low = prefs_low.getBoolean("timerRunning_low", false);
                    updateCountDownText();
                    updateButtons();
                    if (mTimerRunning_low) {
                        mEndTime_low = prefs_low.getLong("endTime_low", 0);
                        mTimeLeftInMillis_low = mEndTime_low - current_time;
                        if (mTimeLeftInMillis_low < 0) {
                            mTimeLeftInMillis_low = 0;
                            mTimerRunning_low = false;
                            updateCountDownText();
                            updateButtons();
                        } else {
                            startTimer_low();
                        }
                    }
                    break;
                case "normal":
                    on_ADS_lock("low");
                    on_ADS_lock("top");

                    Start_load_normal_ads();

                    break;
                case "top":
                    on_ADS_lock("normal");
                    on_ADS_lock("low");

                    Start_load_top_ads();
                    break;
            }


           /* SharedPreferences prefs_normal = getActivity().getSharedPreferences("prefs_normal", MODE_PRIVATE);
            mTimeLeftInMillis_normal = prefs_normal.getLong("millisLeft_normal", COUNT_TIME_NORMAL_ADS_IN_MILLIS);
            mTimerRunning_normal = prefs_normal.getBoolean("timerRunning_normal", false);
            updateCountDownText_normal();
            updateButtons_normal();
            if (mTimerRunning_normal) {
                mEndTime_normal = prefs_normal.getLong("endTime_normal", 0);
                mTimeLeftInMillis_normal = mEndTime_normal - current_time;
                if (mTimeLeftInMillis_normal < 0) {
                    mTimeLeftInMillis_normal = 0;
                    mTimerRunning_normal = false;
                    updateCountDownText_normal();
                    updateButtons_normal();
                } else {
                    startTimer_normal();
                }
            }

            SharedPreferences prefs_top = getActivity().getSharedPreferences("prefs_top", MODE_PRIVATE);
            mTimeLeftInMillis_top = prefs_top.getLong("millisLeft_top", COUNT_TIME_TOP_ADS_IN_MILLIS);
            mTimerRunning_top = prefs_top.getBoolean("timerRunning_top", false);
            updateCountDownText_top();
            updateButtons_top();
            if (mTimerRunning_top) {
                mEndTime_top = prefs_top.getLong("endTime_top", 0);
                mTimeLeftInMillis_top = mEndTime_top - current_time;
                if (mTimeLeftInMillis_top < 0) {
                    mTimeLeftInMillis_top = 0;
                    mTimerRunning_top = false;
                    updateCountDownText_top();
                    updateButtons_top();
                } else {
                    startTimer_top();
                }
            }
            */

            SharedPreferences prefs_logo = getActivity().getSharedPreferences("prefs_logo", MODE_PRIVATE);
            mTimeLeftInMillis_logo = prefs_logo.getLong("millisLeft_logo", COUNT_TIME_LOGO_ADS_IN_MILLIS);
            mTimerRunning_logo = prefs_logo.getBoolean("timerRunning_logo", false);
            updateCountDownText_logo();
            updateButtons_logo();
            if (mTimerRunning_logo) {
                mEndTime_logo = prefs_logo.getLong("endTime_logo", 0);
                mTimeLeftInMillis_logo = mEndTime_logo - current_time;
                if (mTimeLeftInMillis_logo < 0) {
                    mTimeLeftInMillis_logo = 0;
                    mTimerRunning_logo = false;
                    updateCountDownText_logo();
                    updateButtons_logo();
                } else {
                    startTimer_logo();
                }
            }

        });
    }

    private void resume(){
       /* switch (ad){
            case "low":
                if (mTimerRunning_normal) {
                    mTimeLeftInMillis_normal = mEndTime_normal -System.currentTimeMillis();
                    if (mTimeLeftInMillis_normal < 0) {
                        mTimeLeftInMillis_normal = 0;
                        mTimerRunning_normal = false;
                        updateCountDownText_normal();
                        updateButtons_normal();
                    } else {
                        startTimer_normal();
                    }
                }
                if (mTimerRunning_top) {
                    mTimeLeftInMillis_top = mEndTime_top - System.currentTimeMillis();
                    if (mTimeLeftInMillis_top < 0) {
                        mTimeLeftInMillis_top = 0;
                        mTimerRunning_top = false;
                        updateCountDownText_top();
                        updateButtons_top();
                    } else {
                        startTimer_top();
                    }
                }
                break;
            case "normal":
                if (mTimerRunning_low) {
                    mTimeLeftInMillis_low = mEndTime_low - System.currentTimeMillis();
                    if (mTimeLeftInMillis_low < 0) {
                        mTimeLeftInMillis_low = 0;
                        mTimerRunning_low = false;
                        updateCountDownText();
                        updateButtons();
                    } else {
                        startTimer_low();
                    }
                }
                if (mTimerRunning_top) {
                    mTimeLeftInMillis_top = mEndTime_top - System.currentTimeMillis();
                    if (mTimeLeftInMillis_top < 0) {
                        mTimeLeftInMillis_top = 0;
                        mTimerRunning_top = false;
                        updateCountDownText_top();
                        updateButtons_top();
                    } else {
                        startTimer_top();
                    }
                }
                break;
            case "top":
                if (mTimerRunning_low) {
                    mTimeLeftInMillis_low = mEndTime_low - System.currentTimeMillis();
                    if (mTimeLeftInMillis_low < 0) {
                        mTimeLeftInMillis_low = 0;
                        mTimerRunning_low = false;
                        updateCountDownText();
                        updateButtons();
                    } else {
                        startTimer_low();
                    }
                }
                if (mTimerRunning_normal) {
                    mTimeLeftInMillis_normal = mEndTime_normal - System.currentTimeMillis();
                    if (mTimeLeftInMillis_normal < 0) {
                        mTimeLeftInMillis_normal = 0;
                        mTimerRunning_normal = false;
                        updateCountDownText_normal();
                        updateButtons_normal();
                    } else {
                        startTimer_normal();
                    }
                }
                break;
        }

        */
        if (mTimerRunning_logo) {
            mTimeLeftInMillis_logo = mEndTime_logo - System.currentTimeMillis();
            if (mTimeLeftInMillis_logo < 0) {
                mTimeLeftInMillis_logo = 0;
                mTimerRunning_logo = false;
                updateCountDownText_logo();
                updateButtons_logo();

            } else {
                startTimer_logo();
            }
        }


    }

    //// logo ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private void startTimer_logo() {
        get_time(current_time -> {
            mEndTime_logo = current_time + mTimeLeftInMillis_logo;
            mCountDownTimer_logo = new CountDownTimer(mTimeLeftInMillis_logo, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis_logo = millisUntilFinished;
                    updateCountDownText_logo();
                }
                @Override
                public void onFinish() {
                    mTimerRunning_logo = false;
                    updateButtons_logo();
                    updateCountDownText_logo();
                }
            }.start();
            mTimerRunning_logo = true;
            updateButtons_logo();
        });
    }
    private void resetTimer_logo() {
        mTimeLeftInMillis_logo = COUNT_TIME_LOGO_ADS_IN_MILLIS;
        updateCountDownText_logo();
        updateButtons_logo();
    }
    private void updateCountDownText_logo() {
       // progress_aniation((int)(COUNT_TIME_LOGO_ADS_IN_MILLIS-mTimeLeftInMillis_logo),
       //         (int) (COUNT_TIME_LOGO_ADS_IN_MILLIS-(mTimeLeftInMillis_logo-1000))
       // );
        if(!mTimerRunning_logo){
          //txt_chrono_logo.setText(getResources().getString(R.string.swipe));
        }else {
            int minutes = (int) (mTimeLeftInMillis_logo / 1000) / 60;
            int seconds = (int) ((mTimeLeftInMillis_logo / 1000) % 60) + (minutes * 60);
            @SuppressLint("DefaultLocale")
            String timeLeftFormatted = String.format("%02d %s", seconds, getResources().getString(R.string.seconde));
            txt_chrono_logo.setText(String.format("\t%1$s\t",timeLeftFormatted));

            progressBar.setProgress((int)(600000-mTimeLeftInMillis_logo));
        }
    }
    private void updateButtons_logo() {
        if (mTimerRunning_logo) {
            on_logo_state("wait");
        } else {
            on_logo_state("load");
        }
    }
    //// logo ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    //// low ads ///////////////////////////////////////////////
    public void Start_load_low_ads() {
        /*
        rewardedAd_low = new RewardedVideoAd(context, code_rewardedAd_low);
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e("low ads", "Rewarded video ad failed to load: " + error.getErrorMessage());
                // Handle the error.
                if (error.getErrorCode() == 2 || error.getErrorCode() == 3)
                    type_wait_low = error.getErrorCode();
                else
                    type_wait_low = 2;


                Log.e("low_ads", error.getErrorMessage());
            }


            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d("low ads", "Rewarded video ad is loaded and ready to be displayed!");
                type_wait_low = 0;
                updateButtons();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d("low ads", "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d("low ads", "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d("low ads", "Rewarded video completed!");

                // Call method to give reward
                // giveReward();
                // Handle the reward.
                //int rewardAmount = rewardItem.getAmount();
                //String rewardType = rewardItem.getType();
                save_gain("low");

                on_ADS_lock("low");
                Start_load_normal_ads();
                next_to_load = "normal";
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d("low ads", "Rewarded video ad closed!");
                resume();
                rewardedAd_low = null;

            }
        };
        rewardedAd_low.loadAd(
                rewardedAd_low.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
        /* */

        createAndLoad_low_RewardedAd(new add_Created() {
            @Override
            public void onCall_back(RewardedAd mRewaredAd) {
                rewardedAd_low = mRewaredAd;

                rewardedAd_low.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.e("low ads", "Ad was shown.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.e("low_ads", "Ad failed to show.");
                    }


                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.e("low_ads", "Ad was dismissed.");
                        rewardedAd_low = null;
                        if(next_to_load.equals("low")){
                            Start_load_low_ads();
                            on_ADS_load("low");
                        }
                    }


                });
            }
        });
    }




    public void createAndLoad_low_RewardedAd(add_Created add_created) {
        AdRequest adRequest = new AdRequest.Builder().build();


        RewardedAd.load(getContext(),  code_rewardedAd_low,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        if(loadAdError.getCode()==2 || loadAdError.getCode()==3)
                            type_wait_low=loadAdError.getCode();
                        else
                            type_wait_low=2;


                        Log.e("low_ads", loadAdError.getMessage());
                        add_created.onCall_back(null);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        type_wait_low=0;
                        updateButtons();
                        add_created.onCall_back(rewardedAd);
                        Log.d("low_ads", "Ad was loaded.");
                    }
                });




    }

    public void play_the_low_ads(){

       // rewardedAd_low.show();

      on_ADS_lock("low");
      if (rewardedAd_low != null) {
            Activity activityContext = getActivity();
            rewardedAd_low.show(activityContext, rewardItem -> {
                // Handle the reward.
                Log.d("low_ads", "The user earned the reward.");
               // int rewardAmount = rewardItem.getAmount();
               // String rewardType = rewardItem.getType();
                save_gain("low");

                Start_load_normal_ads();
                on_ADS_load("normal");
                next_to_load="normal";

            });
        } else {
            Log.d("low_ads", "The rewarded ad wasn't ready yet.");
        }

    }

    private void startTimer_low() {
        get_time(current_time -> {
            mEndTime_low = current_time + mTimeLeftInMillis_low;
            mCountDownTimer_low = new CountDownTimer(mTimeLeftInMillis_low, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis_low = millisUntilFinished;
                    updateCountDownText();
                }
                @Override
                public void onFinish() {
                    mTimerRunning_low = false;
                    updateButtons();
                }
            }.start();
            updateButtons();
        });
    }
    private void resetTimer_low() {
        mTimerRunning_low = true;
        mTimeLeftInMillis_low = COUNT_TIME_LOW_ADS_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {

        int heure = (int) (mTimeLeftInMillis_low / 1000) / 60 / 60;
        int minutes = (int) ((mTimeLeftInMillis_low / 1000) / 60)-(heure*60) ;
        int seconds = (int) ((mTimeLeftInMillis_low / 1000) % 60);

        String timeLeftFormatted;

        if(heure!=0)
             timeLeftFormatted = String.format( "%02d %s : %02d %s : %02d %s",heure,getResources().getString(R.string.h),minutes,getResources().getString(R.string.m),seconds,getResources().getString(R.string.seconde));
        else if(minutes != 0)
             timeLeftFormatted = String.format( "%02d %s : %02d %s",minutes,getResources().getString(R.string.m),seconds,getResources().getString(R.string.seconde));
        else 
            timeLeftFormatted = String.format( " %02d %s",seconds,getResources().getString(R.string.seconde));


        txt_second1.setText(String.format("\t%1$s\t",timeLeftFormatted));
    }
    private void updateButtons() {
        if (mTimerRunning_low) {
            if(type_wait_low==0)
                on_ADS_lock("low");
            else if(type_wait_low==2)
                on_ADS_lock("low");
            else if(type_wait_low==3)
                on_ADS_lock("low");
        } else {
            if(rewardedAd_low!=null) {
                on_ADS_ready("low");
            }
            else {
                on_ADS_load("low");
                Start_load_low_ads();
            }
        }
    }
    //// low ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    //// normal ads ///////////////////////////////////////////////
    public void Start_load_normal_ads(){
       /* on_ADS_load("normal");
        rewardedAd_normal = new RewardedVideoAd(context, code_rewardedAd_normal);
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e("normal ads", "Rewarded video ad failed to load: " + error.getErrorMessage());
                // Handle the error.
                if(error.getErrorCode()==2 || error.getErrorCode()==3)
                    type_wait_normal=error.getErrorCode();
                else
                    type_wait_normal=2;


                Log.e("normal_ads", error.getErrorMessage());
            }


            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d("normal ads", "Rewarded video ad is loaded and ready to be displayed!");
                type_wait_normal=0;
                updateButtons_normal();
                Log.d("normal_ads", "Ad was loaded.");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d("normal ads", "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d("normal ads", "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d("normal ads", "Rewarded video completed!");

                // Call method to give reward
                // giveReward();
                // Handle the reward.
                //int rewardAmount = rewardItem.getAmount();
                //String rewardType = rewardItem.getType();
                save_gain("normal");

                on_ADS_lock("normal");
                Start_load_top_ads();
                next_to_load="top";
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d("normal ads", "Rewarded video ad closed!");
                //resetTimer_normal();
                //startTimer_normal();

            }
        };
        rewardedAd_normal.loadAd(
                rewardedAd_normal.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
*/
       createAndLoad_normal_RewardedAd(new add_Created() {
            @Override
            public void onCall_back(RewardedAd mRewaredAd) {
                rewardedAd_normal =mRewaredAd;

                rewardedAd_normal.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.e("normal ads", "Ad was shown.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.e("normal_ads", "Ad failed to show.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.e("normal_ads", "Ad was dismissed.");
                        //resetTimer_normal();
                        //startTimer_normal();
                        resume();
                        rewardedAd_normal = null;
                        if(next_to_load.equals("normal")) {
                            Start_load_normal_ads();
                            on_ADS_load("normal");
                        }
                    }
                });
            }
        });


    }
   public void createAndLoad_normal_RewardedAd(add_Created add_created) {
        AdRequest adRequest = new AdRequest.Builder().build();


        RewardedAd.load(getContext(), code_rewardedAd_normal,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        if(loadAdError.getCode()==2 || loadAdError.getCode()==3)
                            type_wait_normal=loadAdError.getCode();
                        else
                            type_wait_normal=2;
                        //resetTimer_normal();
                        //startTimer_normal();

                        Log.e("normal_ads", loadAdError.getMessage());
                        add_created.onCall_back(null);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        type_wait_normal=0;
                        updateButtons_normal();
                        add_created.onCall_back(rewardedAd);
                        Log.d("normal_ads", "Ad was loaded.");
                    }
                });



    }


    public void play_the_normal_ads(){
       // rewardedAd_normal.show();
        on_ADS_lock("normal");
          if (rewardedAd_normal != null) {
            Activity activityContext = getActivity();
            rewardedAd_normal.show(activityContext, rewardItem -> {
                // Handle the reward.
                Log.d("normal_ads", "The user earned the reward.");
               // int rewardAmount = rewardItem.getAmount();
               // String rewardType = rewardItem.getType();
                save_gain("normal");

                Start_load_top_ads();
                on_ADS_load("top");
                next_to_load="top";
            });
        } else {
            Log.d("normal_ads", "The rewarded ad wasn't ready yet.");
        }


    }

   /* private void startTimer_normal() {
        get_time(new get_current_time() {
            @Override
            public void onCallback(long current_time) {
                mEndTime_normal = current_time + mTimeLeftInMillis_normal;
                mCountDownTimer_normal = new CountDownTimer(mTimeLeftInMillis_normal, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimeLeftInMillis_normal = millisUntilFinished;
                        updateCountDownText_normal();
                    }
                    @Override
                    public void onFinish() {
                        mTimerRunning_normal = false;
                        updateButtons_normal();
                    }
                }.start();
                updateButtons_normal();
            }
        });

    }
    private void resetTimer_normal() {
        mTimerRunning_normal = true;
        mTimeLeftInMillis_normal = COUNT_TIME_NORMAL_ADS_IN_MILLIS;
        updateCountDownText_normal();
        updateButtons_normal();
    }
    private void updateCountDownText_normal() {
        int minutes = (int) (mTimeLeftInMillis_normal / 1000) / 60;
        int seconds = (int) ((mTimeLeftInMillis_normal / 1000) % 60)+(minutes*60);
        String timeLeftFormatted = String.format( "%02d %s",seconds,getResources().getString(R.string.seconde));
        txt_second2.setText(timeLeftFormatted);
    }

    */
    private void updateButtons_normal() {
        if (mTimerRunning_normal) {
            if(type_wait_normal==0)
                on_ADS_lock("normal");
            else if(type_wait_normal==2)
                on_ADS_lock("normal");
            else if(type_wait_normal==3)
                on_ADS_lock("normal");
        } else {
            if(rewardedAd_normal!=null )
                on_ADS_ready("normal");
            else {
                on_ADS_load("normal");
                Start_load_normal_ads();
            }
        }
    }
    //// normal ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\



    //// top ads ///////////////////////////////////////////////
    public void Start_load_top_ads(){
        /* on_ADS_load("top");
        rewardedAd_top = new RewardedVideoAd(context, code_rewardedAd_top);
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e("normal ads", "Rewarded video ad failed to load: " + error.getErrorMessage());
                // Handle the error.
                if(error.getErrorCode()==2 || error.getErrorCode()==3)
                    type_wait_normal=error.getErrorCode();
                else
                    type_wait_normal=2;


                Log.e("normal_ads", error.getErrorMessage());
            }


            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d("top ads", "Rewarded video ad is loaded and ready to be displayed!");
                type_wait_top =0;
                updateButtons_top();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d("top ads", "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d("top ads", "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                // Handle the reward.
                Log.d("top_ads", "The user earned the reward.");
                //int rewardAmount = rewardItem.getAmount();
                //String rewardType = rewardItem.getType();
                save_gain("top");


                on_ADS_lock("top");
                next_to_load="low";

                resetTimer_low();
                startTimer_low();
                on_ADS_lock("low");
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d("top ads", "Rewarded video ad closed!");

            }
        };
        rewardedAd_top.loadAd(
                rewardedAd_top.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
       */
         createAndLoad_top_RewardedAd(new add_Created() {
            @Override
            public void onCall_back(RewardedAd mRewaredAd) {
                rewardedAd_top =mRewaredAd;

                rewardedAd_top.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d("top ads", "Ad was shown.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("top_ads", "Ad failed to show.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d("top_ads", "Ad was dismissed.");
                        //resetTimer_top();
                        //startTimer_top();
                        resume();
                        rewardedAd_top = null;

                        if(next_to_load.equals("top")) {
                            Start_load_top_ads();
                            on_ADS_load("top");
                        }
                    }
                });
            }
        });


    }
    public void createAndLoad_top_RewardedAd(add_Created add_created) {


        AdRequest adRequest = new AdRequest.Builder().build();


        RewardedAd.load(getContext(), code_rewardedAd_top,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        if(loadAdError.getCode()==2 || loadAdError.getCode()==3)
                            type_wait_top=loadAdError.getCode();
                        else
                            type_wait_top=2;
                        //resetTimer_top();
                        //startTimer_top();

                        Log.e("top_ads", loadAdError.getMessage());
                        add_created.onCall_back(null);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        type_wait_top=0;
                        updateButtons_top();
                        add_created.onCall_back(rewardedAd);
                        Log.d("top_ads", "Ad was loaded.");
                    }
                });





    }
    public void play_the_top_ads(){
        //rewardedAd_top.show();

        on_ADS_lock("top");
         if (rewardedAd_top != null) {
            Activity activityContext = getActivity();
            rewardedAd_top.show(activityContext, rewardItem -> {
                // Handle the reward.
                Log.d("top_ads", "The user earned the reward.");
               // int rewardAmount = rewardItem.getAmount();
               // String rewardType = rewardItem.getType();
                save_gain("top");



                next_to_load="low";

                resetTimer_low();
                startTimer_low();
                on_ADS_lock("low");
            });
        } else {
            Log.d("top_ads", "The rewarded ad wasn't ready yet.");
        }


    }



   /* private void startTimer_top() {
        get_time(new get_current_time() {
            @Override
            public void onCallback(long current_time) {
                mEndTime_top = current_time + mTimeLeftInMillis_top;
                mCountDownTimer_top = new CountDownTimer(mTimeLeftInMillis_top, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimeLeftInMillis_top = millisUntilFinished;
                        updateCountDownText_top();
                    }
                    @Override
                    public void onFinish() {
                        mTimerRunning_top = false;
                        updateButtons_top();
                    }
                }.start();
                updateButtons_top();
            }
        });

    }
    private void resetTimer_top() {
        mTimerRunning_top = true;
        mTimeLeftInMillis_top = COUNT_TIME_TOP_ADS_IN_MILLIS;
        updateCountDownText_top();
        updateButtons_top();
    }
    private void updateCountDownText_top() {
        int minutes = (int) (mTimeLeftInMillis_top / 1000) / 60;
        int seconds = (int) ((mTimeLeftInMillis_top / 1000) % 60)+(minutes*60);
        String timeLeftFormatted = String.format( "%02d %s",seconds,getResources().getString(R.string.seconde));
        txt_second3.setText(timeLeftFormatted);
    }

    */
    private void updateButtons_top() {
        if (mTimerRunning_top) {
            if(type_wait_top==0)
                on_ADS_lock("top");
            else if(type_wait_top==2)
                on_ADS_lock("top");
            else if(type_wait_top==3)
                on_ADS_lock("top");
        } else {
            if(rewardedAd_top!=null)
                on_ADS_ready("top");
            else {
                on_ADS_load("top");
                Start_load_top_ads();
            }
        }
    }
    //// top ads \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    @SuppressLint("ClickableViewAccessibility")
    private void click_listner() {




        btn_1.setOnClickListener(v -> play_the_low_ads());
        btn_2.setOnClickListener(v -> play_the_normal_ads());
        btn_3.setOnClickListener(v -> play_the_top_ads());


        btn_collect.setOnClickListener(view -> refrech_logo());
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY > oldScrollY) {
                    Log.i("place-------1", "Scroll DOWN");
                    hide_or_show_logo("hide");
                }
                if (scrollY < oldScrollY) {
                    Log.i("place-------2", "Scroll UP");
                    hide_or_show_logo("hide");
                }

                if (scrollY == 0) {
                    Log.i("place-------3", "TOP SCROLL");
                    hide_or_show_logo("show");
                }

            });
        }

      */

        btn_info.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(9);
            m_Mother_activity.press_back_tacker.add(0);
           // new Msg_box().get_info("info",nums,getContext(),(AppCompatActivity) getActivity());
            m_Mother_activity.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });
    }





    private void on_ADS_lock(String type__Ads){
        switch (type__Ads){
            case "low":
                btn_1.setEnabled(false);
                img_lock_low.setVisibility(View.VISIBLE);
                view_line_1.setVisibility(View.VISIBLE);
                //txt_second1.setVisibility(View.VISIBLE);
                lot_1.setVisibility(View.GONE);

                img_state1.setVisibility(View.GONE);
                txt_state1.setVisibility(View.GONE);

                txt_second1.setVisibility(View.INVISIBLE);
                img_lock_low.setBackground(ContextCompat.getDrawable(context,R.drawable.lock));
                if(mTimerRunning_low) {
                    txt_second1.setVisibility(View.VISIBLE);
                    img_lock_low.setBackground(ContextCompat.getDrawable(context,R.drawable.lock_clock));
                }
                break;
            case "normal":
                btn_2.setEnabled(false);
                img_lock_normal.setVisibility(View.VISIBLE);
                txt_second2.setVisibility(View.INVISIBLE);
                view_line_2.setVisibility(View.VISIBLE);
                lot_2.setVisibility(View.GONE);

                img_state2.setVisibility(View.GONE);
                txt_state2.setVisibility(View.GONE);


                break;
            case "top":
                btn_3.setEnabled(false);
                img_lock_top.setVisibility(View.VISIBLE);
                view_line_3.setVisibility(View.VISIBLE);
                txt_second3.setVisibility(View.INVISIBLE);
                lot_3.setVisibility(View.GONE);

                img_state3.setVisibility(View.GONE);
                txt_state3.setVisibility(View.GONE);
                break;
        }
    }

    private void on_ADS_ready(String type__Ads){
        switch (type__Ads){
            case "low":
                btn_1.setEnabled(true);
                txt_second1.setVisibility(View.GONE);
                img_lock_low.setVisibility(View.GONE);
                view_line_1.setVisibility(View.GONE);
                lot_1.setVisibility(View.GONE);

                img_state1.setBackground(ContextCompat.getDrawable(context,R.drawable.ads_load));
                txt_state1.setText(R.string.request_gain_an_ads);
                break;
            case "normal":
                btn_2.setEnabled(true);
                txt_second2.setVisibility(View.GONE);
                view_line_2.setVisibility(View.GONE);
                lot_2.setVisibility(View.GONE);

                img_state2.setBackground(ContextCompat.getDrawable(context,R.drawable.ads_load));
                txt_state2.setText(R.string.request_gain_an_ads);
                break;
            case "top":
                btn_3.setEnabled(true);
                txt_second3.setVisibility(View.GONE);
                view_line_3.setVisibility(View.GONE);
                lot_3.setVisibility(View.GONE);

                img_state3.setBackground(ContextCompat.getDrawable(context,R.drawable.ads_load));
                txt_state3.setText(R.string.request_gain_an_ads);
                break;
        }
    }

    private void on_ADS_load(String type__Ads){
        switch (type__Ads){
            case "low":
                btn_1.setEnabled(false);
                txt_second1.setVisibility(View.INVISIBLE);
                view_line_1.setVisibility(View.VISIBLE);
                img_lock_low.setVisibility(View.INVISIBLE);
                lot_1.setVisibility(View.VISIBLE);

                img_state1.setBackground(ContextCompat.getDrawable(context,R.drawable.info));
                txt_state1.setText(R.string.request_sent);
                img_state1.setVisibility(View.VISIBLE);
                txt_state1.setVisibility(View.VISIBLE);
                break;
            case "normal":
                btn_2.setEnabled(false);
                txt_second2.setVisibility(View.INVISIBLE);
                view_line_2.setVisibility(View.VISIBLE);
                lot_2.setVisibility(View.VISIBLE);
                img_lock_normal.setVisibility(View.INVISIBLE);

                img_state2.setBackground(ContextCompat.getDrawable(context,R.drawable.info));
                txt_state2.setText(R.string.request_sent);
                img_state2.setVisibility(View.VISIBLE);
                txt_state2.setVisibility(View.VISIBLE);
                break;
            case "top":
                btn_3.setEnabled(false);
                txt_second3.setVisibility(View.INVISIBLE);
                view_line_3.setVisibility(View.VISIBLE);
                lot_3.setVisibility(View.VISIBLE);
                img_lock_top.setVisibility(View.INVISIBLE);

                img_state3.setBackground(ContextCompat.getDrawable(context,R.drawable.info));
                txt_state3.setText(R.string.request_sent);
                img_state3.setVisibility(View.VISIBLE);
                txt_state3.setVisibility(View.VISIBLE);
                break;
        }
    }

  /*  private void on_ADS_wait(String type__Ads) {
        switch (type__Ads){
            case "low":
                btn_1.setEnabled(false);
                txt_second1.setVisibility(View.VISIBLE);
                view_line_1.setVisibility(View.VISIBLE);
                lot_1.setVisibility(View.GONE);

                img_state1.setVisibility(View.GONE);
                txt_state1.setVisibility(View.GONE);
                break;
            case "normal":
                btn_2.setEnabled(false);
                txt_second2.setVisibility(View.VISIBLE);
                view_line_2.setVisibility(View.VISIBLE);
                lot_2.setVisibility(View.GONE);

                img_state2.setVisibility(View.GONE);
                txt_state2.setVisibility(View.GONE);
                break;
            case "top":
                btn_3.setEnabled(false);
                txt_second3.setVisibility(View.VISIBLE);
                view_line_3.setVisibility(View.VISIBLE);
                lot_3.setVisibility(View.GONE);

                img_state3.setVisibility(View.GONE);
                txt_state3.setVisibility(View.GONE);
                break;
        }
    }

   */



    private void on_logo_state(String type__logo) {
        switch (type__logo){
            case "load":
                txt_chrono_logo.setVisibility(View.INVISIBLE);
                btn_collect.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setProgress(0);

                break;
            case "wait":
                txt_chrono_logo.setVisibility(View.VISIBLE);
                btn_collect.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void get_time(final get_current_time get_current_time) {

        get_current_time.onCallback(System.currentTimeMillis());
       /* if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        try {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet("https://google.com/"));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    String dateStr = response.getFirstHeader("Date").getValue();
                    //Here I do something with the Date String
                    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z",Locale.US);
                    try {
                        Date date = format.parse(dateStr);
                        Log.e("date", date.getTime() + "" + " System current second : " + System.currentTimeMillis());
                        get_current_time.onCallback(date.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());

                }
            } catch (ClientProtocolException e) {
                Log.d("Response", e.getMessage());

            } catch (IOException e) {
                Log.d("Response", e.getMessage());
                get_current_time.onCallback(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        */
    }

    private void save_gain(final String type) {
        final sql_manager db_sql=new sql_manager(getContext());
        final  account account=db_sql.get__account();
        connect_to_firebase db_firebase=new connect_to_firebase();

        switch (type){
            case "low":
                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    new sound().collect(getContext());
                    new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()+75);
                    m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(getContext()).get_score_offline(),context));
                }else {
                    account.setMoney(account.getMoney() + 75);
                    new sound().collect(getContext());
                    m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(account.getMoney(), context));
                    account.setPhoto_saved(null);
                    db_sql.Update_V_A_acoount(getContext(), account);
                    db_firebase.update_data_user(account, etat -> {

                    });
                }
                Toast.makeText(getContext(), String.format("+ %01d %2$s %3$s",75," ",getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                break;
            case "normal":
                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    new sound().collect(getContext());
                    new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()+125);
                    m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(getContext()).get_score_offline(),context));
                }else {
                    account.setMoney(account.getMoney() + 125);
                    new sound().collect(getContext());
                    m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(account.getMoney(), context));
                    account.setPhoto_saved(null);
                    db_sql.Update_V_A_acoount(getContext(), account);
                    db_firebase.update_data_user(account, etat -> {

                    });
                }
                Toast.makeText(getContext(), String.format("+ %01d %2$s %3$s",125," ",getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                break;
            case "top":
                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    new sound().collect(getContext());
                    new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()+175);
                    m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(getContext()).get_score_offline(),context));
                }else {
                    account.setMoney(account.getMoney() + 175);
                    new sound().collect(getContext());
                    m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(account.getMoney(), context));
                    account.setPhoto_saved(null);
                    db_sql.Update_V_A_acoount(getContext(), account);
                    db_firebase.update_data_user(account, etat -> {

                    });
                }
                Toast.makeText(getContext(), String.format("+ %01d %2$s %3$s",175," ",getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void refrech_logo() {
        final sql_manager db_sql=new sql_manager(getContext());
        final account account=db_sql.get__account();
        connect_to_firebase db_firebase=new connect_to_firebase();

        if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
            new sound().collect(getContext());
            new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()+25);
            m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(getContext()).get_score_offline(),context));
        }else {
            account.setMoney(account.getMoney()+25);
            new sound().collect(getContext());

            m_Mother_activity.txt_score.setText(new aide().get_the_string_compatible(account.getMoney(),context));
            account.setPhoto_saved(null);
            db_sql.Update_V_A_acoount(getContext(),account);
            db_firebase.update_data_user(account, etat -> {

            });
        }



        Toast.makeText(getContext(), String.format("+ %01d %2$s %3$s",25," ",getString(R.string.coins)), Toast.LENGTH_SHORT).show();
        context.startService(new Intent(getContext(), YourService.class));



        resetTimer_logo();
        startTimer_logo();
    }

  /*  private void hide_or_show_logo(String state){
        switch (state){
            case "hide":
                if(!logo_is_hiden){
                    hide_view_logo(progress_logo);
                    hide_view_logo(img_btn_logo_app);
                    hide_view_logo(txt_chrono_logo);
                    if(swipe_periodc_ads.getVisibility()==View.VISIBLE)
                        hide_view_logo(swipe_periodc_ads);
                    logo_is_hiden=true;
                }
                break;
            case "show":
                if(logo_is_hiden){
                    show_view_logo(progress_logo);
                    show_view_logo(img_btn_logo_app);
                    show_view_logo(txt_chrono_logo);
                    if(txt_chrono_logo.getText().equals(""))
                        show_view_logo(swipe_periodc_ads);
                    else
                        swipe_periodc_ads.setVisibility(View.INVISIBLE);
                    logo_is_hiden=false;
                }
                break;
        }
    }

    public void hide_view_logo(final View b){
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fide_out_l);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                b.setVisibility(View.INVISIBLE);
            }
        });
        b.startAnimation(animFade);

    }
    public void show_view_logo(final View b){
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fide_in_l);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                b.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {

            }
        });
        b.startAnimation(animFade);

    }

   */

    public interface get_current_time{
        void onCallback(long current_time);
    }

  /*  public void progress_aniation(int from, int to){
        ObjectAnimator animation = ObjectAnimator.ofInt(progress_logo, "Value", from,to); // see this max value coming back here, we animate towards that value
        animation.setDuration(500); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

   */


    interface add_Created{
        void onCall_back(RewardedAd mRewaredAd);
    }




}