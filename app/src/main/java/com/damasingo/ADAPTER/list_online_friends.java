package com.damasingo.ADAPTER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.home;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.damasingo.play_onlinge.before_game;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class list_online_friends extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ArrayList<reqeust> all_friend_online = new ArrayList<>();
    sql_manager db_offline;
    connect_to_firebase db_online=new connect_to_firebase();
    Context context;
    ArrayList<Integer> price=new ArrayList<>();

    DatabaseReference databaseReference;
    ArrayList<ValueEventListener> valueEventListeners=new ArrayList<>();
    HashMap<reqeust,Integer> hash_Acc=new HashMap<>();
    HashMap<reqeust,String> hash_tra=new HashMap<>();

    AppCompatActivity activity;

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_name,txt_score,online_circle,online_text;
        private final ImageButton btn_cancel_challenge;
        private final Button btn_send_request,btn_accept_challenge;
        private final SelectableRoundedImageView image_profile;
        private final ConstraintLayout data_view;
        private final ShimmerFrameLayout Shamer_view;

        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            txt_score= view.findViewById(R.id.txt_score);
            btn_send_request= view.findViewById(R.id.btn_send_reqeust_play);
            image_profile= view.findViewById(R.id.image_profil);
            btn_accept_challenge=view.findViewById(R.id.btn_play);
            btn_cancel_challenge=view.findViewById(R.id.btn_cancel);
            data_view=view.findViewById(R.id.data_);
            Shamer_view=view.findViewById(R.id.shimmerLayout);
            online_circle=view.findViewById(R.id.txt_online_c);
            online_text=view.findViewById(R.id.txt_online_t);
        }
    }

    public list_online_friends(ArrayList<reqeust> all_friend_online, Context context, AppCompatActivity activity) {
        db_offline =new sql_manager(context);

        for (reqeust items:all_friend_online){
            items.setScore_you_and_me(total_score(db_offline.get_score_of(items.getId())));
            this.all_friend_online.add(items);
        }

        Collections.sort(this.all_friend_online);
        Collections.reverse(this.all_friend_online);

       // this.all_friend_online = all_friend_online;
        this.context=context;
        this.activity=activity;

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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_friend_online, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        reqeust account =  all_friend_online.get(position);

        charger_data(menuItemHolder,account);
        listener_to_this_friend(menuItemHolder,account);
        listener_to_this_friend_tinming(menuItemHolder,account);
        set_on_click_listner(menuItemHolder,account);


    }
    @SuppressLint("DefaultLocale")
    private void listener_to_this_friend_tinming(MenuItemViewHolder menuItemHolder, reqeust account) {
        db_online.get_time_user(account.getId(), new connect_to_firebase.time() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCallback(long time) {
                if(time==0){
                  //  menuItemHolder.online_circle.setVisibility(View.VISIBLE);
                   menuItemHolder.online_text.setVisibility(View.VISIBLE);
                   menuItemHolder.online_text.setText(context.getResources().getString(R.string.onlinz));
                }else {
                    menuItemHolder.online_text.setVisibility(View.VISIBLE);
                    time = new aide().getCurrentUTC().getTime()-time;




                    int unite;
                    if(time < 60000){
                         String t=String.format("<%01d",1)+"\t"+context.getResources().getString(R.string.m);
                        menuItemHolder.online_text.setText(t);
                    }else {
                        if(time<3600000){
                            unite= (int) (time/60000);
                            String t=String.format("%01d",unite)+"\t"+context.getResources().getString(R.string.m);
                            menuItemHolder.online_text.setText(t);

                        }else if(time<86400000){
                            unite= (int) (time/3600000);
                            String t=String.format("%01d",unite)+"\t"+context.getResources().getString(R.string.h);
                            menuItemHolder.online_text.setText(t);

                        }else if(time<2592000000D){
                            unite= (int) (time/86400000);
                            String t=String.format("%01d",unite)+"\t"+context.getResources().getString(R.string.day);
                            menuItemHolder.online_text.setText(t);

                        }else if(time<31104000000D){
                            unite= (int) (time/2592000000D);
                            String t=String.format("%01d",unite)+"\t"+context.getResources().getString(R.string.month);
                            menuItemHolder.online_text.setText(t);
                        }else {
                            unite= (int) (time/31104000000D);
                            String t=String.format("%01d",unite)+"\t"+context.getResources().getString(R.string.year);
                            menuItemHolder.online_text.setText(t);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_friend_online.size();
    }


    private void listener_to_this_friend(MenuItemViewHolder menuItemHolder, reqeust account) {
        db_online.listen_to_this_friend(db_offline.get__account().getId(), account.getId(), new connect_to_firebase.listener() {
            @Override
            public void get_listener(ValueEventListener listener) {
                valueEventListeners.add(listener);
            }

            @Override
            public void get_references(DatabaseReference reference) {
                databaseReference=reference;
            }

            @Override
            public void get_state(String state) {

                if(get_amount(state).length>1){

                    hash_Acc.put(account,Integer.valueOf(get_amount(state)[1]));
                    hash_tra.put(account,get_amount(state)[0]);
                    state=get_amount(state)[0];
                }
                update_button(menuItemHolder,state,account);
            }
        });
    }

    private void update_button(MenuItemViewHolder menuItemHolder, String state,reqeust account) {

        switch (state){
            case "nothings":
                menuItemHolder.btn_cancel_challenge.setVisibility(View.GONE);
                menuItemHolder.btn_accept_challenge.setVisibility(View.INVISIBLE);
                menuItemHolder.btn_send_request.setEnabled(true);
                menuItemHolder.btn_send_request.setVisibility(View.VISIBLE);
                String o=context.getResources().getString(R.string.sent_challenge);
                menuItemHolder.btn_send_request.setText(o);
                menuItemHolder.btn_send_request.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                menuItemHolder.btn_accept_challenge.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_selector_deffie));
                menuItemHolder.btn_accept_challenge.clearAnimation();
                break;
            case "wait":
                menuItemHolder.btn_send_request.setEnabled(false);
                menuItemHolder.btn_cancel_challenge.setVisibility(View.VISIBLE);
                menuItemHolder.btn_accept_challenge.setVisibility(View.INVISIBLE);
                menuItemHolder.btn_accept_challenge.setEnabled(false);
                menuItemHolder.btn_send_request.setVisibility(View.VISIBLE);
                 menuItemHolder.btn_send_request.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
                menuItemHolder.btn_accept_challenge.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_selector_deffie));
                 menuItemHolder.btn_send_request.setText(context.getResources().getString(R.string.wait));
                menuItemHolder.btn_accept_challenge.clearAnimation();
                break;
            case "request":
                swip(account);
                menuItemHolder.btn_send_request.setEnabled(false);
                menuItemHolder.btn_send_request.setVisibility(View.INVISIBLE);
                menuItemHolder.btn_cancel_challenge.setVisibility(View.VISIBLE);
                menuItemHolder.btn_accept_challenge.setVisibility(View.VISIBLE);
                menuItemHolder.btn_accept_challenge.setEnabled(true);
                menuItemHolder.btn_accept_challenge.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_selector_refuse));
                menuItemHolder.btn_accept_challenge.setText(R.string.lets_play);
                anim(menuItemHolder.btn_accept_challenge);

                Praincipal praincipal = (Praincipal)  activity;
                if(praincipal!=null) {
                    home_ home = (home_) praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                    home.notif_tab(3);
                }
                break;
            case "ok":
               swip(account);
                menuItemHolder.btn_send_request.setEnabled(false);
                menuItemHolder.btn_send_request.setVisibility(View.INVISIBLE);
                menuItemHolder.btn_cancel_challenge.setVisibility(View.VISIBLE);
                menuItemHolder.btn_accept_challenge.setVisibility(View.VISIBLE);
                menuItemHolder.btn_accept_challenge.setEnabled(true);
                menuItemHolder.btn_send_request.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
                menuItemHolder.btn_accept_challenge.setText(R.string.lets_play);
                menuItemHolder.btn_accept_challenge.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_selector_refuse));
                anim(menuItemHolder.btn_accept_challenge);

                Praincipal praincipal2 = (Praincipal)  activity;

                if(praincipal2!=null) {
                    home_ home = (home_) praincipal2.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                    home.notif_tab(3);
                }
                break;
        }

        menuItemHolder.Shamer_view.stopShimmer();
        menuItemHolder.Shamer_view.setVisibility(View.GONE);
        menuItemHolder.data_view.setVisibility(View.VISIBLE);
    }


    Animation animFade;
    public void anim(View view){
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

    private void swip(reqeust account){
        Praincipal praincipal = (Praincipal) activity;
        if(praincipal!=null) {
            home_ home=(home_) praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
            if(home!=null) {
                online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                freind_online freind_online=(freind_online) online.mainAdapter.getSubItemFragment(R.id.view_pager,0);
                if(freind_online.list_friends!=null)
                    freind_online.list_friends.scrollToPosition(all_friend_online.indexOf(account));
            }
        }
    }

    private void set_on_click_listner(MenuItemViewHolder menuItemHolder, reqeust account) {
        menuItemHolder.btn_send_request.setOnClickListener(v -> new Msg_box().get_price(context, value -> {
            if(value!=-1) {
                db_online.up_challenge(db_offline.get__account().getId(), account.getId(), value, etat -> db_online.retrive_Tocken(account.getId(), tocken -> new aide().sendNotifications_lets_play(tocken, "lets_play", db_offline.get__account().getId())));
            }else
            {
                Praincipal activity = (Praincipal)  context;
                activity.button_coins.performClick();
            }

        }));

        menuItemHolder.btn_accept_challenge.setOnClickListener(v -> {
            int price_can_play=get_the_price_acciptable(account);


            String m=hash_tra.get(account);
            assert m != null;
            if(m.equals("request")) {
                if (price_can_play != -1) {
                    v.setEnabled(false);
                    Intent intent=new Intent(context, before_game.class);
                    intent.putExtra("play_to",price_can_play);
                    intent.putExtra("player1",db_offline.get__account().getId());
                    intent.putExtra("player2",account.getId());

                    Praincipal activity = (Praincipal)  context;
                    activity.after_pause=true;

                    context.startActivity(intent);

                } else {
                    Praincipal activity = (Praincipal)  context;
                    activity.button_coins.performClick();
                    Toast.makeText(context, context.getResources().getString(R.string.ollect)+" "+price.get(0)+" "
                            +  context.getResources().getString(R.string.coins), Toast.LENGTH_LONG).show();
                    new vibrateur(context).vibrate(100);
                }
            }else if(hash_tra.get(account).equals("ok")){
                v.setEnabled(false);
                if (price_can_play != -1 && price_can_play>=hash_Acc.get(account)) {
                Intent intent=new Intent(context, before_game.class);
                intent.putExtra("play_to",hash_Acc.get(account));
                intent.putExtra("player1",account.getId());
                intent.putExtra("player2",db_offline.get__account().getId());

                Praincipal activity = (Praincipal)  context;
                activity.after_pause=true;

                context.startActivity(intent);
                }else {
                    Praincipal activity = (Praincipal)  context;
                    activity.button_coins.performClick();
                    Toast.makeText(context, context.getResources().getString(R.string.ollect)+" "+price.get(0)+" "+
                            context.getResources().getString(R.string.coins), Toast.LENGTH_LONG).show();
                    new vibrateur(context).vibrate(100);
                }
            }
        });

        menuItemHolder.btn_cancel_challenge.setOnClickListener(v -> {

            menuItemHolder.btn_accept_challenge.clearAnimation();
            db_online.initial_challenge(db_offline.get__account().getId(), account.getId(), etat -> {

            });
        });

    }

    private int get_the_price_acciptable(reqeust account) {
        double score=db_offline.get__account().getMoney();
        int challenge_price=price.get(hash_Acc.get(account));

        Log.e("ch",""+challenge_price);
        Log.e("sc",""+score);

        if(score>=challenge_price){
            Log.e("return",""+hash_Acc.get(account));
            return hash_Acc.get(account);
        }else {
            if (price.get(8) <= challenge_price && score>=price.get(8))
                return 8;
            else if (price.get(7) <= challenge_price && score>=price.get(7))
                return 7;
            else if (price.get(6) <= challenge_price && score>=price.get(6))
                return 6;
            else if (price.get(5) <= challenge_price && score>=price.get(5))
                return 5;
            else if (price.get(4) <= challenge_price && score>=price.get(4))
                return 4;
            else if (price.get(3) <= challenge_price && score>=price.get(3))
                return 3;
            else if (price.get(2) <= challenge_price && score>=price.get(2))
                return 2;
            else if (price.get(1) <= challenge_price && score>=price.get(1))
                return 1;
            else if (price.get(0) <= challenge_price && score>=price.get(0))
                return 0;
            else
                return -1;
        }


    }

    private void charger_data(MenuItemViewHolder view,reqeust account) {
        byte[] tof_saved=account.getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        view.image_profile.setImageBitmap(decodedByte);

        view.txt_name.setText(account.getName());
        String m=context.getResources().getString(R.string.core)+get_score_description(db_offline.get_score_of(account.getId()));
        view.txt_score.setText(m);

        /////// time
    }

    @SuppressLint("DefaultLocale")
    private String get_score_description(String score){

        String[] temp = score.split("-");


        return String.format("%1d - %2d",Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
    }

    private int total_score(String score){
        String[] temp = score.split("-");
        return Integer.parseInt(temp[0])+Integer.parseInt(temp[1]);
    }

    private String[] get_amount(String score){


        if(score!=null) {
            String[] temp = score.split(" ");
            for (String s : temp) {
                System.out.println(s);
            }
            return temp;
        }
        else
            return new String[]{""};



    }


    public void end_listen() {
        for (ValueEventListener listener:valueEventListeners){
            databaseReference.removeEventListener(listener);
        }
    }


}
