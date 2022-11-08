package com.damasingo.bluetooth_match;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.audio_massage;
import com.damasingo.CLASS_UTIL.conected_bluetouth_ob;
import com.damasingo.CLASS_UTIL.match_running;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.CLASS_UTIL.winoo;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.R;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.damasingo.play_onlinge.game_space;
import com.damasingo.play_onlinge.score_fragment;
import com.damasingo.play_onlinge.table_play;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;



public class bluetooth_game_space extends AppCompatActivity implements table_play.Listener, com.damasingo.play_onlinge.score_fragment.Listener_score {

    FrameLayout fram_tab,fram_score;
    int rotation_degree=0;

    ImageButton btn_rotate_table,btn_send,btn_info,btn_help,btn_full,btn_only_box;
    View view_only_box;
    TextView txt_description;
    EditText input_msg;
    TextView txt_match_score;//txt_recoed;
    //ImageView img_record;
    Button btn_retry;
    Context context;


    FragmentManager manager;
    table_play myFragment ;
    score_fragment score_fragment;

    sql_manager db_offline;


    boolean I_player_one;
    String player1,player2;
    BluetoothDevice bluetoothDevice;

    BluetoothChatService mbluetoothChatService;
    long time_in;
    boolean match_start=false;


    //Facebook element
   //private InterstitialAd mInterstitialAd;

    private static final  String banner_ads_code="ca-app-pub-8595134362857576/8086325301";
    private static final  String Interstitial_Ads_code="ca-app-pub-8595134362857576/2931885755";
    private RewardedInterstitialAd rewardedInterstitialAd;
    private AdView adView;
    ConstraintLayout view_ads;

    public sandbar_info snack_bar=new sandbar_info();
    public boolean snack_bar_showed=false;

    match_running game=new match_running();

    Animation animFade;
    Msg_box msg_box=new Msg_box();

