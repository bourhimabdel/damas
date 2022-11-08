package com.damasingo.play_onlinge;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.R;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

public class before_game extends AppCompatActivity {

    TextView txt_score,txt_match_score,txt_description;
    SelectableRoundedImageView img_player1,img_player2;
    LottieAnimationView lottie_wait_1,lottie_wait_2;


    String player1,player2;
    reqeust acc1,acc2;
    ArrayList<Integer> price=new ArrayList<>();
    int amount_to_play;
    double my_money;

    sql_manager db_Offline;
    connect_to_firebase db_online=new connect_to_firebase();

    DatabaseReference databaseReference;
    ValueEventListener listener;
    String description;

    Bundle bundle_come=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_before_game);

        initial_views();
        charge_player_data(getIntent().getExtras());
        Strat_match_if_i_the_player_1();


    }


    private void Strat_match_if_i_the_player_1() {
        String my_id=db_Offline.get__account().getId();

        txt_description.setText(description);

        if(my_id.equals(player1)){
            acc1=new aide().get_Object_request(db_Offline.get__account());
            acc1.setPhoto_saved(db_Offline.get__account().getPhoto_saved());
            acc2=db_Offline.get_this_request(player2);

            description=getResources().getString(R.string.we_are_waiting_for_your_friend)+" "+acc2.getName()+" "+getResources().getString(R.string.to_Come);

            db_online.Start_match(player1, player2,amount_to_play, etat -> {
                db_online.accept_challenge(player1, player2, amount_to_play, etat1 -> {

                    db_online.retrive_Tocken(player2, tocken -> new aide().sendNotifications_I_accept_your_challenge(tocken, "lets_play", player1));
                    listen_to_this_match();
                    txt_description.setText(description);
                });
            });

            db_online.Start_match(player2, player1,amount_to_play, etat -> {

            });
        }else {
            acc1=db_Offline.get_this_request(player1);
            acc2=new aide().get_Object_request(db_Offline.get__account());
            acc2.setPhoto_saved(db_Offline.get__account().getPhoto_saved());
            db_online.player_come_match(player1, player2, etat -> {

            });
            listen_to_this_match();
        }

    }

    private void listen_to_this_match() {
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
                state_change(1,state);
            }

            @Override
            public void get_player2(String state) {

                state_change(2,state);
            }
        });
    }

    private void state_change(int player,String state) {
        switch (player){
            case 1:
                if(state.equals("come")){
                    lottie_wait_1.setVisibility(View.INVISIBLE);

                    byte[] tof_saved=acc1.getPhoto_saved();
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
                    img_player1.setImageBitmap(decodedByte);
                    collect_money();

                    byte[] tof_saved2=acc2.getPhoto_saved();
                    Bitmap decodedByte2 = BitmapFactory.decodeByteArray(tof_saved2, 0, tof_saved2.length);
                    img_player2.setImageBitmap(decodedByte2);
                    anim(img_player2);
                }
                break;
            case 2:
                if(state.equals("come")){

                    lottie_wait_2.setVisibility(View.INVISIBLE);

                    byte[] tof_saved=acc2.getPhoto_saved();
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
                    img_player2.setImageBitmap(decodedByte);
                    collect_money();

                }
                break;
        }
    }

    Animation animFade;
    public void anim(View view){
        animFade = AnimationUtils.loadAnimation(this, R.anim.image);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animFade);


    }

    private void charge_player_data(Bundle bundle) {
        amount_to_play=bundle.getInt("play_to");
        bundle_come=bundle;

        player1=bundle.getString("player1");
        player2=bundle.getString("player2");
    }

    private void initial_views() {
        db_Offline=new sql_manager(this);
        description=getString(R.string.we_are_create_match)+getResources().getString(R.string.three_p);

        txt_score=findViewById(R.id.txt_my_score);
        txt_match_score=findViewById(R.id.txt_match_score);
        lottie_wait_1=findViewById(R.id.lottie_wait_1);
        lottie_wait_2=findViewById(R.id.lottie_wait_2);
        img_player1=findViewById(R.id.image_player1);
        img_player2=findViewById(R.id.image_player2);
        txt_description=findViewById(R.id.txt_description);

        my_money=db_Offline.get__account().getMoney();
        String m=db_Offline.get__account().getMoney()+"";
        txt_score.setText(m);

        price.add(100);
        price.add(500);
        price.add(2500);
        price.add(10000);
        price.add(50000);
        price.add(100000);
        price.add(500000);
        price.add(2500000);
        price.add(10000000);
    }

    private int[] get_score_description(String score){

        Log.e("score","b "+score);
        String [] temp = score.split("-");
        for (String s : temp) {
            System.out.println(s);
        }

        return new int[]{Integer.parseInt(temp[0]),Integer.parseInt(temp[1])};
    }

    int count_player=0;
    private void collect_money(){
        if(count_player==1) {

            img_player2.clearAnimation();

            new sound().collect(this);
            String m=(my_money-price.get(amount_to_play))+"";
            txt_score.setText(m);
            remplire_txt_match_score(amount_to_play);



            Intent table_game=new Intent(this, game_space.class);
            table_game.putExtras(bundle_come);

            end_listen();

            if(player1.equals(db_Offline.get__account().getId())) {
                end_challenge();

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


            account account=db_Offline.get__account();
            double new_money=account.getMoney()-price.get(amount_to_play);

            db_online.update_money(account.getId(), new_money, etat -> {
                account.setMoney(new_money);
                db_Offline.Update_V_A_acoount(before_game.this,account);
            });


            table_game.putExtra("match_count",0);
            startActivity(table_game);
            before_game.this.finish();


        }
        count_player++;
    }

    private void before_end_match(){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cancel_match);
        builder.setMessage(R.string.friend_not_join);
        builder.setPositiveButton(R.string.cancel_match, (dialog, which) -> {
            end_match();
            finish();
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.show();

        */


        new Msg_box().oui_non_alert(getResources().getString(R.string.cancel_match)
                ,getResources().getString(R.string.friend_not_join),
                getResources().getString(R.string.cancel_match),
                getResources().getString(R.string.no),
                before_game.this, new Msg_box.go_back() {
                    @Override
                    public void onCallBack(int action) {
                        if(action==0){
                            end_match();
                            finish();
                        }
                    }
                });
    }


    private void end_match(){
        db_online.initial_challenge(player1,player2, etat -> {

        });
        db_online.end_match(player1, player2, etat -> {

        });
        end_listen();
    }

    private void end_challenge(){
        db_online.initial_challenge(player1,player2, etat -> {

        });
    }

    public void end_listen() {
            databaseReference.removeEventListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        before_end_match();
    }

    @SuppressLint("DefaultLocale")
    private void remplire_txt_match_score(int amount_to_play) {
        txt_match_score.setVisibility(View.VISIBLE);
        switch (amount_to_play){
            case 0:
                txt_match_score.setText(String.format("%02d",200));
                break;
            case 1:
                txt_match_score.setText(String.format("%1d %2$s",1,getString(R.string.K)));
                break;
            case 2:
                txt_match_score.setText(String.format("%1d %2$s",5,getString(R.string.K)));
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
                txt_match_score.setText(String.format("%1d %2$s",1,getString(R.string.M)));
                break;
            case 7:
                txt_match_score.setText(String.format("%1d %2$s",5,getString(R.string.M)));
                break;
            case 8:
                txt_match_score.setText(String.format("%02d %2$s",20,getString(R.string.M)));
                break;
        }
    }
}