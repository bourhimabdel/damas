package com.damasingo.play_onlinge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.CLASS_UTIL.match_running;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.R;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.bluetooth_match.bluetooth_game_space;
import com.damasingo.randomly.game_space_randomly;
import com.damasingo.table_play_off_line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("NonConstantResourceId")
@SuppressWarnings("unchecked")
public class table_play extends Fragment implements View.OnClickListener {

    ImageButton l1_c1, l1_c3, l1_c5, l1_c7, l2_c2, l2_c4, l2_c6, l2_c8, l3_c1, l3_c3, l3_c5, l3_c7, l4_c2, l4_c4, l4_c6, l4_c8, l5_c1,
            l5_c3, l5_c5, l5_c7, l6_c2, l6_c4, l6_c6, l6_c8, l7_c1, l7_c3, l7_c5, l7_c7, l8_c2, l8_c4, l8_c6, l8_c8;

    ImageButton l1_c2, l1_c4, l1_c6, l1_c8, l2_c1, l2_c3, l2_c5, l2_c7, l3_c2, l3_c4, l3_c6, l3_c8, l4_c1, l4_c3, l4_c5, l4_c7, l5_c2,
            l5_c4, l5_c6, l5_c8, l6_c1, l6_c3, l6_c5, l6_c7, l7_c2, l7_c4, l7_c6, l7_c8, l8_c1, l8_c3, l8_c5, l8_c7;


    ImageView player1_1, player1_2, player1_3, player1_4, player1_5, player1_6, player1_7, player1_8, player1_9,
            player1_10, player1_11, player1_12, player2_1, player2_2, player2_3, player2_4, player2_5, player2_6,
            player2_7, player2_8, player2_9, player2_10, player2_11, player2_12;

    LottieAnimationView lottie1_1, lottie1_2, lottie1_3, lottie1_4, lottie1_5, lottie1_6, lottie1_7, lottie1_8, lottie1_9,
            lottie1_10, lottie1_11, lottie1_12, lottie2_1, lottie2_2,lottie2_3, lottie2_4,lottie2_5, lottie2_6,
            lottie2_7, lottie2_8, lottie2_9, lottie2_10, lottie2_11,lottie2_12,lottie_select_1,lottie_select_2;

    HashMap<Integer, String> brain_pointer = new HashMap<>();
    ArrayList<String> king_traker = new ArrayList<>();
    ArrayList<Integer> piece_to_eat=new ArrayList<>();
    HashMap<String, View> View_to_hide = new HashMap<>();

    View Principal_view;
    ImageButton baida9_to_move = null;

    ArrayList<Integer>    Vtab_2_4=new ArrayList<>(),Vtab_2_25=new ArrayList<>()
            ,Vtab_4_17=new ArrayList<>(),Vtab_4_3=new ArrayList<>()
            ,Vtab_6_9=new ArrayList<>(),Vtab_6_2=new ArrayList<>()
            ,Vtab_8_1=new ArrayList<>()
            ,Htab_3_2=new ArrayList<>(),Htab_3_24=new ArrayList<>()
            ,Htab_5_3=new ArrayList<>(),Htab_5_16=new ArrayList<>()
            ,Htab_7_4=new ArrayList<>(),Htab_7_8=new ArrayList<>();

    boolean player1=true, player2=false;
    String player_1_operation="move",player_2_operation="move";
    ArrayList<Integer> Last_List_of_place_to_move=new ArrayList<>();

    boolean I_am_the_player_1;

    match_running game;
    ArrayList<Integer> places_to_move=new ArrayList<>();

