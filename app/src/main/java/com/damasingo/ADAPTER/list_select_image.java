package com.damasingo.ADAPTER;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.image_profile_data;
import com.damasingo.CLASS_UTIL.pack_generator;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.fragment_account;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.home;
import com.damasingo.sign_in;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class list_select_image extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<image_profile_data> all_image;
    Context context;
    Praincipal praincipal;
    connect_to_firebase db_online=new connect_to_firebase();
    sharedpreferences sharedpreferences;
    sql_manager db_offline;




    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_prix;
        private final SelectableRoundedImageView img_profile;
        private final ImageButton img_lock;
        private final Button btn_use,btn_open;
        private final LottieAnimationView ltt_wait;


        MenuItemViewHolder(View view) {
            super(view);
            txt_prix= view.findViewById(R.id.txt_prix);
            img_profile= view.findViewById(R.id.image_profil);
            img_lock= view.findViewById(R.id.view_lock);
            btn_use=view.findViewById(R.id.btn_use);
            btn_open=view.findViewById(R.id.btn_open);
            ltt_wait=view.findViewById(R.id.lottie_wait);
        }
    }

    public list_select_image(Context context, Praincipal praincipal) {

        this.context=context;
        this.praincipal=praincipal;
        db_offline=new sql_manager(context);
        all_image=new aide().get_all_imege(context);
        sharedpreferences=new sharedpreferences(context);

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chose_tof, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        image_profile_data image = all_image.get(position);

        remplire_Image(bitmap -> {
            complete_load_image_work(menuItemHolder, praincipal, image, bitmap);
        },image.getLien_image());


        click_listner(menuItemHolder,position,image);
    }

    private void click_listner(MenuItemViewHolder menuItemHolder,int position,image_profile_data img) {

        menuItemHolder.btn_open.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                image_profile_data a=all_image.get(position);
                if(db_offline.get__account().getMoney()>=all_image.get(position).getPrix()){


                    sharedpreferences.this_image_opened(position+1);
                    account account=db_offline.get__account();
                    account.setMoney(account.getMoney()-img.getPrix());

                    db_offline.Update_V_A_acoount(context,account);

                    db_online.update_money(account.getId(), account.getMoney(), new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {

                        }
                    });
                    new sound().collect(context);
                    praincipal.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));

                    db_offline.Update_V_A_acoount(context,account);

                    a.setOpened(true);
                    all_image.remove(a);
                    all_image.add(position,a);
                    notifyDataSetChanged();


                }else {
                    String m=context.getResources().getString(R.string.to)+" "+ a.getStr_prix()
                            +" "+context.getResources().getString(R.string.coins)+" "+context.getResources().getString(R.string.tocollect);
//
                    Toast.makeText(context,m, Toast.LENGTH_LONG).show();
                }
            }
        });


        menuItemHolder.btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account account=db_offline.get__account();
                account.setPhoto_saved(tof.get(img));
                account.setImage(img.getLien_image());


                db_offline.Update_V_A_acoount(context,account);
                db_online.update_image(account.getId(), img.getLien_image(), new connect_to_firebase.add_succeful() {
                    @Override
                    public void add_complet(boolean etat) {

                    }
                });

                praincipal.button_account2.setImageBitmap(tof_b.get(img));
                fragment_account fr_a= (fragment_account) praincipal.getSupportFragmentManager().findFragmentById(R.id.fragment_Container);
                fr_a.img_profile.setImageBitmap(tof_b.get(img));

            }
        });
    }


    public void remplire_Image(sign_in.image_ready image_ready, String lien_image){

        Glide.with(praincipal)
                .load(lien_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        remplire_Image(image_ready,lien_image);
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
    private void complete_load_image_work(MenuItemViewHolder m,Praincipal praincipal,image_profile_data img, Drawable bb){
        BitmapDrawable d=(BitmapDrawable)bb;
        Bitmap resource=d.getBitmap();

        tof_b.put(img,resource);


        praincipal.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m.img_profile.setImageBitmap(resource);
                m.ltt_wait.setVisibility(View.INVISIBLE);
                if(img.isOpened()) {
                    m.btn_use.setVisibility(View.VISIBLE);
                }else {
                    m.img_lock.setVisibility(View.VISIBLE);
                    m.btn_open.setVisibility(View.VISIBLE);
                    m.txt_prix.setText(img.getStr_prix());
                    m.txt_prix.setVisibility(View.VISIBLE);
                }
            }
        });



        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        final byte[] b = baos.toByteArray();
        tof.put(img,b);
        //account.setPhoto_saved(b);
//
        //if(all_account.contains(account)){
        //    all_account.remove(account);
        //    all_account.add(account);
        //}

    }

    HashMap<image_profile_data,byte[]> tof=new HashMap<>();
    HashMap<image_profile_data,Bitmap> tof_b=new HashMap<>();

    @Override
    public int getItemCount() {
        return all_image.size();
    }




}
