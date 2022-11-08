package com.damasingo.play_onlinge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.audio_massage;
import com.damasingo.CLASS_UTIL.match_running;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.R;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.damasingo.bluetooth_match.bluetooth_game_space;
import com.damasingo.randomly.game_space_randomly;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;



public class game_space extends AppCompatActivity implements table_play.Listener, score_fragment.Listener_score{

    FrameLayout fram_tab,fram_score;
    int rotation_degree=0;

    ImageButton btn_rotate_table,btn_send,btn_info,btn_help,btn_record,btn_cancel,btn_full,btn_only_box;
    View view_only_box;
    EditText input_msg;
    TextView txt_match_score,txt_recoed;
    ImageView img_record;
    Context context;



    Button wiffi_on,wiffi_off,wiffi_probleme;

    FragmentManager manager;
    table_play myFragment ;
    score_fragment score_fragment;

    sql_manager db_offline;
    connect_to_firebase db_online=new connect_to_firebase();

    int amount_to_play;
    Bundle bundle_come;
    public String player1,player2;
    public boolean Iam_player1,match_stop=false;
    CountDownTimer countDownTimer;

    match_running game;


    DatabaseReference databaseReference_game,databaseReference_msg,databaseReference_winner,databaseReference_connect;
    ValueEventListener valueEventListener_game,valueEventListener_msg,valueEventListener_winner,valueEventListener_connect;


    //Facebook element
   // private InterstitialAd mInterstitialAd;
    private static final  String banner_ads_code="ca-app-pub-8595134362857576/4271396899";
    private static final  String Interstitial_Ads_code="ca-app-pub-2065152596342192/1136058518";

    private RewardedInterstitialAd rewardedInterstitialAd;
    int amount_after_reward_ads=0;
    private AdView adView;
    ConstraintLayout view_ads;

    public sandbar_info snack_bar=new sandbar_info();
    public boolean snack_bar_showed=false;
    Msg_box msg_box=new Msg_box();
    boolean only_box=false;

