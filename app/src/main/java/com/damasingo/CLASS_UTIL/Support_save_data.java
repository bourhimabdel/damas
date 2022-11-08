package com.damasingo.CLASS_UTIL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.sign_in;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Support_save_data {
    private final sql_manager db_offline;
    private final connect_to_firebase db_online=new connect_to_firebase();
    Context context;

    public  Support_save_data(Context context){
        db_offline=new sql_manager(context);
        this.context=context;
    }
    public void add_this_collect_to_sent(ArrayList<String> list,ok ok){
        db_offline.delete_request_sent();

        if (list.size()==0)
            ok.on_Call_back();


        for(String m:list){
            get_data_of_this_account(m, account -> {

                reqeust reqeust=new aide().get_Object_request(account);
                reqeust.setState("encore");
                reqeust.setType("sent");

                add_puc_and_save_data(reqeust, ok::on_Call_back);
            });
        }
    }

    public void add_this_collect_to_receive(ArrayList<String> list,ok ok){
        if (list.size()==0)
            ok.on_Call_back();

        for(String m:list){
            get_data_of_this_account(m, account -> {

                reqeust reqeust=new aide().get_Object_request(account);
                reqeust.setState(" ");
                reqeust.setType("receive");

                add_puc_and_save_data(reqeust, ok::on_Call_back);

            });
        }
    }

    public void add_this_collect_to_friends(ArrayList<score> list,ok ok){
        if (list.size()==0)
            ok.on_Call_back();

        for(score score:list){
            get_data_of_this_account(score.getId(), account -> {

                db_offline.insert_data_into_score(score);

                reqeust reqeust=new aide().get_Object_request(account);
                reqeust.setState("accept");
                reqeust.setType("fr");


                add_puc_and_save_data(reqeust, ok::on_Call_back);

            });
        }
    }

    public void get_data_of_this_account(String id, sign_in.data_account data_account){
        db_online.user_exist(id, (etat, r) -> data_account.on_callback(r));
    }

    public void add_puc_and_save_data(reqeust reqeust, sign_in.refrech_complet refrech_complet){
        Glide.with(context)
                .load(reqeust.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        add_puc_and_save_data(reqeust, refrech_complet);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        BitmapDrawable d=(BitmapDrawable)bb;
                        Bitmap resource=d.getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                        final byte[] b = baos.toByteArray();
                        reqeust.setPhoto_saved(b);

                        db_offline.insert_data_into_request(reqeust);

                        refrech_complet.complet();
                        return false;
                    }
                }).submit();
    }

    public interface  ok{
        void on_Call_back();
    }
}
