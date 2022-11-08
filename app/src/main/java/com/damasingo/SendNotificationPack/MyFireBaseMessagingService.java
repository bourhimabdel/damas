package com.damasingo.SendNotificationPack;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;

import com.bumptech.glide.request.target.Target;

import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.push_notification;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;

import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.ByteArrayOutputStream;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcaster;
    sharedpreferences sharedpreferences=new sharedpreferences(this);
    private final connect_to_firebase db_online =new connect_to_firebase();
    private sql_manager db_offline;

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
        db_offline=new sql_manager(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);

        switch (remoteMessage.getData().get("type")) {
            case "request_friend":
                Log.e("request receive", "from " + remoteMessage.getData().get("id_user_send_request"));
                add_this_to_request_receive(remoteMessage.getData().get("id_user_send_request"));
                break;
            case "request_repense":
                Log.e("request repence", "from " + remoteMessage.getData().get("id_user_send_request"));
                lock_at_this_repense(remoteMessage.getData().get("id_user_send_request"), remoteMessage.getData().get("etat"));
                break;
            case "lets_play":
                Log.e("lets_play", "from " + remoteMessage.getData().get("id_user_send_request"));
                lets_play_with_me(remoteMessage.getData().get("id_user_send_request"), remoteMessage);
                break;
            case "classment":
                new push_notification(this).classment_day(remoteMessage.getData().get("id_user_send_request"),
                        Integer.parseInt(remoteMessage.getData().get("etat")));
                break;
        }

    }

    private void lets_play_with_me(String id_user_send_request, RemoteMessage remoteMessage) {

        Log.e("request  receive","added success");

        if(remoteMessage.getData().get("etat").equals("okey")){
            //broadcaster.sendBroadcast(new Intent("online"));
            //broadcaster.sendBroadcast(new Intent("home").putExtra("tab",0));
            //sharedpreferences.put_tab_notification("tab0",true);
            push_notification_(id_user_send_request,"acc");

            Intent intent=new Intent("play");
            intent.putExtra("id",id_user_send_request);
            intent.putExtra("tab",0);
            broadcaster.sendBroadcast(intent);
        }else {
            //broadcaster.sendBroadcast(new Intent("online"));
            //broadcaster.sendBroadcast(new Intent("home").putExtra("tab",0));
            //sharedpreferences.put_tab_notification("tab0",true);
            push_notification_(id_user_send_request,"inv");

            Intent intent=new Intent("play");
            intent.putExtra("id",id_user_send_request);
            intent.putExtra("tab",1);
            broadcaster.sendBroadcast(intent);
        }


    }

    private void lock_at_this_repense(String id_user_send_request, String etat) {
        switch (etat){
            case "refuse":
                db_offline.Update_Request(new reqeust(id_user_send_request,etat,"sent"));
                Log.e("request  receive","added success");

                broadcaster.sendBroadcast(new Intent("online"));
                broadcaster.sendBroadcast(new Intent("sent"));
                broadcaster.sendBroadcast(new Intent("home").putExtra("tab",1));
                sharedpreferences.put_tab_notification("tab1",true);

                push_notification_(id_user_send_request,"req");
                break;
            case "accept":

                score score=new score(id_user_send_request,"0-0");
                db_offline.insert_data_into_score(score);
                db_offline.Update_Request(new reqeust(id_user_send_request,etat,"sent"));
                Log.e("request  receive","added success");

                broadcaster.sendBroadcast(new Intent("online"));
                broadcaster.sendBroadcast(new Intent("sent"));
                broadcaster.sendBroadcast(new Intent("home").putExtra("tab",0));
                broadcaster.sendBroadcast(new Intent("home").putExtra("tab",1));

                sharedpreferences.put_tab_notification("tab1",true);
                sharedpreferences.put_tab_notification("tab0",true);
                push_notification_(id_user_send_request,"req");


                break;
        }
    }

    public void add_this_to_request_receive(String id_user){
        db_online.user_exist(id_user, (etat, r) -> {
            reqeust new_request=new aide().get_Object_request(r);

            get_request_complete(new_request, object -> {

                db_offline.insert_data_into_request(object);

                Log.e("request  receive","added success");
                broadcaster.sendBroadcast(new Intent("receive"));
                broadcaster.sendBroadcast(new Intent("home").putExtra("tab",2));

                sharedpreferences.put_tab_notification("tab2",true);
                push_notification_(id_user,"req");
            });
        });
    }

    public void push_notification_(String id,String ge){

        Context context=this;

        switch (ge){
            case "req":
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> new push_notification(context).generate_request(id));
                break;
            case "inv":
                new push_notification(context).invit_to_play(id);
                break;
            case "acc":
                new push_notification(context).accept_invit_to_play(id);
                break;
        }


    }

    private void get_request_complete(reqeust object_request ,  object_plain object_plain) {
        object_request.setType("receive");
        object_request.setState(" ");

        Glide.with(this)
                .load(object_request.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        get_request_complete(object_request, object_plain);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        BitmapDrawable d=(BitmapDrawable)bb;
                        Bitmap resource=d.getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                        final byte[] b = baos.toByteArray();
                        object_request.setPhoto_saved(b);

                        object_plain.get_Object(object_request);
                        return false;
                    }
                }).submit();

    }

    public interface object_plain{
        void get_Object(reqeust object);
    }


}
