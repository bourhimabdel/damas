package com.damasingo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.push_notification;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.damasingo.fragment_bluetooth.no_paired_devices;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.home_fragments.bluetooth;
import com.damasingo.home_fragments.local;
import com.damasingo.home_fragments.online;
import com.damasingo.home_fragments.randomly;
import com.damasingo.play_onlinge.game_space;
import com.damasingo.randomly.selecto_fragment;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

public class Praincipal extends AppCompatActivity implements View.OnClickListener, fragment_account.click_add_coins, com.damasingo.home.click_add_coins_home {

    public ImageButton button_setting,button_home,button_account,button_coins,button_trend;
    public SelectableRoundedImageView button_account2;
    TextView text_setting,text_home,text_account,text_coins;
    home_ home;

    public TextView txt_score;

    public ArrayList<Integer> press_back_tacker=new ArrayList<>();


    public Animation animFade,animFadein;
    String from_fragment="nothing";

    public boolean after_pause=false;
    private static final String KEY_TEXT_VALUE = "textValue";

    public sandbar_info snack_bar=new sandbar_info();

    Bitmap image_user;

    public int current_fragement_in_home=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
       //     new adapter_setting().set_setting_language(this,this);
       // }
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_praincipal);



            initial_view();
            animation_clique();
            charge_the_fragment(savedInstanceState);
            anime(button_coins);
            anime(button_setting);
            anime(button_trend);
            anime(button_account2);




    }

    private void anime(View button_coins) {

        Handler handler0=new Handler();
        handler0.postDelayed(() -> {
            button_coins.animate()
                    .setDuration(500)
                    .rotation(45)
                    .start();
            Handler handler=new Handler();
            handler.postDelayed(() -> {
                button_coins.animate()
                        .setDuration(1000)
                        .rotation(-45)
                        .start();

                Handler handler2=new Handler();
                handler2.postDelayed(() -> {
                    button_coins.animate()
                            .setDuration(500)
                            .rotation(0)
                            .start();

                }, 1000);
            }, 500);

        }, 500);


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

    private void initial_view() {
        new adapter_setting().Control_navigate_bottom(this);
        button_home = findViewById(R.id.button_0);
        button_setting = findViewById(R.id.button_2);
        button_account = findViewById(R.id.button_1);
        button_account2 = findViewById(R.id.account);
        button_trend= findViewById(R.id.button_5);
        button_coins= findViewById(R.id.button_4);
        text_setting= findViewById(R.id.text_setting);
        text_home= findViewById(R.id.text_home);
        text_account= findViewById(R.id.text_account);
        text_coins= findViewById(R.id.text_stat);
        txt_score=findViewById(R.id.txt_my_score);

        txt_score.setOnClickListener(this);


        if(new sharedpreferences(this).get_setting_type("user_is_not_sign_in")) {
            txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(this).get_score_offline(), this));

            Drawable d= ContextCompat.getDrawable(this,R.drawable.inconnu);
            assert d != null;
            button_account2.setImageBitmap(get_Bitmap(d));
        }else {
            txt_score.setText(new aide().get_the_string_compatible(new sql_manager(this).get__account().getMoney(), this));
            byte[] tof_saved=new sql_manager(this).get__account().getPhoto_saved();
            Bitmap image_user= BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            button_account2.setImageBitmap(image_user);
        }


    }

    public void animation_clique(){
        button_home.setOnClickListener(this);
        button_setting.setOnClickListener(this);
        button_account.setOnClickListener(this);
        button_coins.setOnClickListener(this);
        button_account2.setOnClickListener(this);
        button_trend.setOnClickListener(this);
    }

    public void anim_this_button(final ImageButton b,final int r){
        animFade = AnimationUtils.loadAnimation(this, R.anim.fide_out_100);
        animFadein = AnimationUtils.loadAnimation(this, R.anim.fide_in);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                b.setImageResource(r);
                animFadein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                b.startAnimation(animFadein);
            }
        });
        b.startAnimation(animFade);

    }

    public void that_is_me(String name){

        switch (name) {
            case "a":
                button_account.setImageResource(R.drawable.back_circle_);
                button_setting.setImageResource(R.drawable.ic_setting_empty);
                button_coins.setImageResource(R.drawable.add_empty);
                button_trend.setImageResource(R.drawable.trending_empty);
                text_coins.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_setting.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_account.setTextColor(getResources().getColor(R.color.color_text_petit));
                switch (current_fragement_in_home){
                    case 2:
                        anim_this_button(button_home,R.drawable.ic_home_plain_a);
                        break;
                    case 3:
                        anim_this_button(button_home,R.drawable.ic_home_plain_b);
                        break;
                    case 0:
                        anim_this_button(button_home,R.drawable.ic_home_plain_c);
                        break;
                    case 1:
                        anim_this_button(button_home,R.drawable.ic_home_plain_d);
                        break;
                }

                text_home.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                press_back_tacker.add(1);
                break;
            case "b":
                button_home.setImageResource(R.drawable.ic_home_empty);
                button_setting.setImageResource(R.drawable.ic_setting_empty);
                button_coins.setImageResource(R.drawable.add_empty);
                button_trend.setImageResource(R.drawable.trending_empty);
                text_coins.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_home.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_setting.setTextColor(getResources().getColor(R.color.color_text_petit));
                anim_this_button(button_account,R.drawable.back_circle);
                text_account.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                press_back_tacker.add(3);
                break;
            case "c":
                button_home.setImageResource(R.drawable.ic_home_empty);
                button_account.setImageResource(R.drawable.back_circle_);
                button_coins.setImageResource(R.drawable.add_empty);
                button_trend.setImageResource(R.drawable.trending_empty);
                text_coins.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_home.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_account.setTextColor(getResources().getColor(R.color.color_text_petit));
                anim_this_button(button_setting,R.drawable.ic_setting_plain);
                text_setting.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                press_back_tacker.add(4);
                break;
            case "d":
                button_home.setImageResource(R.drawable.ic_home_empty);
                button_account.setImageResource(R.drawable.back_circle_);
                button_setting.setImageResource(R.drawable.ic_setting_empty);
                button_trend.setImageResource(R.drawable.trending_empty);
                text_home.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_account.setTextColor(getResources().getColor(R.color.color_text_petit));
                text_setting.setTextColor(getResources().getColor(R.color.color_text_petit));
                anim_this_button(button_coins,R.drawable.add_plean);
                text_coins.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                press_back_tacker.add(2);
                break;
            case "e":
                button_home.setImageResource(R.drawable.ic_home_empty);
                button_account.setImageResource(R.drawable.back_circle_);
                button_setting.setImageResource(R.drawable.ic_setting_empty);
                button_coins.setImageResource(R.drawable.add_empty);

               // text_home.setTextColor(getResources().getColor(R.color.color_text_petit));
               // text_account.setTextColor(getResources().getColor(R.color.color_text_petit));
               // text_setting.setTextColor(getResources().getColor(R.color.color_text_petit));

                anim_this_button(button_trend,R.drawable.trending_plain);
               // text_coins.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                press_back_tacker.add(5);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new adapter_setting().Control_navigate_bottom(this);
        }
    }

    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_0:
                that_is_me("a");
                from_fragment=home_.class.getName();
                switch_from_To(home_.class.getName());

                //Fragment accuil = getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                //if (accuil != null) {
                //    home h = (home) accuil;
                //    freind_online freind_online = (freind_online) h.mainAdapter.getSubItemFragment(R.id.view_pager,0);
                //    if(freind_online!=null)
                //        freind_online.plain_list();