    boolean only_box=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
       //    new adapter_setting().set_setting_language(this,this);
       //}
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_bluetooth_game_space);

        initial_views();
        charge_player_data(getIntent().getExtras());
        click_listner();
        before_playing();
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
        adView = findViewById(R.id.ads_bluetooth);
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

        RewardedInterstitialAd.load(bluetooth_game_space.this, Interstitial_Ads_code,
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
            rewardedInterstitialAd.show(bluetooth_game_space.this, new OnUserEarnedRewardListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    if(new sharedpreferences(bluetooth_game_space.this).get_setting_type("user_is_not_sign_in")){
                        new sound().collect(bluetooth_game_space.this);
                        new sharedpreferences(bluetooth_game_space.this).update_score_offline(
                                new sharedpreferences(bluetooth_game_space.this).get_score_offline()+75);

                        msg_box.txt_score.setText(new aide().get_the_string_compatible(
                                new sharedpreferences(bluetooth_game_space.this).get_score_offline(),context));

                    }else {
                           new sound().collect(bluetooth_game_space.this);

                           account account = db_offline.get__account();
                           double new_money = account.getMoney() + 75;

                           new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                           });

                           account.setMoney(new_money);
                           db_offline.Update_V_A_acoount(bluetooth_game_space.this, account);

                        msg_box.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));

                    }

                    Toast.makeText(bluetooth_game_space.this,
                            String.format("+ %01d %2$s",75,getResources().getString(R.string.coins)),
                            Toast.LENGTH_SHORT).show();


                }
            });
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    private void before_playing(){
        mbluetoothChatService=new BluetoothChatService(mHandler);
        start_connection();
    }

    @SuppressLint({"HardwareIds", "DefaultLocale"})
    private void how_I_am(long my_friends_time){
        if(match_count%2==0){
        if(time_in<=my_friends_time ){
            I_player_one=true;

            player1=BluetoothAdapter.getDefaultAdapter().getAddress();
            player2=bluetoothDevice.getAddress();
        }else {
            I_player_one=false;
            player2=BluetoothAdapter.getDefaultAdapter().getAddress();
            player1=bluetoothDevice.getAddress();
        }
        }else {
            if(time_in>=my_friends_time ){
                I_player_one=true;

                player1=BluetoothAdapter.getDefaultAdapter().getAddress();
                player2=bluetoothDevice.getAddress();
            }else {
                I_player_one=false;
                player2=BluetoothAdapter.getDefaultAdapter().getAddress();
                player1=bluetoothDevice.getAddress();
            }
        }

        hide__this_view(txt_description, new ok() {
            @Override
            public void oncallback() {

            }
        });


        if (I_player_one){
            msg="msg1";
        }
        else {
            msg = "msg2";
        }


        if(I_player_one) {
            fram_tab.animate()
                    .setDuration(100)
                    .rotation(180)
                    .start();
            rotation_degree=(180*2);
        }
        start_game();
    }

    public interface ok{
        void oncallback();
    }
    private void hide__this_view(View view,ok ok) {

        animFade = AnimationUtils.loadAnimation(this, R.anim.scale_out_500);

            animFade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {

                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ok.oncallback();
                    view.setVisibility(View.GONE);
                }
            });
            view.startAnimation(animFade);

    }

    private void show_this_view(View view) {


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

    String msg;
    @SuppressLint("ClickableViewAccessibility")
    private void click_listner() {


        btn_retry.setOnClickListener(v -> {
            Intent intent = getIntent();
            mbluetoothChatService.stop();
            match_start=true;
            finish();
            startActivity(intent);
        });

        btn_info.setOnClickListener(v -> {
            snack_bar.snack_bar_reglage("rule", null, bluetooth_game_space.this, bluetooth_game_space.this);
            snack_bar_showed=true;
        });


        btn_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scale();
            }
        });
        btn_rotate_table.setOnClickListener(v -> rotate_table());

        btn_help.setOnClickListener(v -> myFragment.help_me());


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


        btn_send.setOnClickListener(v -> {


            if (I_player_one){
                msg="msg1";
            }
            else {
                msg = "msg2";
            }


            String m=input_msg.getText().toString();
            hideKeyboard(bluetooth_game_space.this);
            if(!m.equals("")) {
                input_msg.setText("");
                input_msg.clearFocus();

                String mmessageText=m+"";
                byte[] bytes=mmessageText.getBytes(Charset.defaultCharset());
                mbluetoothChatService.write(bytes);

                score_fragment.receive(msg,m,false);
               // Handler handler=new Handler();
               // handler.postDelayed(() -> btn_send.setEnabled(true), 3500);
            }
        });

        input_msg.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btn_send.performClick();
                hideKeyboard(bluetooth_game_space.this);
                return true;
            }
            return false;
        });

       //input_msg.addTextChangedListener(new TextWatcher() {
       //    @Override
       //    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       //    }

       //    @Override
       //    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       //        if(i2>0)
       //            message_listner(3);
       //        else
       //            message_listner(0);
       //    }

       //    @Override
       //    public void afterTextChanged(Editable editable) {

       //    }
       //});



       /* btn_record.setOnTouchListener(new View.OnTouchListener() {
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
                        btn_record.setBackground(ContextCompat.getDrawable(bluetooth_game_space.this,R.drawable.bg_rotate_table_click));
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
                            btn_cancel.setBackground(ContextCompat.getDrawable(bluetooth_game_space.this,R.drawable.bg_btn_canel));
                        }else {
                            btn_cancel.setBackground(ContextCompat.getDrawable(bluetooth_game_space.this,R.drawable.bg_rotate_table));
                        }

                        return true;
                    }

                    else if(event.getAction() == MotionEvent.ACTION_UP)
                    {
                        float x = 1;
                        float y = 1;

                        btn_record.setScaleX(x);
                        btn_record.setScaleY(y);
                        btn_record.setBackground(ContextCompat.getDrawable(bluetooth_game_space.this,R.drawable.bg_rotate_table));
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
                            Toast.makeText(bluetooth_game_space.this, "cancel", Toast.LENGTH_SHORT).show();
                        }else {
                            if(time>1000)
                                stop_record();
                                //Toast.makeText(game_space.this, "send_this_audio", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(bluetooth_game_space.this, "Less than second", Toast.LENGTH_SHORT).show();
                        }

                        btn_cancel.setBackground(ContextCompat.getDrawable(bluetooth_game_space.this,R.drawable.bg_rotate_table));
                        message_listner(0);

                        if(timer!=null)
                            timer.cancel();
                    }
                }
                return false;

            }
        });

        */
    }
    float dX, dY,Iy,Fy,Iyc,Fyc,decalage_y;

