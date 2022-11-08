package com.damasingo.ADAPTER;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.pack_generator;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.star_data;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.home;
import com.damasingo.sign_in;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class list_add_friends extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<String> all_data=new ArrayList<>();
    ArrayList<account> all_account=new ArrayList<>();
    Context context;
    Praincipal praincipal;
    connect_to_firebase db_online=new connect_to_firebase();
    sql_manager db_offline;

    ArrayList<reqeust> my_requests_receive ;
    ArrayList<reqeust> friends;
    ArrayList<reqeust> my_requests;

    int pack_num;


    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_name,img_level;
        private final SelectableRoundedImageView img_profile;
        private final Button btn_add;
        private final LottieAnimationView ltt_wait;


        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            img_level= view.findViewById(R.id.img_level);
            img_profile= view.findViewById(R.id.image_profil);
            btn_add=view.findViewById(R.id.btn_add);
            ltt_wait=view.findViewById(R.id.lottie_wait);
        }
    }

    public list_add_friends(Context context,ArrayList<String> li,Praincipal praincipal,int pack) {

        this.context=context;
        this.praincipal=praincipal;
        this.pack_num=pack;

        db_offline=new sql_manager(context);

        my_requests_receive =db_offline.get__request_receive();
        friends=db_offline.get__freinds();
        my_requests=db_offline.get__request_sent();

        check_id(li);

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_objet_add_friends, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        String user_id = all_data.get(position);
        get_this_user(user_id, menuItemHolder);

        if (position==all_data.size()-1){
            charge_more_data();
        }

        add_click(menuItemHolder,user_id);
    }

    private void add_click(MenuItemViewHolder menuItemHolder,String user_id) {
        menuItemHolder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account acc = null;
                for(account j:all_account) {
                    if(j.getId().equals(user_id)){
                        acc=j;
                    }
                }

                menuItemHolder.ltt_wait.setVisibility(View.VISIBLE);
                menuItemHolder.btn_add.setVisibility(View.INVISIBLE);

                account finalAcc = acc;
                Complet_add(new aide().get_Object_request(acc), acc, new add() {
                    @Override
                    public void onComplete() {
                        int m=all_data.indexOf(user_id);
                        all_data.remove(user_id);
                        all_account.remove(finalAcc);
                        notifyItemRemoved(m);
                    }
                });
            }


        });
    }


    interface add{
        void onComplete();
    }

    private void send_notification(String id,Context context) {
       db_online.retrive_Tocken(id, tocken -> new aide().sendNotifications(tocken, "request_friend", new sql_manager(context).get__account().getId()));
    }

    private void Complet_add(reqeust reqeust,account account,add add) {
        db_online.add_request_receive(db_offline.get__account().getId(), reqeust, new connect_to_firebase.add_succeful() {
            @Override
            public void add_complet(boolean etat) {
                db_online.add_request_sent(db_offline.get__account().getId(), reqeust, new connect_to_firebase.add_succeful() {
                    @Override
                    public void add_complet(boolean etat) {

                        get_request_complete(reqeust,account, new reqeust_sent.object_plain() {
                            @Override
                            public void get_Object(com.damasingo.CLASS_UTIL.reqeust object) {
                                add.onComplete();
                                new sql_manager(context).insert_data_into_request(object);
                                send_notification(object.getId(),context);
                                Fragment accuil = praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                                if (accuil != null) {
                                    home h = (home) accuil;
                                    h.viewPager.setCurrentItem(1, true);

                                    try {
                                        reqeust_sent reqeust_sent = (reqeust_sent) h.mainAdapter.getSubItemFragment(R.id.view_pager
                                                ,1);
                                        reqeust_sent.add_request(reqeust);

                                    }catch (Exception e){
                                        Toast.makeText(praincipal, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    //reqeust_sent.plain_list();
                                    //reqeust_sent.add_request(object_request);
                                } else
                                    Toast.makeText(praincipal, "acceul adapter null", Toast.LENGTH_SHORT).show();

                            }
                        });

                     }
                 });
             }
         });

    }


    private void get_request_complete(reqeust object_request ,account acc, reqeust_sent.object_plain object_plain) {
        object_request.setType("sent");
        object_request.setState("encore");


        object_request.setPhoto_saved(acc.getPhoto_saved());
        object_plain.get_Object(object_request);
    }

    private void charge_more_data() {
        db_online.get_actuel_pack(new connect_to_firebase.pack_is() {
            @Override
            public void onCallback(pack_generator pack) {
                db_online.get_element_of_pack(pack_num-1, new connect_to_firebase.key_user_recipe() {
                    @Override
                    public void on_callback(ArrayList<String> keys) {
                        pack_num--;
                        int a=check_id(keys);
                        notifyItemRangeInserted(all_data.size(),all_data.size()+a);
                    }
                });
            }
        });
    }

    private void get_this_user(String user_id,MenuItemViewHolder m) {

        db_online.user_exist(user_id, new connect_to_firebase.user_exist() {
            @SuppressLint("DefaultLocale")
            @Override
            public void exist(boolean etat, account r) {

                if(!all_account.contains(r))
                    all_account.add(r);

                m.ltt_wait.setVisibility(View.GONE);
                m.txt_name.setText(r.getName());
                m.img_level.setText(String.format("%02d",r.getLevel()));
                m.img_level.setBackground(new aide().get_the_star_compatible(r.getLevel(),context));

                remplire_Image(bitmap -> complete_load_image_work(m,praincipal,r,bitmap),r);
            }
        });

    }

    public void remplire_Image(sign_in.image_ready image_ready, account account){
        Log.e("image is","gg "+account.getImage());
        Glide.with(praincipal)
                .load(account.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        remplire_Image(image_ready, account);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        image_ready.onCallback(bb);
                        return false;
                    }
                }).submit();

    }

    @SuppressLint("DefaultLocale")
    private void complete_load_image_work(MenuItemViewHolder m,Praincipal praincipal,account account, Drawable bb){
        BitmapDrawable d=(BitmapDrawable)bb;
        Bitmap resource=d.getBitmap();


        praincipal.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m.img_profile.setImageBitmap(resource);
                m.btn_add.setVisibility(View.VISIBLE);
            }
        });


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        final byte[] b = baos.toByteArray();
        account.setPhoto_saved(b);

        if(all_account.contains(account)){
            all_account.remove(account);
            all_account.add(account);
        }

    }

    private int check_id(ArrayList<String> li) {
        int a=0;
        boolean checked=true;
        for (String m:li) {
            if(db_offline.get__account().getId().equals(m)){
                checked=false;
            }
            for (reqeust account : my_requests) {
                if (account.getId().equals(m)) {
                    checked=false;
                }
            }
            for (reqeust account : my_requests_receive) {
                if (account.getId().equals(m)) {
                    checked=false;
                }
            }
            for (reqeust account : friends) {
                if (account.getId().equals(m)) {
                    checked=false;
                }
            }

            if (checked) {
                all_data.add(m);
                a++;
            }

            checked=true;
        }

        return a;


    }

    @Override
    public int getItemCount() {
        return all_data.size();
    }




}
