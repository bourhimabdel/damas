package com.damasingo.Msg_alert;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.ADAPTER.trending_items;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;

import com.damasingo.CLASS_UTIL.gain_day;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;

import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.local_match.local_game_space_robot;
import com.damasingo.play_onlinge.before_game;
import com.damasingo.table_play_off_line;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Msg_box  {

    com.damasingo.CLASS_UTIL.account account;

    public TextView txt_score;

    ArrayList<Integer> price=new ArrayList<>();
    ArrayList<Integer> point=new ArrayList<>();

    public Msg_box(){}

    @SuppressLint("DefaultLocale")
    public void get_price(final Context context, get_price get_price){


        LayoutInflater factory = LayoutInflater.from(context);

        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.select_price_to_play, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(true);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final Button button1=textEntryView.findViewById(R.id.p_0);
        final Button button2=textEntryView.findViewById(R.id.p_1);
        final Button button3=textEntryView.findViewById(R.id.p_2);
        final Button button4=textEntryView.findViewById(R.id.p_3);
        final Button button5=textEntryView.findViewById(R.id.p_4);
        final Button button6=textEntryView.findViewById(R.id.p_5);
        final Button button7=textEntryView.findViewById(R.id.p_6);
        final Button button8=textEntryView.findViewById(R.id.p_7);
        final Button button9=textEntryView.findViewById(R.id.p_8);

        account=new sql_manager(context).get__account();
        price.add(100);
        price.add(500);
        price.add(2500);
        price.add(10000);
        price.add(50000);
        price.add(100000);
        price.add(500000);
        price.add(2500000);
        price.add(10000000);

        check_if_this_button_on(0,button1);
        check_if_this_button_on(1,button2);
        check_if_this_button_on(2,button3);
        check_if_this_button_on(3,button4);
        check_if_this_button_on(4,button5);
        check_if_this_button_on(5,button6);
        check_if_this_button_on(6,button7);
        check_if_this_button_on(7,button8);
        check_if_this_button_on(8,button9);

        button1.setText(String.format("%02d",100));
        button2.setText(String.format("%02d",500));
        button3.setText(String.format("%.1f %2$s",2.5,context.getResources().getString(R.string.K)));
        button4.setText(String.format("%02d %2$s",10,context.getResources().getString(R.string.K)));
        button5.setText(String.format("%02d %2$s",50,context.getResources().getString(R.string.K)));
        button6.setText(String.format("%02d %2$s",100,context.getResources().getString(R.string.K)));
        button7.setText(String.format("%02d %2$s",500,context.getResources().getString(R.string.K)));
        button8.setText(String.format("%.1f %2$s",2.5,context.getResources().getString(R.string.M)));
        button9.setText(String.format("%02d %2$s",10,context.getResources().getString(R.string.M)));


        button1.setOnClickListener(v -> {
            get_price.onCallBack(0);
            alertDialog.cancel();
        });
        button2.setOnClickListener(v -> {
            get_price.onCallBack(1);
            alertDialog.cancel();
        });
        button3.setOnClickListener(v -> {
            get_price.onCallBack(2);
            alertDialog.cancel();
        });
        button4.setOnClickListener(v -> {
            get_price.onCallBack(3);
            alertDialog.cancel();
        });
        button5.setOnClickListener(v -> {
            get_price.onCallBack(4);
            alertDialog.cancel();
        });
        button6.setOnClickListener(v -> {
            get_price.onCallBack(5);
            alertDialog.cancel();
        });
        button7.setOnClickListener(v -> {
            get_price.onCallBack(6);
            alertDialog.cancel();
        });
        button8.setOnClickListener(v -> {
            get_price.onCallBack(7);
            alertDialog.cancel();
        });
        button9.setOnClickListener(v -> {
            get_price.onCallBack(8);
            alertDialog.cancel();
        });
       // txt_score.setText(String.format("\t%.0f\t",account.getMoney()));


       // add.setOnClickListener(v -> {
       //     get_price.onCallBack(-1);
       //     alertDialog.cancel();
       // });
        

    }

    private void check_if_this_button_on(int index,Button button) {
        if (price.get(index)>account.getMoney())
            button.setEnabled(false);
    }

    public interface get_price{
        void onCallBack(int value);
    }

    sql_manager db_offline;
    connect_to_firebase db_online=new connect_to_firebase();

    private int[] get_score_description(String score){

        Log.e("score","b "+score);
        String[] temp = score.split("-");
        for (String s : temp) {
            System.out.println(s);
        }

        return new int[]{Integer.parseInt(temp[0]),Integer.parseInt(temp[1])};
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void match_end(boolean gift_ready,final Context context, int amount, String player1, String player2,
                          String winner, go_back go_back){
        LayoutInflater factory = LayoutInflater.from(context);

        db_offline=new sql_manager(context);
        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.winner, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(false);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final boolean[] re_1 = {false};
        final boolean[] re_2 = {false};
        price.add(100);
        price.add(500);
        price.add(2500);
        price.add(10000);
        price.add(50000);
        price.add(100000);
        price.add(500000);
        price.add(2500000);
        price.add(10000000);

        point.add(200);
        point.add(400);
        point.add(800);
        point.add(1000);
        point.add(1040);
        point.add(1060);
        point.add(1070);
        point.add(1080);
        point.add(1090);

        final Button button_go_home=textEntryView.findViewById(R.id.btn_home);
        final Button button_replay=textEntryView.findViewById(R.id.btn_replay);
        final Button button_watch_ads=textEntryView.findViewById(R.id.btn_ads);


        txt_score=textEntryView.findViewById(R.id.txt_my_score);
        final TextView txt_description=textEntryView.findViewById(R.id.txt_match_description);
        final TextView msg1=textEntryView.findViewById(R.id.txt_msg1);
        final TextView msg2=textEntryView.findViewById(R.id.txt_msg2);



        final int[] lom = {0};
        listen_to_this_match(player1, player2, new get_state() {
            @Override
            public void onCallBack(int player, String state) {

                if(player==0){
                    msg1.setVisibility(View.VISIBLE);
                    if(state.equals("re")){
                        re_1[0] =true;
                        msg1.setText(context.getResources().getString(R.string.lets_play_a));
                        if(re_1[0] && re_2[0]) {
                            if(lom[0] ==0) {
                                databaseReference.removeEventListener(listener);
                                alertDialog.cancel();
                                go_back.onCallBack(1);
                                lom[0]++;
                            }
                        }
                    }else if(state.equals("exit")){
                        msg1.setText(context.getResources().getString(R.string.canot_play));
                        button_replay.setVisibility(View.GONE);
                    }
                }else if(player==1){
                    msg2.setVisibility(View.VISIBLE);
                    if(state.equals("re")){
                        re_2[0] =true;
                        msg2.setText(context.getResources().getString(R.string.lets_play_a));
                        if(re_1[0] && re_2[0]) {
                            if(lom[0] ==0) {
                                databaseReference.removeEventListener(listener);
                                alertDialog.cancel();
                                go_back.onCallBack(1);
                                lom[0]++;
                            }
                        }
                    }else if(state.equals("exit")){
                        msg2.setText(context.getResources().getString(R.string.canot_play));
                        button_replay.setVisibility(View.GONE);
                    }
                }
            }


        });




        final View view1=textEntryView.findViewById(R.id.view_player1);
        final View view2=textEntryView.findViewById(R.id.view_player2);

        final SelectableRoundedImageView image_1=textEntryView.findViewById(R.id.image_player1);
        final SelectableRoundedImageView image_2=textEntryView.findViewById(R.id.image_player2);

        final LottieAnimationView lottie_win3=textEntryView.findViewById(R.id.lottie_win3);
        final LottieAnimationView lottie_gift=textEntryView.findViewById(R.id.lottie_gift);
        final LottieAnimationView lottie_trophie=textEntryView.findViewById(R.id.lottie_trophie);
        final LottieAnimationView lottie_trophie2=textEntryView.findViewById(R.id.lottie_trophie2);




        if(!gift_ready){
            lottie_gift.setVisibility(View.INVISIBLE);
            button_watch_ads.setVisibility(View.INVISIBLE);
        }

        String child_player;
        if(player1.equals(db_offline.get__account().getId()))
        {
            child_player="player_1";
            account a=db_offline.get__account();
            reqeust b=db_offline.get_this_request(player2);

            byte[] tof_1=a.getPhoto_saved();
            Bitmap puc = BitmapFactory.decodeByteArray(tof_1, 0, tof_1.length);
            image_1.setImageBitmap(puc);

            byte[] tof_2=b.getPhoto_saved();
            Bitmap puc2 = BitmapFactory.decodeByteArray(tof_2, 0, tof_2.length);
            image_2.setImageBitmap(puc2);

        }
        else
        {
            child_player="player_2";
            account a=db_offline.get__account();
            reqeust b=db_offline.get_this_request(player1);

            byte[] tof_1=b.getPhoto_saved();
            Bitmap puc = BitmapFactory.decodeByteArray(tof_1, 0, tof_1.length);
            image_1.setImageBitmap(puc);

            byte[] tof_2=a.getPhoto_saved();
            Bitmap puc2 = BitmapFactory.decodeByteArray(tof_2, 0, tof_2.length);
            image_2.setImageBitmap(puc2);
        }

        String win=context.getResources().getString(R.string.congrats);
        win+="\n\n"+context.getResources().getString(R.string.coins)+" : +"+(price.get(amount)*2);
      //  win+="\n"+context.getResources().getString(R.string.Point_of_experience)+" : +"+(point.get(amount));

        int cash_back = 0;

        if(player1.equals(winner) && player1.equals(db_offline.get__account().getId()))
        {

            button_watch_ads.setText(context.getResources().getString(R.string.p));
            int[] sc_tab=get_score_description(db_offline.get_score_of(player2));
            sc_tab[0]++;
            sc_tab[1]--;
            score score=new score(player2, sc_tab[0]+"-"+sc_tab[1]);
            db_offline.Update_score_of(score);
            db_online.add_friends_score(context, score, etat -> {

            });

            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);




            //view1.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
            //view1.setVisibility(View.VISIBLE);
            lottie_trophie.setVisibility(View.VISIBLE);

            cash_back=price.get(amount)*2;
            double new_money=(db_offline.get__account().getMoney()+(price.get(amount)*2));
            txt_score.setText(new aide().get_the_string_compatible(new_money,context));




                account account=db_offline.get__account();

                account.setVictoir_gain(account.getVictoir_gain()+1);
                account.setTotal_money_win(account.getTotal_money_win()+(price.get(amount)*2));
                account.setMoney(account.getMoney()+(price.get(amount)*2));

                //if(account.getPoint_experience()+point.get(amount) >= new aide().get_max_point_experience(account.getLevel()+1)){
                //    int deference=(account.getPoint_experience()+point.get(amount))-(new aide().get_max_point_experience(account.getLevel()+1));
                //    account.setPoint_experience(deference);
                //    account.setLevel(account.getLevel()+1);
                //    win+="\n"+context.getResources().getString(R.string.level)+" "+account.getLevel();
                //}else {
                //    account.setPoint_experience(account.getPoint_experience()+point.get(amount));
                //}

            txt_description.setText(String.format("\t%1$s\t",win));

                account.setPhoto_saved(null);

                db_online.update_data_user(account, etat -> db_offline.Update_V_A_acoount(context,account));

                db_online.update_data_user_match_loser(player2, etat -> {

                });


        }
        else if(player2.equals(winner) && player2.equals(db_offline.get__account().getId()))
        {
            button_watch_ads.setText(context.getResources().getString(R.string.p));
            int[] sc_tab=get_score_description(db_offline.get_score_of(player1));
            sc_tab[0]++;
            sc_tab[1]--;
            score score=new score(player1, sc_tab[0]+"-"+sc_tab[1]);
            db_offline.Update_score_of(score);
            db_online.add_friends_score(context, score, etat -> {

            });

            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);

            cash_back=price.get(amount)*2;

            //view1.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
            //view1.setVisibility(View.VISIBLE);
            lottie_trophie2.setVisibility(View.VISIBLE);

            double new_money=(db_offline.get__account().getMoney()+(price.get(amount)*2));
            txt_score.setText(new aide().get_the_string_compatible(new_money,context));

            account account=db_offline.get__account();

            account.setVictoir_gain(account.getVictoir_gain()+1);
            account.setTotal_money_win(account.getTotal_money_win()+(price.get(amount)*2));
            account.setMoney(account.getMoney()+(price.get(amount)*2));

           //if(account.getPoint_experience()+point.get(amount) >= new aide().get_max_point_experience(account.getLevel()+1)){
           //    int deference=(account.getPoint_experience()+point.get(amount))-(new aide().get_max_point_experience(account.getLevel()+1));
           //    account.setPoint_experience(deference);
           //    account.setLevel(account.getLevel()+1);
           //    win+="\n"+context.getResources().getString(R.string.level)+" "+account.getLevel();
           //}else {
           //    account.setPoint_experience(account.getPoint_experience()+point.get(amount));
           //}


            txt_description.setText(String.format("\t%1$s\t",win));


                account.setPhoto_saved(null);

                db_online.update_data_user(account, etat -> db_offline.Update_V_A_acoount(context,account));

                db_online.update_data_user_match_loser(player1, etat -> {

                });

        }

        String lose= context.getResources().getString(R.string.Unfortunately);

        if(player1.equals(db_offline.get__account().getId()) && !player1.equals(winner))
        {
            //int[] sc_tab=get_score_description(db_offline.get_score_of(player2));
            //sc_tab[1]++;
            //score score=new score(player2, sc_tab[0]+"-"+sc_tab[1]);
            //db_offline.Update_score_of(score);
            //db_online.add_friends_score(context, score, new connect_to_firebase.add_succeful() {
            //    @Override
            //    public void add_complet(boolean etat) {
//
            //    }
            //});


            cash_back=price.get(amount);
            new sound().lose(context);
            txt_description.setText(lose);
            txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
        }
        else if(player2.equals(db_offline.get__account().getId()) && !player2.equals(winner))
        {

            //int[] sc_tab=get_score_description(db_offline.get_score_of(player1));
            //sc_tab[1]++;
            //score score=new score(player1, sc_tab[0]+"-"+sc_tab[1]);
            //db_offline.Update_score_of(score);
            //db_online.add_friends_score(context, score, new connect_to_firebase.add_succeful() {
            //    @Override
            //    public void add_complet(boolean etat) {
//
            //    }
            //});

            cash_back=price.get(amount);
            new sound().lose(context);
            txt_description.setText(lose);
            txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));

        }


        button_go_home.setOnClickListener(v -> {
            databaseReference.removeEventListener(listener);
            db_online.player_state_after_match(player1, player2, child_player, "exit", new connect_to_firebase.add_succeful() {
                @Override
                public void add_complet(boolean etat) {

                }
            });
            alertDialog.cancel();
            go_back.onCallBack(2);
        });

        button_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db_offline.get__account().getMoney()>=price.get(amount)) {
                    db_online.player_state_after_match(player1, player2, child_player, "re", new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {

                        }
                    });
                    button_replay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
                    button_replay.setText(context.getResources().getString(R.string.wait));
                    button_replay.setEnabled(false);
                }else {
                    Toast.makeText(context, context.getResources().getString(R.string.ollect)+" "+price.get(amount)+" "+
                            context.getResources().getString(R.string.coins), Toast.LENGTH_LONG).show();
                    new vibrateur(context).vibrate(100);
                }
                //alertDialog.cancel();
                //go_back.onCallBack(1);
            }
        });

        int finalCash_back = cash_back;
        button_watch_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottie_gift.setVisibility(View.INVISIBLE);
                button_watch_ads.setVisibility(View.INVISIBLE);
                go_back.onCallBack(finalCash_back);
            }
        });


    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void match_randomly(Bitmap bitmap,boolean gift_ready
            ,final Context context, int amount, String player1, String player2, String winner, go_back go_back){
        LayoutInflater factory = LayoutInflater.from(context);

        db_offline=new sql_manager(context);
        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.winner, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(false);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final boolean[] re_1 = {false};
        final boolean[] re_2 = {false};
        price.add(100);
        price.add(500);
        price.add(2500);
        price.add(10000);
        price.add(50000);
        price.add(100000);
        price.add(500000);
        price.add(2500000);
        price.add(10000000);

        point.add(200);
        point.add(400);
        point.add(800);
        point.add(1000);
        point.add(1040);
        point.add(1060);
        point.add(1070);
        point.add(1080);
        point.add(1090);

        final Button button_go_home=textEntryView.findViewById(R.id.btn_home);
        final Button button_replay=textEntryView.findViewById(R.id.btn_replay);
        final Button button_watch_ads=textEntryView.findViewById(R.id.btn_ads);


        txt_score=textEntryView.findViewById(R.id.txt_my_score);
        final TextView txt_description=textEntryView.findViewById(R.id.txt_match_description);
        final TextView msg1=textEntryView.findViewById(R.id.txt_msg1);
        final TextView msg2=textEntryView.findViewById(R.id.txt_msg2);
        final TextView level=textEntryView.findViewById(R.id.currentState_level);


        final int[] lom = {0};
        listen_to_this_match(player1, player2, new get_state() {
            @Override
            public void onCallBack(int player, String state) {

                if(player==0){
                    msg1.setVisibility(View.VISIBLE);
                    if(state.equals("re")){
                        re_1[0] =true;
                        msg1.setText(context.getResources().getString(R.string.lets_play_a));
                        if(re_1[0] && re_2[0]) {
                            if(lom[0] ==0) {
                                databaseReference.removeEventListener(listener);
                                alertDialog.cancel();
                                go_back.onCallBack(1);
                                lom[0]++;
                            }
                        }
                    }else if(state.equals("exit")){
                        msg1.setText(context.getResources().getString(R.string.canot_play));
                        button_replay.setVisibility(View.GONE);
                    }
                }else if(player==1){
                    msg2.setVisibility(View.VISIBLE);
                    if(state.equals("re")){
                        re_2[0] =true;
                        msg2.setText(context.getResources().getString(R.string.lets_play_a));
                        if(re_1[0] && re_2[0]) {
                            if(lom[0] ==0) {
                                databaseReference.removeEventListener(listener);
                                alertDialog.cancel();
                                go_back.onCallBack(1);
                                lom[0]++;
                            }
                        }
                    }else if(state.equals("exit")){
                        msg2.setText(context.getResources().getString(R.string.canot_play));
                        button_replay.setVisibility(View.GONE);
                    }
                }
            }


        });




        final View view1=textEntryView.findViewById(R.id.view_player1);
        final View view2=textEntryView.findViewById(R.id.view_player2);

        final SelectableRoundedImageView image_1=textEntryView.findViewById(R.id.image_player1);
        final SelectableRoundedImageView image_2=textEntryView.findViewById(R.id.image_player2);

        final LottieAnimationView lottie_win3=textEntryView.findViewById(R.id.lottie_win3);
        final LottieAnimationView lottie_gift=textEntryView.findViewById(R.id.lottie_gift);
        final LottieAnimationView lottie_trophie=textEntryView.findViewById(R.id.lottie_trophie);
        final LottieAnimationView lottie_trophie2=textEntryView.findViewById(R.id.lottie_trophie2);





        if(!gift_ready){
            lottie_gift.setVisibility(View.INVISIBLE);
            button_watch_ads.setVisibility(View.INVISIBLE);
        }

        String child_player;
        if(player1.equals(db_offline.get__account().getId()))
        {
            child_player="player_1";
            account a=db_offline.get__account();
           // reqeust b=db_offline.get_this_request(player2);

            byte[] tof_1=a.getPhoto_saved();
            Bitmap puc = BitmapFactory.decodeByteArray(tof_1, 0, tof_1.length);
            image_1.setImageBitmap(puc);

           // byte[] tof_2=b.getPhoto_saved();
           // Bitmap puc2 = BitmapFactory.decodeByteArray(tof_2, 0, tof_2.length);
            image_2.setImageBitmap(bitmap);

        }
        else
        {
            child_player="player_2";
            account a=db_offline.get__account();
            //reqeust b=db_offline.get_this_request(player1);

           // byte[] tof_1=b.getPhoto_saved();
           // Bitmap puc = BitmapFactory.decodeByteArray(tof_1, 0, tof_1.length);
            image_1.setImageBitmap(bitmap);

            byte[] tof_2=a.getPhoto_saved();
            Bitmap puc2 = BitmapFactory.decodeByteArray(tof_2, 0, tof_2.length);
            image_2.setImageBitmap(puc2);
        }

        String win=context.getResources().getString(R.string.congrats);
        win+="\n\n"+context.getResources().getString(R.string.coins)+" : +"+(price.get(amount)*2);
        win+="\n"+context.getResources().getString(R.string.Point_of_experience)+" : +"+(point.get(amount));

        int cash_back = 0;

        if(player1.equals(winner) && player1.equals(db_offline.get__account().getId()))
        {

            button_watch_ads.setText(context.getResources().getString(R.string.p));
           // int[] sc_tab=get_score_description(db_offline.get_score_of(player2));
           // sc_tab[0]++;
           // sc_tab[1]--;
           // score score=new score(player2, sc_tab[0]+"-"+sc_tab[1]);
           // db_offline.Update_score_of(score);
           // db_online.add_friends_score(context, score, etat -> {
//
           // });

            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);




            //view1.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
            //view1.setVisibility(View.VISIBLE);
            lottie_trophie.setVisibility(View.VISIBLE);


            cash_back=price.get(amount)*2;
            double new_money=(db_offline.get__account().getMoney()+(price.get(amount)*2));
            txt_score.setText(new aide().get_the_string_compatible(new_money,context));




            account account=db_offline.get__account();

            account.setVictoir_gain(account.getVictoir_gain()+1);
            account.setTotal_money_win(account.getTotal_money_win()+(price.get(amount)*2));
            account.setMoney(account.getMoney()+(price.get(amount)*2));

            if(account.getPoint_experience()+point.get(amount) >= new aide().get_max_point_experience(account.getLevel()+1)){
                int deference=(account.getPoint_experience()+point.get(amount))-(new aide().get_max_point_experience(account.getLevel()+1));
                account.setPoint_experience(deference);
                account.setLevel(account.getLevel()+1);
               // win+="\n"+context.getResources().getString(R.string.level)+" "+account.getLevel();
                level.setText(String.format("%01d",(account.getLevel())));
                level.setBackground(new aide().get_the_star_compatible(account.getLevel(),context));
                anim(level,context);

            }else {
                account.setPoint_experience(account.getPoint_experience()+point.get(amount));
            }

            txt_description.setText(String.format("\t%1$s\t",win));

            account.setPhoto_saved(null);

            db_online.upgrade_my_score_day(account.getId(),price.get(amount)*2,account.getCountry());
            db_online.update_data_user(account, etat -> db_offline.Update_V_A_acoount(context,account));

            db_online.update_data_user_match_loser(player2, etat -> {

            });



        }
        else if(player2.equals(winner) && player2.equals(db_offline.get__account().getId()))
        {
            button_watch_ads.setText(context.getResources().getString(R.string.p));
          //  int[] sc_tab=get_score_description(db_offline.get_score_of(player1));
          //  sc_tab[0]++;
          //  sc_tab[1]--;
          //  score score=new score(player1, sc_tab[0]+"-"+sc_tab[1]);
          //  db_offline.Update_score_of(score);
          //  db_online.add_friends_score(context, score, etat -> {
//
          //  });

            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);

            cash_back=price.get(amount)*2;
            //view2.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
            //view2.setVisibility(View.VISIBLE);
            lottie_trophie2.setVisibility(View.VISIBLE);

            double new_money=(db_offline.get__account().getMoney()+(price.get(amount)*2));
            txt_score.setText(new aide().get_the_string_compatible(new_money,context));

            account account=db_offline.get__account();

            account.setVictoir_gain(account.getVictoir_gain()+1);
            account.setTotal_money_win(account.getTotal_money_win()+(price.get(amount)*2));
            account.setMoney(account.getMoney()+(price.get(amount)*2));
            if(account.getPoint_experience()+point.get(amount) >= new aide().get_max_point_experience(account.getLevel()+1)){
                int deference=(account.getPoint_experience()+point.get(amount))-(new aide().get_max_point_experience(account.getLevel()+1));
                account.setPoint_experience(deference);
                account.setLevel(account.getLevel()+1);
                //win+="\n"+context.getResources().getString(R.string.level)+" "+account.getLevel();
                level.setText(String.format("%01d",(account.getLevel())));
                level.setBackground(new aide().get_the_star_compatible(account.getLevel(),context));
                anim(level,context);
            }else {
                account.setPoint_experience(account.getPoint_experience()+point.get(amount));
            }


            txt_description.setText(String.format("\t%1$s\t",win));


            account.setPhoto_saved(null);

            db_online.upgrade_my_score_day(account.getId(),price.get(amount)*2,account.getCountry());

            db_online.update_data_user(account, etat -> db_offline.Update_V_A_acoount(context,account));

            db_online.update_data_user_match_loser(player1, etat -> {

            });

        }

        String lose= context.getResources().getString(R.string.Unfortunately);

        if(player1.equals(db_offline.get__account().getId()) && !player1.equals(winner))
        {
            //int[] sc_tab=get_score_description(db_offline.get_score_of(player2));
            //sc_tab[1]++;
            //score score=new score(player2, sc_tab[0]+"-"+sc_tab[1]);
            //db_offline.Update_score_of(score);
            //db_online.add_friends_score(context, score, new connect_to_firebase.add_succeful() {
            //    @Override
            //    public void add_complet(boolean etat) {
//
            //    }
            //});


            cash_back=price.get(amount);
            new sound().lose(context);
            txt_description.setText(lose);
            txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
        }
        else if(player2.equals(db_offline.get__account().getId()) && !player2.equals(winner))
        {

            //int[] sc_tab=get_score_description(db_offline.get_score_of(player1));
            //sc_tab[1]++;
            //score score=new score(player1, sc_tab[0]+"-"+sc_tab[1]);
            //db_offline.Update_score_of(score);
            //db_online.add_friends_score(context, score, new connect_to_firebase.add_succeful() {
            //    @Override
            //    public void add_complet(boolean etat) {
//
            //    }
            //});

            cash_back=price.get(amount);
            new sound().lose(context);
            txt_description.setText(lose);
            txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
        }


        button_go_home.setOnClickListener(v -> {
            databaseReference.removeEventListener(listener);
            db_online.player_state_after_match(player1, player2, child_player, "exit", new connect_to_firebase.add_succeful() {
                @Override
                public void add_complet(boolean etat) {

                }
            });
            alertDialog.cancel();
            go_back.onCallBack(2);
        });

        button_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db_offline.get__account().getMoney()>=price.get(amount)) {
                    db_online.player_state_after_match(player1, player2, child_player, "re", new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {

                        }
                    });
                    button_replay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
                    button_replay.setText(context.getResources().getString(R.string.wait));
                    button_replay.setEnabled(false);
                }else {
                    Toast.makeText(context, context.getResources().getString(R.string.ollect)+" "+price.get(amount)+" "+
                            context.getResources().getString(R.string.coins), Toast.LENGTH_LONG).show();
                    new vibrateur(context).vibrate(100);
                }
                //alertDialog.cancel();
                //go_back.onCallBack(1);
            }
        });

        int finalCash_back = cash_back;
        button_watch_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottie_gift.setVisibility(View.INVISIBLE);
                button_watch_ads.setVisibility(View.INVISIBLE);
                go_back.onCallBack(finalCash_back);
            }
        });


    }

    Animation animFade;
    public void anim(View view,Context context){
        animFade = AnimationUtils.loadAnimation(context, R.anim.fide_out_btn);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animFade);
    }

    DatabaseReference databaseReference;
    ValueEventListener listener;
    private void listen_to_this_match(String player1,String player2,get_state call) {
        db_online.listen_to_this_match(player1, player2, new connect_to_firebase.listener_match_before_start() {
            @Override
            public void get_listener(ValueEventListener listener2) {
                listener=listener2;
            }

            @Override
            public void get_references(DatabaseReference reference) {
                databaseReference =reference;
            }

            @Override
            public void get_player1(String state) {
                if(state.equals("exit"))
                    call.onCallBack(0,"exit");
                else if(state.equals("re"))
                    call.onCallBack(0,"re");
                else
                    call.onCallBack(-1,"no");

            }

            @Override
            public void get_player2(String state) {
                if(state.equals("exit"))
                    call.onCallBack(1,"exit");
                else if(state.equals("re"))
                    call.onCallBack(1,"re");
                else
                    call.onCallBack(-1,"no");
            }
        });
    }

    @SuppressLint({"HardwareIds", "DefaultLocale"})
    public void match_bluettoth_end(boolean gift_ready,final Context context, String player1, String player2, String winner, go_back go_back){
        LayoutInflater factory = LayoutInflater.from(context);

        db_offline=new sql_manager(context);
        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.winner, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(false);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final Button button_go_home=textEntryView.findViewById(R.id.btn_home);
        final Button button_replay=textEntryView.findViewById(R.id.btn_replay);
        final Button button_watch_ads=textEntryView.findViewById(R.id.btn_ads);



        final View view1=textEntryView.findViewById(R.id.view_player1);
        final View view2=textEntryView.findViewById(R.id.view_player2);

        final SelectableRoundedImageView image_1=textEntryView.findViewById(R.id.image_player1);
        final SelectableRoundedImageView image_2=textEntryView.findViewById(R.id.image_player2);

        final LottieAnimationView lottie_win3=textEntryView.findViewById(R.id.lottie_win3);
        final LottieAnimationView lottie_gift=textEntryView.findViewById(R.id.lottie_gift);
        final LottieAnimationView lottie_trophie=textEntryView.findViewById(R.id.lottie_trophie);
        final LottieAnimationView lottie_trophie2=textEntryView.findViewById(R.id.lottie_trophie2);

        final TextView txt_description=textEntryView.findViewById(R.id.txt_match_description);
        txt_score=textEntryView.findViewById(R.id.txt_my_score);

        //txt_score.setVisibility(View.INVISIBLE);

        if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in"))
            txt_score.setText(new aide().get_the_string_compatible(
                    new sharedpreferences(context).get_score_offline(),context));
        else
            txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));

        if(!gift_ready){
            lottie_gift.setVisibility(View.INVISIBLE);
            button_watch_ads.setVisibility(View.INVISIBLE);
        }

        Bitmap puc;
        if(player1.equals(BluetoothAdapter.getDefaultAdapter().getAddress()))
        {

            if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in")){
                Drawable d= ContextCompat.getDrawable(context,R.drawable.inconnu);
                assert d != null;
                puc=get_Bitmap(d);
            }else {
                byte[] tof_saved=db_offline.get__account().getPhoto_saved();
                puc = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            }

            image_1.setImageBitmap(puc);
            Drawable d= ContextCompat.getDrawable(context,R.drawable.smartphone);
            assert d != null;
            puc = get_Bitmap(d);
            image_2.setImageBitmap(puc);
        }
        else
        {
            if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in")){
                Drawable d= ContextCompat.getDrawable(context,R.drawable.inconnu);
                assert d != null;
                puc=get_Bitmap(d);
            }else {
                byte[] tof_saved=db_offline.get__account().getPhoto_saved();
                puc = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            }

            image_2.setImageBitmap(puc);
            Drawable d= ContextCompat.getDrawable(context,R.drawable.smartphone);
            assert d != null;
            puc = get_Bitmap(d);
            image_1.setImageBitmap(puc);
        }

        String win=context.getResources().getString(R.string.congrats);

        if(player1.equals(winner) && player1.equals(BluetoothAdapter.getDefaultAdapter().getAddress()))
        {
            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);

           //view1.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
           //view1.setVisibility(View.VISIBLE);
            lottie_trophie.setVisibility(View.VISIBLE);



            txt_description.setText(String.format("\t%1$s\t",win));




        }
        else if(player2.equals(winner) && player2.equals(BluetoothAdapter.getDefaultAdapter().getAddress()))
        {
            new sound().win(context);

            lottie_win3.setVisibility(View.VISIBLE);

           // view2.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
           // view2.setVisibility(View.VISIBLE);
            lottie_trophie2.setVisibility(View.VISIBLE);




            txt_description.setText(String.format("\t%1$s\t",win));



        }

        String lose=context.getResources().getString(R.string.Unfortunately);

        if(player1.equals(BluetoothAdapter.getDefaultAdapter().getAddress()) && !player1.equals(winner))
        {
            new sound().lose(context);
            txt_description.setText(lose);
        }
        else if(player2.equals(BluetoothAdapter.getDefaultAdapter().getAddress()) && !player2.equals(winner))
        {
            new sound().lose(context);
            txt_description.setText(lose);
        }


        button_go_home.setOnClickListener(v -> {
            alertDialog.cancel();
            go_back.onCallBack(2);
        });

        button_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back.onCallBack(1);
            }
        });

        button_watch_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottie_gift.setVisibility(View.INVISIBLE);
                button_watch_ads.setVisibility(View.INVISIBLE);
                go_back.onCallBack(0);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void match_local_end(boolean gift_ready, final Context context, String winner, go_back go_back){
        LayoutInflater factory = LayoutInflater.from(context);

        db_offline=new sql_manager(context);
        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.winner, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(false);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final Button button_go_home=textEntryView.findViewById(R.id.btn_home);
        final Button button_replay=textEntryView.findViewById(R.id.btn_replay);
        final Button button_watch_ads=textEntryView.findViewById(R.id.btn_ads);


        final View view1=textEntryView.findViewById(R.id.view_player1);
        final View view2=textEntryView.findViewById(R.id.view_player2);

        final SelectableRoundedImageView image_1=textEntryView.findViewById(R.id.image_player1);
        final SelectableRoundedImageView image_2=textEntryView.findViewById(R.id.image_player2);


        final LottieAnimationView lottie_win3=textEntryView.findViewById(R.id.lottie_win3);
        final LottieAnimationView lottie_gift=textEntryView.findViewById(R.id.lottie_gift);
        final LottieAnimationView lottie_trophie=textEntryView.findViewById(R.id.lottie_trophie);
        final LottieAnimationView lottie_trophie2=textEntryView.findViewById(R.id.lottie_trophie2);

        final TextView txt_description=textEntryView.findViewById(R.id.txt_match_description);
        txt_score=textEntryView.findViewById(R.id.txt_my_score);

        //txt_score.setVisibility(View.INVISIBLE);

        if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in"))
            txt_score.setText(new aide().get_the_string_compatible(
                    new sharedpreferences(context).get_score_offline(),context));
        else
            txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));


        txt_description.setVisibility(View.INVISIBLE);

        if(!gift_ready){
            lottie_gift.setVisibility(View.INVISIBLE);
            button_watch_ads.setVisibility(View.INVISIBLE);
        }

        Bitmap puc;
        Drawable d= ContextCompat.getDrawable(context,R.drawable.piece_player_1);
        assert d != null;
        puc = get_Bitmap(d);
        image_1.setImageBitmap(puc);

        Drawable dd;
        if(context instanceof local_game_space_robot) {
            dd = ContextCompat.getDrawable(context, R.drawable.robot_);
        }else {
            dd = ContextCompat.getDrawable(context, R.drawable.piece_player_2);
        }
        assert dd != null;
        puc = get_Bitmap(dd);
        image_2.setImageBitmap(puc);


        if(winner.equals("0"))
        {
            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);

            //view1.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
            //view1.setVisibility(View.VISIBLE);
            lottie_trophie.setVisibility(View.VISIBLE);

        }
        else
        {
            new sound().win(context);
            lottie_win3.setVisibility(View.VISIBLE);

           // view2.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_win));
           // view2.setVisibility(View.VISIBLE);
            lottie_trophie2.setVisibility(View.VISIBLE);




        }

        button_go_home.setOnClickListener(v -> {
            alertDialog.cancel();
            go_back.onCallBack(2);
        });

        button_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back.onCallBack(1);
            }
        });

        button_watch_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottie_gift.setVisibility(View.INVISIBLE);
                button_watch_ads.setVisibility(View.INVISIBLE);
                go_back.onCallBack(0);
            }
        });
    }


    Context context;
    @SuppressLint("DefaultLocale")
    public void show_classment(String space, final Context context, AppCompatActivity activity){
        LayoutInflater factory = LayoutInflater.from(context);

        Praincipal praincipal= (Praincipal) activity;
        db_offline=new sql_manager(context);
        this.context=context;
        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.callment_day, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(true);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final ConstraintLayout view_data=textEntryView.findViewById(R.id.data_view);
        final ShimmerFrameLayout view_shimmer=textEntryView.findViewById(R.id.shimmerLayout);

        final SelectableRoundedImageView image_player_1=textEntryView.findViewById(R.id.image_player1);
        final SelectableRoundedImageView image_player_2=textEntryView.findViewById(R.id.image_player3);
        final SelectableRoundedImageView image_player_3=textEntryView.findViewById(R.id.image_player2);

        final TextView txt_1=textEntryView.findViewById(R.id.txt_collect_score);
        final TextView txt_2=textEntryView.findViewById(R.id.txt_collect_score_3);
        final TextView txt_3=textEntryView.findViewById(R.id.txt_collect_score_2);

        final TextView txt_class_1=textEntryView.findViewById(R.id.c1);
        final TextView txt_class_2=textEntryView.findViewById(R.id.c3);
        final TextView txt_class_3=textEntryView.findViewById(R.id.c2);

        final TextView no_data=textEntryView.findViewById(R.id.no_data);


        new connect_to_firebase().retrive_top_score(space, new connect_to_firebase.game_gain_list() {
            @Override
            public void onCall_back(ArrayList<gain_day> lists) {

                Collections.sort(lists);
                Collections.reverse(lists);

                if(lists.size()==0) {
                    view_shimmer.stopShimmer();
                    view_shimmer.setVisibility(View.GONE);
                    view_data.setVisibility(View.INVISIBLE);
                    no_data.setVisibility(View.VISIBLE);
                }
                else if(lists.size()==1){
                    image_player_3.setVisibility(View.GONE);
                    txt_class_3.setVisibility(View.GONE);
                    txt_3.setVisibility(View.GONE);
                    image_player_2.setVisibility(View.GONE);
                    txt_class_2.setVisibility(View.GONE);
                    txt_2.setVisibility(View.GONE);

                    txt_1.setText(new aide().get_the_string_compatible(lists.get(0).getGain(), context));

                    get_ob_from_data_base(0,lists.get(0).getId(), image_player_1, new on_complete() {
                        @Override
                        public void onCallback() {
                            praincipal.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view_shimmer.stopShimmer();
                                    view_shimmer.setVisibility(View.GONE);
                                    view_data.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                }
                else if(lists.size()==2){
                    image_player_3.setVisibility(View.GONE);
                    txt_class_3.setVisibility(View.GONE);
                    txt_3.setVisibility(View.GONE);

                    txt_1.setText(new aide().get_the_string_compatible(lists.get(0).getGain(), context));
                    txt_2.setText(new aide().get_the_string_compatible(lists.get(1).getGain(), context));

                    get_ob_from_data_base(0,lists.get(0).getId(), image_player_1, new on_complete() {
                        @Override
                        public void onCallback() {
                            get_ob_from_data_base(1,lists.get(1).getId(), image_player_2, new on_complete() {
                                @Override
                                public void onCallback() {
                                    praincipal.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            view_shimmer.stopShimmer();
                                            view_shimmer.setVisibility(View.GONE);
                                            view_data.setVisibility(View.VISIBLE);
                                        }
                                    });

                                }
                            });
                        }
                    });
                }
                else if(lists.size()==3){
                    txt_1.setText(new aide().get_the_string_compatible(lists.get(0).getGain(), context));
                    txt_2.setText(new aide().get_the_string_compatible(lists.get(1).getGain(), context));
                    txt_3.setText(new aide().get_the_string_compatible(lists.get(2).getGain(), context));

                    get_ob_from_data_base(0,lists.get(0).getId(), image_player_1, new on_complete() {
                        @Override
                        public void onCallback() {
                            get_ob_from_data_base(1,lists.get(1).getId(), image_player_2, new on_complete() {
                                @Override
                                public void onCallback() {
                                    get_ob_from_data_base(2,lists.get(2).getId(), image_player_3, new on_complete() {
                                        @Override
                                        public void onCallback() {
                                            praincipal.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    view_shimmer.stopShimmer();
                                                    view_shimmer.setVisibility(View.GONE);
                                                    view_data.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }


                image_player_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        praincipal.press_back_tacker.add(0);
                        praincipal.snack_bar.show_this_data(lists.get(0).getId(), context, praincipal,images[0],null);
                        alertDialog.cancel();

                    }
                });
                image_player_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        praincipal.press_back_tacker.add(0);
                        praincipal.snack_bar.show_this_data(lists.get(1).getId(), context, praincipal,images[1],null);
                        alertDialog.cancel();
                    }
                });
                image_player_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        praincipal.press_back_tacker.add(0);
                        praincipal.snack_bar.show_this_data(lists.get(2).getId(), context, praincipal,images[2],null);
                        alertDialog.cancel();
                    }
                });
            }
        });




    }

    Bitmap[] images=new Bitmap[3];
    private void get_ob_from_data_base(int num,String id,SelectableRoundedImageView imageView,on_complete on_complete) {
        db_online.retrive_url_img(id, new connect_to_firebase.URL() {
            @Override
            public void onCallback(String url) {
                download_Image(url, new trending_items.image_ready() {
                    @Override
                    public void onCallback(Drawable bitmap) {
                        BitmapDrawable d=(BitmapDrawable)bitmap;
                        final Bitmap resource=d.getBitmap();
                        imageView.setImageBitmap(resource);
                        on_complete.onCallback();
                        images[num]=resource;
                    }
                });
            }
        });
    }

    public void download_Image(String url, trending_items.image_ready image_ready){
        // Log.e("image is","gg "+account.getImage());
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        download_Image(url,image_ready);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        image_ready.onCallback(bb);
                        return false;
                    }
                }).submit();

    }



    public interface on_complete{
        void onCallback();
    }

    public interface go_back{
        void onCallBack(int action);
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

    @SuppressLint("DefaultLocale")
    public void oui_non_alert(String title,String description,String oui,String non,Context context,go_back go_back){
        LayoutInflater factory = LayoutInflater.from(context);

        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.oui_non_alert, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(textEntryView);
        alert.setCancelable(true);
        AlertDialog alertDialog=alert.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        final TextView txt_title=textEntryView.findViewById(R.id.title);
        final TextView txt_description=textEntryView.findViewById(R.id.discription);

        final Button btn_oui=textEntryView.findViewById(R.id.btn_oui);
        final Button btn_non=textEntryView.findViewById(R.id.btn_non);


        txt_title.setText(title);
        txt_description.setText(description);
        btn_oui.setText(oui);
        btn_non.setText(non);

        btn_oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back.onCallBack(0);
                alertDialog.cancel();
            }
        });

        btn_non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back.onCallBack(1);
                alertDialog.cancel();
            }
        });
    }

    public interface get_state{
        void onCallBack(int player,String state );
    }
}
