package com.damasingo.randomly;

import androidx.annotation.NonNull;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.randomly_ob;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.R;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.play_onlinge.before_game;
import com.damasingo.play_onlinge.game_space;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

public class before_rundomly_match extends AppCompatActivity {

    Bundle bundle;
    TextView txt_score,txt_match_score,txt_description;
    SelectableRoundedImageView img_player1,img_player2;
    LottieAnimationView lottie_wait_1,lottie_wait_2;

    sql_manager db_Offline;
    connect_to_firebase db_online=new connect_to_firebase();

    ArrayList<Integer> price=new ArrayList<>();
    int amount_to_play;

    String match_state="connecting";

    Query query=null;
    ChildEventListener childEventListener=null;
    DatabaseReference reference_to_close=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_before_game);

        initiale_view();
        Start_match();
    }

    private void charge_data_user() {
        byte[] tof_saved=db_Offline.get__account().getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        img_player1.setImageBitmap(decodedByte);
        img_player2.setImageResource(R.drawable.inconnu);
        anim(img_player2);

        lottie_wait_1.setVisibility(View.GONE);
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

    private void initiale_view() {
        bundle=getIntent().getExtras();
        amount_to_play=bundle.getInt("amount");

        db_Offline=new sql_manager(this);
        //description=getString(R.string.we_are_create_match)+getResources().getString(R.string.three_p);

        txt_score=findViewById(R.id.txt_my_score);
        txt_match_score=findViewById(R.id.txt_match_score);
        lottie_wait_1=findViewById(R.id.lottie_wait_1);
        lottie_wait_2=findViewById(R.id.lottie_wait_2);
        img_player1=findViewById(R.id.image_player1);
        img_player2=findViewById(R.id.image_player2);
        txt_description=findViewById(R.id.txt_description);



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


    DatabaseReference server,reference_palyer2,reference_palyer1,reference_etat,open_refer;
    ValueEventListener listner_player2,listner_player1,listner_Open,listner_etat;
    private void Start_match(){
        txt_description.setText(getResources().getString(R.string.coo)+" "+getResources().getString(R.string.three_p));


        final int[] i = {0};
        db_online.randomly_match(new sql_manager(this).get__account().getId()
                , amount_to_play, new connect_to_firebase.Random() {
                    @Override
                    public void waiting_friend() {
                        charge_data_user();
                        txt_description.setText(getResources().getString(R.string.w)+" "+getResources().getString(R.string.three_p));
                        match_state="tack_place";
                    }

                    @Override
                    public void get_players(String f, String s) {
                        if(i[0] ==0) {
                            lunch_game(f, s);
                            i[0]++;
                        }
                        txt_description.setText(getResources().getString(R.string.starting)+" "+getResources().getString(R.string.three_p));
                        match_state="match_start";

                        Log.e("start_match","ggg");
                    }

                    @Override
                    public void listening_to_some_on(Query queryy, ChildEventListener listener, DatabaseReference ref_to_close) {
                        query=queryy;
                        childEventListener=listener;
                        reference_to_close=ref_to_close;
                        match_state="listening";
                    }
                });
        /*new connect_to_firebase().randomly_check(new sql_manager(this).get__account().getId()
                ,amount_to_play, new connect_to_firebase.randomly_call_back() {
                    @Override
                    public void get_players(String first_player_id, String second_player_id) {
                        lunch_game(first_player_id,second_player_id);
                        txt_description.setText("Starting match "+getResources().getString(R.string.three_p));
                        match_state="start";
                    }

                    @Override
                    public void is_close() {
                        match_state="nothing";
                    }

                    @Override
                    public void connected_to_server(){
                        charge_data_user();
                        txt_description.setText("waiting a player "+getResources().getString(R.string.three_p));
                        match_state="connected";
                    }


                    @Override
                    public void server_ref(DatabaseReference server_ref) {
                        server=server_ref;
                    }



                    @Override
                    public void listener_to_player_2(DatabaseReference db_reference, ValueEventListener va) {
                        reference_palyer2=db_reference;
                        listner_player2=va;
                        match_state="listener_to_player2";
                    }

                    @Override
                    public void listener_to_player_1(DatabaseReference db_reference, ValueEventListener va) {
                        reference_palyer1=db_reference;
                        listner_player1=va;
                        match_state="listener_to_player1";
                    }

                    @Override
                    public void listener_to_etat(DatabaseReference db_reference, ValueEventListener va) {
                        reference_etat=db_reference;
                        listner_etat=va;
                        match_state="listener_to_etat";
                    }

                    @Override
                    public void listener_to_open(DatabaseReference db_reference, ValueEventListener va) {
                        open_refer=db_reference;
                        listner_Open=va;
                        match_state="listener_to_open";
                    }
                });

         */
    }

    private void lunch_game(String player1,String player2){
        img_player2.clearAnimation();

        lottie_wait_1.setVisibility(View.GONE);
        lottie_wait_2.setVisibility(View.GONE);
        img_player2.setVisibility(View.VISIBLE);

        new sound().collect(this);
        String m=(db_Offline.get__account().getMoney()-price.get(amount_to_play))+"";
        txt_score.setText(m);
        remplire_txt_match_score(amount_to_play);



        Intent table_game=new Intent(this, game_space_randomly.class);
        table_game.putExtra("play_to",amount_to_play);
        table_game.putExtra("player1",player1);
        table_game.putExtra("player2",player2);


        account account=db_Offline.get__account();
        double new_money=account.getMoney()-price.get(amount_to_play);

        db_online.update_money(account.getId(), new_money, etat -> {
            account.setMoney(new_money);
            db_Offline.Update_V_A_acoount(before_rundomly_match.this,account);
        });


        table_game.putExtra("match_count",0);
        startActivity(table_game);
        finish();
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

    @Override
    public void onBackPressed() {
        //finish();
       // super.onBackPressed();
        if(!match_state.equals("match_start"))
            before_end_match();
    }

    private void before_end_match(){
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cancel_match);
        builder.setMessage(R.string.look);
        builder.setPositiveButton(R.string.cancel_match, (dialog, which) -> {
            cancel_matcht();
            finish();
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.show();

         */

        new Msg_box().oui_non_alert(getResources().getString(R.string.cancel_match)
                ,getResources().getString(R.string.look),
                getResources().getString(R.string.cancel_match),
                getResources().getString(R.string.no),
                before_rundomly_match.this, new Msg_box.go_back() {
                    @Override
                    public void onCallBack(int action) {
                        if(action==0){
                            cancel_matcht();
                            finish();
                        }
                    }
                });
    }

    private void cancel_matcht() {
        switch(match_state){
            case "connecting":  case "tack_place":
                db_online.match_canceled=true;
                break;
            case "listening":
                query.removeEventListener(childEventListener);
                reference_to_close.setValue(2);
                break;
            case "match_start":

                break;
        }
    }
}