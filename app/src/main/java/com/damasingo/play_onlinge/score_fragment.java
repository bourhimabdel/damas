package com.damasingo.play_onlinge;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.ADAPTER.list_add_friends;
import com.damasingo.CLASS_UTIL.ByteArrayMediaDataSource;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.audio_massage;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.bluetooth_match.bluetooth_game_space;
import com.damasingo.local_match.local_game_space;
import com.damasingo.local_match.local_game_space_robot;
import com.damasingo.randomly.game_space_randomly;
import com.damasingo.sign_in;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class score_fragment extends Fragment {


    TextView txt_msg_1,txt_msg_2,txt_chrono_1,txt_chrono_2,text_record1,text_record2;
    SeekBar seekbar_1,seekbar_2;

    audio_massage audio1,audio2;
    MediaPlayer mediaPlayer,mediaPlayer2;
    int current_data1=-1,current_data2=-1;

    ConstraintLayout view_rocord1,view_rocord2,view_msg1,view_msg2,view_p;
    ImageButton btn_next_1,btn_next_2,btn_previous_1,btn_previous_2,play1,play2,clear_1,clear_2;
    SelectableRoundedImageView img_player1,img_player2;
    sql_manager db_offline;
    CustomGauge progress_player1,progress_player2;
    Button show1,show2;
    View tab_simulation;

    ArrayList<Object> messages_1=new ArrayList<>();
    ArrayList<Object> messages_2=new ArrayList<>();


    public long millis=30000;
    int ob;
    boolean bo;
    public CountDownTimer countDownTimer;

    public sound sound;

    View v;
    Context context;
    boolean circule_bar;


    game_space_randomly game_space_randomly;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_score_fragment, container, false);

       initial_view(v);
       click_listner();

       return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.somethingHappenedInFragment_score("score_created",0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void click_listner() {

        clear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int a;
                    if(view.getId()==R.id.clear_1)
                        a=0;
                    else
                        a=1;


                    hide_this_msg(clear_1,a,false);

                    hide_this_msg(view_msg1,a,true);

                    if(btn_next_1.getVisibility()==View.VISIBLE)
                        hide_this_msg(btn_next_1,a,false);
                    if(btn_previous_1.getVisibility()==View.VISIBLE)
                        hide_this_msg(btn_previous_1,a,false);

                    if(mediaPlayer!=null)
                        mediaPlayer.pause();
                    mediaPlayer=null;
                    if(timer1!=null)
                        timer1.cancel();
                    timer1=null;

                    current_data1=-1;
                    messages_1.clear();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        clear_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int a;
                    if(view.getId()==R.id.clear_1)
                        a=0;
                    else
                        a=1;


                    hide_this_msg(clear_2,a,false);
                    hide_this_msg(view_msg2,a,true);
                    if(btn_next_2.getVisibility()==View.VISIBLE)
                        hide_this_msg(btn_next_2,a,false);
                    if(btn_previous_2.getVisibility()==View.VISIBLE)
                        hide_this_msg(btn_previous_2,a,false);

                    if(mediaPlayer2!=null)
                        mediaPlayer2.pause();
                    mediaPlayer2=null;
                    if(timer2!=null)
                        timer2.cancel();
                    timer2=null;

                    current_data2=-1;
                    messages_2.clear();
                 }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        show1.setOnClickListener(v -> {
            if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in"))
                return;
            if(getActivity() instanceof game_space) {
                game_space activity = (game_space) getActivity();
                activity.snack_bar.show_this_data( activity.player1, getContext(), activity,null,null);
                activity.snack_bar_showed=true;
            }else  if(getActivity() instanceof bluetooth_game_space){
                bluetooth_game_space activity = (bluetooth_game_space) getActivity();
                assert activity != null;
                activity.snack_bar.show_this_data(db_offline.get__account().getId(), getContext(), activity,null,null);
                activity.snack_bar_showed=true;
            }else {
                game_space_randomly activity = (game_space_randomly) getActivity();
                activity.snack_bar.show_this_data( activity.player1, getContext(), activity,a,null);
                activity.snack_bar_showed=true;
            }
        });

        show2.setOnClickListener(v -> {
            if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in"))
                return;
            if(getActivity() instanceof game_space) {
                game_space activity= (game_space) getActivity();
                activity.snack_bar.show_this_data(activity.player2,getContext(),(AppCompatActivity)getActivity(),null,null);
                activity.snack_bar_showed=true;
             }else if(getActivity() instanceof bluetooth_game_space) {
                bluetooth_game_space activity = (bluetooth_game_space) getActivity();
                assert activity != null;
                activity.snack_bar.show_this_data(db_offline.get__account().getId(), getContext(), (AppCompatActivity) getActivity(),null,null);
                activity.snack_bar_showed=true;
            }else {
                game_space_randomly activity= (game_space_randomly) getActivity();
                activity.snack_bar.show_this_data(activity.player2,getContext(),(AppCompatActivity) getActivity(),b,null);
                activity.snack_bar_showed=true;
            }
        });


        btn_next_1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {

                view_rocord1.setVisibility(View.GONE);
                txt_msg_1.setVisibility(View.GONE);

                int size = messages_1.size();
                int index;
                if(current_data1==0) {
                    String current_text = txt_msg_1.getText().toString();
                    index = messages_1.indexOf(current_text);
                }else {
                    index = messages_1.indexOf(audio1);
                }

                if (size != index + 1) {
                    if(messages_1.get(index + 1) instanceof String) {
                        txt_msg_1.setText((String) messages_1.get(index + 1));

                        view_rocord1.setVisibility(View.GONE);
                        txt_msg_1.setVisibility(View.VISIBLE);


                        current_data1=0;
                    }else {
                        audio1=(audio_massage) messages_1.get(index + 1);

                        txt_msg_1.setVisibility(View.GONE);
                        view_rocord1.setVisibility(View.VISIBLE);
                        int time=audio1.getSeconds();
                        int minutes = (time) / 60;
                        int seconds = (time) % 60;
                        text_record1.setText(String.format( "%02d:%02d",minutes,seconds));

                        current_data1=1;
                    }
                    btn_previous_1.setVisibility(View.VISIBLE);
                }
                if (size == index + 2)
                    btn_next_1.setVisibility(View.INVISIBLE);

            }
        });

        btn_previous_1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                view_rocord1.setVisibility(View.GONE);
                txt_msg_1.setVisibility(View.GONE);

                int index;
                if(current_data1==0) {
                    String current_text = txt_msg_1.getText().toString();
                     index = messages_1.indexOf(current_text);
                }else {
                    index = messages_1.indexOf(audio1);
                }

                if (index != 0) {
                    if(messages_1.get(index - 1) instanceof String) {
                        txt_msg_1.setText((String) messages_1.get(index - 1));


                        view_rocord1.setVisibility(View.GONE);
                        txt_msg_1.setVisibility(View.VISIBLE);


                        current_data1=0;
                    }else {
                        audio1=(audio_massage) messages_1.get(index - 1);


                        txt_msg_1.setVisibility(View.GONE);
                        view_rocord1.setVisibility(View.VISIBLE);

                        int time=audio1.getSeconds();
                        int minutes = (time) / 60;
                        int seconds = (time) % 60;

                        text_record1.setText(String.format( "%02d:%02d",minutes,seconds));

                        current_data1=1;
                    }
                    btn_next_1.setVisibility(View.VISIBLE);
                    if (index - 1 == 0)
                        btn_previous_1.setVisibility(View.INVISIBLE);
                }
            }
        });

        btn_next_2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                view_rocord1.setVisibility(View.GONE);
                txt_msg_1.setVisibility(View.GONE);

                int size = messages_2.size();
                int index;
                if(current_data2==0) {
                    String current_text = txt_msg_2.getText().toString();
                    index = messages_2.indexOf(current_text);
                }else {
                    index = messages_2.indexOf(audio2);
                }

                    if (size != index + 1) {
                        if(messages_2.get(index + 1) instanceof String) {
                            txt_msg_2.setText((String) messages_2.get(index + 1));

                            view_rocord2.setVisibility(View.GONE);
                            txt_msg_2.setVisibility(View.VISIBLE);


                            current_data2=0;
                        }else {
                            audio2=(audio_massage) messages_2.get(index + 1);


                            txt_msg_2.setVisibility(View.GONE);
                            view_rocord2.setVisibility(View.VISIBLE);

                            int time=audio2.getSeconds();
                            int minutes = (time) / 60;
                            int seconds = (time) % 60;

                            text_record2.setText(String.format( "%02d:%02d",minutes,seconds));

                            current_data2=1;
                        }
                        btn_previous_2.setVisibility(View.VISIBLE);
                    }
                    if (size == index + 2)
                        btn_next_2.setVisibility(View.INVISIBLE);



            }
        });

        btn_previous_2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {

                view_rocord1.setVisibility(View.GONE);
                txt_msg_1.setVisibility(View.GONE);

                int index;
                if(current_data2==0) {
                    String current_text = txt_msg_2.getText().toString();
                    index = messages_2.indexOf(current_text);
                }else {
                    index = messages_2.indexOf(audio2);
                }

                if (index != 0) {
                    if(messages_2.get(index - 1) instanceof String) {
                        txt_msg_2.setText((String) messages_2.get(index - 1));


                        view_rocord2.setVisibility(View.GONE);
                        txt_msg_2.setVisibility(View.VISIBLE);


                        current_data2=0;
                    }else {
                        audio2=(audio_massage) messages_2.get(index - 1);


                        txt_msg_2.setVisibility(View.GONE);
                        view_rocord2.setVisibility(View.VISIBLE);

                        int time=audio2.getSeconds();
                        int minutes = (time) / 60;
                        int seconds = (time) % 60;

                        text_record2.setText(String.format( "%02d:%02d",minutes,seconds));

                        current_data2=1;
                    }
                    btn_next_2.setVisibility(View.VISIBLE);
                    if (index - 1 == 0)
                        btn_previous_2.setVisibility(View.INVISIBLE);
                }
            }
        });

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null){
                    if(mediaPlayer.isPlaying()) {
                        play1.setImageResource(R.drawable.play);
                        mediaPlayer.pause();
                        timer1.cancel();
                    }else {
                        if(mediaPlayer2!=null){
                            if(mediaPlayer2.isPlaying())
                                play2.performClick();
                        }
                        play1.setImageResource(R.drawable.pause);
                        mediaPlayer.start();
                        start_record(0, time);

                    }
                }else
                    {
                        if(mediaPlayer2!=null){
                            if(mediaPlayer2.isPlaying())
                                play2.performClick();
                        }
                        play1.setImageResource(R.drawable.pause);
                        play_audio(0);
                        if (timer1 == null) {
                            start_record(0, 0);
                        }

                    }
            }
        });

        seekbar_1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //mediaPlayer.seekTo(i+);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                play1.setImageResource(R.drawable.play);
                if(mediaPlayer!=null)
                    mediaPlayer.pause();
                if(timer1!=null)
                    timer1.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer!=null){
                    mediaPlayer.seekTo(seekBar.getProgress()*1000);
                    time=seekBar.getProgress()*1000;
                    if(!mediaPlayer.isPlaying()) {
                        play1.setImageResource(R.drawable.pause);
                        mediaPlayer.start();
                        start_record(0,time);
                    }
                }{
                  seekbar_1.setProgress(0);
                }
            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer2!=null){
                    if(mediaPlayer2.isPlaying()) {
                        play2.setImageResource(R.drawable.play);
                        mediaPlayer2.pause();
                        timer2.cancel();
                    }else {
                          if(mediaPlayer!=null){
                              if(mediaPlayer.isPlaying())
                                  play1.performClick();
                          }
                              play2.setImageResource(R.drawable.pause);
                              mediaPlayer2.start();
                              start_record(1, time2);

                    }
                }else
                {
                    if(mediaPlayer!=null){
                        if(mediaPlayer.isPlaying())
                            play1.performClick();
                    }
                         play2.setImageResource(R.drawable.pause);
                         play_audio(1);
                         if(timer2==null) {
                             start_record(1,0);
                         }

                }
            }

        });

        seekbar_2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //mediaPlayer.seekTo(i+);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                play2.setImageResource(R.drawable.play);
                if(mediaPlayer2!=null)
                    mediaPlayer2.pause();
                if(timer2!=null)
                    timer2.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer2!=null){
                    mediaPlayer2.seekTo(seekBar.getProgress()*1000);
                    time2=seekBar.getProgress()*1000;
                    if(!mediaPlayer2.isPlaying()) {
                        play2.setImageResource(R.drawable.pause);
                        mediaPlayer2.start();
                        start_record(1,time2);
                    }
                }{
                    seekbar_2.setProgress(0);
                }
            }
        });

      /*  final float[] x1 = new float[1];
        final float[] x2 = new float[1];
        final int MIN_DISTANCE = 150;
        view_msg1.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1[0] = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2[0] = event.getX();
                        float deltaX = x2[0] - x1[0];
                        if (Math.abs(deltaX) > MIN_DISTANCE)
                        {
                            if (x2[0] > x1[0])
                            {
                                Toast.makeText(getContext(), "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                            }


                            // Right to left swipe action
                            else
                            {
                                Toast.makeText(getContext(), "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                            }
                        }
                        break;
                }
                return true;
            }
        });
       */
    }

    private void initial_view(View v) {
        if(getActivity() instanceof game_space)
            mListener=(game_space)getActivity();
        else if (getActivity() instanceof bluetooth_game_space)
            mListener=(bluetooth_game_space)getActivity();
        else if (getActivity() instanceof local_game_space)
            mListener=(local_game_space)getActivity();
        else if (getActivity() instanceof game_space_randomly) {
            mListener = (game_space_randomly) getActivity();
            game_space_randomly=(game_space_randomly)getActivity();
        }
        else
            mListener=(local_game_space_robot)getActivity();



        circule_bar= new sharedpreferences(getContext()).get_setting_type("chrono");

        context=getContext();
        db_offline=new sql_manager(getContext());
        sound=new sound();
        this.v=v;
       // txt_score=v.findViewById(R.id.txt_match_score);
        txt_msg_1=v.findViewById(R.id.txt_msg1);
        txt_msg_2=v.findViewById(R.id.txt_msg2);
        img_player1=v.findViewById(R.id.image_player1);
        img_player2=v.findViewById(R.id.image_player2);
        progress_player1=v.findViewById(R.id.progressbar_player1);
        progress_player2=v.findViewById(R.id.progressbar_player2);
        show1=v.findViewById(R.id.btn_show_1);
        show2=v.findViewById(R.id.btn_show_2);
        txt_chrono_1=v.findViewById(R.id.chrono_1);
        txt_chrono_2=v.findViewById(R.id.chrono_2);
        btn_next_1=v.findViewById(R.id.next_message1);
        btn_next_2=v.findViewById(R.id.next_message2);
        btn_previous_1=v.findViewById(R.id.previous_message1);
        btn_previous_2=v.findViewById(R.id.previous_message2);
        view_rocord1=v.findViewById(R.id.view_record);
        view_rocord2=v.findViewById(R.id.view_record2);
        text_record1=v.findViewById(R.id.text_record_1);
        text_record2=v.findViewById(R.id.text_record_2);
        seekbar_1=v.findViewById(R.id.seek_record_1);
        seekbar_2=v.findViewById(R.id.seek_record_2);
        play1=v.findViewById(R.id.btn_play_1);
        play2=v.findViewById(R.id.btn_play_2);
        clear_1=v.findViewById(R.id.clear_1);
        clear_2=v.findViewById(R.id.clear_2);
        view_msg1=v.findViewById(R.id.view_msg1);
        view_msg2=v.findViewById(R.id.view_msg2);
        tab_simulation=v.findViewById(R.id.tab_simulation);
        view_p=v.findViewById(R.id.view_P);


        txt_msg_1.setMovementMethod(new ScrollingMovementMethod());
        txt_msg_2.setMovementMethod(new ScrollingMovementMethod());
    }
    Bitmap a,b;
    public void rotate() {

        txt_chrono_1.setText("");
        txt_chrono_2.setText("");
        if(txt_msg_1.getId()==R.id.txt_msg1)
        {
            txt_msg_1=v.findViewById(R.id.txt_msg2);
            txt_msg_2=v.findViewById(R.id.txt_msg1);
            show1=v.findViewById(R.id.btn_show_2);
            show2=v.findViewById(R.id.btn_show_1);

            boolean p1;
            if(circule_bar){
                p1=progress_player1.getVisibility()==View.VISIBLE;
            }else {
                p1=txt_chrono_1.getVisibility()==View.VISIBLE;
            }

            progress_player1=v.findViewById(R.id.progressbar_player2);
            progress_player2=v.findViewById(R.id.progressbar_player1);
            txt_chrono_1=v.findViewById(R.id.chrono_2);
            txt_chrono_2=v.findViewById(R.id.chrono_1);
            btn_next_1=v.findViewById(R.id.next_message2);
            btn_next_2=v.findViewById(R.id.next_message1);
            btn_previous_1=v.findViewById(R.id.previous_message2);
            btn_previous_2=v.findViewById(R.id.previous_message1);
            view_rocord1=v.findViewById(R.id.view_record2);
            view_rocord2=v.findViewById(R.id.view_record);
            text_record1=v.findViewById(R.id.text_record_2);
            text_record2=v.findViewById(R.id.text_record_1);
            seekbar_1=v.findViewById(R.id.seek_record_2);
            seekbar_2=v.findViewById(R.id.seek_record_1);
            play1=v.findViewById(R.id.btn_play_2);
            play2=v.findViewById(R.id.btn_play_1);
            clear_1=v.findViewById(R.id.clear_2);
            clear_2=v.findViewById(R.id.clear_1);
            view_msg1=v.findViewById(R.id.view_msg2);
            view_msg2=v.findViewById(R.id.view_msg1);


            if(p1) {
                if(circule_bar) {
                    progress_player2.setVisibility(View.INVISIBLE);
                    progress_player1.setVisibility(View.VISIBLE);
                }else {
                    txt_chrono_1.setVisibility(View.VISIBLE);
                    txt_chrono_2.setVisibility(View.INVISIBLE);
                }
            }
            else {
                if(circule_bar) {
                    progress_player1.setVisibility(View.INVISIBLE);
                    progress_player2.setVisibility(View.VISIBLE);
                }else {
                    txt_chrono_1.setVisibility(View.INVISIBLE);
                    txt_chrono_2.setVisibility(View.VISIBLE);
                }
            }

            img_player1.setImageBitmap(b);
            img_player2.setImageBitmap(a);
        }
        else
            {
            txt_msg_1=v.findViewById(R.id.txt_msg1);
            txt_msg_2=v.findViewById(R.id.txt_msg2);
            show1=v.findViewById(R.id.btn_show_1);
            show2=v.findViewById(R.id.btn_show_2);

            btn_next_1=v.findViewById(R.id.next_message1);
            btn_next_2=v.findViewById(R.id.next_message2);
            btn_previous_1=v.findViewById(R.id.previous_message1);
            btn_previous_2=v.findViewById(R.id.previous_message2);
            view_rocord1=v.findViewById(R.id.view_record);
            view_rocord2=v.findViewById(R.id.view_record2);
            text_record1=v.findViewById(R.id.text_record_1);
            text_record2=v.findViewById(R.id.text_record_2);
            seekbar_1=v.findViewById(R.id.seek_record_1);
            seekbar_2=v.findViewById(R.id.seek_record_2);
            play1=v.findViewById(R.id.btn_play_1);
            play2=v.findViewById(R.id.btn_play_2);
            clear_1=v.findViewById(R.id.clear_1);
            clear_2=v.findViewById(R.id.clear_2);
            view_msg1=v.findViewById(R.id.view_msg1);
            view_msg2=v.findViewById(R.id.view_msg2);

            boolean p1;
            if(circule_bar){
                p1=progress_player1.getVisibility()==View.VISIBLE;
            }else {
                p1=txt_chrono_1.getVisibility()==View.VISIBLE;
            }

            progress_player1=v.findViewById(R.id.progressbar_player1);
            progress_player2=v.findViewById(R.id.progressbar_player2);
            txt_chrono_1=v.findViewById(R.id.chrono_1);
            txt_chrono_2=v.findViewById(R.id.chrono_2);

            if(p1) {
                if(circule_bar) {
                    progress_player2.setVisibility(View.INVISIBLE);
                    progress_player1.setVisibility(View.VISIBLE);
                }else {
                    txt_chrono_1.setVisibility(View.VISIBLE);
                    txt_chrono_2.setVisibility(View.INVISIBLE);
                }
            }
            else {
                if(circule_bar) {
                    progress_player1.setVisibility(View.INVISIBLE);
                    progress_player2.setVisibility(View.VISIBLE);
                }else {
                    txt_chrono_1.setVisibility(View.INVISIBLE);
                    txt_chrono_2.setVisibility(View.VISIBLE);
                }
            }

            img_player1.setImageBitmap(a);
            img_player2.setImageBitmap(b);
        }

        click_listner();
        rotate_massage();
    }
    public void scale(int scale){
        tab_simulation.setLayoutParams(new ConstraintLayout.LayoutParams(scale,scale));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(view_p);


        constraintSet.connect(R.id.tab_simulation,ConstraintSet.END,R.id.view_P,ConstraintSet.END,0);
        constraintSet.connect(R.id.tab_simulation,ConstraintSet.START,R.id.view_P,ConstraintSet.START,0);
        constraintSet.connect(R.id.tab_simulation,ConstraintSet.TOP,R.id.view_P,ConstraintSet.TOP,0);
        constraintSet.connect(R.id.tab_simulation,ConstraintSet.BOTTOM,R.id.view_P,ConstraintSet.BOTTOM,0);
        constraintSet.applyTo(view_p);
    }

    @SuppressLint("DefaultLocale")
    public void rotate_massage(){
        btn_next_1.setVisibility(View.INVISIBLE);
        btn_next_2.setVisibility(View.INVISIBLE);
        btn_previous_1.setVisibility(View.INVISIBLE);
        btn_previous_2.setVisibility(View.INVISIBLE);
        txt_msg_1.setVisibility(View.GONE);
        txt_msg_2.setVisibility(View.GONE);
        view_rocord1.setVisibility(View.GONE);
        view_rocord2.setVisibility(View.GONE);
        clear_1.setVisibility(View.GONE);
        clear_2.setVisibility(View.GONE);

        play1.setImageResource(R.drawable.play);
        play2.setImageResource(R.drawable.play);

        if(mediaPlayer!=null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            mediaPlayer=null;
        }
        if(mediaPlayer2!=null) {
            if (mediaPlayer2.isPlaying())
                mediaPlayer2.pause();
            mediaPlayer2=null;
        }

        if(timer1!=null){
            timer1.cancel();
            timer1=null;
            time=0;
        }
        if(timer2!=null){
            timer2.cancel();
            timer2=null;
            time2=0;
        }

        seekbar_1.setProgress(0);
        seekbar_2.setProgress(0);

        if(!(current_data1==-1)){

            view_msg1.setVisibility(View.VISIBLE);
                Object ob= messages_1.get(messages_1.size()-1);
                if(ob instanceof String){
                    txt_msg_1.setVisibility(View.VISIBLE);
                    txt_msg_1.setText((String)ob);

                    current_data1=0;
                }else {
                    view_rocord1.setVisibility(View.VISIBLE);
                    audio1=(audio_massage) ob;

                    int time=audio1.getSeconds();
                    int minutes = (time ) / 60;
                    int seconds = (time ) % 60;
                    text_record1.setText(String.format( "%02d:%02d",minutes,seconds));
                    current_data1=1;
                }

                if(messages_1.size()>1)
                    btn_previous_1.setVisibility(View.VISIBLE);

                if(messages_1.size()>0)
                    clear_1.setVisibility(View.VISIBLE);
        }

        if(!(current_data2==-1)){
            view_msg2.setVisibility(View.VISIBLE);
            Object ob= messages_2.get(messages_2.size()-1);
            if(ob instanceof String){
                txt_msg_2.setVisibility(View.VISIBLE);
                txt_msg_2.setText((String)ob);

                current_data2=0;
            }else {
                view_rocord2.setVisibility(View.VISIBLE);
                audio2=(audio_massage) ob;

                int time=audio2.getSeconds();
                int minutes = (time ) / 60;
                int seconds = (time ) % 60;
                text_record2.setText(String.format( "%02d:%02d",minutes,seconds));

                current_data2=1;
            }
            if(messages_2.size()>1)
                btn_previous_2.setVisibility(View.VISIBLE);

            if(messages_2.size()>0)
                clear_2.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("DefaultLocale")
    private void play_audio(int player){
        if(player==0) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(audio1.getFile().getPath());
               // mediaPlayer.setDataSource(audio1.getUrl());
                mediaPlayer.prepare();
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer meo) {
                        timer1.cancel();
                        timer1=null;
                        mediaPlayer=null;
                        int time=audio1.getSeconds();
                        int minutes = (time ) / 60;
                        int seconds = (time ) % 60;
                        text_record1.setText(String.format( "%02d:%02d",minutes,seconds));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            seekbar_1.setProgress(0,true);
                        }else
                            seekbar_1.setProgress(0);
                        play1.setImageResource(R.drawable.play);

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                mediaPlayer2 = new MediaPlayer();
                mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer2.setDataSource(audio2.getFile().getPath());
                mediaPlayer2.prepare();
                mediaPlayer2.start();

                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer meo) {
                        timer2.cancel();
                        timer2=null;
                        mediaPlayer2=null;
                        int time=audio2.getSeconds();
                        int minutes = (time ) / 60;
                        int seconds = (time ) % 60;
                        text_record2.setText(String.format( "%02d:%02d",minutes,seconds));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            seekbar_2.setProgress(0,true);
                        }else
                            seekbar_2.setProgress(0);
                        play2.setImageResource(R.drawable.play);

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Timer timer1,timer2 ;
    @SuppressLint("DefaultLocale")
    private void start_record(int player,int timing){
        if(player==0) {
            seekbar_1.setMax(audio1.getSeconds());
            time=timing;
            timer1=null;
            timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){


                            int minutes = ((time / 1000) / 60) ;
                            int seconds = ((time / 1000) % 60);

                                text_record1.setText(String.format("%02d:%02d", (audio1.getSeconds() / 60) - minutes, audio1.getSeconds() - seconds));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    seekbar_1.setProgress(time/1000,true);
                                }else
                                    seekbar_1.setProgress(time/1000);


                            time+=1000;
                        }
                    });

                }
            }, 0, 1000);
        }else {
            seekbar_2.setMax(audio2.getSeconds());
            time2=timing;
            timer2=null;
            timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){


                            int minutes = (int) ((time2 / 1000) / 60) ;
                            int seconds = (int) ((time2 / 1000) % 60);


                                text_record2.setText(String.format("%02d:%02d", (audio2.getSeconds() / 60) - minutes, audio2.getSeconds() - seconds));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    seekbar_2.setProgress(time2/1000,true);
                                }else
                                    seekbar_2.setProgress(time2/1000);


                            time2+=1000;
                        }
                    });

                }
            }, 0, 1000);
        }

    }
    int time=0;
    int time2=0;


    public interface Listener_score {
        void somethingHappenedInFragment_score(String tag,Object object);
    }
    private Listener_score mListener;

    public void receive(String tag,Object object,boolean me){
        switch (tag){
            case "player1":
                remplire_data(0,(String)object);
                break;
            case "player2":
                remplire_data(1,(String)object);
                break;
            case "msg1":
                remplire_msg(0,object);
                break;
            case "msg2":
                remplire_msg(1,object);
                break;
            case "role":
                change_role((int)object,me);
                break;
        }
    }

    private void change_role(int object,boolean me) {
        ob=object;
        bo=me;

        switch (object){
            case 0:
                sound.stop_timer();
                if(circule_bar) {
                    progress_player1.setVisibility(View.VISIBLE);
                    progress_player2.setVisibility(View.GONE);
                }else {
                    txt_chrono_1.setVisibility(View.VISIBLE);
                    txt_chrono_2.setVisibility(View.GONE);
                }
                on_progress_state(1,0);
                progress_player1.setValue((int)millis);

                if(countDownTimer!=null)
                    countDownTimer.cancel();

                final int[] sa = {0};

                countDownTimer=new CountDownTimer(millis, 1000) {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int second=(int)(millisUntilFinished/1000);

                        millis=millisUntilFinished;

                        if(circule_bar)
                            setProgress_player1((int)millisUntilFinished,(int)millisUntilFinished-1000);
                        else
                            txt_chrono_1.setText(String.format("%02d",second));

                        if(second==29){
                            on_progress_state(0,0);
                        }
                        if(second==15){
                            if(sa[0]==0) {
                                if (me)
                                    sound.timer(getContext());
                                on_progress_state(0, 1);
                                sa[0]++;
                            }
                        }
                        if(second==7){
                            on_progress_state(0,2);
                        }
                    }
                    @Override
                    public void onFinish() {
                        if(me){
                            sound.stop_timer();
                            mListener.somethingHappenedInFragment_score("time",0);
                        }else
                            mListener.somethingHappenedInFragment_score("time",-1);
                    }
                }.start();

                break;
            case 1:
                if(circule_bar) {
                    progress_player1.setVisibility(View.GONE);
                    progress_player2.setVisibility(View.VISIBLE);
                }else {
                    txt_chrono_1.setVisibility(View.GONE);
                    txt_chrono_2.setVisibility(View.VISIBLE);
                }
                on_progress_state(0,0);
                progress_player2.setValue((int)millis);
                if(countDownTimer!=null)
                    countDownTimer.cancel();

                sound.stop_timer();

                final int[] se = {0};
                countDownTimer=new CountDownTimer(millis, 1000) {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int second=(int)(millisUntilFinished/1000);

                        millis=millisUntilFinished;
                        if(circule_bar)
                            setProgress_player2((int)millisUntilFinished,(int)millisUntilFinished-1000);
                        else
                            txt_chrono_2.setText(String.format("%02d",second));

                        if(second==29){
                            on_progress_state(1,0);
                        }
                        if(second<15){
                            if(se[0] ==0) {
                                if (!me)
                                    sound.timer(getContext());
                                on_progress_state(1, 1);
                                se[0]++;
                            }
                        }
                        if(second==7){
                            on_progress_state(1,2);
                        }
                    }
                    @Override
                    public void onFinish() {
                        if(!me){
                            sound.stop_timer();
                            mListener.somethingHappenedInFragment_score("time",0);
                        }else
                            mListener.somethingHappenedInFragment_score("time",-1);
                    }
                }.start();
                break;
            case -1:
                if(countDownTimer!=null)
                    countDownTimer.cancel();
                break;
        }

        if(first_time){
            first_time=false;
            show_this_view(img_player1);
            show_this_view(img_player2);
        }
    }


    boolean first_time=true;
    private void show_this_view(View view){
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.scale_500);
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

    @SuppressLint("DefaultLocale")
    private void remplire_msg(int i, Object object) {
        Handler handler=new Handler();
        switch (i){
            case 0:
              //  txt_msg_1.setVisibility(View.VISIBLE);
                if(object instanceof String) {
                    show_this_msg(txt_msg_1, 0);
                    txt_msg_1.setText((String)object);
                    view_rocord1.setVisibility(View.GONE);
                    txt_msg_1.setVisibility(View.VISIBLE);
                    current_data1=0;
                }else {
                    show_this_msg(view_rocord1, 0);
                    view_rocord1.setVisibility(View.VISIBLE);
                    txt_msg_1.setVisibility(View.GONE);
                    audio1=(audio_massage)object;

                    int time=audio1.getSeconds();
                    int minutes = (time ) / 60;
                    int seconds = (time ) % 60;

                    text_record1.setText(String.format( "%02d:%02d",minutes,seconds));
                    current_data1=1;
                }
                message_add(0,object);
                //handler.postDelayed(() -> hide_this_msg(txt_msg_1), 5000);
                break;
            case 1:
               // txt_msg_2.setVisibility(View.VISIBLE);
                if(object instanceof String) {
                    show_this_msg(txt_msg_2,1);
                    txt_msg_2.setText((String)object);
                    view_rocord2.setVisibility(View.GONE);
                    txt_msg_2.setVisibility(View.VISIBLE);
                    current_data2=0;
                }else {
                    show_this_msg(view_rocord2,1);
                    view_rocord2.setVisibility(View.VISIBLE);
                    txt_msg_2.setVisibility(View.GONE);
                    audio2=(audio_massage)object;

                    int time=audio2.getSeconds();
                    int minutes = (time ) / 60;
                    int seconds = (time ) % 60;

                    text_record2.setText(String.format( "%02d:%02d",minutes,seconds));
                    current_data2=1;
                }
                message_add(1,object);
               // handler.postDelayed(() -> hide_this_msg(txt_msg_2), 5000);
                break;
        }
    }

    @SuppressLint("HardwareIds")
    private void remplire_data(int i, String object) {

        if(getActivity() instanceof game_space) {
            Bitmap puc;
            show1.setEnabled(true);
            show2.setEnabled(true);

            if(db_offline.get__account().getId().equals(object)){
                byte[] tof_saved=db_offline.get__account().getPhoto_saved();
                puc = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            }else {
                byte[] tof_saved=db_offline.get_this_request(object).getPhoto_saved();
                puc = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            }
            switch (i){
                case 0:
                    a=puc;
                    img_player1.setImageBitmap(puc);
                    break;
                case 1:
                    b=puc;
                    img_player2.setImageBitmap(puc);
                    break;
            }
        }
        else  if(getActivity() instanceof game_space_randomly) {
            Bitmap puc = null;
            show1.setEnabled(true);
            show2.setEnabled(true);

            if(db_offline.get__account().getId().equals(object)){
                byte[] tof_saved=db_offline.get__account().getPhoto_saved();
                puc = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);

                switch (i){
                    case 0:
                        a=puc;
                        img_player1.setImageBitmap(puc);
                        break;
                    case 1:
                        b=puc;
                        img_player2.setImageBitmap(puc);
                        break;
                }
            }else if(!db_offline.get__account().getId().equals(object)){

               // Drawable d= ContextCompat.getDrawable(context,R.drawable.inconnu);
               // assert d != null;
               // puc = get_Bitmap(d);

               // switch (i){
               //     case 0:
               //         a=puc;
               //         img_player1.setImageBitmap(puc);
               //         break;
               //     case 1:
               //         b=puc;
               //         img_player2.setImageBitmap(puc);
               //         break;
               // }
                 load_image(i,object);
            }

        }
        else if (getActivity() instanceof bluetooth_game_space) {
            show1.setEnabled(true);
            show2.setEnabled(true);
            Bitmap puc;
            if(BluetoothAdapter.getDefaultAdapter().getAddress().equals(object)){
                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    Drawable d= ContextCompat.getDrawable(context,R.drawable.inconnu);
                    assert d != null;
                    puc=get_Bitmap(d);
                }else {
                    byte[] tof_saved=db_offline.get__account().getPhoto_saved();
                    puc = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
                }

            }else {
                Drawable d= ContextCompat.getDrawable(context,R.drawable.smartphone_);
                assert d != null;
                puc = get_Bitmap(d);
            }
            switch (i){
                case 0:
                    a=puc;
                    img_player1.setImageBitmap(puc);
                    break;
                case 1:
                    b=puc;
                    img_player2.setImageBitmap(puc);
                    break;
            }
        }
        else if (getActivity() instanceof local_game_space) {
            Bitmap puc;
            Drawable d;
            if(i==0){
                d = ContextCompat.getDrawable(context, R.drawable.piece_player_1);
            }else {
                d = ContextCompat.getDrawable(context, R.drawable.piece_player_2);
            }
            assert d != null;
            puc = get_Bitmap(d);
            switch (i){
                case 0:
                    a=puc;
                    img_player1.setImageBitmap(puc);
                    break;
                case 1:
                    b=puc;
                    img_player2.setImageBitmap(puc);
                    break;
            }
        }
        else if (getActivity() instanceof local_game_space_robot) {
            Bitmap puc;
            Drawable d;
            if(i==0){
                d = ContextCompat.getDrawable(context, R.drawable.piece_player_1);
            }else {
                d = ContextCompat.getDrawable(context, R.drawable.robot_);
            }
            assert d != null;
            puc = get_Bitmap(d);
            switch (i){
                case 0:
                    a=puc;
                    img_player1.setImageBitmap(puc);
                    break;
                case 1:
                    b=puc;
                    img_player2.setImageBitmap(puc);
                    break;
            }
        }


    }

    private void load_image(int i,String id) {
        new connect_to_firebase().retrive_url_img(id, new connect_to_firebase.URL() {
            @Override
            public void onCallback(String url) {
                remplire_Image(bitmap -> complete_load_image_work(id,bitmap,i),url);
            }
        });
    }

    public void remplire_Image(sign_in.image_ready image_ready, String url){
       // Log.e("image is","gg "+account.getImage());
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        remplire_Image(image_ready, url);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        image_ready.onCallback(bb);
                        return false;
                    }
                }).submit();

    }


    private void complete_load_image_work(String id, Drawable bb,int i){
        BitmapDrawable d=(BitmapDrawable)bb;
        Bitmap resource=d.getBitmap();


        game_space_randomly.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    if(i==1) {
                        b = resource;
                        img_player1.setImageBitmap(b);


                    }else {
                        a = resource;
                        img_player2.setImageBitmap(a);

                        rotate();
                        rotate();
                    }


            }
        });

    }


    Animation animFade;
    public void hide_this_msg(final View b,int where,boolean is_message){
        if(getContext() != null && b !=null) {
            if(where==0) {
                if(is_message)
                    animFade = AnimationUtils.loadAnimation(getContext(), R.anim.exit_r_m);
                else
                    animFade = AnimationUtils.loadAnimation(getContext(), R.anim.exit_r);
            }else {
                if(is_message)
                    animFade = AnimationUtils.loadAnimation(getContext(), R.anim.exit_l_m);
                else
                    animFade = AnimationUtils.loadAnimation(getContext(), R.anim.exit_l);

            }

            animFade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    b.setVisibility(View.GONE);
                }
            });
            b.startAnimation(animFade);
        }
    }

    public void show_this_msg(final View b,int player){
        if(getContext() != null && b !=null) {

            if ((bo && player == 1) || (!bo && player == 0)) {
                new sound().msg(getContext());
                change_color(b,R.color.n,R.color.m);
            }
            b.setVisibility(View.GONE);


            animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fide_in_message);

            animFade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    b.setVisibility(View.VISIBLE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                }
            });
            b.startAnimation(animFade);


            if(player==0) {
                clear_1.setVisibility(View.VISIBLE);
                if(view_msg1.getVisibility()!=View.VISIBLE)
                    view_msg1.setVisibility(View.VISIBLE);
            }
            else {
                clear_2.setVisibility(View.VISIBLE);
                if(view_msg2.getVisibility()!=View.VISIBLE)
                    view_msg2.setVisibility(View.VISIBLE);
            }



        }


    }

    public void setProgress_player1(int from, int to){
        if(to>=0) {
            ObjectAnimator animation = ObjectAnimator.ofInt(progress_player1, "Value", from, to); // see this max value coming back here, we animate towards that value
            animation.setDuration(1000); // in milliseconds
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }
    }

    public void setProgress_player2(int from, int to){
        if(to>=0) {
            ObjectAnimator animation = ObjectAnimator.ofInt(progress_player2, "Value", from, to); // see this max value coming back here, we animate towards that value
            animation.setDuration(1000); // in milliseconds
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }
    }

    private void on_progress_state(int role,int m) {
       switch (role){
           case 0:
               switch (m){
                   case 0:
                       progress_player1.setPointStartColor(context.getResources().getColor(R.color.time30));
                       progress_player1.setPointEndColor(context.getResources().getColor(R.color.time30));
                       txt_chrono_1.setTextColor(context.getResources().getColor(R.color.color_text_login_and_cadre));
                       break;
                   case 1:
                       progress_player1.setPointStartColor(context.getResources().getColor(R.color.time15));
                       progress_player1.setPointEndColor(context.getResources().getColor(R.color.time15));
                       txt_chrono_1.setTextColor(context.getResources().getColor(R.color.time15));
                       break;
                   case 2:
                       progress_player1.setPointStartColor(context.getResources().getColor(R.color.time5));
                       progress_player1.setPointEndColor(context.getResources().getColor(R.color.time5));
                       txt_chrono_1.setTextColor(context.getResources().getColor(R.color.time5));
                       break;
               }
               break;
           case 1:
               switch (m){
                   case 0:
                       progress_player2.setPointStartColor(context.getResources().getColor(R.color.time30));
                       progress_player2.setPointEndColor(context.getResources().getColor(R.color.time30));
                       txt_chrono_2.setTextColor(context.getResources().getColor(R.color.color_text_login_and_cadre));
                       break;
                   case 1:
                       progress_player2.setPointStartColor(context.getResources().getColor(R.color.time15));
                       progress_player2.setPointEndColor(context.getResources().getColor(R.color.time15));
                       txt_chrono_2.setTextColor(context.getResources().getColor(R.color.time15));
                       break;
                   case 2:
                       progress_player2.setPointStartColor(context.getResources().getColor(R.color.time5));
                       progress_player2.setPointEndColor(context.getResources().getColor(R.color.time5));
                       txt_chrono_2.setTextColor(context.getResources().getColor(R.color.time5));
                       break;
               }
               break;
       }
    }

    public void pause(){
        if(countDownTimer!=null)
            countDownTimer.cancel();
    }
    public void resume(){
        change_role(ob,bo);
    }

    private Bitmap get_Bitmap(Drawable drawable){
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }

    private void message_add(int role,Object ob){
        switch (role){
            case 0:
                messages_1.add(ob);
                btn_next_1.setVisibility(View.INVISIBLE);
                if(messages_1.size()==1)
                    btn_previous_1.setVisibility(View.INVISIBLE);
                else
                    btn_previous_1.setVisibility(View.VISIBLE);
                break;
            case 1:
                messages_2.add(ob);
                btn_next_2.setVisibility(View.INVISIBLE);
                if(messages_2.size()==1)
                    btn_previous_2.setVisibility(View.INVISIBLE);
                else
                    btn_previous_2.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void change_color(View view,int id_color_start,int id_color_end){
        int colorFrom = getResources().getColor(id_color_start);
        int colorTo = getResources().getColor(id_color_end);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(3500); // milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAnimation.addUpdateListener(animator -> view.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue())));
        }
        colorAnimation.start();
    }

    public Bitmap get_bitmap (Boolean plauer){
        if(plauer)
            return b;
        else
            return a;
    }
}