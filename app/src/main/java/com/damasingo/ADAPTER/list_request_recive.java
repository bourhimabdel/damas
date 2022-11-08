package com.damasingo.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;

import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;

import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.fragment_play_online.reqeust_recive;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

import java.util.Collections;

public class list_request_recive extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<reqeust> all_friend_online;
    connect_to_firebase db_online =new connect_to_firebase();
    sql_manager db_offline;
    Context context;
    private final LocalBroadcastManager broadcaster;
    Praincipal praincipal;

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
     private final TextView txt_name;
     private final SelectableRoundedImageView image_profile;
     private final Button accept;
     private final ImageButton refuse;

        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            image_profile= view.findViewById(R.id.image_profil);
            accept= view.findViewById(R.id.btn_accept);
            refuse= view.findViewById(R.id.btn_cancel);
        }
    }

    public list_request_recive(Context context, AppCompatActivity activity, ArrayList<reqeust> all_friend_online) {
        Collections.reverse(all_friend_online);
        db_offline=new sql_manager(context);
        this.context=context;
        broadcaster= LocalBroadcastManager.getInstance(context);
        this.all_friend_online = all_friend_online;
        praincipal= (Praincipal) activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_request_recive, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        reqeust account =  all_friend_online.get(position);
        charger_data(menuItemHolder,account);
        click_listner(menuItemHolder,account);

    }

    private void click_listner(MenuItemViewHolder menuItemHolder, reqeust account) {

        menuItemHolder.refuse.setOnClickListener(v -> {
            menuItemHolder.refuse.setVisibility(View.GONE);
            menuItemHolder.accept.setText(R.string.deleting);
            menuItemHolder.accept.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
            menuItemHolder.accept.setEnabled(false);
            delete_and_sent_notification(account);
        });

        menuItemHolder.accept.setOnClickListener(v -> {
            menuItemHolder.refuse.setVisibility(View.GONE);
            menuItemHolder.accept.setText(R.string.accepting);
            menuItemHolder.accept.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
            menuItemHolder.accept.setEnabled(false);
            accept_and_send_notification(account);
        });

    }

    private void accept_and_send_notification(reqeust account) {
        db_online.delete_request(db_offline.get__account().getId(), account.getId(), etat -> {
            score score=new score(account.getId(),"0-0");
            db_online.add_friends_score(context,score, etat1 -> db_online.add_other_friends_score(context, score, etat11 -> {
                db_offline.insert_data_into_score(score);


                db_online.retrive_Tocken(account.getId(), tocken -> {

                    db_offline.Update_Request(new reqeust(account.getId(),"accept","non"));
                    new aide().sendNotifications_request_repence(tocken,
                            "request_repense",db_offline.get__account().getId(),"accept");



                    new sharedpreferences(context).put_tab_notification("tab0",true);
                    broadcaster.sendBroadcast(new Intent("online"));
                    broadcaster.sendBroadcast(new Intent("home").putExtra("tab",0));

                    all_friend_online.remove(account);
                    notifyDataSetChanged();

                    if(all_friend_online.size()==0){
                        Fragment accuil = praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                        home_ home = (home_) accuil;
                        online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                        reqeust_recive reqeust_recive = (reqeust_recive) online.mainAdapter.getSubItemFragment(R.id.view_pager,2);
                        if(reqeust_recive!=null)
                            reqeust_recive.plain_list();
                    }
                });




            }));

        });
    }

    private void delete_and_sent_notification(reqeust account) {
        db_online.delete_request(db_offline.get__account().getId(), account.getId(), etat -> db_online.retrive_Tocken(account.getId(), tocken -> {
            db_offline.delete_this_request(account.getId());
            new aide().sendNotifications_request_repence(tocken,
                    "request_repense",db_offline.get__account().getId(),"refuse");

            all_friend_online.remove(account);
            notifyDataSetChanged();

            if(all_friend_online.size()==0){
                Fragment accuil = praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                home_ home = (home_) accuil;
                online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                reqeust_recive reqeust_recive = (reqeust_recive) online.mainAdapter.getSubItemFragment(R.id.view_pager,2);
                if(reqeust_recive!=null)
                    reqeust_recive.plain_list();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return all_friend_online.size();
    }

    private void charger_data(MenuItemViewHolder view,reqeust account) {
        byte[] tof_saved=account.getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        view.image_profile.setImageBitmap(decodedByte);

        view.txt_name.setText(account.getName());
    }
}
