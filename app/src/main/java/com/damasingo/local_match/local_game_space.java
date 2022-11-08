package com.damasingo.local_match;


import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.match_running;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.damasingo.play_onlinge.score_fragment;
import com.damasingo.play_onlinge.table_play;
import com.damasingo.table_play_off_line;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;



public class local_game_space extends AppCompatActivity implements table_play_off_line.Listener_off, com.damasingo.play_onlinge.score_fragment.Listener_score {

    FrameLayout fram_tab,fram_score;
    int rotation_degree=0;

    ImageButton btn_rotate_table,btn_send,btn_info,btn_help,btn_full,btn_only_box;
    View view_only_box;
    TextView txt_description;
    EditText input_msg;




    FragmentManager manager;
    table_play_off_line myFragment ;
    score_fragment score_fragment;

    sql_manager db_offline;


    boolean I_player_one;
    String player1,player2;

    long time_in;
    boolean match_start=false;
    boolean match_end=false;

    match_running game=new match_running();
    Context context;


    //Facebook element
 //   private InterstitialAd mInterstitialAd;


    private static final  String banner_ads_code="ca-app-pub-8595134362857576/3955508606";
    private static final  String Interstitial_Ads_code="ca-app-pub-2065152596342192/1136058518";
    private RewardedInterstitialAd rewardedInterstitialAd;
    private AdView adView;
    ConstraintLayout view_ads;

    public sandbar_info snack_bar=new sandbar_info();
    public boolean snack_bar_showed=false;
    Msg_box msg_box=new Msg_box();