//
                //    h.txt_score.setText(String.format("\t%.0f\t",new sql_manager(this).get__account().getMoney()));
                //}

                break;


            case R.id.account:
                that_is_me("b");
                 from_fragment=fragment_account.class.getName();
                 switch_from_To(fragment_account.class.getName());
                break;
            case R.id.button_2:
                that_is_me("c");
                 from_fragment=Setting.class.getName();
                 switch_from_To(Setting.class.getName());
                break;

            case R.id.button_4:
                that_is_me("d");
                 from_fragment=fragment_add_coins.class.getName();
                 switch_from_To(fragment_add_coins.class.getName());

                //Fragment d = getSupportFragmentManager().findFragmentById(R.id.fragment_add_coins);
                //if (d != null) {
                //    fragment_add_coins h = (fragment_add_coins) d;
//
                //    h.txt_my_score.setText(String.format("\t%1d\t",new sql_manager(this).get__account().getMoney()));
                //}
                break;
            case R.id.button_5:
                that_is_me("e");
                from_fragment=trending.class.getName();
                switch_from_To(trending.class.getName());
               // from_fragment=fragment_add_coins.class.getName();
               // switch_from_To(fragment_add_coins.class.getName());
                break;
            case R.id.txt_my_score:
                button_coins.performClick();
                break;


        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);



         if(from_fragment.equals(home.class.getName())){
             outState.putCharSequence(KEY_TEXT_VALUE, home.class.getName());
         }else if(from_fragment.equals(fragment_add_coins.class.getName())){
             outState.putCharSequence(KEY_TEXT_VALUE, fragment_add_coins.class.getName());
         }else if(from_fragment.equals(fragment_account.class.getName())){
             outState.putCharSequence(KEY_TEXT_VALUE, fragment_account.class.getName());
         }else if(from_fragment.equals(Setting.class.getName())){
             outState.putCharSequence(KEY_TEXT_VALUE, Setting.class.getName());
         }


    }

    private void charge_the_fragment(Bundle savedInstanceState){
        if (savedInstanceState != null && savedInstanceState.getCharSequence(KEY_TEXT_VALUE) !=null){

            String fragment_name;
            fragment_name=savedInstanceState.getCharSequence(KEY_TEXT_VALUE)+"";

            if (fragment_name.equals(home_.class.getName())) {
                from_fragment=home_.class.getName();
                switch_from_To(home_.class.getName());
                that_is_me("a");
            }
            else if (fragment_name.equals(fragment_account.class.getName())) {
                from_fragment=fragment_account.class.getName();
                switch_from_To(fragment_account.class.getName());
                that_is_me("b");
            }
            else if (fragment_name.equals(Setting.class.getName())) {
                from_fragment=Setting.class.getName();
                switch_from_To(Setting.class.getName());
                that_is_me("c");
            }
            else if (fragment_name.equals(fragment_add_coins.class.getName())){
                from_fragment=fragment_add_coins.class.getName();
                switch_from_To(fragment_add_coins.class.getName());
                that_is_me("d");
            }else if (fragment_name.equals(trending.class.getName())){
                from_fragment=trending.class.getName();
                switch_from_To(trending.class.getName());
                that_is_me("e");
            }
        }
        else {
            if(getIntent().getExtras()!=null){
                from_fragment=Setting.class.getName();
                switch_from_To(Setting.class.getName());
                that_is_me("c");}
            else{
                from_fragment=home_.class.getName();
                switch_from_To(home_.class.getName());
                that_is_me("a");
            }
        }



        // fragmentTransaction.commit();

 /*

         */




    }

    private void switch_from_To( String to) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


        Fragment others = getSupportFragmentManager().findFragmentById(R.id.fragment_Container);
        Fragment accuil = getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
        Fragment add_coins = getSupportFragmentManager().findFragmentById(R.id.fragment_add_coins);
        Fragment trending = getSupportFragmentManager().findFragmentById(R.id.fragment_trending);



        switch (to) {
            case "com.damasingo.home_":

                if (accuil != null) {
                    if (others != null)
                        fragmentTransaction.hide(others);
                    if (add_coins != null)
                        fragmentTransaction.hide(add_coins);
                    if (trending != null)
                        fragmentTransaction.hide(trending);

                    fragmentTransaction.show(accuil).commit();
                } else {
                    home=new home_();
                    fragmentTransaction.replace(R.id.fragment_accuil,home).commit();

                }
                 break;
            case "com.damasingo.trending":

                if (others != null)
                    fragmentTransaction.hide(others);
                if (add_coins != null)
                    fragmentTransaction.hide(add_coins);
                if (accuil != null)
                    fragmentTransaction.hide(accuil);
                if (trending != null) {


                    fragmentTransaction.show(trending).commit();
                } else {

                    if(new sharedpreferences(Praincipal.this).get_setting_type("user_is_not_sign_in"))
                        trending=new sign_ni();
                    else
                        trending=new trending();

                    fragmentTransaction.replace(R.id.fragment_trending,trending).commit();

                }
                break;
            case "com.damasingo.Setting":
                if (accuil != null)
                    fragmentTransaction.hide(accuil);
                if (add_coins != null)
                    fragmentTransaction.hide(add_coins);
                if (trending != null)
                    fragmentTransaction.hide(trending);

                fragmentTransaction.replace(R.id.fragment_Container, new Setting()).commit();
                break;
            case "com.damasingo.fragment_add_coins":
                if (add_coins != null) {
                    if (others != null)
                        fragmentTransaction.hide(others);
                    if (accuil != null)
                        fragmentTransaction.hide(accuil);
                    if (trending != null)
                        fragmentTransaction.hide(trending);

                    fragmentTransaction.show(add_coins).commit();
                } else {
                    if(accuil!= null)
                        fragmentTransaction.hide(accuil);
                    if (trending != null)
                        fragmentTransaction.hide(trending);
                    fragmentTransaction.replace(R.id.fragment_add_coins, new fragment_add_coins()).commit();
                }
                break;
            case "com.damasingo.fragment_account":
                 if (accuil != null)
                     fragmentTransaction.hide(accuil);
                if (add_coins != null)
                    fragmentTransaction.hide(add_coins);
                if (trending != null)
                    fragmentTransaction.hide(trending);

                Fragment X;
                if(new sharedpreferences(Praincipal.this).get_setting_type("user_is_not_sign_in"))
                    X=new sign_ni();
                else
                    X=new fragment_account();

                fragmentTransaction.replace(R.id.fragment_Container, X).commit();
                break;
        }

        fragmentTransaction.addToBackStack(null);



    }

    @Override
    public void clickAddCoins() {
        button_coins.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
           try {
               home_ home= ((home_)getSupportFragmentManager().findFragmentById(R.id.fragment_accuil));
               bluetooth bluetooth=(bluetooth) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,0);
               if(home!=null) {
                   bluetooth.enableLocationSettings();
                   no_paired_devices p=(no_paired_devices)bluetooth.main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth, 1);
                   if(p!=null)
                       p.plain_list();
               }
           }catch (Exception e){
               Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
           }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (123 == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                home_ home= ((home_)getSupportFragmentManager().findFragmentById(R.id.fragment_accuil));
                bluetooth bluetooth=(bluetooth) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,0);

                if(home!=null) {
                    bluetooth.switch_location.setChecked(true);
                    bluetooth.switch_location.setEnabled(false);

                    bluetooth.img_location.setBackground(ContextCompat.getDrawable(this, R.drawable.location_on));
                    no_paired_devices p=(no_paired_devices)bluetooth.main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth, 1);

                    if(p!=null)
                        p.plain_list();
                }
            }
        }else if (3==requestCode){
            if (120 == resultCode) {
                Toast.makeText(this, getString(R.string.mak), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void clickAddCoinsHome() {
            button_coins.performClick();
    }


    @SuppressLint("DefaultLocale")
    @Override
    protected void onResume() {
        super.onResume();

        if (after_pause) {
            Fragment accuil = getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
            if (accuil != null) {

                home_ home = (home_) accuil;


                local local=(local) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,1);
                if(new sharedpreferences(this).get_setting_type("user_is_not_sign_in"))
                    txt_score.setText(new aide().get_the_string_compatible(new sharedpreferences(this).get_score_offline(),this));
                else {
                    txt_score.setText(new aide().get_the_string_compatible(new sql_manager(this).get__account().getMoney(), this));

                    online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                    freind_online freind_online = (freind_online) online.mainAdapter.getSubItemFragment(R.id.view_pager,0);
                    if(freind_online!=null)
                        freind_online.plain_list();

                    selecto_fragment selecto = (selecto_fragment) getSupportFragmentManager().findFragmentById(R.id.view_pager_randomly);
                    selecto.enabled__buttons();
                }


                local.btn_start_a_local_match.setEnabled(true);
                local.btn_start_a_local_match_robot.setEnabled(true);


            }

            after_pause=false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e("pause","v");

    }

    @Override
    public void onBackPressed() {
        if(press_back_tacker.size()!=0) {
            int index = press_back_tacker.size() - 1;
           // if (press_back_tacker.get(index) == 0) {
           //     snack_bar.dimiss_snackbar_bar(this);
           // }
           // else {
                int to_reserve=press_back_tacker.get(press_back_tacker.size() - 1);
                int to_remove = press_back_tacker.size() - 1;
                press_back_tacker.remove(to_remove);
                if(press_back_tacker.size()!=0) {
                    int to_go = press_back_tacker.get(press_back_tacker.size() - 1);
                    switch(to_go){
                        case 1:
                            button_home.performClick();
                            press_back_tacker.remove(press_back_tacker.size()-1);
                            break;
                        case 2:
                            button_coins.performClick();
                            press_back_tacker.remove(press_back_tacker.size()-1);
                            break;
                        case 3:
                            button_account.performClick();
                            press_back_tacker.remove(press_back_tacker.size()-1);
                            break;
                        case 4:
                            button_setting.performClick();
                            press_back_tacker.remove(press_back_tacker.size()-1);
                            break;
                        case 5:
                            button_trend.performClick();
                            press_back_tacker.remove(press_back_tacker.size()-1);
                            break;
                   }
                }else {
                    press_back_tacker.add(to_reserve);
                    onPause();
                    moveTaskToBack(true);
                    new connect_to_firebase().desincrument_online(Praincipal.this);
                }

                    //}
        }else {
            onPause();
            moveTaskToBack(true);
            new connect_to_firebase().desincrument_online(Praincipal.this);
        }

    }


}