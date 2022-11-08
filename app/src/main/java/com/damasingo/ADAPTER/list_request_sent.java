package com.damasingo.ADAPTER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.fragment_play_online.reqeust_recive;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collections;

public class list_request_sent extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ArrayList<reqeust> all_friend_online;
    Context context;
    Praincipal praincipal;


    private final sql_manager db_offline;


    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_name,txt_etat;
        private final SelectableRoundedImageView image_profile;



        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            image_profile= view.findViewById(R.id.image_profil);
            txt_etat=view.findViewById(R.id.txt_etat);
        }
    }

    public list_request_sent(ArrayList<reqeust> all_friend_online, Context context, AppCompatActivity activity) {
        Collections.reverse(all_friend_online);
        this.context=context;
        db_offline=new sql_manager(context);
        this.all_friend_online = all_friend_online;
        praincipal = (Praincipal) activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_request_sent, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        reqeust account =  all_friend_online.get(position);
        charger_data(menuItemHolder,account);
        click_listner(menuItemHolder,account,position);
    }

    private void click_listner(MenuItemViewHolder menuItemHolder, reqeust account,int position) {
        menuItemHolder.txt_etat.setOnClickListener(v -> {
             if(account.getState().equals("encore")) {
                 notifyItemChanged(all_friend_online.indexOf(account));
                 return;
             }

            if(account.getState().equals("refuse"))
                db_offline.delete_this_request(account.getId());
            else
                db_offline.hide_this_request(account.getId());

            all_friend_online.remove(position);
           // notifyItemRemoved(position);
           // notifyItemRangeChanged(position, getItemCount()-position);
            notifyDataSetChanged();

            if(all_friend_online.size()==0){
                Fragment accuil = praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                home_ home = (home_) accuil;
                online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                reqeust_sent reqeust_sent = (reqeust_sent) online.mainAdapter.getSubItemFragment(R.id.view_pager,1);
                if(reqeust_sent!=null)
                    reqeust_sent.plain_list();
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_friend_online.size();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void charger_data(MenuItemViewHolder view, reqeust account) {

        byte[] tof_saved=account.getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        view.image_profile.setImageBitmap(decodedByte);

        view.txt_name.setText(account.getName());

        if(account.getState().equals("refuse")){
            view.txt_etat.setEnabled(true);
            view.txt_etat.setText(R.string.refuse);
            view.txt_etat.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_etat_refuse));
            view.txt_etat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.no_reponse, 0);
        }else if(account.getState().equals("accept")){
            view.txt_etat.setEnabled(true);
            view.txt_etat.setText(R.string.accept);
            view.txt_etat.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_etat_accept));

        }
    }
}