    boolean only_box=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
       //     new adapter_setting().set_setting_language(this,this);
       // }
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_bluetooth_game_space);

        initial_views();
        start_game();


    }

    boolean scaled=false;
    private void scale(){
        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        int width = displayMetrics1.widthPixels;


        if(!scaled) {
            float a = width / this.getResources().getDimension(R.dimen.dimens_800ox);
            fram_tab.setScaleX(a);
            fram_tab.setScaleY(a);
            score_fragment.scale(width);
            btn_full.setImageResource(R.drawable.in_full);
            scaled=true;
        }else {
            float a = 1;
            fram_tab.setScaleX(a);
            fram_tab.setScaleY(a);
            score_fragment.scale((int)this.getResources().getDimension(R.dimen.dimens_800ox));
            btn_full.setImageResource(R.drawable.full);
            scaled=false;
        }
    }


    private void create_ads(){


       adView = findViewById(R.id.ads_local);

        MobileAds.initialize(this, initializationStatus -> {
        });

       AdRequest adRequest = new AdRequest.Builder().build();
       adView.loadAd(adRequest);

       adView.setAdListener(new AdListener() {
           @Override
           public void onAdLoaded() {
               view_ads.setVisibility(View.VISIBLE);
               super.onAdLoaded();
           }
       });

        RewardedInterstitialAd.load(local_game_space.this, Interstitial_Ads_code,
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;

                        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            /** Called when the ad failed to show full screen content. */
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.i("AdMob", "onAdFailedToShowFullScreenContent");
                            }

                            /** Called when ad showed the full screen content. */
                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.i("AdMob", "onAdShowedFullScreenContent");
                            }

                            /** Called when full screen content is dismissed. */
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.i("AdMob", "onAdDismissedFullScreenContent");
                            }
                        });

                        Log.e("AdMob", "onAdLoaded");
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e("AdMob", "onAdFailedToLoad");
                    }
                });

    }



    private void show_ads(){
     if (rewardedInterstitialAd != null) {
         rewardedInterstitialAd.show(local_game_space.this, new OnUserEarnedRewardListener() {
             @SuppressLint("DefaultLocale")
             @Override
             public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                  if(new sharedpreferences(local_game_space.this).get_setting_type("user_is_not_sign_in")){
                     new sound().collect(local_game_space.this);
                     new sharedpreferences(local_game_space.this).
                             update_score_offline(new sharedpreferences(local_game_space.this).get_score_offline()+25);

                      msg_box.txt_score.setText(new aide().get_the_string_compatible(
                              new sharedpreferences(local_game_space.this).get_score_offline(),context));

                  }else {
                      new sound().collect(local_game_space.this);

                      account account = db_offline.get__account();
                      double new_money = account.getMoney() + 25;

                      new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                      });

                      account.setMoney(new_money);
                      db_offline.Update_V_A_acoount(local_game_space.this, account);

                      msg_box.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));

                  }


                 Toast.makeText(local_game_space.this,
                         String.format("+ %01d %2$s",25,getResources().getString(R.string.coins)),
                         Toast.LENGTH_SHORT).show();


             }
         });
     } else {
         Log.d("TAG", "The interstitial ad wasn't ready yet.");
     }
    }

    private void start_game() {
        myFragment=  new table_play_off_line();
        new adapter_setting().Control_navigate_bottom(local_game_space.this);
        manager.beginTransaction()
                .replace(R.id.fragment_table_place, myFragment,table_play.class.getName()).commit();
        click_listner();

        score_fragment=  new score_fragment();
        manager.beginTransaction()
                .replace(R.id.fragment_score_place,score_fragment,score_fragment.class.getName()).commit();

    }

    Animation animFade;
    private void show_this_view(View view){


        animFade = AnimationUtils.loadAnimation(this, R.anim.scale_500);
        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animFade);
    }

    private void click_listner() {


        btn_info.setOnClickListener(v -> {
            snack_bar.snack_bar_reglage("rule", null, local_game_space.this, local_game_space.this);
            snack_bar_showed=true;
        });
        btn_rotate_table.setOnClickListener(v -> rotate_table());

        btn_help.setOnClickListener(v -> myFragment.help_me());

        btn_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scale();
            }
        });

        view_only_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_only_box.performClick();
            }
        });


        btn_only_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(only_box){
                    only_box=false;
                    view_only_box.setVisibility(View.GONE);
                }else {
                    only_box=true;
                    view_only_box.setVisibility(View.VISIBLE);
                }

                setActivityBackgroundColor(a);
            }
        });

        if(I_player_one) {
            fram_tab.animate()
                    .setDuration(100)
                    .rotation(180)
                    .start();
            rotation_degree=(180*2);
        }


    }

    private void initial_views() {
        time_in=new aide().getCurrentUTC().getTime();
        context= this;

        view_only_box=findViewById(R.id.view_box_only);
        btn_only_box=findViewById(R.id.btn_box_only);
        fram_tab=findViewById(R.id.fragment_table_place);
        fram_score=findViewById(R.id.fragment_score_place);
        btn_rotate_table=findViewById(R.id.btn_rotate);
        btn_send=findViewById(R.id.btn_send);
        input_msg=findViewById(R.id.input_msg);
        txt_description=findViewById(R.id.txt_description);
        btn_info=findViewById(R.id.btn_info);
        btn_help=findViewById(R.id.btn_help);
        btn_full=findViewById(R.id.btn_full);
        view_ads=findViewById(R.id.view_ads);

        rotation_degree+=180;

        db_offline=new sql_manager(this);
        manager=getSupportFragmentManager();


    }

    private void hide_element(){
        manager.beginTransaction().hide(myFragment).commit();
        manager.beginTransaction().hide(score_fragment).commit();
        input_msg.setVisibility(View.INVISIBLE);
        btn_send.setVisibility(View.INVISIBLE);
        btn_rotate_table.setVisibility(View.INVISIBLE);
        btn_info.setVisibility(View.INVISIBLE);
        btn_help.setVisibility(View.INVISIBLE);
        btn_full.setVisibility(View.INVISIBLE);
        btn_only_box.setVisibility(View.INVISIBLE);
        view_only_box.setVisibility(View.INVISIBLE);
    }

    private void rotate_table() {
        fram_tab.animate()
                .setDuration(2000)
                .rotation(rotation_degree)
                .start();
        rotation_degree+=180;

        score_fragment.rotate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new adapter_setting().Control_navigate_bottom(this);
        }
    }

    private void wino(int i){
       //Handler handler=new Handler();
       //handler.postDelayed(() -> {
       //}, game.getPlace().size()*500);

        match_end=true;
        hide_element();
        score_fragment.countDownTimer.cancel();
        msg_box.match_local_end(rewardedInterstitialAd!=null,local_game_space.this, "" + i, new Msg_box.go_back() {
            @Override
            public void onCallBack(int action) {
                switch (action){
                    case 0:
                        show_ads();
                        break;
                    case 1:

                        double d;
                        if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in")) {
                            d=new sharedpreferences(context).get_score_offline();
                        }else {
                            d=new sql_manager(context).get__account().getMoney();
                        }

                        if(d>=25) {
                            if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in")){
                                new sound().collect(context);
                                new sharedpreferences(context).update_score_offline(new sharedpreferences(context).get_score_offline()-25);
                            }else {
                                new sound().collect(context);
                                account account = db_offline.get__account();
                                double new_money = account.getMoney() - 25;
                                new connect_to_firebase().update_money(account.getId(), new_money, etat -> {
                                });
                                account.setMoney(new_money);
                                db_offline.Update_V_A_acoount(context, account);
                            }
                            Toast.makeText(local_game_space.this, String.format("- %01d %2$s",25,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();

                            finish();
                            startActivity(new Intent(local_game_space.this, local_game_space.class));
                        }else
                        {
                            String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",25)
                                    +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                            Toast.makeText(local_game_space.this,m, Toast.LENGTH_LONG).show();
                            new vibrateur(local_game_space.this).vibrate(100);
                        }
                        break;
                    case 2:
                        finish();
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(match_start) {
           // if (snack_bar_showed)
           //     snack_bar.dimiss_snackbar_bar(this);
           // else
                before_end_match();
        }
    }

    private void before_end_match(){
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.go_back);
        builder.setMessage(R.string.back);
        builder.setPositiveButton(R.string.go_back, (dialog, which) -> {
            score_fragment.countDownTimer.cancel();
            score_fragment.sound.stop_timer();
            finish();
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.show();

       */

        new Msg_box().oui_non_alert(context.getResources().getString(R.string.go_back)
                ,context.getResources().getString(R.string.back),
                context.getResources().getString(R.string.go_back),
                context.getResources().getString(R.string.no),
                context, new Msg_box.go_back() {
            @Override
            public void onCallBack(int action) {
                if(action==0){
                    score_fragment.countDownTimer.cancel();
                    score_fragment.sound.stop_timer();
                    finish();
                }
            }
        });
    }




    @Override
    public void somethingHappenedInFragment_score(String tag, Object object) {

        if(tag.equals("time")) {
            score_fragment.receive("role", -1, I_player_one);
            myFragment.play_instead_of_me();
            //if(I_player_one && (int) object!=0)
            //    myFragment.play_instead_of_me();
            //else  if(!I_player_one && (int) object==0)
            //    myFragment.play_instead_of_me();
            //else
            //    wino((int) object);
        }else if(tag.equals("score_created")){
            score_cretaed();
        }

    }

    boolean the_first_order=true;
    int a=0;
    boolean b;
    @Override
    public void somethingHappenedInFragment(String tag, int value) {
        if(tag.equals("role")) {
            score_fragment.millis = 30000;
            if (!match_end) {
                score_fragment.receive("role", value, value == 0);
                setActivityBackgroundColor(value);
                a=value;
            }
        } else if(tag.equals("table_created")){
            table_created();
        }else {
            Handler handler=new Handler();
            handler.postDelayed(() -> wino(value), 620);
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    public void setActivityBackgroundColor(int player) {
        int color_start;
        int color_end;
        if(player==0) {
            color_end = R.color.color_p_1;
            color_start= R.color.color_p_2;
        }else {
            color_end = R.color.color_p_2;
            color_start= R.color.color_p_1;

        }

        View view = this.getWindow().getDecorView();
        if(st==0) {
            if(!only_box)
                view.setBackgroundColor(getResources().getColor(color_end));
            else
                view.setBackgroundColor(getResources().getColor(R.color.black));
            st++;
        }else
            change_color(view,color_start,color_end);


    }
    int st=0;

    public void change_color(View view,int id_color_start,int id_color_end){
        int colorFrom = getResources().getColor(id_color_start);
        int colorTo = getResources().getColor(id_color_end);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(1000); // milliseconds
        if(!only_box)
            colorAnimation.addUpdateListener(animator -> view.setBackgroundColor((int) animator.getAnimatedValue()));
        else
            view.setBackgroundColor(getResources().getColor(R.color.black));
       //colorAnimation.addUpdateListener(animator -> view.setBackgroundColor((int) animator.getAnimatedValue()));
        colorAnimation.start();
    }


    private void table_created(){
        show_this_view(btn_rotate_table);
        show_this_view(btn_help);
        show_this_view(btn_info);
        show_this_view(btn_full);
        show_this_view(btn_only_box);

        match_start=true;
        create_ads();
    }

    private void score_cretaed(){

        score_fragment.receive("player1", player1, false);
        score_fragment.receive("player2", player2, false);

        the_first_order=false;
        btn_full.performClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver((mMessageReceiver),
                new IntentFilter("play")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String user_id=intent.getExtras().getString("id");
            if(intent.getExtras().getInt("tab")==0){
                snack_bar.action_while_playne(0,user_id,local_game_space.this,local_game_space.this);
            }else {
                snack_bar.action_while_playne(1,user_id,local_game_space.this,local_game_space.this);
            }
        }
    };
}