/*
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
        recorder.stop();
        //recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release(); // Now the object cannot be reused
        recorder=null;
       // btn_record.setEnabled(false);
       // btn_record.setBackground(ContextCompat.getDrawable(bluetooth_game_space.this,R.drawable.bg_rotate_table_click));
       // btn_record.setImageResource(R.drawable.uploading);


        audio_massage audio_massage_=new audio_massage("no_url",(time/1000)-1);
        audio_massage_.setFile(get_File());
       // db_online.put_audio(player1, player2,msg, get_File(), new connect_to_firebase.URL() {
       //     @Override
       //     public void onCallback(String url) {
                score_fragment.receive(msg,audio_massage_,false);

               // btn_record.setEnabled(true);
               // btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.bg_rotate_table));
               // //   btn_record.setBackground(ContextCompat.getDrawable(game_space.this,R.drawable.selector_retate_table));
               // btn_record.setImageResource(R.drawable.btn_selector_record);

               // audio_massage audio_massage=new audio_massage(url,(time/1000)-1);
                //db_online.send_msg(player1, player2, msg,audio_massage, etat -> {
                //    //audio_massage.setFile(get_File());
                //    //score_fragment.receive(msg,audio_massage,false);
                //});
       //     }
       // });

        //audio_massage_.setBase_64(to_base_64(audio_massage_.getFile()));

        try {
            mbluetoothChatService.write(toByteArray(audio_massage_));
        } catch (IOException e) {
            e.printStackTrace();
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

 */

    private void initial_views() {
        time_in=new aide().getCurrentUTC().getTime();
        context=this;

        view_only_box=findViewById(R.id.view_box_only);
        btn_only_box=findViewById(R.id.btn_box_only);
        txt_description=findViewById(R.id.txt_description);
        fram_score=findViewById(R.id.fragment_score_place);
        fram_tab=findViewById(R.id.fragment_table_place);
        btn_rotate_table=findViewById(R.id.btn_rotate);
        btn_send=findViewById(R.id.btn_send);
        input_msg=findViewById(R.id.input_msg);
        btn_info=findViewById(R.id.btn_info);
        btn_retry=findViewById(R.id.retry);
        btn_help=findViewById(R.id.btn_help);
        btn_full=findViewById(R.id.btn_full);
        view_ads=findViewById(R.id.view_ads);

       //btn_record=findViewById(R.id.btn_record_audio);
       //txt_recoed=findViewById(R.id.txt_record);
       //img_record=findViewById(R.id.image_record);
       //btn_cancel=findViewById(R.id.btn_cancel);

        rotation_degree+=180;

        db_offline=new sql_manager(this);
        manager=getSupportFragmentManager();

    }

    int match_count;
    private void charge_player_data(Bundle bundle) {
        match_count=bundle.getInt("count");
        String address=bundle.getString("address");
        for(BluetoothDevice b:BluetoothAdapter.getDefaultAdapter().getBondedDevices()){
            if(b.getAddress().equals(address))
                bluetoothDevice=b;
        }


    }

    private void start_connection() {
        StartBTconnection(bluetoothDevice);
    }

    private void StartBTconnection(BluetoothDevice device){
        mbluetoothChatService.connect(device,true);
    }

    private void rotate_table() {
        fram_tab.animate()
                .setDuration(2000)
                .rotation(rotation_degree)
                .start();
        rotation_degree+=180;

        score_fragment.rotate();
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

    private void start_game() {
        myFragment=  new table_play();
        new adapter_setting().Control_navigate_bottom(bluetooth_game_space.this);
        manager.beginTransaction()
                .replace(R.id.fragment_table_place, myFragment,table_play.class.getName()).commit();

        score_fragment=  new score_fragment();
        manager.beginTransaction()
                .replace(R.id.fragment_score_place,score_fragment,score_fragment.class.getName()).commit();


       // manager.beginTransaction().hide(myFragment).commit();



    }

    int role=0;
    private void game_come(match_running match_running){
            score_fragment.millis = 30000;
            game = match_running;
            myFragment.recieve_game(match_running, role);
            score_fragment.receive("role", role, I_player_one);
            setActivityBackgroundColor(role);

           // if (myFragment.isHidden()) {
           //     manager.beginTransaction().show(myFragment).commit();
//
           //
           // }

            if (!I_player_one)
                role = 1;
    }

    private void message_come(Object message){
        String msg;
        if (I_player_one){
            msg="msg2";
        }
        else {
            msg = "msg1";
        }

        if(!message.equals(""))
            score_fragment.receive(msg,message,false);
    }

    /////////////////////////////////////////
    public  void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        new adapter_setting().Control_navigate_bottom(bluetooth_game_space.this);
    }

    int a=0;
    @Override
    public void somethingHappenedInFragment(Object ob) {
        if(ob instanceof match_running) {
            score_fragment.millis=30000;

            int new_r;
            if (I_player_one) {
                new_r = 1;
            } else {
                new_r = 0;
            }
            setActivityBackgroundColor(new_r);
            a=new_r;

            score_fragment.receive("role", new_r, I_player_one);

            try {
                mbluetoothChatService.write(toByteArray(ob));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(ob instanceof Integer){
           // String mmessageText=(int)ob+"";
           // byte[] bytes=mmessageText.getBytes(Charset.defaultCharset());
            winoo wi=new winoo((int)ob);
            try {
                mbluetoothChatService.write(toByteArray(wi));
            } catch (IOException e) {
                e.printStackTrace();
            }
            wino((int)ob);
        }else if(ob instanceof String){
            if(m==0) {
                table_created();
                m++;
            }
        }
    }
    int m=0;

    @Override
    public void somethingHappenedInFragment_score(String tag, Object object) {

        if(tag.equals("score_created")){
            btn_full.performClick();
            return;
        }

        score_fragment.receive("role",-1,I_player_one);
        myFragment.play_instead_of_me();
      // if((int)object==0) {
      //     if(I_player_one)
      //         myFragment.play_instead_of_me();
      // }else {
      //     if(I_player_one)
      //         myFragment.play_instead_of_me();
      // }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new adapter_setting().Control_navigate_bottom(this);
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            btn_retry.setVisibility(View.GONE);
                            btn_retry.setEnabled(false);
                            txt_description.setText(R.string.Connected_successful);
                            String mmessageText=time_in+"";
                            conected_bluetouth_ob ob;
                            ob=new conected_bluetouth_ob(mmessageText,match_count);
                            //byte[] bytes=mmessageText.getBytes(Charset.defaultCharset());
                            byte[] bytes= new byte[0];
                            try {
                                bytes = toByteArray(ob);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mbluetoothChatService.write(bytes);
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            String s=getString(R.string.try_to_C)+bluetoothDevice.getName();
                            txt_description.setText(s);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                            String d=getString(R.string.waiting)+bluetoothDevice.getName()+getString(R.string.to_Come);
                            txt_description.setText(d);

                            if(!match_start) {
                                String v=btn_retry.getText() + " " + bluetoothDevice.getName();
                                btn_retry.setText(v);
                                btn_retry.setVisibility(View.VISIBLE);
                            }
                        case BluetoothChatService.STATE_NONE:

                            break;
                    }
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                   /* // construct a string from the valid bytes in the buffer
                    if(readBuf!=null) {

                        String readMessage = new String(readBuf, 0, msg.arg1);
                        Log.e("read in",""+readMessage);

                        message_come(readMessage);
                    }
                    */
                    if(readBuf!=null) {

                        if(toObject(readBuf) instanceof match_running){
                            match_running match_running = (match_running) toObject(readBuf);
                            game_come(match_running);
                        }else if(toObject(readBuf) instanceof audio_massage){
                            audio_massage audio_massage = (audio_massage) toObject(readBuf);

                         //   audio_massage.setFile(From_base_64(audio_massage.getBase_64()));
                            message_come(audio_massage);
                        }else if(toObject(readBuf) instanceof conected_bluetouth_ob){
                            conected_bluetouth_ob ob = (conected_bluetouth_ob) toObject(readBuf);
                            long l = Long.parseLong(ob.getTime_in());
                            if(ob.getType_match()!=0)
                                match_count=ob.getType_match();
                            how_I_am(l);
                        }else if(toObject(readBuf) instanceof winoo){
                            winoo ob = (winoo) toObject(readBuf);
                            wino(ob.getWino());
                        }
                        else {
                            String readMessage = new String(readBuf, 0, msg.arg1);
                            message_come(readMessage);
                        }

                    }
                    break;
            }
        }
    };


    private void wino(int i){
       String win;
        if(i==0)
            win=player1;
        else
            win=player2;

        mbluetoothChatService.stop();
        Handler handler=new Handler();
        handler.postDelayed(() -> {
            hide_element();
            score_fragment.countDownTimer.cancel();
            msg_box.match_bluettoth_end(rewardedInterstitialAd!=null,bluetooth_game_space.this, player1, player2, win, new Msg_box.go_back() {
                @Override
                public void onCallBack(int action) {
                    switch (action){
                        case 0:
                            show_ads();
                            break;
                        case 1:
                            Context context=bluetooth_game_space.this;

                            double d;
                            if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in")) {
                                d=new sharedpreferences(context).get_score_offline();
                            }else {
                                d=new sql_manager(context).get__account().getMoney();
                            }

                            if(d>=75) {
                                if(BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                                    finish();
                                    Intent table_game = getIntent();
                                    table_game.putExtra("count", match_count+1);
                                    startActivity(table_game);
                                }else {
                                    Toast.makeText(context, ""+context.getResources().getString(R.string.turn_on_bluetooth), Toast.LENGTH_LONG).show();
                                    new vibrateur(context).vibrate(100);
                                }
                            }else
                            {
                                @SuppressLint("DefaultLocale")
                                String m=context.getResources().getString(R.string.bluetooth)+" "+String.format("%02d",75)
                                        +" "+context.getResources().getString(R.string.coins)+" "+context.getResources().getString(R.string.tocollect);

                                Toast.makeText(context,m, Toast.LENGTH_LONG).show();
                                new vibrateur(context).vibrate(100);

                            }
                            break;
                        case 2:
                            finish();
                            break;
                    }
                }
            });
        }, game.getPlace().size()*500);
    }

    public static byte[] toByteArray(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out;
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return bos.toByteArray();
        }
        // ignore close exception
    }

    public static Object toObject(byte[] bytes) {

        ObjectInputStream ois ;

        try
        {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        }
        catch(Exception ex)
        {
            Log.e("Bluetooth", "Cast exception at receiving end ...");
        }

        return null;
    }

    @Override
    public void onBackPressed() {
          // if (snack_bar_showed)
          //     snack_bar.dimiss_snackbar_bar(this);
          // else
                before_end_match();
    }

    private void before_end_match(){

      /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.go_back);
        if(match_start)
            builder.setMessage(R.string.back);
        else
            builder.setMessage(R.string.end);
        builder.setPositiveButton(R.string.go_back, (dialog, which) -> {
            if(match_start)
                end_match();
            mbluetoothChatService.stop();
            finish();
            match_start=true;
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.show();

       */
        String message;

        if(match_start)
            message =context.getResources().getString(R.string.back);
        else
            message =context.getResources().getString(R.string.end);


        new Msg_box().oui_non_alert(context.getResources().getString(R.string.go_back)
                ,message,
                context.getResources().getString(R.string.go_back),
                context.getResources().getString(R.string.no),
                context, new Msg_box.go_back() {
                    @Override
                    public void onCallBack(int action) {
                        if(action==0){
                            if(match_start)
                                end_match();
                            mbluetoothChatService.stop();
                            finish();
                            match_start=true;
                        }
                    }
                });
    }

    private void end_match() {
        score_fragment.countDownTimer.cancel();
        score_fragment.sound.stop_timer();
        int win;
        if (I_player_one)
            win = 1;
        else
            win = 0;
        //String mmessageText=win+"";
        //byte[] bytes=mmessageText.getBytes(Charset.defaultCharset());
        winoo wi=new winoo((int)win);
        try {
            mbluetoothChatService.write(toByteArray(wi));
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  mbluetoothChatService.write(bytes);
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!match_start){
            mbluetoothChatService.stop();
            finish();
        }
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
        //show_this_view(btn_record);
        show_this_view(btn_send);
        show_this_view(input_msg);
        show_this_view(btn_full);
        show_this_view(btn_only_box);
        score_cretaed();






        if(new sharedpreferences(this).get_setting_type("user_is_not_sign_in")){
            new sound().collect(this);
            new sharedpreferences(this).update_score_offline(new sharedpreferences(this).get_score_offline()-75);
        }else {
            new sound().collect(bluetooth_game_space.this);
            account account = db_offline.get__account();
            double new_money = account.getMoney() - 75;
            new connect_to_firebase().update_money(account.getId(), new_money, etat -> {
            });
            account.setMoney(new_money);
            db_offline.Update_V_A_acoount(bluetooth_game_space.this, account);
        }


        Toast.makeText(bluetooth_game_space.this, String.format("- %01d %2$s",75,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
        // Handler handler=new Handler();
        // handler.postDelayed(() -> {
        game_come(game);
        match_start=true;
        // }, 500);

        create_ads();
    }

    private void score_cretaed(){
        if (I_player_one) {
            myFragment.receiveMsg(0);
            Log.e("iam the player 1","f");
        }
        else{
            myFragment.receiveMsg(1);
            Log.e("iam the player 2","f");
        }

        score_fragment.receive("player1", player1, false);
        score_fragment.receive("player2", player2, false);
        if (I_player_one) {
            score_fragment.rotate();
        }


    }

 /*   private File get_local_file(Context context, audio_massage audio_massage){
        String msg2;
        if (I_player_one){
            msg2="msg2";
        }
        else {
            msg2 = "msg1";
        }
        long v=new Date().getTime();
        ContextWrapper contextWrapper =new ContextWrapper(context);
        File mus=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file =  new File(mus,msg2+v+".mp3");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(audio_massage.getAudio());
            fos.close();

            return file;
        } catch (Exception e) {
            Log.e("AdMob", e.getMessage());
        }


        return null;
    }

  */

  /*  private String to_base_64(File file) {

        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int j = 0; j < b.length; j++) {
                System.out.print((char) b[j]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }

        byte[] byteFileArray = new byte[0];
        try {
            byteFileArray = getBytesFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String base64String = "";
        if (byteFileArray.length > 0) {
            base64String = Base64.encodeToString(byteFileArray, Base64.NO_WRAP);
            Log.i("File Base64 string", "IMAGE PARSE ==>" + base64String);

            return base64String;
        }

        return "";
    }

    private File From_base_64(String imageData){
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);

        String msg2;
        if (I_player_one){
            msg2="msg2";
        }
        else {
            msg2 = "msg1";
        }
        long v=new Date().getTime();
        ContextWrapper contextWrapper =new ContextWrapper(this);
        File mus=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file =  new File(mus,msg2+v+".mp3");

        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }



    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }*/

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
                snack_bar.action_while_playne(0,user_id,bluetooth_game_space.this,bluetooth_game_space.this);
            }else {
                snack_bar.action_while_playne(1,user_id,bluetooth_game_space.this,bluetooth_game_space.this);
            }
        }
    };
}