    vibrateur vibrateur;
    Context context;
    boolean auto_clicked=false;
    sharedpreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_table_play, container, false);

       initial_view(view);
        anime_start(new anim_end() {
            @Override
            public void oncall_back() {
                mListener.somethingHappenedInFragment("table_created");
                click_listner();
            }
        });



        return view;
    }
    private void click_listner() {
        l1_c1.setOnClickListener(this);
        l1_c3.setOnClickListener(this);
        l1_c5.setOnClickListener(this);
        l1_c7.setOnClickListener(this);
        l2_c2.setOnClickListener(this);
        l2_c4.setOnClickListener(this);
        l2_c6.setOnClickListener(this);
        l2_c8.setOnClickListener(this);
        l3_c1.setOnClickListener(this);
        l3_c3.setOnClickListener(this);
        l3_c5.setOnClickListener(this);
        l3_c7.setOnClickListener(this);
        l4_c2.setOnClickListener(this);
        l4_c4.setOnClickListener(this);
        l4_c6.setOnClickListener(this);
        l4_c8.setOnClickListener(this);
        l5_c1.setOnClickListener(this);
        l5_c3.setOnClickListener(this);
        l5_c5.setOnClickListener(this);
        l5_c7.setOnClickListener(this);
        l6_c2.setOnClickListener(this);
        l6_c4.setOnClickListener(this);
        l6_c6.setOnClickListener(this);
        l6_c8.setOnClickListener(this);
        l7_c1.setOnClickListener(this);
        l7_c3.setOnClickListener(this);
        l7_c5.setOnClickListener(this);
        l7_c7.setOnClickListener(this);
        l8_c2.setOnClickListener(this);
        l8_c4.setOnClickListener(this);
        l8_c6.setOnClickListener(this);
        l8_c8.setOnClickListener(this);

        player1_1.setOnClickListener(this);
        player1_2.setOnClickListener(this);
        player1_3.setOnClickListener(this);
        player1_4.setOnClickListener(this);
        player1_5.setOnClickListener(this);
        player1_6.setOnClickListener(this);
        player1_7.setOnClickListener(this);
        player1_8.setOnClickListener(this);
        player1_9.setOnClickListener(this);
        player1_10.setOnClickListener(this);
        player1_11.setOnClickListener(this);
        player1_12.setOnClickListener(this);
        player2_1.setOnClickListener(this);
        player2_2.setOnClickListener(this);
        player2_3.setOnClickListener(this);
        player2_4.setOnClickListener(this);
        player2_5.setOnClickListener(this);
        player2_6.setOnClickListener(this);
        player2_7.setOnClickListener(this);
        player2_8.setOnClickListener(this);
        player2_9.setOnClickListener(this);
        player2_10.setOnClickListener(this);
        player2_11.setOnClickListener(this);
        player2_12.setOnClickListener(this);


    }
    private void initial_view(View view) {
        Principal_view = view;
        if(getActivity() instanceof game_space)
            mListener=(game_space)getActivity();
        else if (getActivity() instanceof bluetooth_game_space)
            mListener=(bluetooth_game_space)getActivity();
        else if (getActivity() instanceof game_space_randomly)
            mListener=(game_space_randomly)getActivity();

        sharedpreferences=new sharedpreferences(getContext());

        context=getContext();
        vibrateur=new vibrateur(getContext());

        l1_c1 = view.findViewById(R.id.l1_c1);
        l1_c3 = view.findViewById(R.id.l1_c3);
        l1_c5 = view.findViewById(R.id.l1_c5);
        l1_c7 = view.findViewById(R.id.l1_c7);

        l2_c2 = view.findViewById(R.id.l2_c2);
        l2_c4 = view.findViewById(R.id.l2_c4);
        l2_c6 = view.findViewById(R.id.l2_c6);
        l2_c8 = view.findViewById(R.id.l2_c8);

        l3_c1 = view.findViewById(R.id.l3_c1);
        l3_c3 = view.findViewById(R.id.l3_c3);
        l3_c5 = view.findViewById(R.id.l3_c5);
        l3_c7 = view.findViewById(R.id.l3_c7);

        l4_c2 = view.findViewById(R.id.l4_c2);
        l4_c4 = view.findViewById(R.id.l4_c4);
        l4_c6 = view.findViewById(R.id.l4_c6);
        l4_c8 = view.findViewById(R.id.l4_c8);

        l5_c1 = view.findViewById(R.id.l5_c1);
        l5_c3 = view.findViewById(R.id.l5_c3);
        l5_c5 = view.findViewById(R.id.l5_c5);
        l5_c7 = view.findViewById(R.id.l5_c7);

        l6_c2 = view.findViewById(R.id.l6_c2);
        l6_c4 = view.findViewById(R.id.l6_c4);
        l6_c6 = view.findViewById(R.id.l6_c6);
        l6_c8 = view.findViewById(R.id.l6_c8);

        l7_c1 = view.findViewById(R.id.l7_c1);
        l7_c3 = view.findViewById(R.id.l7_c3);
        l7_c5 = view.findViewById(R.id.l7_c5);
        l7_c7 = view.findViewById(R.id.l7_c7);

        l8_c2 = view.findViewById(R.id.l8_c2);
        l8_c4 = view.findViewById(R.id.l8_c4);
        l8_c6 = view.findViewById(R.id.l8_c6);
        l8_c8 = view.findViewById(R.id.l8_c8);



        l1_c2 = view.findViewById(R.id.l1_c2);
        l1_c4 = view.findViewById(R.id.l1_c4);
        l1_c6 = view.findViewById(R.id.l1_c6);
        l1_c8 = view.findViewById(R.id.l1_c8);

        l2_c1 = view.findViewById(R.id.l2_c1);
        l2_c3 = view.findViewById(R.id.l2_c3);
        l2_c5 = view.findViewById(R.id.l2_c5);
        l2_c7 = view.findViewById(R.id.l2_c7);

        l3_c2 = view.findViewById(R.id.l3_c2);
        l3_c4 = view.findViewById(R.id.l3_c4);
        l3_c6 = view.findViewById(R.id.l3_c6);
        l3_c8 = view.findViewById(R.id.l3_c8);

        l4_c1 = view.findViewById(R.id.l4_c1);
        l4_c3 = view.findViewById(R.id.l4_c3);
        l4_c5 = view.findViewById(R.id.l4_c5);
        l4_c7 = view.findViewById(R.id.l4_c7);

        l5_c2 = view.findViewById(R.id.l5_c2);
        l5_c4 = view.findViewById(R.id.l5_c4);
        l5_c6 = view.findViewById(R.id.l5_c6);
        l5_c8 = view.findViewById(R.id.l5_c8);

        l6_c1 = view.findViewById(R.id.l6_c1);
        l6_c3 = view.findViewById(R.id.l6_c3);
        l6_c5 = view.findViewById(R.id.l6_c5);
        l6_c7 = view.findViewById(R.id.l6_c7);

        l7_c2 = view.findViewById(R.id.l7_c2);
        l7_c4 = view.findViewById(R.id.l7_c4);
        l7_c6 = view.findViewById(R.id.l7_c6);
        l7_c8 = view.findViewById(R.id.l7_c8);

        l8_c1 = view.findViewById(R.id.l8_c1);
        l8_c3 = view.findViewById(R.id.l8_c3);
        l8_c5 = view.findViewById(R.id.l8_c5);
        l8_c7 = view.findViewById(R.id.l8_c7);


        player1_1 = view.findViewById(R.id.b_l1_c1);
        player1_2 = view.findViewById(R.id.b_l1_c3);
        player1_3 = view.findViewById(R.id.b_l1_c5);
        player1_4 = view.findViewById(R.id.b_l1_c7);
        player1_5 = view.findViewById(R.id.b_l2_c2);
        player1_6 = view.findViewById(R.id.b_l2_c4);
        player1_7 = view.findViewById(R.id.b_l2_c6);
        player1_8 = view.findViewById(R.id.b_l2_c8);
        player1_9 = view.findViewById(R.id.b_l3_c1);
        player1_10 = view.findViewById(R.id.b_l3_c3);
        player1_11 = view.findViewById(R.id.b_l3_c5);
        player1_12 = view.findViewById(R.id.b_l3_c7);


        player2_1 = view.findViewById(R.id.b_l6_c2);
        player2_2 = view.findViewById(R.id.b_l6_c4);
        player2_3 = view.findViewById(R.id.b_l6_c6);
        player2_4 = view.findViewById(R.id.b_l6_c8);
        player2_5 = view.findViewById(R.id.b_l7_c1);
        player2_6 = view.findViewById(R.id.b_l7_c3);
        player2_7 = view.findViewById(R.id.b_l7_c5);
        player2_8 = view.findViewById(R.id.b_l7_c7);
        player2_9 = view.findViewById(R.id.b_l8_c2);
        player2_10 = view.findViewById(R.id.b_l8_c4);
        player2_11 = view.findViewById(R.id.b_l8_c6);
        player2_12 = view.findViewById(R.id.b_l8_c8);


        lottie1_1 = view.findViewById(R.id.lottie_1_1);
        lottie1_2 = view.findViewById(R.id.lottie_1_3);
        lottie1_3 = view.findViewById(R.id.lottie_1_5);
        lottie1_4 = view.findViewById(R.id.lottie_1_7);
        lottie1_5 = view.findViewById(R.id.lottie_2_2);
        lottie1_6 = view.findViewById(R.id.lottie_2_4);
        lottie1_7 = view.findViewById(R.id.lottie_2_6);
        lottie1_8 = view.findViewById(R.id.lottie_2_8);
        lottie1_9 = view.findViewById(R.id.lottie_3_1);
        lottie1_10 = view.findViewById(R.id.lottie_3_3);
        lottie1_11 = view.findViewById(R.id.lottie_3_5);
        lottie1_12 = view.findViewById(R.id.lottie_3_7);


        lottie2_1 = view.findViewById(R.id.lottie_6_2);
        lottie2_2 = view.findViewById(R.id.lottie_6_4);
        lottie2_3 = view.findViewById(R.id.lottie_6_6);
        lottie2_4 = view.findViewById(R.id.lottie_6_8);
        lottie2_5 = view.findViewById(R.id.lottie_7_1);
        lottie2_6 = view.findViewById(R.id.lottie_7_3);
        lottie2_7 = view.findViewById(R.id.lottie_7_5);
        lottie2_8 = view.findViewById(R.id.lottie_7_7);
        lottie2_9 = view.findViewById(R.id.lottie_8_2);
        lottie2_10 = view.findViewById(R.id.lottie_8_4);
        lottie2_11 = view.findViewById(R.id.lottie_8_6);
        lottie2_12 = view.findViewById(R.id.lottie_8_8);

        lottie_select_1=view.findViewById(R.id.lottie_select_player1);
        lottie_select_2=view.findViewById(R.id.lottie_select_player2);

        charger_the_brain_pointer();
        charge_the_king_line();
        disabled_all_piece();
    }


    ArrayList<View> to_anime=new ArrayList<>();
    private void anime_start(anim_end anim_end){
        to_anime.add(l1_c1);
        to_anime.add(l8_c8);
        to_anime.add(l1_c2);
        to_anime.add(l8_c7);
        to_anime.add(l1_c3);
        to_anime.add(l8_c6);
        to_anime.add(l1_c4);
        to_anime.add(l8_c5);
        to_anime.add(l1_c5);
        to_anime.add(l8_c4);
        to_anime.add(l1_c6);
        to_anime.add(l8_c3);
        to_anime.add(l1_c7);
        to_anime.add(l8_c2);
        to_anime.add(l1_c8);
        to_anime.add(l8_c1);

        to_anime.add(l2_c1);
        to_anime.add(l7_c8);
        to_anime.add(l2_c2);
        to_anime.add(l7_c7);
        to_anime.add(l2_c3);
        to_anime.add(l7_c6);
        to_anime.add(l2_c4);
        to_anime.add(l7_c5);
        to_anime.add(l2_c5);
        to_anime.add(l7_c4);
        to_anime.add(l2_c6);
        to_anime.add(l7_c3);
        to_anime.add(l2_c7);
        to_anime.add(l7_c2);
        to_anime.add(l2_c8);
        to_anime.add(l7_c1);

        to_anime.add(l3_c1);
        to_anime.add(l6_c8);
        to_anime.add(l3_c2);
        to_anime.add(l6_c7);
        to_anime.add(l3_c3);
        to_anime.add(l6_c6);
        to_anime.add(l3_c4);
        to_anime.add(l6_c5);
        to_anime.add(l3_c5);
        to_anime.add(l6_c4);
        to_anime.add(l3_c6);
        to_anime.add(l6_c3);
        to_anime.add(l3_c7);
        to_anime.add(l6_c2);
        to_anime.add(l3_c8);
        to_anime.add(l6_c1);

        to_anime.add(l4_c1);
        to_anime.add(l5_c8);
        to_anime.add(l4_c2);
        to_anime.add(l5_c7);
        to_anime.add(l4_c3);
        to_anime.add(l5_c6);
        to_anime.add(l4_c4);
        to_anime.add(l5_c5);
        to_anime.add(l4_c5);
        to_anime.add(l5_c4);
        to_anime.add(l4_c6);
        to_anime.add(l5_c3);
        to_anime.add(l4_c7);
        to_anime.add(l5_c2);
        to_anime.add(l4_c8);
        to_anime.add(l5_c1);

        to_anime.add(player1_1 );
        to_anime.add(player2_12);
        to_anime.add(player1_2 );
        to_anime.add(player2_11);
        to_anime.add(player1_3 );
        to_anime.add(player2_10);
        to_anime.add(player1_4 );
        to_anime.add(player2_9 );
        to_anime.add(player1_5 );
        to_anime.add(player2_8 );
        to_anime.add(player1_6 );
        to_anime.add(player2_7 );
        to_anime.add(player1_7 );
        to_anime.add(player2_6 );
        to_anime.add(player1_8 );
        to_anime.add(player2_5 );
        to_anime.add(player1_9 );
        to_anime.add(player2_4 );
        to_anime.add(player1_10);
        to_anime.add(player2_3 );
        to_anime.add(player1_11);
        to_anime.add(player2_2 );
        to_anime.add(player1_12);
        to_anime.add(player2_1 );

        start_anime_table(new anim_end() {
            @Override
            public void oncall_back() {
                anim_end.oncall_back();
            }
        });
    }


    int num=0;
    public void start_anime_table(anim_end anim_end){



        if(num==to_anime.size()-1){
            anim_end.oncall_back();
        }

        View b=to_anime.get(num);

        if(b instanceof ImageButton) {
            animFade = AnimationUtils.loadAnimation(getContext(), R.anim.scale_40);

            animFade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    b.setVisibility(View.VISIBLE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    num++;
                    if(num<to_anime.size())
                        start_anime_table(anim_end);
                    else
                        anim_end.oncall_back();


                }
            });
            b.startAnimation(animFade);
        }
        else {
            animFade = AnimationUtils.loadAnimation(getContext(), R.anim.scale_500);

            animFade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    b.setVisibility(View.VISIBLE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {

                }
            });
            num++;
            if(num<to_anime.size())
                start_anime_table(anim_end);
            else {
                anim_end.oncall_back();
                return;
            }

            b.startAnimation(animFade);
        }



    }

    interface anim_end{
        void oncall_back();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_l1_c1:
                checked_this_piece(v, "player1_" + 1);
                break;
            case R.id.b_l1_c3:
                checked_this_piece(v, "player1_" + 2);
                break;
            case R.id.b_l1_c5:
                checked_this_piece(v, "player1_" + 3);
                break;
            case R.id.b_l1_c7:
                checked_this_piece(v, "player1_" + 4);
                break;
            case R.id.b_l2_c2:
                checked_this_piece(v, "player1_" + 5);
                break;
            case R.id.b_l2_c4:
                checked_this_piece(v, "player1_" + 6);
                break;
            case R.id.b_l2_c6:
                checked_this_piece(v, "player1_" + 7);
                break;
            case R.id.b_l2_c8:
                checked_this_piece(v, "player1_" + 8);
                break;
            case R.id.b_l3_c1:
                checked_this_piece(v, "player1_" + 9);
                break;
            case R.id.b_l3_c3:
                checked_this_piece(v, "player1_" + 10);
                break;
            case R.id.b_l3_c5:
                checked_this_piece(v, "player1_" + 11);
                break;
            case R.id.b_l3_c7:
                checked_this_piece(v, "player1_" + 12);
                break;
            case R.id.b_l6_c2:
                checked_this_piece(v, "player2_" + 1);
                break;
            case R.id.b_l6_c4:
                checked_this_piece(v, "player2_" + 2);
                break;
            case R.id.b_l6_c6:
                checked_this_piece(v, "player2_" + 3);
                break;
            case R.id.b_l6_c8:
                checked_this_piece(v, "player2_" + 4);
                break;
            case R.id.b_l7_c1:
                checked_this_piece(v, "player2_" + 5);
                break;
            case R.id.b_l7_c3:
                checked_this_piece(v, "player2_" + 6);
                break;
            case R.id.b_l7_c5:
                checked_this_piece(v, "player2_" + 7);
                break;
            case R.id.b_l7_c7:
                checked_this_piece(v, "player2_" + 8);
                break;
            case R.id.b_l8_c2:
                checked_this_piece(v, "player2_" + 9);
                break;
            case R.id.b_l8_c4:
                checked_this_piece(v, "player2_" + 10);
                break;
            case R.id.b_l8_c6:
                checked_this_piece(v, "player2_" + 11);
                break;
            case R.id.b_l8_c8:
                checked_this_piece(v, "player2_" + 12);
                break;


            case R.id.l1_c1:
            case R.id.l1_c3:
            case R.id.l1_c5:
            case R.id.l1_c7:
            case R.id.l2_c2:
            case R.id.l2_c4:
            case R.id.l2_c6:
            case R.id.l2_c8:
            case R.id.l3_c1:
            case R.id.l3_c3:
            case R.id.l3_c5:
            case R.id.l3_c7:
            case R.id.l4_c2:
            case R.id.l4_c4:
            case R.id.l4_c6:
            case R.id.l4_c8:
            case R.id.l5_c1:
            case R.id.l5_c3:
            case R.id.l5_c5:
            case R.id.l5_c7:
            case R.id.l6_c2:
            case R.id.l6_c4:
            case R.id.l6_c6:
            case R.id.l6_c8:
            case R.id.l7_c1:
            case R.id.l7_c3:
            case R.id.l7_c5:
            case R.id.l7_c7:
            case R.id.l8_c2:
            case R.id.l8_c4:
            case R.id.l8_c6:
            case R.id.l8_c8:
                move_to_this_place(Principal_view, baida9_to_move, (ImageButton) v,0);
                break;
        }
    }

    private void checked_this_piece(View v, String s) {



        lottie_select_1.setVisibility(View.GONE);
        lottie_select_2.setVisibility(View.GONE);


        ImageButton i = (ImageButton) v;
        if(baida9_to_move==i){
            lottie_select_1.setVisibility(View.GONE);
            lottie_select_2.setVisibility(View.GONE);
            re_show_lottie();
            disabled_all_place(new ArrayList<>());
            unchecked_all_piece();
            return;
        }

        new sound().click(getContext());
        vibrateur.vibrate(100);
        unchecked_all_piece();

        String [] temp = s.split("_");
        game.setPiece(Integer.parseInt(temp[1]));

        if(s.startsWith("player1"))
        {
            if (brain_pointer.containsValue(s)) {
                if(king_traker.contains(s))
                    i.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi_clique));
                else
                    i.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_clique));
            }

            if (player_1_operation.equals("move")) {

                if(king_traker.contains(s))
                    enabled_the_place_to_move(get_all_place_can_King_go_from_this_piece(s));
                else
                    enabled_the_place_to_move(get_all_place_can_go_from_this_piece(s));

                baida9_to_move = i;
            }
            else if (player_1_operation.equals("eat")){
                HashMap<String, ArrayList<Integer>> eat_from_this_place = new HashMap<>();

                for (Map.Entry mapentry : brain_pointer.entrySet()) {
                    if (mapentry.getValue() != null) {
                        if (mapentry.getValue().toString().equals(s)) {
                            eat_from_this_place=check_some_things_to_eats_player1_from_this_exact_place((int) mapentry.getKey());
                        }
                    }
                }

                ArrayList<String> piece=new ArrayList<>();
                ArrayList<Integer> places=new ArrayList<>();


                for (Map.Entry mapentry : eat_from_this_place.entrySet()) {
                    ArrayList<Integer> ace= (ArrayList<Integer>) mapentry.getValue();
                    for (int a:ace){
                        if(!places.contains(a))
                            places.add(a);
                    }
                    ace.clear();

                    piece.add((String) mapentry.getKey());
                }

                Log.e("place",places.toString());
                enabled_piece_and_place_to_eat(places,piece);
                baida9_to_move = i;
            }

            if(sharedpreferences.get_setting_type("first_click_to_move_player_1")) {
                help_me();
                sharedpreferences.put_setting_type("first_click_to_move_player_1",false);
            }

        }
        else if(s.startsWith("player2"))
        {
            if (brain_pointer.containsValue(s)) {
                if (king_traker.contains(s))
                    i.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi_clique));
                else
                    i.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_clique));
            }

            if(player_2_operation.equals("move")) {

                if(king_traker.contains(s))
                    enabled_the_place_to_move(get_all_place_can_King_go_from_this_piece(s));
                else
                    enabled_the_place_to_move(get_all_place_can_go_from_this_piece(s));
                baida9_to_move = i;
            }else if (player_2_operation.equals("eat")){


                HashMap<String, ArrayList<Integer>> eat_from_this_place = new HashMap<>();

                for (Map.Entry mapentry : brain_pointer.entrySet()) {
                    if (mapentry.getValue() != null) {
                        if (mapentry.getValue().toString().equals(s)) {
                            eat_from_this_place=check_some_things_to_eats_player2_from_this_exact_place((int) mapentry.getKey());
                        }
                    }
                }

                ArrayList<String> piece=new ArrayList<>();
                ArrayList<Integer> places=new ArrayList<>();

                for (Map.Entry mapentry : eat_from_this_place.entrySet()) {
                    ArrayList<Integer> ace= (ArrayList<Integer>) mapentry.getValue();
                    for (int a:ace){
                        if(!places.contains(a))
                            places.add(a);
                    }
                    ace.clear();

                    piece.add((String) mapentry.getKey());
                }

                enabled_piece_and_place_to_eat(places,piece);
                baida9_to_move = i;
            }

            if(sharedpreferences.get_setting_type("first_click_to_move_player_2")) {
                help_me();
                sharedpreferences.put_setting_type("first_click_to_move_player_2",false);
            }

        }

        gone_all_lottie();


    }
    public void re_show_lottie(){
        if(player1_1.isEnabled()) {
            lottie1_1.setVisibility(View.VISIBLE);
            lottie1_1.playAnimation();
        }
        if(player1_2.isEnabled()) {
            lottie1_2.setVisibility(View.VISIBLE);
            lottie1_2.playAnimation();
        }
        if(player1_3.isEnabled()) {
            lottie1_3.setVisibility(View.VISIBLE);
            lottie1_3.playAnimation();
        }
        if(player1_4.isEnabled()) {
            lottie1_4.setVisibility(View.VISIBLE);
            lottie1_4.playAnimation();
        }
        if(player1_5.isEnabled()) {
            lottie1_5.setVisibility(View.VISIBLE);
            lottie1_5.playAnimation();
        }
        if(player1_6.isEnabled()) {
            lottie1_6.setVisibility(View.VISIBLE);
            lottie1_6.playAnimation();
        }
        if(player1_7.isEnabled()) {
            lottie1_7.setVisibility(View.VISIBLE);
            lottie1_7.playAnimation();
        }
        if(player1_8.isEnabled()) {
            lottie1_8.setVisibility(View.VISIBLE);
            lottie1_8.playAnimation();
        }
        if(player1_9.isEnabled()) {
            lottie1_9.setVisibility(View.VISIBLE);
            lottie1_9.playAnimation();
        }
        if(player1_10.isEnabled()) {
            lottie1_10.setVisibility(View.VISIBLE);
            lottie1_10.playAnimation();
        }
        if(player1_11.isEnabled()) {
            lottie1_11.setVisibility(View.VISIBLE);
            lottie1_11.playAnimation();
        }
        if(player1_12.isEnabled()) {
            lottie1_12.setVisibility(View.VISIBLE);
            lottie1_12.playAnimation();
        }

        if(player2_1.isEnabled()) {
            lottie2_1.setVisibility(View.VISIBLE);
            lottie2_1.playAnimation();
        }
        if(player2_2.isEnabled()) {
            lottie2_2.setVisibility(View.VISIBLE);
            lottie2_2.playAnimation();
        }
        if(player2_3.isEnabled()) {
            lottie2_3.setVisibility(View.VISIBLE);
            lottie2_3.playAnimation();
        }
        if(player2_4.isEnabled()) {
            lottie2_4.setVisibility(View.VISIBLE);
            lottie2_4.playAnimation();
        }
        if(player2_5.isEnabled()) {
            lottie2_5.setVisibility(View.VISIBLE);
            lottie2_5.playAnimation();
        }
        if(player2_6.isEnabled()) {
            lottie2_6.setVisibility(View.VISIBLE);
            lottie2_6.playAnimation();
        }
        if(player2_7.isEnabled()) {
            lottie2_7.setVisibility(View.VISIBLE);
            lottie2_7.playAnimation();
        }
        if(player2_8.isEnabled()) {
            lottie2_8.setVisibility(View.VISIBLE);
            lottie2_8.playAnimation();
        }
        if(player2_9.isEnabled()) {
            lottie2_9.setVisibility(View.VISIBLE);
            lottie2_9.playAnimation();
        }
        if(player2_10.isEnabled()) {
            lottie2_10.setVisibility(View.VISIBLE);
            lottie2_10.playAnimation();
        }
        if(player2_11.isEnabled()) {
            lottie2_11.setVisibility(View.VISIBLE);
            lottie2_11.playAnimation();
        }
        if(player2_12.isEnabled()) {
            lottie2_12.setVisibility(View.VISIBLE);
            lottie2_12.playAnimation();
        }
    }


    private ArrayList<Integer> get_all_place_can_go_from_this_piece(String piece) {

        int a = 0;

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().equals(piece))
                    a = (int) mapentry.getKey();
            }
        }

        ArrayList<Integer> place_available = new ArrayList<>();


        if (player1) {
            if (a == 1 || a == 9 || a == 17 || a == 25 || a == 8 || a == 16 || a == 24) {
                place_available = new ArrayList<>();
                if (brain_pointer.get(a + 4) == null)
                    place_available.add(a + 4);
            } else if (a == 2 || a == 3 || a == 4 || a == 10 || a == 11 || a == 12 || a == 18 || a == 19 || a == 20 || a == 26 || a == 27 || a == 28) {
                place_available = new ArrayList<>();
                if (brain_pointer.get(a + 3) == null)
                    place_available.add(a + 3);
                if (brain_pointer.get(a + 4) == null)
                    place_available.add(a + 4);
            } else if (a == 5 || a == 6 || a == 7 || a == 13 || a == 14 || a == 15 || a == 21 || a == 22 || a == 23) {
                place_available = new ArrayList<>();
                if (brain_pointer.get(a + 5) == null)
                    place_available.add(a + 5);
                if (brain_pointer.get(a + 4) == null)
                    place_available.add(a + 4);
            }
        } else if (player2) {
            if (a == 32 || a == 9 || a == 17 || a == 25 || a == 8 || a == 16 || a == 24) {
                place_available = new ArrayList<>();
                if (brain_pointer.get(a - 4) == null)
                    place_available.add(a - 4);
            } else if (a == 29 || a == 30 || a == 31 || a == 21 || a == 22 || a == 23 || a == 13 || a == 14 || a == 15 || a == 5 || a == 6 || a == 7) {
                place_available = new ArrayList<>();
                if (brain_pointer.get(a - 3) == null)
                    place_available.add(a - 3);
                if (brain_pointer.get(a - 4) == null)
                    place_available.add(a - 4);
            } else if (a == 26 || a == 27 || a == 28 || a == 18 || a == 19 || a == 20 || a == 10 || a == 11 || a == 12) {
                place_available = new ArrayList<>();
                if (brain_pointer.get(a - 5) == null)
                    place_available.add(a - 5);
                if (brain_pointer.get(a - 4) == null)
                    place_available.add(a - 4);
            }
        }


        return place_available;
    }
    private void enabled_the_place_to_move(ArrayList<Integer> place) {


        ArrayList<Integer> deja_enabled=new ArrayList<>();
        for(Integer a:place){
            if(Last_List_of_place_to_move.contains(a))
                deja_enabled.add(a);
        }
        disabled_all_place(deja_enabled);

        for (int a : place) {
            switch (a) {
                case 1:
                    l1_c1.setEnabled(true);
                    break;
                case 2:
                    l1_c3.setEnabled(true);
                    break;
                case 3:
                    l1_c5.setEnabled(true);
                    break;
                case 4:
                    l1_c7.setEnabled(true);
                    break;
                case 5:
                    l2_c2.setEnabled(true);
                    break;
                case 6:
                    l2_c4.setEnabled(true);
                    break;
                case 7:
                    l2_c6.setEnabled(true);
                    break;
                case 8:
                    l2_c8.setEnabled(true);
                    break;
                case 9:
                    l3_c1.setEnabled(true);
                    break;
                case 10:
                    l3_c3.setEnabled(true);
                    break;
                case 11:
                    l3_c5.setEnabled(true);
                    break;
                case 12:
                    l3_c7.setEnabled(true);
                    break;
                case 13:
                    l4_c2.setEnabled(true);
                    break;
                case 14:
                    l4_c4.setEnabled(true);
                    break;
                case 15:
                    l4_c6.setEnabled(true);
                    break;
                case 16:
                    l4_c8.setEnabled(true);
                    break;
                case 17:
                    l5_c1.setEnabled(true);
                    break;
                case 18:
                    l5_c3.setEnabled(true);
                    break;
                case 19:
                    l5_c5.setEnabled(true);
                    break;
                case 20:
                    l5_c7.setEnabled(true);
                    break;
                case 21:
                    l6_c2.setEnabled(true);
                    break;
                case 22:
                    l6_c4.setEnabled(true);
                    break;
                case 23:
                    l6_c6.setEnabled(true);
                    break;
                case 24:
                    l6_c8.setEnabled(true);
                    break;
                case 25:
                    l7_c1.setEnabled(true);
                    break;
                case 26:
                    l7_c3.setEnabled(true);
                    break;
                case 27:
                    l7_c5.setEnabled(true);
                    break;
                case 28:
                    l7_c7.setEnabled(true);
                    break;
                case 29:
                    l8_c2.setEnabled(true);
                    break;
                case 30:
                    l8_c4.setEnabled(true);
                    break;
                case 31:
                    l8_c6.setEnabled(true);
                    break;
                case 32:
                    l8_c8.setEnabled(true);
                    break;
            }
        }


        Last_List_of_place_to_move=place;
    }
    private void enabled_pieces_can_eat(ArrayList<String> place) {
        for (String plc:place){
                switch (plc) {
                    case "player1_1":
                        player1_1.setEnabled(true);
                        lottie1_1.setVisibility(View.VISIBLE);
                        lottie1_1.playAnimation();
                        break;
                    case "player1_2":
                        player1_2.setEnabled(true);
                        lottie1_2.setVisibility(View.VISIBLE);
                        lottie1_2.playAnimation();
                        break;
                    case "player1_3":
                        player1_3.setEnabled(true);
                        lottie1_3.setVisibility(View.VISIBLE);
                        lottie1_3.playAnimation();
                        break;
                    case "player1_4":
                        player1_4.setEnabled(true);
                        lottie1_4.setVisibility(View.VISIBLE);
                        lottie1_4.playAnimation();
                        break;
                    case "player1_5":
                        player1_5.setEnabled(true);
                        lottie1_5.setVisibility(View.VISIBLE);
                        lottie1_5.playAnimation();
                        break;
                    case "player1_6":
                        player1_6.setEnabled(true);
                        lottie1_6.setVisibility(View.VISIBLE);
                        lottie1_6.playAnimation();
                        break;
                    case "player1_7":
                        player1_7.setEnabled(true);
                        lottie1_7.setVisibility(View.VISIBLE);
                        lottie1_7.playAnimation();
                        break;
                    case "player1_8":
                        player1_8.setEnabled(true);
                        lottie1_8.setVisibility(View.VISIBLE);
                        lottie1_8.playAnimation();
                        break;
                    case "player1_9":
                        player1_9.setEnabled(true);
                        lottie1_9.setVisibility(View.VISIBLE);
                        lottie1_9.playAnimation();
                        break;
                    case "player1_10":
                        player1_10.setEnabled(true);
                        lottie1_10.setVisibility(View.VISIBLE);
                        lottie1_10.playAnimation();
                        break;
                    case "player1_11":
                        player1_11.setEnabled(true);
                        lottie1_11.setVisibility(View.VISIBLE);
                        lottie1_11.playAnimation();
                        break;
                    case "player1_12":
                        player1_12.setEnabled(true);
                        lottie1_12.setVisibility(View.VISIBLE);
                        lottie1_12.playAnimation();
                        break;
                    case "player2_1":
                        player2_1.setEnabled(true);
                        lottie2_1.setVisibility(View.VISIBLE);
                        lottie2_1.playAnimation();
                        break;
                    case "player2_2":
                        player2_2.setEnabled(true);
                        lottie2_2.setVisibility(View.VISIBLE);
                        lottie2_2.playAnimation();
                        break;
                    case "player2_3":
                        player2_3.setEnabled(true);
                        lottie2_3.setVisibility(View.VISIBLE);
                        lottie2_3.playAnimation();
                        break;
                    case "player2_4":
                        player2_4.setEnabled(true);
                        lottie2_4.setVisibility(View.VISIBLE);
                        lottie2_4.playAnimation();
                        break;
                    case "player2_5":
                        player2_5.setEnabled(true);
                        lottie2_5.setVisibility(View.VISIBLE);
                        lottie2_5.playAnimation();
                        break;
                    case "player2_6":
                        player2_6.setEnabled(true);
                        lottie2_6.setVisibility(View.VISIBLE);
                        lottie2_6.playAnimation();
                        break;
                    case "player2_7":
                        player2_7.setEnabled(true);
                        lottie2_7.setVisibility(View.VISIBLE);
                        lottie2_7.playAnimation();
                        break;
                    case "player2_8":
                        player2_8.setEnabled(true);
                        lottie2_8.setVisibility(View.VISIBLE);
                        lottie2_8.playAnimation();
                        break;
                    case "player2_9":
                        player2_9.setEnabled(true);
                        lottie2_9.setVisibility(View.VISIBLE);
                        lottie2_9.playAnimation();
                        break;
                    case "player2_10":
                        player2_10.setEnabled(true);
                        lottie2_10.setVisibility(View.VISIBLE);
                        lottie2_10.playAnimation();
                        break;
                    case "player2_11":
                        player2_11.setEnabled(true);
                        lottie2_11.setVisibility(View.VISIBLE);
                        lottie2_11.playAnimation();
                        break;
                    case "player2_12":
                        player2_12.setEnabled(true);
                        lottie2_12.setVisibility(View.VISIBLE);
                        lottie2_12.playAnimation();
                        break;

                }
        }
    }
    private void move_to_this_place(View view, ImageButton piece_to_move, ImageButton place,int role) {

        lottie_select_1.setVisibility(View.GONE);
        lottie_select_2.setVisibility(View.GONE);

        if (piece_to_move!=null){

            ConstraintLayout constraintLayout = view.findViewById(R.id.table);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);


            constraintSet.connect(piece_to_move.getId(), ConstraintSet.END, place.getId(), ConstraintSet.END, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.START, place.getId(), ConstraintSet.START, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.TOP, place.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.BOTTOM, place.getId(), ConstraintSet.BOTTOM, 0);

            ChangeBounds transition = new ChangeBounds();
            transition.setDuration(500);
            scale_out_and_in(piece_to_move);

            TransitionManager.beginDelayedTransition(constraintLayout, transition);


            constraintSet.applyTo(constraintLayout);
            Update_the_brain_pointer(place, role);
            vibrateur.vibrate(500);
        }else {

             Toast.makeText(getContext(), "you don't select any piece yet !!", Toast.LENGTH_SHORT).show();
        }

    }

    public void scale_out_and_in(final View b){
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.scale_250);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation){

                animFade = AnimationUtils.loadAnimation(getContext(), R.anim.scale_250_in);

                animFade.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {}
                    public void onAnimationRepeat(Animation animation) {}
                    public void onAnimationEnd(Animation animation){}

                });
                b.startAnimation(animFade);
            }

        });
        b.startAnimation(animFade);

    }


    private void Update_the_brain_pointer(ImageButton place ,int role) {
        int the_place = 0;
        switch (place.getId()) {
            case R.id.l1_c1:
                the_place = 1;
                break;
            case R.id.l1_c3:
                the_place = 2;
                break;
            case R.id.l1_c5:
                the_place = 3;
                break;
            case R.id.l1_c7:
                the_place = 4;
                break;
            case R.id.l2_c2:
                the_place = 5;
                break;
            case R.id.l2_c4:
                the_place = 6;
                break;
            case R.id.l2_c6:
                the_place = 7;
                break;
            case R.id.l2_c8:
                the_place = 8;
                break;
            case R.id.l3_c1:
                the_place = 9;
                break;
            case R.id.l3_c3:
                the_place = 10;
                break;
            case R.id.l3_c5:
                the_place = 11;
                break;
            case R.id.l3_c7:
                the_place = 12;
                break;
            case R.id.l4_c2:
                the_place = 13;
                break;
            case R.id.l4_c4:
                the_place = 14;
                break;
            case R.id.l4_c6:
                the_place = 15;
                break;
            case R.id.l4_c8:
                the_place = 16;
                break;
            case R.id.l5_c1:
                the_place = 17;
                break;
            case R.id.l5_c3:
                the_place = 18;
                break;
            case R.id.l5_c5:
                the_place = 19;
                break;
            case R.id.l5_c7:
                the_place = 20;
                break;
            case R.id.l6_c2:
                the_place = 21;
                break;
            case R.id.l6_c4:
                the_place = 22;
                break;
            case R.id.l6_c6:
                the_place = 23;
                break;
            case R.id.l6_c8:
                the_place = 24;
                break;
            case R.id.l7_c1:
                the_place = 25;
                break;
            case R.id.l7_c3:
                the_place = 26;
                break;
            case R.id.l7_c5:
                the_place = 27;
                break;
            case R.id.l7_c7:
                the_place = 28;
                break;
            case R.id.l8_c2:
                the_place = 29;
                break;
            case R.id.l8_c4:
                the_place = 30;
                break;
            case R.id.l8_c6:
                the_place = 31;
                break;
            case R.id.l8_c8:
                the_place = 32;
                break;
        }


        places_to_move.add(the_place);


        String the_piece = "";
        switch (baida9_to_move.getId()) {
            case R.id.b_l1_c1:
                the_piece = "player1_" + 1;
                break;
            case R.id.b_l1_c3:
                the_piece = "player1_" + 2;
                break;
            case R.id.b_l1_c5:
                the_piece = "player1_" + 3;
                break;
            case R.id.b_l1_c7:
                the_piece = "player1_" + 4;
                break;
            case R.id.b_l2_c2:
                the_piece = "player1_" + 5;
                break;
            case R.id.b_l2_c4:
                the_piece = "player1_" + 6;
                break;
            case R.id.b_l2_c6:
                the_piece = "player1_" + 7;
                break;
            case R.id.b_l2_c8:
                the_piece = "player1_" + 8;
                break;
            case R.id.b_l3_c1:
                the_piece = "player1_" + 9;
                break;
            case R.id.b_l3_c3:
                the_piece = "player1_" + 10;
                break;
            case R.id.b_l3_c5:
                the_piece = "player1_" + 11;
                break;
            case R.id.b_l3_c7:
                the_piece = "player1_" + 12;
                break;
            case R.id.b_l6_c2:
                the_piece = "player2_" + 1;
                break;
            case R.id.b_l6_c4:
                the_piece = "player2_" + 2;
                break;
            case R.id.b_l6_c6:
                the_piece = "player2_" + 3;
                break;
            case R.id.b_l6_c8:
                the_piece = "player2_" + 4;
                break;
            case R.id.b_l7_c1:
                the_piece = "player2_" + 5;
                break;
            case R.id.b_l7_c3:
                the_piece = "player2_" + 6;
                break;
            case R.id.b_l7_c5:
                the_piece = "player2_" + 7;
                break;
            case R.id.b_l7_c7:
                the_piece = "player2_" + 8;
                break;
            case R.id.b_l8_c2:
                the_piece = "player2_" + 9;
                break;
            case R.id.b_l8_c4:
                the_piece = "player2_" + 10;
                break;
            case R.id.b_l8_c6:
                the_piece = "player2_" + 11;
                break;
            case R.id.b_l8_c8:
                the_piece = "player2_" + 12;
                break;
        }

        
        int key = 0;

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().equals(the_piece)) {
                    key = (int) mapentry.getKey();
                }
            }
        }


        brain_pointer.remove(key);
        brain_pointer.put(key, null);

        brain_pointer.remove(the_place);
        brain_pointer.put(the_place, the_piece);


        //Log.e("in ",""+the_place+" there is "+brain_pointer.get(place));

        if((player_1_operation.equals("eat") && player1)||(player_2_operation.equals("eat") && player2))
        {
            new sound().swip_to_eat(getContext());
            game.setOperation(1);
            int a=get_the_place_of_piece_eated(key, the_place);
            Log.e("delet_the piece in : ",""+a);
            add_to_delete(a);

            if(player1)
                player_1_next_to_eat(the_place,role);
            else
                player_2_next_to_eat(the_place,role);
        }
        else if((player_1_operation.equals("move") && player1)||(player_2_operation.equals("move") && player2)) {
            new sound().swip_to_move(getContext());
            game.setOperation(0);
            //change_the_role();
            piece_to_eat.clear();
            clear();

            if(role==0) {
                if(I_am_the_player_1){
                    int a=detect_if_user_1_win();
                    game.setPlace(places_to_move);
                    Log.e("place to move", "" + places_to_move.size());
                    mListener.somethingHappenedInFragment(game);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(a==1)
                        { mListener.somethingHappenedInFragment(0);
                        }
                    }, places_to_move.size()*500);

                }else {
                    int a=detect_if_user_2_win();
                    game.setPlace(places_to_move);
                    Log.e("place to move", "" + places_to_move.size());
                    mListener.somethingHappenedInFragment(game);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(a==1)
                        { mListener.somethingHappenedInFragment(1);
                        }
                    }, places_to_move.size()*500);
                }

            }


        }
        if ((the_place ==29 || the_place==30 || the_place==31 || the_place==32 || the_place==1 || the_place==2 || the_place==3 || the_place==4)
                && !king_traker.contains(the_piece)) {
            king_traker.add(the_piece);
            clear();
        }




    }

    private int get_the_place_of_piece_eated(int key, int the_place) {

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if ((int)mapentry.getKey() == the_place) {
                for(String str:king_traker) {
                    if (mapentry.getValue().toString().equals(str)) {

                        ArrayList<ArrayList<Integer>> f=get_king_lines(the_place);
                        ArrayList<ArrayList<Integer>> s=get_king_lines(key);
                        ArrayList<Integer> line;
                        if (f.get(0).equals(s.get(0)))
                            line=f.get(0);
                        else
                            line=f.get(1);

                        Log.e("line of operation",line.toString()+"");
                        int new_place=line.indexOf(the_place);
                        int last_place=line.indexOf(key);
                        Log.e("my last value is",key+" index is "+line.indexOf(key));
                        Log.e("my new value is",the_place+" index is "+line.indexOf(the_place));
//
                        if(new_place<last_place){
                            for(int a=new_place;a<last_place ;a++){
                                 if(brain_pointer.get(line.get(a))!=null && a!=new_place)
                                      return line.get(a);
                            }
                        }else if(new_place>last_place){
                            for(int a=last_place;a<new_place ;a++){
                                if(brain_pointer.get(line.get(a))!=null && a!=last_place)
                                    return line.get(a);
                            }
                        }
                    }
                }
            }
        }

        if (key == 1 || key == 2 || key == 3 || key == 4 || key == 9 || key == 10 || key == 11 || key == 12 ||
                key == 17 || key == 18 || key == 19 || key == 20 || key == 25 || key == 26 || key == 27 || key == 28 )
            return ((key+the_place)/2);
        else
            return (((key+the_place)/2)+1);

    }



    private void charger_the_brain_pointer() {
        for (int i = 1; i < 33; i++) {
            if (i < 13)
                brain_pointer.put(i, "player1_" + i);
            else if (i < 21)
                brain_pointer.put(i, null);
            else
                brain_pointer.put(i, "player2_" + (i - 20));
        }
    }



    private void delete_biade9_mort() {
        for (Map.Entry mapentry : View_to_hide.entrySet()) {
            if(mapentry.getValue()!=null)
                hide_this_button((View) mapentry.getValue());
        }

        for(int a:piece_to_eat){
            brain_pointer.remove(a);
            brain_pointer.put(a, null);
        }

        piece_to_eat.clear();
        View_to_hide.clear();
    }




    private void unchecked_all_piece() {

        ArrayList<String> pieces_was_deleted=new ArrayList<>();

        for (Map.Entry mapentry : View_to_hide.entrySet()) {
                pieces_was_deleted.add(mapentry.getKey().toString());
        }


        if(pieces_was_deleted.contains("player1_1"))
            player1_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_1"))
            player1_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_2"))
            player1_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_2"))
            player1_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_3"))
            player1_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_3"))
            player1_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_4"))
            player1_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_4"))
            player1_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_5"))
            player1_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_5"))
            player1_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_6"))
            player1_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_6"))
            player1_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_7"))
            player1_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_7"))
            player1_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_8"))
            player1_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_8"))
            player1_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_9"))
            player1_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_9"))
            player1_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_10"))
            player1_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_10"))
            player1_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_11"))
            player1_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_11"))
            player1_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player1_12"))
            player1_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player1_12"))
            player1_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi));
        else
            player1_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1));

        if(pieces_was_deleted.contains("player2_1"))
            player2_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_1"))
            player2_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_2"))
            player2_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_2"))
            player2_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_3"))
            player2_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_3"))
            player2_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_4"))
            player2_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_4"))
            player2_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_5"))
            player2_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_5"))
            player2_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_6"))
            player2_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_6"))
            player2_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_7"))
            player2_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_7"))
            player2_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_8"))
            player2_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_8"))
            player2_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_9"))
            player2_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_9"))
            player2_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_10"))
            player2_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_10"))
            player2_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_11"))
            player2_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_11"))
            player2_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));

        if(pieces_was_deleted.contains("player2_12"))
            player2_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
        else if(king_traker.contains("player2_12"))
            player2_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi));
        else
            player2_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2));




        baida9_to_move=null;
    }
    private void gone_all_lottie(){
        lottie1_1.setVisibility(View.INVISIBLE);
        lottie1_2.setVisibility(View.INVISIBLE);
        lottie1_3.setVisibility(View.INVISIBLE);
        lottie1_4.setVisibility(View.INVISIBLE);
        lottie1_5.setVisibility(View.INVISIBLE);
        lottie1_6.setVisibility(View.INVISIBLE);
        lottie1_7.setVisibility(View.INVISIBLE);
        lottie1_8.setVisibility(View.INVISIBLE);
        lottie1_9.setVisibility(View.INVISIBLE);
        lottie1_10.setVisibility(View.INVISIBLE);
        lottie1_11.setVisibility(View.INVISIBLE);
        lottie1_12.setVisibility(View.INVISIBLE);
        lottie2_1.setVisibility(View.INVISIBLE);
        lottie2_2.setVisibility(View.INVISIBLE);
        lottie2_3.setVisibility(View.INVISIBLE);
        lottie2_4.setVisibility(View.INVISIBLE);
        lottie2_5.setVisibility(View.INVISIBLE);
        lottie2_6.setVisibility(View.INVISIBLE);
        lottie2_7.setVisibility(View.INVISIBLE);
        lottie2_8.setVisibility(View.INVISIBLE);
        lottie2_9.setVisibility(View.INVISIBLE);
        lottie2_10.setVisibility(View.INVISIBLE);
        lottie2_11.setVisibility(View.INVISIBLE);
        lottie2_12.setVisibility(View.INVISIBLE);
        lottie1_1.pauseAnimation();
        lottie1_2.pauseAnimation();
        lottie1_3.pauseAnimation();
        lottie1_4.pauseAnimation();
        lottie1_5.pauseAnimation();
        lottie1_6.pauseAnimation();
        lottie1_7.pauseAnimation();
        lottie1_8.pauseAnimation();
        lottie1_9.pauseAnimation();
        lottie1_10.pauseAnimation();
        lottie1_11.pauseAnimation();
        lottie1_12.pauseAnimation();

        lottie2_1.pauseAnimation();
        lottie2_2.pauseAnimation();
        lottie2_3.pauseAnimation();
        lottie2_4.pauseAnimation();
        lottie2_5.pauseAnimation();
        lottie2_6.pauseAnimation();
        lottie2_7.pauseAnimation();
        lottie2_8.pauseAnimation();
        lottie2_9.pauseAnimation();
        lottie2_10.pauseAnimation();
        lottie2_11.pauseAnimation();
        lottie2_12.pauseAnimation();

    }
    private void disabled_all_piece() {
        player1_1.setEnabled(false);
        player1_2.setEnabled(false);
        player1_3.setEnabled(false);
        player1_4.setEnabled(false);
        player1_5.setEnabled(false);
        player1_6.setEnabled(false);
        player1_7.setEnabled(false);
        player1_8.setEnabled(false);
        player1_9.setEnabled(false);
        player1_10.setEnabled(false);
        player1_11.setEnabled(false);
        player1_12.setEnabled(false);
        player2_1.setEnabled(false);
        player2_2.setEnabled(false);
        player2_3.setEnabled(false);
        player2_4.setEnabled(false);
        player2_5.setEnabled(false);
        player2_6.setEnabled(false);
        player2_7.setEnabled(false);
        player2_8.setEnabled(false);
        player2_9.setEnabled(false);
        player2_10.setEnabled(false);
        player2_11.setEnabled(false);
        player2_12.setEnabled(false);
    }
    private void disabled_all_place(ArrayList<Integer> deja_enabled)  {
        if(!deja_enabled.contains(1))
            l1_c1.setEnabled(false);
        if(!deja_enabled.contains(2))
            l1_c3.setEnabled(false);
        if(!deja_enabled.contains(3))
            l1_c5.setEnabled(false);
        if(!deja_enabled.contains(4))
            l1_c7.setEnabled(false);
        if(!deja_enabled.contains(5))
            l2_c2.setEnabled(false);
        if(!deja_enabled.contains(6))
            l2_c4.setEnabled(false);
        if(!deja_enabled.contains(7))
            l2_c6.setEnabled(false);
        if(!deja_enabled.contains(8))
            l2_c8.setEnabled(false);
        if(!deja_enabled.contains(9))
            l3_c1.setEnabled(false);
        if(!deja_enabled.contains(10))
            l3_c3.setEnabled(false);
        if(!deja_enabled.contains(11))
            l3_c5.setEnabled(false);
        if(!deja_enabled.contains(12))
            l3_c7.setEnabled(false);
        if(!deja_enabled.contains(13))
            l4_c2.setEnabled(false);
        if(!deja_enabled.contains(14))
            l4_c4.setEnabled(false);
        if(!deja_enabled.contains(15))
            l4_c6.setEnabled(false);
        if(!deja_enabled.contains(16))
            l4_c8.setEnabled(false);
        if(!deja_enabled.contains(17))
            l5_c1.setEnabled(false);
        if(!deja_enabled.contains(18))
            l5_c3.setEnabled(false);
        if(!deja_enabled.contains(19))
            l5_c5.setEnabled(false);
        if(!deja_enabled.contains(20))
            l5_c7.setEnabled(false);
        if(!deja_enabled.contains(21))
            l6_c2.setEnabled(false);
        if(!deja_enabled.contains(22))
            l6_c4.setEnabled(false);
        if(!deja_enabled.contains(23))
            l6_c6.setEnabled(false);
        if(!deja_enabled.contains(24))
            l6_c8.setEnabled(false);
        if(!deja_enabled.contains(25))
            l7_c1.setEnabled(false);
        if(!deja_enabled.contains(26))
            l7_c3.setEnabled(false);
        if(!deja_enabled.contains(27))
            l7_c5.setEnabled(false);
        if(!deja_enabled.contains(28))
            l7_c7.setEnabled(false);
        if(!deja_enabled.contains(29))
            l8_c2.setEnabled(false);
        if(!deja_enabled.contains(30))
            l8_c4.setEnabled(false);
        if(!deja_enabled.contains(31))
            l8_c6.setEnabled(false);
        if(!deja_enabled.contains(32))
            l8_c8.setEnabled(false);
    }


    private void player_1_helper(){
        HashMap<String, ArrayList<Integer>> eats=check_some_things_to_eats_player1();


        if(eats.size()==0)
        {
           // enabled_piece_of_this_player("player1");
            ArrayList<String> pieces=new ArrayList<>();
            for (String f:get_all_piece_can_move_for_this_play("player1")){
                if(get_all_place_can_go_from_this_piece(f).size()!=0)
                    pieces.add(f);
            }


            for (String n:king_traker){
                if(n.startsWith("player1")) {
                  if(get_all_place_can_King_go_from_this_piece(n).size()!=0){
                      pieces.add(n);
                  }
                }
            }

            enabled_pieces_can_eat(pieces);
            player_1_operation="move";

        }
        else
            {
            player_1_operation="eat";
            time_to_eat();

            ArrayList<String> piece=new ArrayList<>();
            ArrayList<Integer> place=new ArrayList<>();

            for (Map.Entry mapentry : eats.entrySet()) {
                ArrayList<Integer> ace= (ArrayList<Integer>) mapentry.getValue();
                for (int a:ace){
                    if(!place.contains(a))
                        place.add(a);
                }
                ace.clear();

                piece.add((String) mapentry.getKey());
            }

            //////////
            Log.e("some_things",place.toString());
            //////////
            //enabled_piece_and_place_to_eat(place,piece);
                enabled_pieces_can_eat(piece);
        }
    }

    private void player_2_helper(){
         HashMap<String, ArrayList<Integer>> eats=check_some_things_to_eats_player2();
         if(eats.size()==0){
           // enabled_piece_of_this_player("player2");
             ArrayList<String> pieces=new ArrayList<>();
             for (String f:get_all_piece_can_move_for_this_play("player2")){
                 if(get_all_place_can_go_from_this_piece(f).size()!=0)
                     pieces.add(f);
             }

             for (String n:king_traker){
                 if(n.startsWith("player2")) {
                     if(get_all_place_can_King_go_from_this_piece(n).size()!=0){
                         pieces.add(n);
                     }
                 }
             }

             enabled_pieces_can_eat(pieces);
            player_2_operation="move";

         }else{
             player_2_operation="eat";
             time_to_eat();

             ArrayList<String> piece=new ArrayList<>();
             ArrayList<Integer> place=new ArrayList<>();

             for (Map.Entry mapentry : eats.entrySet()) {
                 ArrayList<Integer> ace= (ArrayList<Integer>) mapentry.getValue();
                 for (int a:ace){
                     if(!place.contains(a))
                         place.add(a);
                 }
                 ace.clear();

                 piece.add((String) mapentry.getKey());
             }

             //enabled_piece_and_place_to_eat(place,piece);
             enabled_pieces_can_eat(piece);
         }
    }

    private void add_to_delete(int a){
       String name_piece=brain_pointer.get(a);


        assert name_piece != null;
        switch (name_piece) {
            case "player1_1":
                player1_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_2":
                player1_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_3":
                player1_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_4":
                player1_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_5":
                player1_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_6":
                player1_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_7":
                player1_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_8":
                player1_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_9":
                player1_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_10":
                player1_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_11":
                player1_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player1_12":
                player1_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
            break;
            case "player2_1":
                player2_1.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_2":
                player2_2.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_3":
                player2_3.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_4":
                player2_4.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_5":
                player2_5.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_6":
                player2_6.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_7":
                player2_7.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_8":
                player2_8.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_9":
                player2_9.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_10":
                player2_10.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_11":
                player2_11.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;
            case "player2_12":
                player2_12.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_eated));
                break;

        }

        piece_to_eat.add(a);

        View v=null;
        switch (name_piece) {
            case "player1_1":
                v=player1_1;
                break;
            case "player1_2":
                v= player1_2;
                break;
            case "player1_3":
                v= player1_3;
                break;
            case "player1_4":
                v= player1_4;
                break;
            case "player1_5":
                v= player1_5;
                break;
            case "player1_6":
                v= player1_6;
                break;
            case "player1_7":
                v= player1_7;
                break;
            case "player1_8":
                v= player1_8;
                break;
            case "player1_9":
                v= player1_9;
                break;
            case "player1_10":
                v=player1_10;
                break;
            case "player1_11":
                v=player1_11;
                break;
            case "player1_12":
                v=player1_12;
                break;
            case "player2_1":
                v= player2_1;
                break;
            case "player2_2":
                v= player2_2;
                break;
            case "player2_3":
                v= player2_3;
                break;
            case "player2_4":
                v= player2_4;
                break;
            case "player2_5":
                v= player2_5;
                break;
            case "player2_6":
                v= player2_6;
                break;
            case "player2_7":
                v= player2_7;
                break;
            case "player2_8":
                v= player2_8;
                break;
            case "player2_9":
                v= player2_9;
                break;
            case "player2_10":
                v=player2_10;
                break;
            case "player2_11":
                v=player2_11;
                break;
            case "player2_12":
                v= player2_12;
                break;

        }

        if(player1){
            brain_pointer.remove(a);
            brain_pointer.put(a, "player1_0");
        }
        else{
            brain_pointer.remove(a);
            brain_pointer.put(a, "player2_0");
        }


        View_to_hide.put(name_piece,v);

        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.fide_in_b);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        });
        v.startAnimation(animFade);
    }

    private void player_1_next_to_eat(int the_place,int role){

        HashMap<String, ArrayList<Integer>> next_to_eats=check_some_things_to_eats_player1_from_this_exact_place(the_place);

        if (next_to_eats.size()!=0){
                clear();
                ArrayList<String> piece = new ArrayList<>();
                ArrayList<Integer> places = new ArrayList<>();

                for (Map.Entry mapentry : next_to_eats.entrySet()) {
                    ArrayList<Integer> ace = (ArrayList<Integer>) mapentry.getValue();
                    for (int a : ace) {
                        if (!places.contains(a))
                            places.add(a);
                    }
                    ace.clear();

                    piece.add((String) mapentry.getKey());
                }

                time_to_eat();
                //enabled_piece_and_place_to_eat(places,piece);
                enabled_pieces_can_eat(piece);
                auto_click_when_next_to_eat(the_place);
        }
        else {


            if(role==0) {
                delete_biade9_mort();
                piece_to_eat.clear();
                clear();

                if(I_am_the_player_1){
                    int a=detect_if_user_1_win();
                    game.setPlace(places_to_move);
                    Log.e("place to move", "" + places_to_move.size());
                    mListener.somethingHappenedInFragment(game);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(a==1)
                        { mListener.somethingHappenedInFragment(0);
                        }
                    }, places_to_move.size()*500);

                }else {
                    int a=detect_if_user_2_win();
                    game.setPlace(places_to_move);
                    Log.e("place to move", "" + places_to_move.size());
                    mListener.somethingHappenedInFragment(game);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(a==1)
                        { mListener.somethingHappenedInFragment(1);
                        }
                    }, places_to_move.size()*500);
                }
            }

           // change_the_role();
        }
    }

    private void player_2_next_to_eat(int the_place,int role){
        time_to_eat();
        HashMap<String, ArrayList<Integer>> next_to_eats=check_some_things_to_eats_player2_from_this_exact_place(the_place);

        if (next_to_eats.size()!=0){
                clear();
                ArrayList<String> piece = new ArrayList<>();
                ArrayList<Integer> places = new ArrayList<>();

                for (Map.Entry mapentry : next_to_eats.entrySet()) {
                    ArrayList<Integer> ace = (ArrayList<Integer>) mapentry.getValue();
                    for (int a : ace) {
                        if (!places.contains(a))
                            places.add(a);
                    }
                    ace.clear();

                    piece.add((String) mapentry.getKey());
                }

                time_to_eat();
                //enabled_piece_and_place_to_eat(places,piece);
                enabled_pieces_can_eat(piece);
                auto_click_when_next_to_eat(the_place);

        }
        else {





            if(role==0) {
                delete_biade9_mort();
                piece_to_eat.clear();
                clear();

                if(I_am_the_player_1){
                    int a=detect_if_user_1_win();
                    game.setPlace(places_to_move);
                    Log.e("place to move", "" + places_to_move.size());
                    mListener.somethingHappenedInFragment(game);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(a==1)
                        { mListener.somethingHappenedInFragment(0);
                        }
                    }, places_to_move.size()*500);

                }else {
                    int a=detect_if_user_2_win();
                    game.setPlace(places_to_move);
                    Log.e("place to move", "" + places_to_move.size());
                    mListener.somethingHappenedInFragment(game);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(a==1)
                        { mListener.somethingHappenedInFragment(1);
                        }
                    }, places_to_move.size()*500);
                }
            }

           // change_the_role();
        }
    }

    private void auto_click_when_next_to_eat(int the_place){
        String the_piece="";
        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (((int) mapentry.getKey())==the_place) {
                the_piece=(String) mapentry.getValue();
            }
        }

        switch (the_piece) {
            case "player1_1":
                player1_1.performClick();
                break;
            case "player1_2":
                player1_2.performClick();
                break;
            case "player1_3":
                player1_3.performClick();
                break;
            case "player1_4":
                player1_4.performClick();
                break;
            case "player1_5":
                player1_5.performClick();
                break;
            case "player1_6":
                player1_6.performClick();
                break;
            case "player1_7":
                player1_7.performClick();
                break;
            case "player1_8":
                player1_8.performClick();
                break;
            case "player1_9":
                player1_9.performClick();
                break;
            case "player1_10":
                player1_10.performClick();
                break;
            case "player1_11":
                player1_11.performClick();
                break;
            case "player1_12":
                player1_12.performClick();
                break;
            case "player2_1":
                player2_1.performClick();
                break;
            case "player2_2":
                player2_2.performClick();
                break;
            case "player2_3":
                player2_3.performClick();
                break;
            case "player2_4":
                player2_4.performClick();
                break;
            case "player2_5":
                player2_5.performClick();
                break;
            case "player2_6":
                player2_6.performClick();
                break;
            case "player2_7":
                player2_7.performClick();
                break;
            case "player2_8":
                player2_8.performClick();
                break;
            case "player2_9":
                player2_9.performClick();
                break;
            case "player2_10":
                player2_10.performClick();
                break;
            case "player2_11":
                player2_11.performClick();
                break;
            case "player2_12":
                player2_12.performClick();
                break;
        }

         if(auto_clicked){
             Handler handler=new Handler();
             handler.postDelayed(this::move_istead_of_me, 500);
         }

    }

    private ArrayList<String> get_all_piece_can_move_for_this_play(String player){
        ArrayList<String> all_my_places = new ArrayList<>();


        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith(player))
                    all_my_places.add((String) mapentry.getValue());
            }
        }

        return all_my_places;

    }

    private void enabled_piece_and_place_to_eat(ArrayList<Integer> place_can_recive, ArrayList<String> piece_can_move) {

        ArrayList<Integer> deja_enabled=new ArrayList<>();
        for(Integer a:place_can_recive){
            if(Last_List_of_place_to_move.contains(a))
                deja_enabled.add(a);
        }
        disabled_all_place(deja_enabled);


        for (int number : place_can_recive) {
            switch (number) {
                case 1:
                    //l1_c1.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l1_c1.setEnabled(true);
                    break;
                case 2:
                    //l1_c3.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l1_c3.setEnabled(true);
                    break;
                case 3:
                    //l1_c5.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l1_c5.setEnabled(true);
                    break;
                case 4:
                    //l1_c7.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l1_c7.setEnabled(true);
                    break;
                case 5:
                    //l2_c2.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l2_c2.setEnabled(true);
                    break;
                case 6:
                    //l2_c4.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l2_c4.setEnabled(true);
                    break;
                case 7:
                    //l2_c6.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l2_c6.setEnabled(true);
                    break;
                case 8:
                    //l2_c8.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l2_c8.setEnabled(true);
                    break;
                case 9:
                    //l3_c1.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l3_c1.setEnabled(true);
                    break;
                case 10:
                    //l3_c3.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l3_c3.setEnabled(true);
                    break;
                case 11:
                    //l3_c5.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l3_c5.setEnabled(true);
                    break;
                case 12:
                    //l3_c7.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l3_c7.setEnabled(true);
                    break;
                case 13:
                    //l4_c2.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l4_c2.setEnabled(true);
                    break;
                case 14:
                    //l4_c4.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l4_c4.setEnabled(true);
                    break;
                case 15:
                    //l4_c6.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l4_c6.setEnabled(true);
                    break;
                case 16:
                    //l4_c8.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l4_c8.setEnabled(true);
                    break;
                case 17:
                    //l5_c1.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l5_c1.setEnabled(true);
                    break;
                case 18:
                    //l5_c3.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l5_c3.setEnabled(true);
                    break;
                case 19:
                    //l5_c5.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l5_c5.setEnabled(true);
                    break;
                case 20:
                    //l5_c7.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l5_c7.setEnabled(true);
                    break;
                case 21:
                    //l6_c2.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    //if(!l6_c2.isEnabled())
                     l6_c2.setEnabled(true);
                    break;
                case 22:
                    //l6_c4.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l6_c4.setEnabled(true);
                    break;
                case 23:
                    //l6_c6.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l6_c6.setEnabled(true);
                    break;
                case 24:
                    //l6_c8.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l6_c8.setEnabled(true);
                    break;
                case 25:
                    //l7_c1.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l7_c1.setEnabled(true);
                    break;
                case 26:
                    //l7_c3.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l7_c3.setEnabled(true);
                    break;
                case 27:
                    //l7_c5.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l7_c5.setEnabled(true);
                    break;
                case 28:
                    //l7_c7.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l7_c7.setEnabled(true);
                    break;
                case 29:
                    //l8_c2.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l8_c2.setEnabled(true);
                    break;
                case 30:
                    //l8_c4.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l8_c4.setEnabled(true);
                    break;
                case 31:
                    //l8_c6.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l8_c6.setEnabled(true);
                    break;
                case 32:
                    //l8_c8.setBackground(getResources().getDrawable(R.drawable.caree_selector_eat));
                    l8_c8.setEnabled(true);
                    break;
            }
        }


        enabled_pieces_can_eat(piece_can_move);

         Last_List_of_place_to_move=place_can_recive;


    }
    private HashMap<String, ArrayList<Integer>> check_some_things_to_eats_player1() {

        HashMap<String, ArrayList<Integer>> collection_can_eats = new HashMap<>();
        ArrayList<Integer> all_my_places = new ArrayList<>();
        ArrayList<Integer> all_my_King = new ArrayList<>();
        ArrayList<Integer> all_others_place = new ArrayList<>();

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player1")) {
                    if(king_traker.contains(mapentry.getValue().toString()))
                        all_my_King.add((int) mapentry.getKey());
                    else
                        all_my_places.add((int) mapentry.getKey());
                }
            }
        }

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player2"))
                    all_others_place.add((int) mapentry.getKey());
            }
        }


        for (int place:all_my_King){
            ArrayList<ArrayList<Integer>> lines=get_king_lines(place);

            if (lines.size()>=1){
                ArrayList<Integer> vertical_places=lines.get(0);

                Log.e("line",vertical_places.toString());
                int my_position_in_vertical=vertical_places.indexOf(place);

                boolean collect_place=false;

                for (int i=my_position_in_vertical;i>=0;i--){
                    if(i!=my_position_in_vertical) {

                         String m=brain_pointer.get(vertical_places.get(i));
                         if(m==null) {
                            if(collect_place){
                                if (collection_can_eats.get(brain_pointer.get(vertical_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(vertical_places.get(i)));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        }else  if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        }
                        else if(m.startsWith("player1"))
                            break;
                    }
                }

                collect_place=false;
                for (int i=my_position_in_vertical;i<vertical_places.size();i++){
                    if(i!=my_position_in_vertical) {

                        String m=brain_pointer.get(vertical_places.get(i));
                        if(m==null) {
                            if(collect_place){
                                if (collection_can_eats.get(brain_pointer.get(vertical_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(vertical_places.get(i)));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        }
                        else if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        }
                        else if(m.startsWith("player1"))
                            break;

                    }
                }


                //Log.e("my position vertical",""+my_position_in_vertical);
                //Log.e("i can go verticaly to ",place_available.toString());
            }

            if(lines.size()>1) {
                ArrayList<Integer> horizontal_places = lines.get(1);

                Log.e("line",horizontal_places.toString());
                int my_position_in_horizental=horizontal_places.indexOf(place);

                boolean collect_place=false;
                for (int i=my_position_in_horizental;i>=0;i--) {
                    if (i != my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                        if (m== null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(horizontal_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(horizontal_places.get(i)));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }
                        } else if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player1"))
                            break;
                    }
                }



                collect_place=false;
                for (int i=my_position_in_horizental;i<horizontal_places.size();i++){
                    if(i!=my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                       if(m==null) {
                            if(collect_place){
                                if (collection_can_eats.get(brain_pointer.get(horizontal_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(horizontal_places.get(i)));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        }
                        else if (m.startsWith("player2")) {
                           if(!collect_place)
                               collect_place=true;
                           else
                               break;
                        }
                        else if(m.startsWith("player1"))
                            break;

                    }
                }


               // Log.e("my position vertical",""+my_position_in_horizental);
               // Log.e("i can go verticaly to ",place_available.toString());
            }

        }

        for (int place : all_my_places) {
            if (place == 1 || place == 9 || place == 17 || place == 5 || place == 13 || place == 21) {
                if (place == 1 || place == 9 || place == 17) {
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 5 || place == 13 || place == 21) {
                    if (all_others_place.contains(place + 5) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
            else if (place == 8 || place == 16 || place == 24 || place == 4 || place == 12 || place == 20) {
                if (place == 8 || place == 16 || place == 24) {
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 4 || place == 12 || place == 20) {
                    if (all_others_place.contains(place + 3) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            } else if (place == 2 || place == 3 || place == 6 || place == 7 || place == 10 || place == 11 || place == 14
                    || place == 15 || place == 18 || place == 19 || place == 22 || place == 23) {

                if (place == 2 || place == 3 || place == 10 || place == 11 || place == 18 || place == 19) {
                    if (all_others_place.contains(place + 3) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 6 || place == 7 || place == 14 || place == 15 || place == 22 || place == 23) {
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place + 5) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
        }

        return collection_can_eats;

    }
    private HashMap<String, ArrayList<Integer>> check_some_things_to_eats_player2(){
        HashMap<String, ArrayList<Integer>> collection_can_eats = new HashMap<>();
        ArrayList<Integer> all_my_places = new ArrayList<>();
        ArrayList<Integer> all_others_place = new ArrayList<>();
        ArrayList<Integer> all_my_King = new ArrayList<>();

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player2"))
                    if(king_traker.contains(mapentry.getValue().toString()))
                        all_my_King.add((int) mapentry.getKey());
                    else
                        all_my_places.add((int) mapentry.getKey());
            }
        }

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player1"))
                    all_others_place.add((int) mapentry.getKey());
            }
        }

        for (int place:all_my_King){
            ArrayList<ArrayList<Integer>> lines=get_king_lines(place);

            if (lines.size()>=1){
                ArrayList<Integer> vertical_places=lines.get(0);

                Log.e("line",vertical_places.toString());
                int my_position_in_vertical=vertical_places.indexOf(place);

                boolean collect_place=false;

                for (int i=my_position_in_vertical;i>=0;i--){
                    if(i!=my_position_in_vertical) {

                        String m=brain_pointer.get(vertical_places.get(i));
                        if(m==null) {
                            if(collect_place){
                                if (collection_can_eats.get(brain_pointer.get(vertical_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(vertical_places.get(i)));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        }else  if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        }
                        else if(m.startsWith("player2"))
                            break;
                    }
                }

                collect_place=false;
                for (int i=my_position_in_vertical;i<vertical_places.size();i++){
                    if(i!=my_position_in_vertical) {

                        String m=brain_pointer.get(vertical_places.get(i));
                        if(m==null) {
                            if(collect_place){
                                if (collection_can_eats.get(brain_pointer.get(vertical_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(vertical_places.get(i)));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        }
                        else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        }
                        else if(m.startsWith("player2"))
                            break;

                    }
                }


                //Log.e("my position vertical",""+my_position_in_vertical);
                //Log.e("i can go verticaly to ",place_available.toString());
            }

            if(lines.size()>1) {
                ArrayList<Integer> horizontal_places = lines.get(1);

                Log.e("line",horizontal_places.toString());
                int my_position_in_horizental=horizontal_places.indexOf(place);

                boolean collect_place=false;
                for (int i=my_position_in_horizental;i>=0;i--) {
                    if (i != my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(horizontal_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(horizontal_places.get(i)));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }
                        } else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player2"))
                            break;
                    }
                }



                collect_place=false;
                for (int i=my_position_in_horizental;i<horizontal_places.size();i++){
                    if(i!=my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                        if(m==null) {
                            if(collect_place){
                                if (collection_can_eats.get(brain_pointer.get(horizontal_places.get(i))) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(horizontal_places.get(i)));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        }
                        else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        }
                        else if(m.startsWith("player2"))
                            break;

                    }
                }


                // Log.e("my position vertical",""+my_position_in_horizental);
                // Log.e("i can go verticaly to ",place_available.toString());
            }

        }

        for (int place : all_my_places){
            if (place == 9 || place == 17 || place == 25 || place == 29 || place == 21 || place == 13) {
                if (place == 9 || place == 17 || place == 25) {
                    if (all_others_place.contains(place - 4) && brain_pointer.get(place -7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place ==29 || place == 21 || place == 13) {
                    if (all_others_place.contains(place -3) && brain_pointer.get(place -7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
            else if (place == 16 || place == 24 || place == 32 || place == 28 || place == 20 || place == 12) {
                if (place == 16 || place == 24 || place == 32) {
                    if (all_others_place.contains(place -4) && brain_pointer.get(place - 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 28 || place == 20 || place == 12) {
                    if (all_others_place.contains(place -5) && brain_pointer.get(place -9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }

            else if (place == 31 || place == 30 || place == 27 || place == 26 || place == 22 || place == 23 || place == 18
                    || place == 19 || place == 14 || place == 15 || place == 10 || place == 11) {

                if (place == 31 || place == 30 || place == 22 || place == 23 || place == 14 || place == 15) {
                    if (all_others_place.contains(place -3) && brain_pointer.get(place -7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place -4) && brain_pointer.get(place -9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 27 || place == 26 || place == 18 || place == 19 || place == 10 || place == 11) {
                    if (all_others_place.contains(place -4) && brain_pointer.get(place - 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place - 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place - 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place - 5) && brain_pointer.get(place - 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
        }

        return collection_can_eats;

    }


    private HashMap<String, ArrayList<Integer>> check_some_things_to_eats_player1_from_this_exact_place(int place) {

        HashMap<String, ArrayList<Integer>> collection_can_eats = new HashMap<>();
        ArrayList<Integer> all_my_places = new ArrayList<>();
        ArrayList<Integer> all_my_King = new ArrayList<>();
        ArrayList<Integer> all_others_place = new ArrayList<>();
        ArrayList<String> pieces_was_deleted=new ArrayList<>();

        for (Map.Entry mapentry : View_to_hide.entrySet()) {
            pieces_was_deleted.add(mapentry.getKey().toString());
        }

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player1")) {
                    if (king_traker.contains(mapentry.getValue().toString()))
                        all_my_King.add((int) mapentry.getKey());
                    else
                        all_my_places.add((int) mapentry.getKey());
                }

            }
        }

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player2"))
                    all_others_place.add((int) mapentry.getKey());
            }
        }

        if(all_my_King.contains(place)) {
            ArrayList<ArrayList<Integer>> lines = get_king_lines(place);

            if (lines.size() >= 1 ) {
                ArrayList<Integer> vertical_places = lines.get(0);

                Log.e("line", vertical_places.toString());
                int my_position_in_vertical = vertical_places.indexOf(place);

                boolean collect_place = false;

                for (int i = my_position_in_vertical; i >= 0; i--) {
                    if (i != my_position_in_vertical) {

                        String m=brain_pointer.get(vertical_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        } else if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player1"))
                            break;
                    }
                }

                collect_place = false;
                for (int i = my_position_in_vertical; i < vertical_places.size(); i++) {
                    if (i != my_position_in_vertical) {

                        String m=brain_pointer.get(vertical_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null){
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        } else if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player1"))
                            break;

                    }
                }


                //Log.e("my position vertical",""+my_position_in_vertical);
                //Log.e("i can go verticaly to ",place_available.toString());
            }

            if (lines.size() > 1 ) {
                ArrayList<Integer> horizontal_places = lines.get(1);

                Log.e("line", horizontal_places.toString());
                int my_position_in_horizental = horizontal_places.indexOf(place);

                boolean collect_place = false;
                for (int i = my_position_in_horizental; i >= 0; i--) {
                    if (i != my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }
                        } else if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player1"))
                            break;
                    }
                }


                collect_place = false;
                for (int i = my_position_in_horizental; i < horizontal_places.size(); i++) {
                    if (i != my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));

                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        } else if (m.startsWith("player2")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player1"))
                            break;

                    }
                }


                // Log.e("my position vertical",""+my_position_in_horizental);
                // Log.e("i can go verticaly to ",place_available.toString());
            }
        }


        if (place == 1 || place == 9 || place == 17 || place == 5 || place == 13 || place == 21)
        {
                if (place == 1 || place == 9 || place == 17) {
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 5 || place == 13 || place == 21) {
                    if (all_others_place.contains(place + 5) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
        else if (place == 8 || place == 16 || place == 24 || place == 4 || place == 12 || place == 20)
        {
                if (place == 8 || place == 16 || place == 24) {
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 4 || place == 12 || place == 20) {
                    if (all_others_place.contains(place + 3) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
        else if (place == 2 || place == 3 || place == 6 || place == 7 || place == 10 || place == 11 || place == 14
                    || place == 15 || place == 18 || place == 19 || place == 22 || place == 23)
        {

                if (place == 2 || place == 3 || place == 10 || place == 11 || place == 18 || place == 19) {
                    if (all_others_place.contains(place + 3) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 6 || place == 7 || place == 14 || place == 15 || place == 22 || place == 23) {
                    if (all_others_place.contains(place + 4) && brain_pointer.get(place + 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place + 5) && brain_pointer.get(place + 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place + 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }


        return collection_can_eats;

    }
    private HashMap<String, ArrayList<Integer>> check_some_things_to_eats_player2_from_this_exact_place(int place){
        HashMap<String, ArrayList<Integer>> collection_can_eats = new HashMap<>();
        ArrayList<Integer> all_my_places = new ArrayList<>();
        ArrayList<Integer> all_my_King = new ArrayList<>();
        ArrayList<Integer> all_others_place = new ArrayList<>();

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player2"))
                    if (king_traker.contains(mapentry.getValue().toString()))
                        all_my_King.add((int) mapentry.getKey());
                    else
                        all_my_places.add((int) mapentry.getKey());
            }
        }

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().startsWith("player1"))
                    all_others_place.add((int) mapentry.getKey());
            }
        }

        if(all_my_King.contains(place)) {
            ArrayList<ArrayList<Integer>> lines = get_king_lines(place);

            if (lines.size() >= 1) {
                ArrayList<Integer> vertical_places = lines.get(0);

                Log.e("line", vertical_places.toString());
                int my_position_in_vertical = vertical_places.indexOf(place);

                boolean collect_place = false;

                for (int i = my_position_in_vertical; i >= 0; i--) {
                    if (i != my_position_in_vertical) {

                        String m=brain_pointer.get(vertical_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        } else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player2"))
                            break;
                    }
                }

                collect_place = false;
                for (int i = my_position_in_vertical; i < vertical_places.size(); i++) {
                    if (i != my_position_in_vertical) {
                        String m=brain_pointer.get(vertical_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null){
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(vertical_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        } else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player2"))
                            break;

                    }
                }


                //Log.e("my position vertical",""+my_position_in_vertical);
                //Log.e("i can go verticaly to ",place_available.toString());
            }

            if (lines.size() > 1) {
                ArrayList<Integer> horizontal_places = lines.get(1);

                Log.e("line", horizontal_places.toString());
                int my_position_in_horizental = horizontal_places.indexOf(place);

                boolean collect_place = false;
                for (int i = my_position_in_horizental; i >= 0; i--) {
                    if (i != my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }
                        } else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player2"))
                            break;
                    }
                }


                collect_place = false;
                for (int i = my_position_in_horizental; i < horizontal_places.size(); i++) {
                    if (i != my_position_in_horizental) {

                        String m=brain_pointer.get(horizontal_places.get(i));
                        if (m == null) {
                            if (collect_place) {
                                if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                                    ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                                    assert places != null;
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                } else {
                                    ArrayList<Integer> places = new ArrayList<>();
                                    places.add(horizontal_places.get(i));
                                    collection_can_eats.put(brain_pointer.get(place), places);
                                }
                            }

                        } else if (m.startsWith("player1")) {
                            if(!collect_place)
                                collect_place=true;
                            else
                                break;
                        } else if (m.startsWith("player2"))
                            break;

                    }
                }


                // Log.e("my position vertical",""+my_position_in_horizental);
                // Log.e("i can go verticaly to ",place_available.toString());
            }
        }


        if (place == 9 || place == 17 || place == 25 || place == 29 || place == 21 || place == 13) {
                if (place == 9 || place == 17 || place == 25) {
                    if (all_others_place.contains(place - 4) && brain_pointer.get(place -7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place ==29 || place == 21 || place == 13) {
                    if (all_others_place.contains(place -3) && brain_pointer.get(place -7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }
            else if (place == 16 || place == 24 || place == 32 || place == 28 || place == 20 || place == 12) {
                if (place == 16 || place == 24 || place == 32) {
                    if (all_others_place.contains(place -4) && brain_pointer.get(place - 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 28 || place == 20 || place == 12) {
                    if (all_others_place.contains(place -5) && brain_pointer.get(place -9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }

            else if (place == 31 || place == 30 || place == 27 || place == 26 || place == 22 || place == 23 || place == 18
                    || place == 19 || place == 14 || place == 15 || place == 10 || place == 11) {

                if (place == 31 || place == 30 || place == 22 || place == 23 || place == 14 || place == 15) {
                    if (all_others_place.contains(place -3) && brain_pointer.get(place -7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place -4) && brain_pointer.get(place -9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place -9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
                if (place == 27 || place == 26 || place == 18 || place == 19 || place == 10 || place == 11) {
                    if (all_others_place.contains(place -4) && brain_pointer.get(place - 7) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place - 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place - 7);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                    if (all_others_place.contains(place - 5) && brain_pointer.get(place - 9) == null) {
                        if (collection_can_eats.get(brain_pointer.get(place)) != null) {
                            ArrayList<Integer> places = collection_can_eats.get(brain_pointer.get(place));
                            assert places != null;
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        } else {
                            ArrayList<Integer> places = new ArrayList<>();
                            places.add(place - 9);
                            collection_can_eats.put(brain_pointer.get(place), places);
                        }
                    }
                }
            }


        return collection_can_eats;

    }

    private void App__conroler (int role){

        piece_to_eat.clear();
        clear();
        auto_clicked=false;


        Log.e("ex","I_am_the_player_1"+I_am_the_player_1+" role"+role );
       //
        if(I_am_the_player_1 && role == 0) {
            player1=true;
            player2=false;
            player_1_helper();

            if(sharedpreferences.get_setting_type("first_tap_to_move_player_1")) {
                help_me();
                sharedpreferences.put_setting_type("first_tap_to_move_player_1",false);
            }
        }
        else if ( !I_am_the_player_1 && role==1) {
            player1=false;
            player2=true;
            player_2_helper();

            if(sharedpreferences.get_setting_type("first_tap_to_move_player_2")) {
                help_me();
                sharedpreferences.put_setting_type("first_tap_to_move_player_2",false);
            }
        }

    }

    private void clear(){
        gone_all_lottie();
        disabled_all_place(new ArrayList<>());
        disabled_all_piece();
        unchecked_all_piece();
        clear_all_eat_backgroung();

    }

    private void clear_all_eat_backgroung() {

        l1_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l1_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l1_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l1_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l2_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l2_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l2_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l2_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l3_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l3_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l3_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l3_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l4_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l4_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l4_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l4_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l5_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l5_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l5_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l5_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l6_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l6_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l6_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l6_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l7_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l7_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l7_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l7_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l8_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l8_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l8_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));
        l8_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_move));

    }
    private void time_to_eat() {

        l1_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l1_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l1_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l1_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l2_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l2_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l2_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l2_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l3_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l3_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l3_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l3_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l4_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l4_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l4_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l4_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l5_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l5_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l5_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l5_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l6_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l6_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l6_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l6_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l7_c1.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l7_c3.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l7_c5.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l7_c7.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l8_c2.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l8_c4.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l8_c6.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));
        l8_c8.setBackground(ContextCompat.getDrawable(context,R.drawable.caree_selector_eat));

    }



    private void charge_the_king_line(){
        int plus=4;

        Vtab_2_4.add(4);
        Vtab_2_25.add(25);
        for(int i=1;i<2;i++){
            Vtab_2_4.add(Vtab_2_4.get(i-1)+plus);
            Vtab_2_25.add(Vtab_2_25.get(i-1)+plus);
        }

        Vtab_4_17.add(17);
        Vtab_4_3.add(3);
        for(int i=1;i<4;i++){
            Vtab_4_17.add(Vtab_4_17.get(i-1)+plus);
            Vtab_4_3 .add(Vtab_4_3.get(i-1)+plus);

            if(plus==4)
                plus=5;
            else
                plus=4;
        }


        plus=4;
        Vtab_6_9.add(9);
        Vtab_6_2.add(2);
        for(int i=1;i<6;i++){
            Vtab_6_2.add(Vtab_6_2.get(i-1)+plus);
            Vtab_6_9 .add(Vtab_6_9.get(i-1)+plus);

            if(plus==4)
                plus=5;
            else
                plus=4;
        }

        plus=4;
        Vtab_8_1.add(1);
        for(int i=1;i<8;i++){
            Vtab_8_1.add(Vtab_8_1.get(i-1)+plus);
            if(plus==4)
                plus=5;
            else
                plus=4;
        }


        plus=3;
        Htab_3_2.add(2);
        Htab_3_24.add(24);
        for(int i=1;i<3;i++){
            Htab_3_2.add(Htab_3_2.get(i-1)+plus);
            if(plus==4)
                plus=3;
            else
                plus=4;
            Htab_3_24.add(Htab_3_24.get(i-1)+plus);
        }

        plus=3;
        Htab_5_3.add(3);
        Htab_5_16.add(16);
        for(int i=1;i<5;i++){
            Htab_5_3.add(Htab_5_3.get(i-1)+plus);
            if(plus==4)
                plus=3;
            else
                plus=4;
            Htab_5_16.add(Htab_5_16.get(i-1)+plus);
        }

        plus=3;
        Htab_7_4.add(4);
        Htab_7_8.add(8);
        for(int i=1;i<7;i++){
            Htab_7_4.add(Htab_7_4.get(i-1)+plus);
            if(plus==4)
                plus=3;
            else
                plus=4;
            Htab_7_8.add(Htab_7_8.get(i-1)+plus);
        }


       //String s=Vtab_2_4.toString()+"//\n"+Vtab_2_25.toString()+"//\n"
       //        +Vtab_4_3.toString()+"//\n"+Vtab_4_17.toString()+"//\n"+Vtab_8_1.toString()+"//\n"+Htab_7_4.toString();

       //Log.e("ff",s);
    }

    private ArrayList<ArrayList<Integer>> get_king_lines(int place){
         ArrayList<ArrayList<Integer>> lines=new ArrayList<>();

         if(Vtab_2_4.contains(place))
             lines.add(Vtab_2_4);
         else if(Vtab_2_25.contains(place))
             lines.add(Vtab_2_25);
         else if(Vtab_4_17.contains(place))
             lines.add(Vtab_4_17);
         else if(Vtab_4_3.contains(place))
             lines.add(Vtab_4_3);
         else if(Vtab_6_2.contains(place))
             lines.add(Vtab_6_2);
         else if(Vtab_6_9.contains(place))
             lines.add(Vtab_6_9);
         else if(Vtab_8_1.contains(place))
             lines.add(Vtab_8_1);

         if (Htab_3_2.contains(place))
             lines.add(Htab_3_2);
         else if (Htab_3_24.contains(place))
             lines.add(Htab_3_24);
         else if (Htab_5_3.contains(place))
             lines.add(Htab_5_3);
         else if (Htab_5_16.contains(place))
             lines.add(Htab_5_16);
         else if (Htab_7_4.contains(place))
             lines.add(Htab_7_4);
         else if (Htab_7_8.contains(place))
             lines.add(Htab_7_8);

         return lines;

     }

    private ArrayList<Integer> get_all_place_can_King_go_from_this_piece(String piece) {

        int a = 0;

        for (Map.Entry mapentry : brain_pointer.entrySet()) {
            if (mapentry.getValue() != null) {
                if (mapentry.getValue().toString().equals(piece))
                    a = (int) mapentry.getKey();
            }
        }

        ArrayList<Integer> place_available = new ArrayList<>();


        ArrayList<ArrayList<Integer>> lines=get_king_lines(a);

        if (lines.size()>=1){
            ArrayList<Integer> vertical_places=lines.get(0);

            Log.e("line",vertical_places.toString());
            int my_position_in_vertical=vertical_places.indexOf(a);

            for (int i=my_position_in_vertical;i>=0;i--){
                if(i!=my_position_in_vertical) {
                    if (brain_pointer.get(vertical_places.get(i)) == null)
                        place_available.add(vertical_places.get(i));
                    else
                        break;
                }
            }
            for (int i=my_position_in_vertical;i<vertical_places.size();i++){
                if(i!=my_position_in_vertical) {
                    if (brain_pointer.get(vertical_places.get(i)) == null)
                        place_available.add(vertical_places.get(i));
                    else
                        break;
                }
            }


            Log.e("my position vertical",""+my_position_in_vertical);
            Log.e("i can go verticaly to ",place_available.toString());
        }

        if(lines.size()>1) {
            ArrayList<Integer> horizontal_places = lines.get(1);

            Log.e("line",horizontal_places.toString());
            int my_position_in_horizental=horizontal_places.indexOf(a);

            for (int i=my_position_in_horizental;i>=0;i--){
                if(i!=my_position_in_horizental) {
                    if (brain_pointer.get(horizontal_places.get(i)) == null)
                        place_available.add(horizontal_places.get(i));
                    else
                        break;
                }
            }
            for (int i=my_position_in_horizental;i<horizontal_places.size();i++){
                if(i!=my_position_in_horizental) {
                    if (brain_pointer.get(horizontal_places.get(i)) == null)
                        place_available.add(horizontal_places.get(i));
                    else
                        break;
                }
            }


            Log.e("my position vertical",""+my_position_in_horizental);
            Log.e("i can go verticaly to ",place_available.toString());
        }




        return place_available;
    }

    Animation animFade;
    public void hide_this_button(final View b){
        animFade = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out_500);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                b.setVisibility(View.GONE);
            }
        });
        b.startAnimation(animFade);

    }

    public interface Listener {
         void somethingHappenedInFragment(Object match_running);
    }

    private Listener mListener;

    public void recieve_game(match_running match,int role){

        Log.e("match ",""+match.toString()+" role "+role);


        if(I_am_the_player_1 && role == 0) {
            player1=true;
            player2=false;
        }
        else if (!I_am_the_player_1 && role==1) {
            player1=false;
            player2=true;
        }

        applique_the_game(match, role, () -> {
            delete_biade9_mort();
            places_to_move.clear();
            App__conroler(role);
        });
    }

    private void applique_the_game(match_running match,int role,okey okey) {
        game=match;



        unchecked_all_piece();
        on_user_check_a_piece(match.getPiece());
        String op;
        if (match.getOperation()==0)
            op="move";
        else
            op="eat";

        if (role==0)
            player_1_operation = op;
        else
            player_2_operation =  op;


        if(match.getPlace().size()==1) {
            Handler handler=new Handler();
            handler.postDelayed(() -> {
                ImageButton place = get_place(match.getPlace().get(0));
                //if (match.getPlace().get(0) != -1 && brain_pointer.get(match.getPlace().get(0)) == null)
                move_to_this_place(Principal_view, baida9_to_move, place,1);
                okey.ok();
            }, 500);

        }
        else if(match.getPlace().size()>1){
            unchecked_all_piece();
            move(match, okey);
        }else if(match.getPlace().size()==0){
            okey.ok();
        }
    }

    int move_c=0;
    private void move(match_running match,okey okey){

        Handler handler=new Handler();
        handler.postDelayed(() -> {
            on_user_check_a_piece(match.getPiece());
            ImageButton place = get_place(match.getPlace().get(move_c));
            move_to_this_place(Principal_view,baida9_to_move, place, 1);
            move_c++;
            if(move_c ==match.getPlace().size()) {
                move_c=0;
                okey.ok();
            }else
                move(match, okey);

        }, 500);

    }

    public void receiveMsg(int value) {
        I_am_the_player_1= value == 0;
    }

    private void on_user_check_a_piece(int piece) {
        if (player2) {
            switch (piece) {
                case 1:
                    piece_cheked(0,piece,player1_1);
                    break;
                case 2:
                    piece_cheked(0,piece,player1_2);
                    break;
                case 3:
                    piece_cheked(0,piece,player1_3);
                    break;
                case 4:
                    piece_cheked(0,piece,player1_4);
                    break;
                case 5:
                    piece_cheked(0,piece,player1_5);
                    break;
                case 6:
                    piece_cheked(0,piece,player1_6);
                    break;
                case 7:
                    piece_cheked(0,piece,player1_7);
                    break;
                case 8:
                    piece_cheked(0,piece,player1_8);
                    break;
                case 9:
                    piece_cheked(0,piece,player1_9);
                    break;
                case 10:
                    piece_cheked(0,piece,player1_10);
                    break;
                case 11:
                    piece_cheked(0,piece,player1_11);
                    break;
                case 12:
                    piece_cheked(0,piece,player1_12);
                    break;
            }
        }else {
            switch (piece) {
                case 1:
                    piece_cheked(1,piece,player2_1);
                    break;
                case 2:
                    piece_cheked(1,piece,player2_2);
                    break;
                case 3:
                    piece_cheked(1,piece,player2_3);
                    break;
                case 4:
                    piece_cheked(1,piece,player2_4);
                    break;
                case 5:
                    piece_cheked(1,piece,player2_5);
                    break;
                case 6:
                    piece_cheked(1,piece,player2_6);
                    break;
                case 7:
                    piece_cheked(1,piece,player2_7);
                    break;
                case 8:
                    piece_cheked(1,piece,player2_8);
                    break;
                case 9:
                    piece_cheked(1,piece,player2_9);
                    break;
                case 10:
                    piece_cheked(1,piece,player2_10);
                    break;
                case 11:
                    piece_cheked(1,piece,player2_11);
                    break;
                case 12:
                    piece_cheked(1,piece,player2_12);
                    break;
            }
        }
    }

    private void piece_cheked(int player,int numo,View button){
        if(player==0) {
            String s="player1_"+numo;
            if (king_traker.contains(s))
                button.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_roi_clique));
            else
                button.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_1_clique));
        }else {
            String s="player2_"+numo;
            if (king_traker.contains(s))
                button.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_roi_clique));
            else
                button.setBackground(ContextCompat.getDrawable(context,R.drawable.piece_player_2_clique));
        }
        baida9_to_move=(ImageButton) button;
    }

    private ImageButton get_place(int value) {
        switch (value){
            case 1:
                return l1_c1;
            case 2:
                return l1_c3;
            case 3:
                return l1_c5;
            case 4:
                return l1_c7;
            case 5:
                return l2_c2;
            case 6:
                return l2_c4;
            case 7:
                return l2_c6;
            case 8:
                return l2_c8;
            case 9:
                return l3_c1;
            case 10:
                return l3_c3;
            case 11:
                return l3_c5;
            case 12:
                return l3_c7;
            case 13:
                return l4_c2;
            case 14:
                return l4_c4;
            case 15:
                return l4_c6;
            case 16:
                return l4_c8;
            case 17:
                return l5_c1;
            case 18:
                return l5_c3;
            case 19:
                return l5_c5;
            case 20:
                return l5_c7;
            case 21:
                return l6_c2;
            case 22:
                return l6_c4;
            case 23:
                return l6_c6;
            case 24:
                return l6_c8;
            case 25:
                return l7_c1;
            case 26:
                return l7_c3;
            case 27:
                return l7_c5;
            case 28:
                return l7_c7;
            case 29:
                return l8_c2;
            case 30:
                return l8_c4;
            case 31:
                return l8_c6;
            case 32:
                return l8_c8;
        }
        return null;
    }

    private int detect_if_user_1_win(){
        player1=false;
        player2=true;
        HashMap<String, ArrayList<Integer>> eats=check_some_things_to_eats_player2();

        int a;


        if(eats.size()==0){
            ArrayList<String> pieces=new ArrayList<>();
            for (String f:get_all_piece_can_move_for_this_play("player2")){
                if(get_all_place_can_go_from_this_piece(f).size()!=0)
                    pieces.add(f);
            }


            for (String n:king_traker){
                if(n.startsWith("player2")) {
                    if(get_all_place_can_King_go_from_this_piece(n).size()!=0){
                        pieces.add(n);
                    }
                }
            }
            if(pieces.size()==0)
                a=1;
            else
                a=0;

        }else{
            a=0;
        }

        Log.e("rr",""+a);
        return a;
    }

    private int detect_if_user_2_win(){
        player1=true;
        player2=false;
        HashMap<String, ArrayList<Integer>> eats=check_some_things_to_eats_player1();

        int a;

        if(eats.size()==0){
            ArrayList<String> pieces=new ArrayList<>();
            for (String f:get_all_piece_can_move_for_this_play("player1")){
                if(get_all_place_can_go_from_this_piece(f).size()!=0)
                    pieces.add(f);
            }


            for (String n:king_traker){
                if(n.startsWith("player1")) {
                    if(get_all_place_can_King_go_from_this_piece(n).size()!=0){
                        pieces.add(n);
                    }
                }
            }
            if(pieces.size()==0)
                a=1;
            else
                a=0;

        }else{
            a=0;
        }

        return a;
    }

    public interface okey{
        void ok();
    }

    public void help_me(){
        if(l1_c1.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l1_c1);
            return;
        }
        if(l1_c3.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l1_c3);
            return;
        }
        if(l1_c5.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l1_c5);
            return;
        }
        if(l1_c7.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l1_c7);
            return;
        }
        if(l2_c2.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l2_c2);
            return;
        }
        if(l2_c4.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l2_c4);
            return;
        }
        if(l2_c6.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l2_c6);
            return;
        }
        if(l2_c8.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l2_c8);
            return;
        }
        if(l3_c1.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l3_c1);
            return;
        }
        if(l3_c3.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l3_c3);
            return;
        }
        if(l3_c5.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l3_c5);
            return;
        }
        if(l3_c7.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l3_c7);
            return;
        }
        if(l4_c2.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l4_c2);
            return;
        }
        if(l4_c4.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l4_c4);
            return;
        }
        if(l4_c6.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l4_c6);
            return;
        }
        if(l4_c8.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l4_c8);
            return;
        }
        if(l5_c1.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l5_c1);
            return;
        }
        if(l5_c3.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l5_c3);
            return;
        }
        if(l5_c5.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l5_c5);
            return;
        }
        if(l5_c7.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l5_c7);
            return;
        }
        if(l6_c2.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l6_c2);
            return;
        }
        if(l6_c4.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l6_c4);
            return;
        }
        if(l6_c6.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l6_c6);
            return;
        }
        if(l6_c8.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l6_c8);
            return;
        }
        if(l7_c1.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l7_c1);
            return;
        }
        if(l7_c3.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l7_c3);
            return;
        }
        if(l7_c5.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l7_c5);
            return;
        }
        if(l7_c7.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l7_c7);
            return;
        }
        if(l8_c2.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l8_c2);
            return;
        }
        if(l8_c4.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l8_c4);
            return;
        }
        if(l8_c6.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l8_c6);
            return;
        }
        if(l8_c8.isEnabled()){
            move_the_lottie_to_this_place(Principal_view, null,l8_c8);
            return;
        }

        if(player1_1.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_1,null);
            return;
        }
        if(player1_2.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_2,null);
            return;
        }
        if(player1_3.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_3,null);
            return;
        }
        if(player1_4.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_4,null);
            return;
        }
        if(player1_5.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_5,null);
            return;
        }
        if(player1_6.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_6,null);
            return;
        }
        if(player1_7.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_7,null);
            return;
        }
        if(player1_8.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_8,null);
            return;
        }
        if(player1_9.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_9,null);
            return;
        }
        if(player1_10.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_10,null);
            return;
        }
        if(player1_11.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_11,null);
            return;
        }
        if(player1_12.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player1_12,null);
            return;
        }

        if(player2_1.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_1,null);
            return;
        }
        if(player2_2.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_2,null);
            return;
        }
        if(player2_3.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_3,null);
            return;
        }
        if(player2_4.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_4,null);
            return;
        }
        if(player2_5.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_5,null);
            return;
        }
        if(player2_6.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_6,null);
            return;
        }
        if(player2_7.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_7,null);
            return;
        }
        if(player2_8.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_8,null);
            return;
        }
        if(player2_9.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_9,null);
            return;
        }
        if(player2_10.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_10,null);
            return;
        }
        if(player2_11.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_11,null);
            return;
        }
        if(player2_12.isEnabled()) {
            move_the_lottie_to_this_place(Principal_view, player2_12,null);
            return;
        }

        Toast.makeText(context, getResources().getString(R.string.ri), Toast.LENGTH_SHORT).show();
    }

    private void move_the_lottie_to_this_place(View view,ImageView place_to,ImageButton po) {
        View piece_to_move;
        if(player1)
            piece_to_move = lottie_select_2;
        else
            piece_to_move=lottie_select_1;

        piece_to_move.setVisibility(View.VISIBLE);

        Log.e("id of place",""+piece_to_move.getId());

        ConstraintLayout constraintLayout = view.findViewById(R.id.table);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        if(place_to!=null) {
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.END, place_to.getId(), ConstraintSet.END, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.START, place_to.getId(), ConstraintSet.START, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.TOP, place_to.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.BOTTOM, place_to.getId(), ConstraintSet.BOTTOM, 0);
        }else {
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.END, po.getId(), ConstraintSet.END, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.START, po.getId(), ConstraintSet.START, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.TOP,po.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(piece_to_move.getId(), ConstraintSet.BOTTOM, po.getId(), ConstraintSet.BOTTOM, 0);
        }

        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(0);
        TransitionManager.beginDelayedTransition(constraintLayout, transition);
        constraintSet.applyTo(constraintLayout);
    }

    public void play_instead_of_me(){
        auto_clicked=true;
        if(baida9_to_move==null) {
            select_a_piece_instead_of_me();
        }
        move_istead_of_me();
    }

    private void select_a_piece_instead_of_me(){
        if(player1_1.isEnabled()) {
            player1_1.performClick();
            return;
        }
        if(player1_2.isEnabled()) {
            player1_2.performClick();
            return;
        }
        if(player1_3.isEnabled()) {
            player1_3.performClick();
            return;
        }
        if(player1_4.isEnabled()) {
            player1_4.performClick();
            return;
        }
        if(player1_5.isEnabled()) {
            player1_5.performClick();
            return;
        }
        if(player1_6.isEnabled()) {
            player1_6.performClick();
            return;
        }
        if(player1_7.isEnabled()) {
            player1_7.performClick();
            return;
        }
        if(player1_8.isEnabled()) {
            player1_8.performClick();
            return;
        }
        if(player1_9.isEnabled()) {
            player1_9.performClick();
            return;
        }
        if(player1_10.isEnabled()) {
            player1_10.performClick();
            return;
        }
        if(player1_11.isEnabled()) {
            player1_11.performClick();
            return;
        }
        if(player1_12.isEnabled()) {
            player1_12.performClick();
            return;
        }

        if(player2_1.isEnabled()) {
            player2_1.performClick();
            return;
        }
        if(player2_2.isEnabled()) {
            player2_2.performClick();
            return;
        }
        if(player2_3.isEnabled()) {
            player2_3.performClick();
            return;
        }
        if(player2_4.isEnabled()) {
            player2_4.performClick();
            return;
        }
        if(player2_5.isEnabled()) {
            player2_5.performClick();
            return;
        }
        if(player2_6.isEnabled()) {
            player2_6.performClick();
            return;
        }
        if(player2_7.isEnabled()) {
            player2_7.performClick();
            return;
        }
        if(player2_8.isEnabled()) {
            player2_8.performClick();
            return;
        }
        if(player2_9.isEnabled()) {
            player2_9.performClick();
            return;
        }
        if(player2_10.isEnabled()){
            player2_10.performClick();
            return;
        }
        if(player2_11.isEnabled()){
            player2_11.performClick();
            return;
        }
        if(player2_12.isEnabled()) {
            player2_12.performClick();
        }

    }

    private void move_istead_of_me(){
        if(l1_c1.isEnabled()){
            l1_c1.performClick();
            return;
        }
        if(l1_c3.isEnabled()){
            l1_c3.performClick();
            return;
        }
        if(l1_c5.isEnabled()){
            l1_c5.performClick();
            return;
        }
        if(l1_c7.isEnabled()){
            l1_c7.performClick();
            return;
        }
        if(l2_c2.isEnabled()){
            l2_c2.performClick();
            return;
        }
        if(l2_c4.isEnabled()){
            l2_c4.performClick();
            return;
        }
        if(l2_c6.isEnabled()){
            l2_c6.performClick();
            return;
        }
        if(l2_c8.isEnabled()){
            l2_c8.performClick();
            return;
        }
        if(l3_c1.isEnabled()){
            l3_c1.performClick();
            return;
        }
        if(l3_c3.isEnabled()){
            l3_c3.performClick();
            return;
        }
        if(l3_c5.isEnabled()){
            l3_c5.performClick();
            return;
        }
        if(l3_c7.isEnabled()){
            l3_c7.performClick();
            return;
        }
        if(l4_c2.isEnabled()){
            l4_c2.performClick();
            return;
        }
        if(l4_c4.isEnabled()){
            l4_c4.performClick();
            return;
        }
        if(l4_c6.isEnabled()){
            l4_c6.performClick();
            return;
        }
        if(l4_c8.isEnabled()){
            l4_c8.performClick();
            return;
        }
        if(l5_c1.isEnabled()){
            l5_c1.performClick();
            return;
        }
        if(l5_c3.isEnabled()){
            l5_c3.performClick();
            return;
        }
        if(l5_c5.isEnabled()){
            l5_c5.performClick();
            return;
        }
        if(l5_c7.isEnabled()){
            l5_c7.performClick();
            return;
        }
        if(l6_c2.isEnabled()){
            l6_c2.performClick();
            return;
        }
        if(l6_c4.isEnabled()){
            l6_c4.performClick();
            return;
        }
        if(l6_c6.isEnabled()){
            l6_c6.performClick();
            return;
        }
        if(l6_c8.isEnabled()){
            l6_c8.performClick();
            return;
        }
        if(l7_c1.isEnabled()){
            l7_c1.performClick();
            return;
        }
        if(l7_c3.isEnabled()){
            l7_c3.performClick();
            return;
        }
        if(l7_c5.isEnabled()){
            l7_c5.performClick();
            return;
        }
        if(l7_c7.isEnabled()){
            l7_c7.performClick();
            return;
        }
        if(l8_c2.isEnabled()){
            l8_c2.performClick();
            return;
        }
        if(l8_c4.isEnabled()){
            l8_c4.performClick();
            return;
        }
        if(l8_c6.isEnabled()){
            l8_c6.performClick();
            return;
        }
        if(l8_c8.isEnabled()){
            l8_c8.performClick();
            return;
        }
    }

}