    boolean match_start=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        //    new adapter_setting().set_setting_language(this,this);
        //}
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_main);

        

        initial_views();
        charge_player_data(getIntent().getExtras());
        start_game();

       // click_listner();



    }

    private void initial_views() {

        context=this;
        view_only_box=findViewById(R.id.view_box_only);
        btn_only_box=findViewById(R.id.btn_box_only);
        fram_score=findViewById(R.id.fragment_score_place);
        txt_match_score=findViewById(R.id.txt_match_score);
        fram_tab=findViewById(R.id.fragment_table_place);
        btn_rotate_table=findViewById(R.id.btn_rotate);
        btn_send=findViewById(R.id.btn_send);
        input_msg=findViewById(R.id.input_msg);
        wiffi_off=findViewById(R.id.btn_wifi_off);
        wiffi_on=findViewById(R.id.btn_wifi_on);
        wiffi_probleme=findViewById(R.id.btn_wifi_problem);
        btn_info=findViewById(R.id.btn_info);
        btn_help=findViewById(R.id.btn_help);
        btn_record=findViewById(R.id.btn_record_audio);
        txt_recoed=findViewById(R.id.txt_record);
        img_record=findViewById(R.id.image_record);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_full=findViewById(R.id.btn_full);
        view_ads=findViewById(R.id.view_ads);

        rotation_degree+=180;

        db_offline=new sql_manager(this);
        manager=getSupportFragmentManager();
        create_ads();



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

    private void start_game() {

        click_listner();
        myFragment=  new table_play();
        new adapter_setting().Control_navigate_bottom(game_space.this);
        manager.beginTransaction()
                .replace(R.id.fragment_table_place, myFragment,table_play.class.getName()).commit();

        score_fragment=  new score_fragment();
        manager.beginTransaction()
                .replace(R.id.fragment_score_place,score_fragment,score_fragment.class.getName()).commit();

       // manager.beginTransaction().hide(myFragment).commit();


    }

    private void create_ads(){
        adView = findViewById(R.id.ads_freinds);

       // adView.setAdUnitId(banner_ads_code);

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


          RewardedInterstitialAd.load(game_space.this, Interstitial_Ads_code,
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

    private void load_ads(){
      /*  mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
            }
        });

       */
    }

    private void show_ads(){
        if (rewardedInterstitialAd != null) {
            rewardedInterstitialAd.show(game_space.this, new OnUserEarnedRewardListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    new sound().collect(game_space.this);

                    account account=db_offline.get__account();
                    double new_money=account.getMoney()+amount_after_reward_ads;

                    new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                    });

                    account.setMoney(new_money);
                    db_offline.Update_V_A_acoount(game_space.this,account);


                    Toast.makeText(game_space.this,
                            String.format("+ %01d %2$s",amount_after_reward_ads,getResources().getString(R.string.coins)),
                            Toast.LENGTH_SHORT).show();


                    msg_box.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
                }
            });
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    public interface ok{
     void oncallback();
    }

    int role=0;
    private void listener_to_this_match(ok ok) {


        Log.e("Listner"," --------------  ");

        String player;

        if (Iam_player1){
            player="player2";
        }
        else {
            player = "player1";
        }


        final boolean[] the_first_game = {true};
        db_online.listen_to_game(player1, player2,player, new connect_to_firebase.listener_game() {
            @Override
            public void get_listener(ValueEventListener listener) {
                valueEventListener_game=listener;
            }

            @Override
            public void get_references(DatabaseReference reference) {
                databaseReference_game=reference;
            }

            @Override
            public void get_game(match_running match_running) {



                if(countDownTimer!=null) {
                    countDownTimer.cancel();
                    if(wiffi_probleme.getVisibility()==View.VISIBLE)
                        hide_this_button(wiffi_probleme);
                }

                score_fragment.millis=30000;
                game=match_running;

                setActivityBackgroundColor(role);

                if(the_first_game[0]) {
                    ok.oncallback();
                    myFragment.recieve_game(new match_running(), role);
                    score_fragment.receive("role",role,Iam_player1);
                    the_first_game[0] =false;

                    if (!Iam_player1) {
                        role = 1;
                        if(match_running.getPiece()!=0){
                            myFragment.recieve_game(match_running, role);
                            score_fragment.receive("role",role,Iam_player1);
                        }

                        return;
                    }


                }
                myFragment.recieve_game(match_running, role);
                score_fragment.receive("role",role,Iam_player1);




                if (!Iam_player1)
                    role = 1;
            }
        });

        String msg;
        if (Iam_player1){
            msg="msg2";
        }
        else {
           msg = "msg1";
        }

        db_online.listen_to_message(player1, player2, msg, new connect_to_firebase.listener2() {
            @Override
            public void get_listener(ValueEventListener listener) {
                valueEventListener_msg=listener;
            }

            @Override
            public void get_references(DatabaseReference reference) {
                databaseReference_msg=reference;
            }

            @Override
            public void get_state(Object state) {
                if(!state.equals("")) {
                    if(state instanceof String)
                        score_fragment.receive(msg, state, false);
                    else {
                        audio_massage m_audio_massage=(com.damasingo.CLASS_UTIL.audio_massage)state;
                        db_online.get_audio(game_space.this, m_audio_massage.getUrl(), msg, new aide().getCurrentUTC().getTime() + "", new connect_to_firebase.File_() {
                            @Override
                            public void on_call_back(File file) {
                                if(file!=null){
                                    m_audio_massage.setFile(file);
                                    score_fragment.receive(msg,m_audio_massage, false);
                                }
                            }
                        });
                    }
                }
            }
        });


        db_online.listen_to_winner(player1, player2, new connect_to_firebase.listener_winner() {
            @Override
            public void get_listener(ValueEventListener listener) {
                valueEventListener_winner=listener;
            }

            @Override
            public void get_references(DatabaseReference reference) {
                databaseReference_winner=reference;
            }

            @Override
            public void winner(int state) {

                if(countDownTimer!=null)
                    countDownTimer.cancel();

                switch (state){
                    case 0:
                        if(!Iam_player1){
                            account account=db_offline.get__account();
                            account.setVictoir_loss(account.getVictoir_loss()+1);
                            db_offline.Update_V_A_acoount(game_space.this,account);
                        }

                        if(!Iam_player1){
                            Handler handler=new Handler();
                            handler.postDelayed(() -> {
                                hide_element();
                                score_fragment.countDownTimer.cancel();
                                match_stop=true;
                                end_listner();
                                msg_box.match_end(rewardedInterstitialAd!=null,game_space.this, amount_to_play, player1, player2, player1, new Msg_box.go_back() {
                                    @Override
                                    public void onCallBack(int action) {
                                        if(action==2)
                                            finish();
                                        else if(action==1){
                                            replay();
                                        }else {
                                            amount_after_reward_ads=action;
                                            show_ads();
                                        }
                                    }
                                });
                            }, game.getPlace().size()*500);
                        }else {
                            hide_element();
                            score_fragment.countDownTimer.cancel();
                            match_stop=true;
                            end_listner();
                            msg_box.match_end(rewardedInterstitialAd!=null,game_space.this, amount_to_play, player1, player2, player1, new Msg_box.go_back() {
                                @Override
                                public void onCallBack(int action) {
                                    if(action==2)
                                        finish();
                                    else if(action==1){
                                        replay();
                                    }else {
                                        amount_after_reward_ads=action;
                                        show_ads();
                                    }
                                }
                            });
                        }

                        break;
                    case 1:
                        if(Iam_player1){
                            account account=db_offline.get__account();
                            account.setVictoir_loss(account.getVictoir_loss()+1);
                            db_offline.Update_V_A_acoount(game_space.this,account);
                        }

                        if(Iam_player1){
                            Handler handler=new Handler();
                            handler.postDelayed(() -> {
                                hide_element();
                                score_fragment.countDownTimer.cancel();
                                match_stop=true;
                                end_listner();
                                msg_box.match_end(rewardedInterstitialAd!=null,game_space.this, amount_to_play, player1, player2, player2, new Msg_box.go_back() {
                                    @Override
                                    public void onCallBack(int action) {
                                        if(action==2)
                                            finish();
                                        else if(action==1){
                                            replay();
                                        }else{
                                            amount_after_reward_ads=action;
                                            show_ads();
                                        }
                                    }
                                });
                            }, game.getPlace().size()*500);
                        }else {
                            hide_element();
                            score_fragment.countDownTimer.cancel();
                            match_stop=true;
                            end_listner();
                            msg_box.match_end(rewardedInterstitialAd!=null,game_space.this, amount_to_play, player1, player2, player2, new Msg_box.go_back() {
                                @Override
                                public void onCallBack(int action) {
                                    if(action==2)
                                        finish();
                                    else if(action==1){
                                        replay();
                                    }else {
                                        amount_after_reward_ads=action;
                                        show_ads();
                                    }

                                }
                            });
                        }

                        break;
                }
            }
        });


        final int[] a = {0};
        db_online.connection_trigger(new connect_to_firebase.listener_trigger() {
            @Override
            public void get_listener(ValueEventListener listener) {
                valueEventListener_connect=listener;
            }

            @Override
            public void get_references(DatabaseReference reference) {
                databaseReference_connect=reference;
            }

            @Override
            public void connect(boolean i_connect) {
                if(i_connect) {
                    if(a[0] !=0){
                    score_fragment.resume();
                    wiffi_off.setVisibility(View.GONE);
                    wiffi_on.setVisibility(View.VISIBLE);
                    hide_this_button(wiffi_on);
                    }
                    a[0]++;
                }else {
                    score_fragment.pause();
                    wiffi_off.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private int[] get_score_description(String score){

        Log.e("score","b "+score);
        String [] temp = score.split("-");
        for (String s : temp) {
            System.out.println(s);
        }

        return new int[]{Integer.parseInt(temp[0]),Integer.parseInt(temp[1])};
    }

    ArrayList<Integer> price=new ArrayList<>();
    int matchcount;
    private void replay(){
        new sound().collect(this);
        Intent table_game=getIntent();

         String a=table_game.getExtras().getString("player1");
         String b=table_game.getExtras().getString("player2");

         table_game.putExtra("player1",b);
         table_game.putExtra("player2",a);
         table_game.putExtra("match_count",matchcount+1);

        if(player1.equals(db_offline.get__account().getId())) {

            int[] sc_tab=get_score_description(new sql_manager(this).get_score_of(player2));
            sc_tab[1]++;
            score score=new score(player2, sc_tab[0]+"-"+sc_tab[1]);
            new sql_manager(this).Update_score_of(score);
            db_online.add_friends_score(this, score, etat -> {

            });
        }else {
            int[] sc_tab=get_score_description(new sql_manager(this).get_score_of(player1));
            sc_tab[1]++;
            score score=new score(player1, sc_tab[0]+"-"+sc_tab[1]);
            new sql_manager(this).Update_score_of(score);
            db_online.add_friends_score(this, score, etat -> {

            });
        }


        price.add(100);
        price.add(500);
        price.add(2500);
        price.add(10000);
        price.add(50000);
        price.add(100000);
        price.add(500000);
        price.add(2500000);
        price.add(10000000);
        account account=db_offline.get__account();
        double new_money=account.getMoney()-price.get(amount_to_play);

        db_online.update_money(account.getId(), new_money, etat -> {
            account.setMoney(new_money);
            db_offline.Update_V_A_acoount(game_space.this,account);
        });

        finish();
        startActivity(table_game);
        db_online.player_state_after_match(player1, player2, "player_1", "come", new connect_to_firebase.add_succeful() {
            @Override
            public void add_complet(boolean etat) {

            }
        });
        db_online.player_state_after_match(player1, player2, "player_2", "come", new connect_to_firebase.add_succeful() {
            @Override
            public void add_complet(boolean etat) {

            }
        });
    }
    private void end_listner() {
        databaseReference_game.removeEventListener(valueEventListener_game);
        databaseReference_msg.removeEventListener(valueEventListener_msg);
        databaseReference_winner.removeEventListener(valueEventListener_winner);
        databaseReference_connect.removeEventListener(valueEventListener_connect);
    }

    private void hide_element(){
        manager.beginTransaction().hide(myFragment).commit();
        manager.beginTransaction().hide(score_fragment).commit();
        input_msg.setVisibility(View.INVISIBLE);
        btn_send.setVisibility(View.INVISIBLE);
        btn_rotate_table.setVisibility(View.INVISIBLE);
        wiffi_probleme.setVisibility(View.GONE);
        btn_info.setVisibility(View.INVISIBLE);
        txt_match_score.setVisibility(View.INVISIBLE);
        btn_help.setVisibility(View.GONE);


        btn_cancel.setVisibility(View.INVISIBLE);
        btn_record.setVisibility(View.INVISIBLE);
        img_record.setVisibility(View.INVISIBLE);
        txt_recoed.setVisibility(View.INVISIBLE);
        btn_full.setVisibility(View.INVISIBLE);
        btn_only_box.setVisibility(View.INVISIBLE);
        view_only_box.setVisibility(View.INVISIBLE);
    }

    private void charge_player_data(Bundle bundle) {
        amount_to_play=bundle.getInt("play_to");
        bundle_come=bundle;

        player1=bundle.getString("player1");
        player2=bundle.getString("player2");
        matchcount=bundle.getInt("match_count");

        Log.e("player_1_is",player1);
        Log.e("player_2_is",player2);

      //  if(matchcount%2==0)
            Iam_player1= player1.equals(db_offline.get__account().getId());
       // else
       //     Iam_player1= !player1.equals(db_offline.get__account().getId());

        remplire_txt_match_score(amount_to_play);

    }

    @SuppressLint("DefaultLocale")
    private void remplire_txt_match_score(int amount_to_play) {
        switch (amount_to_play){
            case 0:
                txt_match_score.setText(String.format("%02d %2$s",200," "));
                break;
            case 1:
                txt_match_score.setText(String.format("%02d %2$s",1,getString(R.string.K)));
                break;
            case 2:
                txt_match_score.setText(String.format("%02d %2$s",5,getString(R.string.K)));
                break;
            case 3:
                txt_match_score.setText(String.format("%02d %2$s",20,getString(R.string.K)));
                break;
            case 4:
                txt_match_score.setText(String.format("%02d %2$s",100,getString(R.string.K)));
                break;
            case 5:
                txt_match_score.setText(String.format("%02d %2$s",200,getString(R.string.K)));
                break;
            case 6:
                txt_match_score.setText(String.format("%02d %2$s",1,getString(R.string.M)));
                break;
            case 7:
                txt_match_score.setText(String.format("%02d %2$s",5,getString(R.string.M)));
                break;
            case 8:
                txt_match_score.setText(String.format("%02d %2$s",20,getString(R.string.M)));
                break;
        }
    }


    String msg;
    @SuppressLint("ClickableViewAccessibility")
    private void click_listner() {
        btn_info.setOnClickListener(v -> {
            snack_bar.snack_bar_reglage("rule", null, game_space.this, game_space.this);
            snack_bar_showed=true;
        });
        btn_help.setOnClickListener(v -> myFragment.help_me());
        btn_rotate_table.setOnClickListener(v -> rotate_table());

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


        if(Iam_player1) {
            fram_tab.animate()
                    .setDuration(100)
                    .rotation(180)
                    .start();
            rotation_degree=(180*2);
        }


        if (Iam_player1){
            msg="msg1";
        }
        else {
            msg = "msg2";
        }


        btn_send.setOnClickListener(v -> {
            String m=input_msg.getText().toString();
            hideKeyboard(game_space.this);
            if(!m.equals("")) {
                input_msg.setText("");
                input_msg.clearFocus();
                db_online.send_msg(player1, player2, msg, m, etat -> {
                    score_fragment.receive(msg,m,false);
                });
            }
        });

        input_msg.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btn_send.performClick();
                hideKeyboard(game_space.this);
                return true;
            }
            return false;
        });

        input_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>0)
                    message_listner(3);
                else
                    message_listner(0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(check_permition_record())
                {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                {


                    start_record_audio();

                    float x = (float) 1.25;
                    float y = (float) 1.25;

                    btn_record.setScaleX(x);
                    btn_record.setScaleY(y);
                    btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table_click));
                    btn_record.setImageResource(R.drawable.voice_n);


                    message_listner(1);
                    start_record();


                    float height= (float) (getResources().getDimension(R.dimen.dimens_130px));
                    Iy=v.getY();
                    Fy= (float) (Iy+(height));

                    decalage_y=Fy-event.getRawY();
                    Iyc=v.getY()+height+getResources().getDimension(R.dimen.dimens_70px);
                    Fyc=v.getY()+height
                            +getResources().getDimension(R.dimen.dimens_70px)+getResources().getDimension(R.dimen.dimen_100_px);
                    dX = v.getX() - event.getRawX();
                    dY = v.getY() - event.getRawY();
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    if((event.getRawY() + dY)>Iy && (event.getRawY() + decalage_y)<Fyc)
                        v.animate()
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();

                   if(((event.getRawY() )>=Iyc && (event.getRawY() )<=Fyc) || (event.getRawY())>Fyc)
                   {
                       v.animate()
                               .y(Iyc-(((Fy-Iy)-(Fyc-Iyc))/2))
                               .setDuration(0)
                               .start();
                       btn_cancel.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_btn_canel));
                   }else {
                       btn_cancel.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table));
                   }

                    return true;
                }

                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    float x = 1;
                    float y = 1;

                    btn_record.setScaleX(x);
                    btn_record.setScaleY(y);
                    btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table));
                    btn_record.setImageResource(R.drawable.voice);

                    v.animate()
                            .y(Iy)
                            .setDuration(0)
                            .start();

                    if(((event.getRawY() )>=Iyc && (event.getRawY() )<=Fyc) || (event.getRawY())>Fyc)
                    {
                        recorder.stop();
                        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
                        recorder.release(); // Now the object cannot be reused
                        recorder=null;
                        Toast.makeText(game_space.this, ""+getResources().getString(R.string.cancel), Toast.LENGTH_SHORT).show();
                    }else {
                        if(time>1000)
                            stop_record();
                            //Toast.makeText(game_space.this, "send_this_audio", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(game_space.this, ""+getResources().getString(R.string.cancel), Toast.LENGTH_SHORT).show();
                    }

                    btn_cancel.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table));
                    message_listner(0);

                    if(timer!=null)
                        timer.cancel();
                }
                }
                return false;

            }
        });

    }
    float dX, dY,Iy,Fy,Iyc,Fyc,decalage_y;


    MediaRecorder recorder;

    private void start_record_audio() {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(get_path());
            recorder.prepare();
            recorder.start();

        } catch (IOException e) {
            Log.e("re",""+e.getMessage());
        }
    }

    long name_of_recorded_audio;
    private String get_path(){
        name_of_recorded_audio=new aide().getCurrentUTC().getTime();
        ContextWrapper contextWrapper =new ContextWrapper(getApplicationContext());
        File mus=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(mus,msg+"_"+name_of_recorded_audio+".mp3");
        return file.getPath();
    }

    private File get_File(){
        ContextWrapper contextWrapper =new ContextWrapper(getApplicationContext());
        File mus=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(mus,msg+"_"+name_of_recorded_audio+".mp3");
        return file;
    }

    private void stop_record(){
        try {
            recorder.stop();
            //recorder.reset();   // You can reuse the object by going back to setAudioSource() step
            recorder.release(); // Now the object cannot be reused
            recorder=null;
            btn_record.setEnabled(false);
            btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table_click));
            btn_record.setImageResource(R.drawable.uploading);

            audio_massage audio_massage_=new audio_massage("no_url",(time/1000)-1);
            audio_massage_.setFile(get_File());
            db_online.put_audio(player1, player2,msg, get_File(), new connect_to_firebase.URL() {
                @Override
                public void onCallback(String url) {
                    score_fragment.receive(msg,audio_massage_,false);
                    btn_record.setEnabled(true);
                    btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table));
                    //   btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.selector_retate_table));
                    btn_record.setImageResource(R.drawable.btn_selector_record);

                    audio_massage audio_massage=new audio_massage(url,(time/1000)-1);
                    db_online.send_msg(player1, player2, msg,audio_massage, etat -> {
                        //audio_massage.setFile(get_File());
                        //score_fragment.receive(msg,audio_massage,false);
                    });
                }
            });
        }catch (Exception e){
            Toast.makeText(this, ""+getResources().getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        }

    }

    Timer timer ;
    @SuppressLint("DefaultLocale")
    private void start_record(){
        txt_recoed.setText(String.format( " %02d : %02d ",0,0));
        time=0;
        timer=null;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){

                        int heure = (int) (time / 1000) / 60 / 60;
                        int minutes = (int) ((time / 1000) / 60)-(heure*60) ;
                        int seconds = (int) ((time / 1000) % 60);

                        txt_recoed.setText(String.format( "%02d:%02d",minutes,seconds));
                        time+=1000;
                        anim();
                    }
                });

            }
        }, 0, 1000);
    }
    int time=0;

    private boolean check_permition_record(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
            return false;
        } else {
            return true;
        }
    }


    public void anim(){
        animFade = AnimationUtils.loadAnimation(this, R.anim.fide_out_1s);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                img_record.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                img_record.setVisibility(View.INVISIBLE);
            }
        });
        img_record.startAnimation(animFade);

    }

    private void message_listner(int i) {
        switch (i){
            case 0:
                input_msg.setVisibility(View.VISIBLE);
                btn_record.setVisibility(View.VISIBLE);

                btn_send.setVisibility(View.INVISIBLE);
                img_record.setVisibility(View.INVISIBLE);
                txt_recoed.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.INVISIBLE);
                break;
            case 1:
                input_msg.setVisibility(View.INVISIBLE);
                btn_record.setVisibility(View.VISIBLE);

                btn_send.setVisibility(View.INVISIBLE);
                img_record.setVisibility(View.VISIBLE);
                txt_recoed.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                break;
            case 3:
                input_msg.setVisibility(View.VISIBLE);
                btn_record.setVisibility(View.INVISIBLE);

                btn_send.setVisibility(View.VISIBLE);
                img_record.setVisibility(View.INVISIBLE);
                txt_recoed.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.INVISIBLE);
                break;
        }
    }


    private void rotate_table() {

        fram_tab.animate()
                .setDuration(2000)
                .rotation(rotation_degree)
                .start();
        rotation_degree+=180;

        score_fragment.rotate();
    }



    int a=0;
    @Override
    public void somethingHappenedInFragment(Object ob) {
       if(ob instanceof match_running) {
           score_fragment.millis=30000;
           String player;

           int new_r;
           if (Iam_player1) {
               player = "player1";
               new_r = 1;
           } else {
               player = "player2";
               new_r = 0;
           }
           setActivityBackgroundColor(new_r);
           a=new_r;

           score_fragment.receive("role", -1, Iam_player1);
           db_online.player_game(player1, player2, player,(match_running) ob, etat -> score_fragment.receive("role", new_r, Iam_player1));
       }
       else if(ob instanceof Integer){
           score_fragment.receive("role", -1, Iam_player1);

           db_online.update_win(player1, player2,(int) ob, etat -> {

           });
       }else if(ob instanceof String){
           if(m==0) {
               table_created();
               m++;
           }
       }


    }
    int m=0;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new adapter_setting().Control_navigate_bottom(this);
        }
    }

    @Override
    public void somethingHappenedInFragment_score(String tag, Object object) {

        if(tag.equals("score_created")){
            btn_full.performClick();
            return;
        }

        score_fragment.receive("role",-1,Iam_player1);

        if((int)object==0) {
          /*  String winer;
            int win;
            if (Iam_player1) {
                win = 1;
                winer = "player2";
            } else {
                win = 0;
                winer = "player1";
            }

            end_listner();
            db_online.update_win(player1, player2, win, etat -> {
                account account = db_offline.get__account();
                account.setVictoir_loss(account.getVictoir_loss() + 1);
                db_offline.Update_V_A_acoount(game_space.this, account);

                hide_element();
                score_fragment.countDownTimer.cancel();
                match_stop = true;

                new Msg_box().match_end(game_space.this, amount_to_play, player1, player2, winer, new Msg_box.go_back() {
                    @Override
                    public void onCallBack(int action) {
                        finish();
                    }
                });

            });

           */
            myFragment.play_instead_of_me();
        }
        else {
            wiffi_probleme.setVisibility(View.VISIBLE);
            countDownTimer=new CountDownTimer(15000, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    int second = (int) (millisUntilFinished / 1000);

                    String m=getResources().getString(R.string.waiting_for_your_fr)+" "+String.format("%02d",second)
                            +" "+getResources().getString(R.string.seconde);
                    wiffi_probleme.setText(m);
                }

                @Override
                public void onFinish() {
                    int win;
                    if (Iam_player1)
                        win = 0;
                    else
                        win = 1;
                    db_online.update_win(player1, player2,win, etat -> {

                    });
                }
            }.start();
        }


    }

    public  void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        new adapter_setting().Control_navigate_bottom(game_space.this);
    }

    private void before_end_match(){
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.go_back);
        builder.setMessage(R.string.back);
        builder.setPositiveButton(R.string.go_back, (dialog, which) -> {
            end_listner();
            end_match();
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
                            end_listner();
                            end_match();
                            finish();
                        }
                    }
                });
    }

    private void end_match() {
        if(!match_stop) {
            score_fragment.countDownTimer.cancel();
            score_fragment.sound.stop_timer();

            int win;
            String child_player;
            if (Iam_player1) {
                child_player="player_1";
                win = 1;
            }else {
                child_player="player_2";
                win = 0;
            }

            hide_element();
            account account = db_offline.get__account();
            account.setVictoir_loss(account.getVictoir_loss() + 1);
            db_offline.Update_V_A_acoount(game_space.this, account);

            db_online.update_win(player1, player2, win, etat -> {
            });

            db_online.player_state_after_match(player1, player2, child_player, "exit", new connect_to_firebase.add_succeful() {
                @Override
                public void add_complet(boolean etat) {

                }
            });
        }
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



    Animation animFade;
    public void hide_this_button(final View b){
        animFade = AnimationUtils.loadAnimation(this, R.anim.fide_out_1500);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                b.setVisibility(View.GONE);
            }
        });
        b.startAnimation(animFade);

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
         show_this_view(btn_record);
         show_this_view(input_msg);
         show_this_view(txt_match_score);
         show_this_view(btn_full);
         show_this_view(btn_only_box);

         score_cretaed();
    }

    private void score_cretaed(){
        if (db_offline.get__account().getId().equals(player1)) {
            myFragment.receiveMsg(0);
            Log.e("iam the player 1","f");
        }
        else if(db_offline.get__account().getId().equals(player2)){
            myFragment.receiveMsg(1);
            Log.e("iam the player 2","f");
        }



        listener_to_this_match(new ok() {
            @Override
            public void oncallback() {
                score_fragment.receive("player1", player1,false);
                score_fragment.receive("player2", player2,false);
                score_fragment.receive("amount_to_play", amount_to_play,false);
                // setActivityBackgroundColor(0);
                if(Iam_player1) {
                    score_fragment.rotate();
                }
                match_start=true;


            }
        });


    }


    private void show_this_view(View view){


        if(view instanceof TextView)
            animFade = AnimationUtils.loadAnimation(this, R.anim.scale_500_rectangle);
        else
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
                snack_bar.action_while_playne(0,user_id,game_space.this,game_space.this);
            }else {
                snack_bar.action_while_playne(1,user_id,game_space.this,game_space.this);
            }
        }
    };
}