package com.damasingo.Snack_bar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.ADAPTER.list_add_friends;
import com.damasingo.ADAPTER.list_info_and_rule;
import com.damasingo.ADAPTER.list_select_image;
import com.damasingo.ADAPTER.list_start_info;
import com.damasingo.CLASS_UTIL.Chrono;
import com.damasingo.CLASS_UTIL.Support_save_data;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.info_rule;
import com.damasingo.CLASS_UTIL.pack_generator;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.JSON_FILE_CONVERT.convert_json_setting;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.bluetooth_match.bluetooth_game_space;
import com.damasingo.fragment_account;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.home;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.damasingo.local_match.local_game_space;
import com.damasingo.local_match.local_game_space_robot;
import com.damasingo.play_onlinge.game_space;
import com.damasingo.randomly.game_space_randomly;
import com.damasingo.sign_in;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class sandbar_info {

    public sandbar_info(){}

    Snackbar snackbar;
    /*public void dimiss_snackbar_bar(AppCompatActivity activity){
        if (snackbar!=null && snackbar.isShown()) {
            snackbar.dismiss();

            if (activity instanceof Praincipal) {
                Praincipal praincipal = (Praincipal) activity;
                int index = praincipal.press_back_tacker.size();
                praincipal.press_back_tacker.remove(index - 1);
            }else if(activity instanceof game_space){
                game_space om= (game_space) activity;
                om.snack_bar_showed=false;

            }else if(activity instanceof local_game_space){
                local_game_space om= (local_game_space) activity;
                om.snack_bar_showed=false;
            }else if(activity instanceof bluetooth_game_space){
                bluetooth_game_space om= (bluetooth_game_space) activity;
                om.snack_bar_showed=false;
            }else if(activity instanceof game_space_randomly){
                game_space_randomly om= (game_space_randomly) activity;
                om.snack_bar_showed=false;
            }else if(activity instanceof local_game_space_robot){
                local_game_space_robot om= (local_game_space_robot) activity;
                om.snack_bar_showed=false;
            }else if(activity instanceof sign_in){
                sign_in om= (sign_in) activity;
                om.snack_showed=false;
            }
        }
    }

     */




    @SuppressLint("ClickableViewAccessibility")
    public void snack_bar_reglage(String rule_or_info, ArrayList<Integer> nums, final Context context, final AppCompatActivity activity){



        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View snackView = activity.getLayoutInflater().inflate(R.layout.info_alert, null);


        // final Button button1=snackView.findViewById(R.id.cancel);
        final TextView title=snackView.findViewById(R.id.t1);
        final RecyclerView list=snackView.findViewById(R.id.list_paired);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);



        ArrayList<info_rule> info_or_rules;
        if(rule_or_info.equals("info")){
            //// info
            info_or_rules=new convert_json_setting().get_info(context,nums);
            title.setText(R.string.read_more_information_about_this_game_and_watch_videos);
        }else {
            info_or_rules=new convert_json_setting().get_rule(context);
            title.setText(R.string.learn_the_rules_of_the_game);
        }

        list_info_and_rule adapter = new list_info_and_rule(info_or_rules,context,activity);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context));


        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();

       /* boolean i_up_the_bottom_bar=false;
        if(!new sql_manager(context).get_all_setting().get(4).getChoice().equals("مفعل")){
            new sql_manager(context).Update_data_setting("5", "مفعل");
            new adapter_setting().Control_navigate_bottom(activity);
            i_up_the_bottom_bar=true;
        }







        // Create the Snackbar
        snackbar= Snackbar.make(activity.getWindow().getDecorView().getRootView(), "", Snackbar.LENGTH_INDEFINITE);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbar.getView().getRootView().setBackgroundResource(android.R.color.transparent);




        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {

            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });

        // Inflate our custom view
        @SuppressLint("InflateParams")
        View snackView = activity.getLayoutInflater().inflate(R.layout.info_alert, null);



       // final Button button1=snackView.findViewById(R.id.cancel);
        final TextView title=snackView.findViewById(R.id.t1);
        final RecyclerView list=snackView.findViewById(R.id.list_paired);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);





        ArrayList<info_rule> info_or_rules;
        if(rule_or_info.equals("info")){
            //// info
            info_or_rules=new convert_json_setting().get_info(context,nums);
            title.setText(R.string.read_more_information_about_this_game_and_watch_videos);
        }else {
            info_or_rules=new convert_json_setting().get_rule(context);
            title.setText(R.string.learn_the_rules_of_the_game);
        }

        list_info_and_rule adapter = new list_info_and_rule(info_or_rules,context,activity);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context));

        //If the view is not covering the whole snackbar layout, add this line
        layout=get_layout(layout,context,activity);

      ////// Add the view to the Snackbar's layout
      layout.addView(snackView, 0);





        // Show the Snackbar
        snackbar.show();


        final int[] a = {0};
        final float[] y = new float[1];
        final float[] decalge = new float[1];
        final Chrono chrono = new Chrono();

        boolean finalI_up_the_bottom_bar = i_up_the_bottom_bar;
        snackbar.getView().setOnTouchListener((v, event) -> {


            if(a[0] ==0) {
                y[0] = v.getY();
                a[0]++;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    chrono.start();
                    dY = y[0]- event.getRawY();
                    decalge[0] = event.getRawY()-y[0];
                    cancel_line.setBackground(ContextCompat.getDrawable(context,R.drawable.flech_c));
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!(event.getRawY()-decalge[0] < y[0])) {
                        ViewPropertyAnimator nn = v.animate();
                        nn.y(event.getRawY() + dY);
                        nn.setDuration(0);
                        nn.start();
                    }else
                    {
                        ViewPropertyAnimator nn = v.animate();
                        nn.y(y[0]);
                        nn.setDuration(0);
                        nn.start();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    cancel_line.setBackground(ContextCompat.getDrawable(context,R.drawable.flech));
                    chrono.stop();
                    if (event.getRawY()>y[0]) {
                        ViewPropertyAnimator nn = v.animate();
                        if(chrono.getDureeMs()<1300) {
                            nn.y(y[0] + v.getHeight());
                            nn.setDuration(150);
                            nn.start();
                            Handler handler2 = new Handler();
                            handler2.postDelayed(() -> {
                                snackbar.getView().setVisibility(View.INVISIBLE);
                                if(finalI_up_the_bottom_bar){
                                    new sql_manager(context).Update_data_setting("5", "غير مفعل");
                                    new adapter_setting().Control_navigate_bottom(activity);
                                }
                                dimiss_snackbar_bar(activity);
                            }, 150);
                        }
                        else{
                            nn.y(y[0]);
                            nn.setDuration(0);
                            nn.start();
                        }
                    }

                    break;
                default:
                    return false;
            }
            return true;
        });


        */

    }
    float  dY;

    @SuppressLint("ClickableViewAccessibility")
    public void snack_bar_add_fiends( final Context context, final AppCompatActivity activity){

        sql_manager db_offline=new sql_manager(context);
        connect_to_firebase db_online=new connect_to_firebase();

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View sheetView = activity.getLayoutInflater().inflate(R.layout.add_friend_alert, null);

        final EditText input_ID=sheetView.findViewById(R.id.edt_id);
        final Button add=sheetView.findViewById(R.id.btn_ajj);

        add.setOnClickListener(v -> {


            final ArrayList<reqeust> my_requests_receive =db_offline.get__request_receive();
            ArrayList<reqeust> friends=db_offline.get__freinds();
            ArrayList<reqeust> my_requests=db_offline.get__request_sent();


            String id_typing =input_ID.getText().toString();


            if(id_typing.contains(".") || id_typing.contains("#") || id_typing.contains("$") ||
                    id_typing.contains("[") || id_typing.contains("]")){
                input_ID.setError(context.getResources().getString(R.string.id_m) + " '.', '#', '$', '[', or ']' ");
                input_ID.setText("");
                return;
            }

            if (id_typing.equals("")){
                input_ID.setError(context.getResources().getString(R.string.type_the_ID));
                input_ID.setText("");
                return;
            }
            if (id_typing.equals(db_offline.get__account().getId())){
                input_ID.setError(context.getResources().getString(R.string.add_you_self));
                input_ID.setText("");
                return;
            }
            for(reqeust account:my_requests){
                if(account.getId().equals(id_typing)){
                    input_ID.setError(context.getResources().getString(R.string.yo)+account.getName());
                    input_ID.setText("");
                    return;
                }
            }
            for(reqeust account: my_requests_receive){
                if(account.getId().equals(id_typing)){
                    input_ID.setError(context.getResources().getString(R.string.re)+account.getName()+ context.getResources().getString(R.string.cce));
                    input_ID.setText("");
                    return;
                }
            }
            for(reqeust account:friends){
                if(account.getId().equals(id_typing)){
                    input_ID.setError(context.getResources().getString(R.string.ve)+account.getName()+ context.getResources().getString(R.string.is));
                    input_ID.setText("");
                    return;
                }
            }

            add.setEnabled(false);
            add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);

            add.setText(context.getResources().getString(R.string.wait));
            db_online.user_exist(id_typing, (etat, r) -> {
                if(etat){
                    db_online.get_all_request_receive(db_offline.get__account().getId(), keys -> {

                        for(String s:keys){
                            if(s.equals(id_typing)){
                                input_ID.setError(context.getResources().getString(R.string.lrea)+r.getName()+ context.getResources().getString(R.string.an));
                                input_ID.setText("");
                                add.setEnabled(true);
                                add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                                add.setText(R.string.add);


                                new Support_save_data(context).add_this_collect_to_receive(keys, () -> {

                                });

                                return;
                            }
                        }

                        complet_send_request_operation(activity, context, new aide().get_Object_request(r), () -> {
                            add.setEnabled(true);
                            add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                            add.setText(R.string.add);
                        });


                    });

                }else{
                    input_ID.setError(context.getResources().getString(R.string.no_account_found)+id_typing);
                    input_ID.setText("");

                    add.setEnabled(true);
                    add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                    add.setText(R.string.add);
                }
            });



        });

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

     /*   boolean i_up_the_bottom_bar=false;
        if(!new sql_manager(context).get_all_setting().get(4).getChoice().equals("مفعل")){
            new sql_manager(context).Update_data_setting("5", "مفعل");
            new adapter_setting().Control_navigate_bottom(activity);
            i_up_the_bottom_bar=true;
        }

        sql_manager db_offline=new sql_manager(context);
        connect_to_firebase db_online=new connect_to_firebase();





        // Create the Snackbar
        snackbar= Snackbar.make(activity.getWindow().getDecorView().getRootView(), "", Snackbar.LENGTH_INDEFINITE);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbar.getView().getRootView().setBackgroundResource(android.R.color.transparent);




        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {

            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });

        // Inflate our custom view
        @SuppressLint("InflateParams")
        View snackView = activity.getLayoutInflater().inflate(R.layout.add_friend_alert, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        final EditText input_ID=snackView.findViewById(R.id.edt_id);
        final Button add=snackView.findViewById(R.id.btn_ajj);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);




        add.setOnClickListener(v -> {


            final ArrayList<reqeust> my_requests_receive =db_offline.get__request_receive();
            ArrayList<reqeust> friends=db_offline.get__freinds();
            ArrayList<reqeust> my_requests=db_offline.get__request_sent();


            String id_typing =input_ID.getText().toString();


            if(id_typing.contains(".") || id_typing.contains("#") || id_typing.contains("$") ||
                    id_typing.contains("[") || id_typing.contains("]")){
                 input_ID.setError(context.getResources().getString(R.string.id_m) + " '.', '#', '$', '[', or ']' ");
                input_ID.setText("");
                return;
            }

            if (id_typing.equals("")){
                input_ID.setError(context.getResources().getString(R.string.type_the_ID));
                input_ID.setText("");
                return;
            }
            if (id_typing.equals(db_offline.get__account().getId())){
                input_ID.setError(context.getResources().getString(R.string.add_you_self));
                input_ID.setText("");
                return;
            }
            for(reqeust account:my_requests){
                if(account.getId().equals(id_typing)){
                    input_ID.setError(context.getResources().getString(R.string.yo)+account.getName());
                    input_ID.setText("");
                    return;
                }
            }
            for(reqeust account: my_requests_receive){
                if(account.getId().equals(id_typing)){
                    input_ID.setError(context.getResources().getString(R.string.re)+account.getName()+ context.getResources().getString(R.string.cce));
                    input_ID.setText("");
                    return;
                }
            }
            for(reqeust account:friends){
                if(account.getId().equals(id_typing)){
                    input_ID.setError(context.getResources().getString(R.string.ve)+account.getName()+ context.getResources().getString(R.string.is));
                    input_ID.setText("");
                    return;
                }
            }

            add.setEnabled(false);
            add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);

            add.setText(context.getResources().getString(R.string.wait));
                db_online.user_exist(id_typing, (etat, r) -> {
                if(etat){
                    db_online.get_all_request_receive(db_offline.get__account().getId(), keys -> {

                        for(String s:keys){
                            if(s.equals(id_typing)){
                                input_ID.setError(context.getResources().getString(R.string.lrea)+r.getName()+ context.getResources().getString(R.string.an));
                                input_ID.setText("");
                                add.setEnabled(true);
                                add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                                add.setText(R.string.add);


                                new Support_save_data(context).add_this_collect_to_receive(keys, () -> {

                                });

                                return;
                            }
                        }

                        complet_send_request_operation(activity, context, new aide().get_Object_request(r), () -> {
                            add.setEnabled(true);
                            add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                            add.setText(R.string.add);
                        });


                    });

                }else{
                    input_ID.setError(context.getResources().getString(R.string.no_account_found)+id_typing);
                    input_ID.setText("");

                    add.setEnabled(true);
                    add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                    add.setText(R.string.add);
                }
            });



        });




        //If the view is not covering the whole snackbar layout, add this line
        layout=get_layout(layout,context,activity);
//
      //  //// Add the view to the Snackbar's layout
        layout.addView(snackView, 0);





        // Show the Snackbar
        snackbar.show();


        final int[] a = {0};
        final float[] y = new float[1];
        final float[] decalge = new float[1];
        final Chrono chrono = new Chrono();

        boolean finalI_up_the_bottom_bar = i_up_the_bottom_bar;
        snackbar.getView().setOnTouchListener((v, event) -> {


            if(a[0] ==0) {
                y[0] = v.getY();
                a[0]++;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    chrono.start();
                    dY = y[0]- event.getRawY();
                    decalge[0] = event.getRawY()-y[0];
                    cancel_line.setBackground(ContextCompat.getDrawable(context,R.drawable.flech_c));
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!(event.getRawY()-decalge[0] < y[0])) {
                        ViewPropertyAnimator nn = v.animate();
                        nn.y(event.getRawY() + dY);
                        nn.setDuration(0);
                        nn.start();
                    }else
                    {
                        ViewPropertyAnimator nn = v.animate();
                        nn.y(y[0]);
                        nn.setDuration(0);
                        nn.start();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    cancel_line.setBackground(ContextCompat.getDrawable(context,R.drawable.flech));
                    chrono.stop();
                    if (event.getRawY()>y[0]) {
                        ViewPropertyAnimator nn = v.animate();
                        if(chrono.getDureeMs()<1300) {
                            nn.y(y[0] + v.getHeight());
                            nn.setDuration(150);
                            nn.start();
                            Handler handler2 = new Handler();
                            handler2.postDelayed(() -> {
                                snackbar.getView().setVisibility(View.INVISIBLE);
                                if(finalI_up_the_bottom_bar){
                                    new sql_manager(context).Update_data_setting("5", "غير مفعل");
                                    new adapter_setting().Control_navigate_bottom(activity);
                                }
                                dimiss_snackbar_bar(activity);
                            }, 150);
                        }
                        else{
                            nn.y(y[0]);
                            nn.setDuration(0);
                            nn.start();
                        }
                    }

                    break;
                default:
                    return false;
            }
            return true;
        });


      */

    }

    private void complet_send_request_operation(AppCompatActivity activity,Context context,reqeust object_request,add add) {
        new connect_to_firebase().add_request_receive(new sql_manager(context).get__account().getId(), object_request, etat ->
                new connect_to_firebase().add_request_sent(new sql_manager(context).get__account().getId(), object_request, etat1 ->
                        get_request_complete(context,object_request, object -> activity.runOnUiThread(() -> {
                            add.onComplete();
                            new sql_manager(context).insert_data_into_request(object);
                            send_notification(object.getId(),context);


                            //dimiss_snackbar_bar(activity);

                            if(i_up_the_bottom_bar_){
                                new sql_manager(context).Update_data_setting("5", "غير مفعل");
                                new adapter_setting().Control_navigate_bottom(activity);
                            }

                            action_invtation_sent(object_request.getId(),context, (AppCompatActivity) context);

                            Fragment accuil = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                            if (accuil != null) {
                                home_ ho = (home_) accuil;
                                online h=(online) ho.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);

                                h.viewPager.setCurrentItem(1, true);

                                try {
                                    reqeust_sent reqeust_sent = (reqeust_sent) h.mainAdapter.getSubItemFragment(R.id.view_pager
                                    ,1);


                                }catch (Exception e){
                                    Toast.makeText(activity, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                              //reqeust_sent.plain_list();
                            //reqeust_sent.add_request(object_request);
                            }// else
                                //Toast.makeText(activity, "acceul adapter null", Toast.LENGTH_SHORT).show();
                        }))));
    }

    private void get_request_complete(Context context,reqeust object_request , reqeust_sent.object_plain object_plain) {
        object_request.setType("sent");
        object_request.setState("encore");

        Glide.with(context)
                .load(object_request.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        get_request_complete(context,object_request, object_plain);
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

    private void send_notification(String id,Context context) {

        new connect_to_firebase().retrive_Tocken(id, tocken -> new aide().sendNotifications(tocken, "request_friend", new sql_manager(context).get__account().getId()));
    }

    interface add{
        void onComplete();
    }


    boolean i_up_the_bottom_bar_=false;
    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void show_this_data(String id, final Context context, final AppCompatActivity activity,Bitmap b,account account){

        String[] id_typing={""};
        String[] name={""};
        account[] accounts=new account[1];


        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetMainStyle);
        View snackView = activity.getLayoutInflater().inflate(R.layout.show_data_user, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        SelectableRoundedImageView img_profile;
        TextView txt_current_level_1,txt_name,txt_total_win,txt_rank,txt_game_win,txt_win_porcentage,img_country;
        Button btn_add;
        final View cancel_line=snackView.findViewById(R.id.cancel_line);

        img_profile=snackView.findViewById(R.id.image_profil);
        txt_current_level_1=snackView.findViewById(R.id.img_current_level);
        txt_name=snackView.findViewById(R.id.txt_name);
        txt_total_win=snackView.findViewById(R.id.text_total_win);
        txt_rank=snackView.findViewById(R.id.text_rank);
        txt_game_win=snackView.findViewById(R.id.text_Games_won);
        txt_win_porcentage=snackView.findViewById(R.id.text_Win_percentage);
        img_country=snackView.findViewById(R.id.img_country);
        btn_add=snackView.findViewById(R.id.btn_add);

        sql_manager db_offline=new sql_manager(context);


        if(db_offline.get__account().getId().equals(id)){
            account info_account =db_offline.get__account();
            byte[] tof_saved=info_account.getPhoto_saved();
            Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            img_profile.setImageBitmap(decodedByte);

            txt_name.setText(info_account.getName());
            txt_current_level_1.setText(String.format("%02d",info_account.getLevel()));
            txt_current_level_1.setBackground(new aide().get_the_star_compatible(info_account.getLevel(),context));
            txt_total_win.setText(new aide().get_the_string_compatible(info_account.getTotal_money_win(),context));
            txt_rank.setText(new aide().get_rank(info_account.getLevel(),context));
            txt_game_win.setText(String.format("%02d /%02d",info_account.getVictoir_gain(),(info_account.getVictoir_gain()+info_account.getVictoir_loss())));
            txt_win_porcentage.setText(String.format("%.2f %2$s",new aide().get_porcentage(info_account.getVictoir_gain(),info_account.getVictoir_loss()),"%"));
            img_country.setText(new aide().get_my_name_country(info_account.getCountry())+"\t"+new aide().country_to_emojie(info_account.getCountry()));
        }
        else if(activity instanceof game_space_randomly){
            img_profile.setImageBitmap(b);
            new connect_to_firebase().user_exist(id, (etat, info_account) -> {
                id_typing[0] =info_account.getId();
                name[0]=info_account.getName();
                accounts[0]=info_account;
                btn_add.setVisibility(View.VISIBLE);
                txt_name.setText( info_account.getName());
                txt_current_level_1.setText(String.format("%02d",info_account.getLevel()));
                txt_current_level_1.setBackground(new aide().get_the_star_compatible(info_account.getLevel(),context));
                txt_total_win.setText(new aide().get_the_string_compatible(info_account.getTotal_money_win(),context));
                txt_rank.setText(new aide().get_rank(info_account.getLevel(),context));
                txt_game_win.setText(String.format("%02d /%02d",info_account.getVictoir_gain(),(info_account.getVictoir_gain()+info_account.getVictoir_loss())));
                txt_win_porcentage.setText(String.format("%.2f %2$s",new aide().get_porcentage(info_account.getVictoir_gain(),info_account.getVictoir_loss()),"%"));
                img_country.setText(new aide().get_my_name_country(info_account.getCountry())+"\t"+new aide().country_to_emojie(info_account.getCountry()));
            });
        }
        else if(activity instanceof Praincipal){

            if(account!=null) {
                account info_account = account;
                byte[] tof_saved = info_account.getPhoto_saved();
                Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
                img_profile.setImageBitmap(decodedByte);


                id_typing[0] = info_account.getId();
                name[0] = info_account.getName();
                accounts[0] = info_account;
                btn_add.setVisibility(View.VISIBLE);

                txt_name.setText(info_account.getName());
                txt_current_level_1.setText(String.format("%02d", info_account.getLevel()));
                txt_current_level_1.setBackground(new aide().get_the_star_compatible(info_account.getLevel(), context));
                txt_total_win.setText(new aide().get_the_string_compatible(info_account.getTotal_money_win(), context));
                txt_rank.setText(new aide().get_rank(info_account.getLevel(), context));
                txt_game_win.setText(String.format("%02d /%02d", info_account.getVictoir_gain(), (info_account.getVictoir_gain() + info_account.getVictoir_loss())));
                txt_win_porcentage.setText(String.format("%.2f %2$s", new aide().get_porcentage(info_account.getVictoir_gain(), info_account.getVictoir_loss()), "%"));
                img_country.setText(new aide().get_my_name_country(info_account.getCountry()) + "\t" + new aide().country_to_emojie(info_account.getCountry()));
            }else {
                img_profile.setImageBitmap(b);
                new connect_to_firebase().user_exist(id, (etat, info_account) -> {
                    id_typing[0] =info_account.getId();
                    name[0]=info_account.getName();
                    accounts[0]=info_account;
                    btn_add.setVisibility(View.VISIBLE);
                    txt_name.setText( info_account.getName());
                    txt_current_level_1.setText(String.format("%02d",info_account.getLevel()));
                    txt_current_level_1.setBackground(new aide().get_the_star_compatible(info_account.getLevel(),context));
                    txt_total_win.setText(new aide().get_the_string_compatible(info_account.getTotal_money_win(),context));
                    txt_rank.setText(new aide().get_rank(info_account.getLevel(),context));
                    txt_game_win.setText(String.format("%02d /%02d",info_account.getVictoir_gain(),(info_account.getVictoir_gain()+info_account.getVictoir_loss())));
                    txt_win_porcentage.setText(String.format("%.2f %2$s",new aide().get_porcentage(info_account.getVictoir_gain(),info_account.getVictoir_loss()),"%"));
                    img_country.setText(new aide().get_my_name_country(info_account.getCountry())+"\t"+new aide().country_to_emojie(info_account.getCountry()));
                });
            }
        }
        else {

            reqeust r =db_offline.get_this_request(id);
            byte[] tof_saved=r.getPhoto_saved();
            Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
            img_profile.setImageBitmap(decodedByte);


            new connect_to_firebase().user_exist(id, (etat, info_account) -> {
                txt_name.setText(info_account.getName());
                txt_current_level_1.setText(String.format("%02d",info_account.getLevel()));
                txt_current_level_1.setBackground(new aide().get_the_star_compatible(info_account.getLevel(),context));
                txt_total_win.setText(new aide().get_the_string_compatible(info_account.getTotal_money_win(),context));
                txt_rank.setText(new aide().get_rank(info_account.getLevel(),context));
                txt_game_win.setText(String.format("%02d /%02d",info_account.getVictoir_gain(),(info_account.getVictoir_gain()+info_account.getVictoir_loss())));
                txt_win_porcentage.setText(String.format("%.2f %2$s",new aide().get_porcentage(info_account.getVictoir_gain(),info_account.getVictoir_loss()),"%"));
                img_country.setText(new aide().get_my_name_country(info_account.getCountry())+"\t"+new aide().country_to_emojie(info_account.getCountry()));
            });
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<reqeust> my_requests_receive =db_offline.get__request_receive();
                ArrayList<reqeust> friends=db_offline.get__freinds();
                ArrayList<reqeust> my_requests=db_offline.get__request_sent();



                if (id_typing[0].equals(db_offline.get__account().getId())){
                    Toast.makeText(context, context.getResources().getString(R.string.add_you_self), Toast.LENGTH_SHORT).show();
                    return;
                }
                for(reqeust account:my_requests){
                    if(account.getId().equals(id_typing[0])){
                        Toast.makeText(context, context.getResources().getString(R.string.yo)+ name[0], Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for(reqeust account: my_requests_receive){
                    if(account.getId().equals(id_typing[0])){
                        Toast.makeText(context, context.getResources().getString(R.string.re)+ name[0]+ context.getResources().getString(R.string.cce)
                                , Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for(reqeust account:friends){
                    if(account.getId().equals(id_typing[0])){
                        Toast.makeText(context,context.getResources().getString(R.string.ve)+ name[0]+ context.getResources().getString(R.string.is)
                                , Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                btn_add.setEnabled(false);
                btn_add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
                btn_add.setText(context.getResources().getString(R.string.wait));

                new connect_to_firebase().get_all_request_receive(db_offline.get__account().getId(), keys -> {

                    for(String s:keys){
                        if(s.equals(id_typing)){
                            Toast.makeText(context, ""+context.getResources().getString(R.string.lrea)+name[0]+ context.getResources().getString(R.string.an), Toast.LENGTH_SHORT).show();

                            btn_add.setEnabled(true);
                            btn_add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                            btn_add.setText(R.string.add);


                            new Support_save_data(context).add_this_collect_to_receive(keys, () -> {

                            });

                            return;
                        }
                    }

                    complet_send_request_operation(activity, context, new aide().get_Object_request(accounts[0]), () -> {
                        btn_add.setEnabled(true);
                        btn_add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_plean_, 0);
                        btn_add.setText(R.string.add);
                    });


                });

            }
        });

        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();

    }



    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void action_while_playne(int action,String id, final Context context, final AppCompatActivity activity){




        // Create the Snackbar
        snackbar= Snackbar.make(activity.getWindow().getDecorView().getRootView(), "", 5000);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbar.getView().getRootView().setBackgroundResource(android.R.color.transparent);


        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {

            }

            @Override
            public void onShown(Snackbar snackbar) {
            }
        });

        // Inflate our custom view
        @SuppressLint("InflateParams")
        View snackView = activity.getLayoutInflater().inflate(R.layout.action_while_playing, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        SelectableRoundedImageView img_profile;
        TextView txt_name,txt_challenge;

        img_profile=snackView.findViewById(R.id.image_profil);
        txt_name=snackView.findViewById(R.id.txt_name);
        txt_challenge=snackView.findViewById(R.id.txt_challenge);

        sql_manager db_offline=new sql_manager(context);
        reqeust r =db_offline.get_this_request(id);
        byte[] tof_saved=r.getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        img_profile.setImageBitmap(decodedByte);
        txt_name.setText(r.getName());

        if(action==0)
           txt_challenge.setText(context.getResources().getString(R.string.accepted_your_challenge));
        else
            txt_challenge.setText(context.getResources().getString(R.string.i_send_you_a_challenge));



        //If the view is not covering the whole snackbar layout, add this line
        layout=get_layout(layout,context,activity);

        //// Add the view to the Snackbar's layout

        layout.addView(snackView, 0);





        // Show the Snackbar
        snackbar.show();
    }

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void action_invtation_sent(String id, final Context context, final AppCompatActivity activity){

        // Create the Snackbar
        snackbar= Snackbar.make(activity.getWindow().getDecorView().getRootView(), "", 3000);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbar.getView().getRootView().setBackgroundResource(android.R.color.transparent);


        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {

            }

            @Override
            public void onShown(Snackbar snackbar) {
            }
        });

        // Inflate our custom view
        @SuppressLint("InflateParams")
        View snackView = activity.getLayoutInflater().inflate(R.layout.invitaation_sent, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        SelectableRoundedImageView img_profile;
        TextView txt_name,txt_challenge;

        img_profile=snackView.findViewById(R.id.image_profil);
        txt_name=snackView.findViewById(R.id.txt_name);
        txt_challenge=snackView.findViewById(R.id.txt_challenge);

        sql_manager db_offline=new sql_manager(context);
        reqeust r =db_offline.get_this_request(id);
        byte[] tof_saved=r.getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        img_profile.setImageBitmap(decodedByte);
        txt_name.setText(r.getName());


        //If the view is not covering the whole snackbar layout, add this line
        //layout=get_layout(layout,context,activity);

        if(new sql_manager(context).get_all_setting().get(4).getChoice().equals("مفعل"))
            layout.setPadding(0,0,0, context.getResources().
                    getDimensionPixelOffset(context.getResources().
                            getIdentifier("navigation_bar_height","dimen","android")));
        else
            layout.setPadding(0,0,0,0);

        //// Add the view to the Snackbar's layout

        layout.addView(snackView, 0);





        // Show the Snackbar
        snackbar.show();
    }

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void snack_bar_start(final Context context, final AppCompatActivity activity){

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View snackView = activity.getLayoutInflater().inflate(R.layout.show_data_star, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        final RecyclerView list=snackView.findViewById(R.id.list_star);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);




        list_start_info adapter = new list_start_info(context);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();




    }


    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void snack_add_friends(final Context context, final AppCompatActivity activity){

        boolean i_up_the_bottom_bar=false;
        if(!new sql_manager(context).get_all_setting().get(4).getChoice().equals("مفعل")){
            new sql_manager(context).Update_data_setting("5", "مفعل");
            new adapter_setting().Control_navigate_bottom(activity);
            i_up_the_bottom_bar=true;
        }




        // Create the Snackbar
        snackbar= Snackbar.make(activity.getWindow().getDecorView().getRootView(), "", Snackbar.LENGTH_INDEFINITE);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbar.getView().getRootView().setBackgroundResource(android.R.color.transparent);

        connect_to_firebase db_online=new connect_to_firebase();





        // Inflate our custom view
        @SuppressLint("InflateParams")
        View snackView = activity.getLayoutInflater().inflate(R.layout.add_new_user_layout, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        RecyclerView list=snackView.findViewById(R.id.list_star);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);
        final LottieAnimationView ltt=snackView.findViewById(R.id.lottie_wait);

        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {

            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });




        db_online.get_actuel_pack(new connect_to_firebase.pack_is() {
            @Override
            public void onCallback(pack_generator pack) {
                db_online.get_element_of_pack(pack.getActuel_pack(), new connect_to_firebase.key_user_recipe() {
                    @Override
                    public void on_callback(ArrayList<String> keys) {

                        ltt.setVisibility(View.GONE);
                        list_add_friends adapter = new list_add_friends(context,keys,(Praincipal) activity,pack.getActuel_pack());
                        list.setAdapter(adapter);
                        list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

                    }
                });
            }
        });



        //If the view is not covering the whole snackbar layout, add this line
        layout=get_layout(layout,context,activity);
//
       // //// Add the view to the Snackbar's layout
        layout.addView(snackView, 0);





        // Show the Snackbar
        snackbar.show();


        final int[] a = {0};
        final float[] y = new float[1];
        final float[] decalge = new float[1];
        final Chrono chrono = new Chrono();

        boolean finalI_up_the_bottom_bar = i_up_the_bottom_bar;
        snackbar.getView().setOnTouchListener((v, event) -> {


            if(a[0] ==0) {
                y[0] = v.getY();
                a[0]++;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    chrono.start();
                    dY = y[0]- event.getRawY();
                    decalge[0] = event.getRawY()-y[0];
                    cancel_line.setBackground(ContextCompat.getDrawable(context,R.drawable.flech_c));
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!(event.getRawY()-decalge[0] < y[0])) {
                        ViewPropertyAnimator nn = v.animate();
                        nn.y(event.getRawY() + dY);
                        nn.setDuration(0);
                        nn.start();
                    }else
                    {
                        ViewPropertyAnimator nn = v.animate();
                        nn.y(y[0]);
                        nn.setDuration(0);
                        nn.start();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    cancel_line.setBackground(ContextCompat.getDrawable(context,R.drawable.flech));
                    chrono.stop();
                    if (event.getRawY()>y[0]) {
                        ViewPropertyAnimator nn = v.animate();
                        if(chrono.getDureeMs()<1300) {
                            nn.y(y[0] + v.getHeight());
                            nn.setDuration(150);
                            nn.start();
                            Handler handler2 = new Handler();
                            handler2.postDelayed(() -> {
                                snackbar.getView().setVisibility(View.INVISIBLE);
                                if(finalI_up_the_bottom_bar){
                                    new sql_manager(context).Update_data_setting("5", "غير مفعل");
                                    new adapter_setting().Control_navigate_bottom(activity);
                                }
                                //dimiss_snackbar_bar(activity);
                            }, 150);
                        }
                        else{
                            nn.y(y[0]);
                            nn.setDuration(0);
                            nn.start();
                        }
                    }

                    break;
                default:
                    return false;
            }
            return true;
        });
    }


    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void snack_select(final Context context, final AppCompatActivity activity,select select){

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View snackView = activity.getLayoutInflater().inflate(R.layout.shosse_type_of_add, null);

        // final Button button1=snackView.findViewById(R.id.cancel);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);
        final Button id=snackView.findViewById(R.id.btn_id);
        final Button randomly=snackView.findViewById(R.id.btn_aleat);



        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.on_callback(0);
                //dimiss_snackbar_bar(activity);
            }
        });

        randomly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.on_callback(1);
                //dimiss_snackbar_bar(activity);
            }
        });

        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();

    }


    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void snack_bar_select_puc(final Context context, final AppCompatActivity activity){

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View snackView = activity.getLayoutInflater().inflate(R.layout.select_new_puc_layout, null);



        // final Button button1=snackView.findViewById(R.id.cancel);
        final RecyclerView list=snackView.findViewById(R.id.list_star);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);




        list_select_image adapter = new list_select_image(context,(Praincipal) activity);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));


        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();



    }


    @SuppressLint("ClickableViewAccessibility")
    public void snack_bar_update_name( final Context context, final AppCompatActivity activity){


        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View snackView = activity.getLayoutInflater().inflate(R.layout.change_name_layout, null);

        sql_manager db_offline=new sql_manager(context);
        connect_to_firebase db_online=new connect_to_firebase();

        // final Button button1=snackView.findViewById(R.id.cancel);
        final EditText input_ID=snackView.findViewById(R.id.edt_id);
        final Button add=snackView.findViewById(R.id.btn_ajj);
        final TextView txt_prix=snackView.findViewById(R.id.txt_prix);
        final View cancel_line=snackView.findViewById(R.id.cancel_line);


        @SuppressLint("DefaultLocale")
        String m=String.format("%01d %2$s",1,context.getResources().getString(R.string.K));
        txt_prix.setText(m);


        add.setOnClickListener(v -> {




            String name =input_ID.getText().toString();

            if(db_offline.get__account().getMoney()>=1000) {

                if (name.trim().equals("")) {
                    input_ID.setError(context.getResources().getString(R.string.it)+"\uD83E\uDD14");
                    input_ID.setText("");
                    return;
                }
                if (name.trim().equals(db_offline.get__account().getName())) {
                    input_ID.setText("");
                    return;
                }


                add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hourglass, 0);
                add.setText(context.getResources().getString(R.string.wait));
                add.setEnabled(false);

                account account=db_offline.get__account();

                account.setName(name);
                account.setMoney(account.getMoney()-1000);

                db_online.update_name(account.getId(), name, new connect_to_firebase.add_succeful() {
                    @Override
                    public void add_complet(boolean etat) {
                        db_online.update_money(account.getId(), account.getMoney(), new connect_to_firebase.add_succeful() {
                            @Override
                            public void add_complet(boolean etat) {
                                db_offline.Update_V_A_acoount(context,account);
                                new sound().collect(context);
                                ((Praincipal)activity).txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));

                                fragment_account fr_a= (fragment_account) ((Praincipal)activity).getSupportFragmentManager().findFragmentById(R.id.fragment_Container);
                                fr_a.txt_name.setText(name);

                                add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.update, 0);
                                add.setText(context.getResources().getString(R.string.update));
                                add.setEnabled(true);

                                //dimiss_snackbar_bar(activity);
                            }
                        });
                    }
                });


            }else {

                String mo=context.getResources().getString(R.string.need)+" "
                        + m
                        +" "+context.getResources().getString(R.string.coins)+" "+context.getResources().getString(R.string.tocollect);
//
                new vibrateur(context).vibrate(100);
                Toast.makeText(context,mo, Toast.LENGTH_LONG).show();
            }




        });



        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();
    }


    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void snack_select_update(final Context context, final AppCompatActivity activity,select select){

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogKeyboardTheme);
        View snackView = activity.getLayoutInflater().inflate(R.layout.shosse_type_of_update, null);


        // final Button button1=snackView.findViewById(R.id.cancel);


        final View cancel_line=snackView.findViewById(R.id.cancel_line);
        final Button id=snackView.findViewById(R.id.btn_id);
        final Button randomly=snackView.findViewById(R.id.btn_aleat);



        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.on_callback(0);
                //dimiss_snackbar_bar(activity);
            }
        });

        randomly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.on_callback(1);
                //dimiss_snackbar_bar(activity);
            }
        });


        mBottomSheetDialog.setContentView(snackView);
        mBottomSheetDialog.show();


    }

    public interface select{
        void on_callback(int selected);
    }


    public boolean isNavigationBarAvailable(){
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }

    public Snackbar.SnackbarLayout get_layout(Snackbar.SnackbarLayout layout, Context context, AppCompatActivity activity){
        if(isNavigationBarAvailable()){
            if(!(activity instanceof game_space || activity instanceof bluetooth_game_space ||activity instanceof local_game_space
                    ||activity instanceof local_game_space_robot||activity instanceof game_space_randomly))
                layout.setPadding(0,0,0, context.getResources().
                    getDimensionPixelOffset(context.getResources().
                            getIdentifier("navigation_bar_height","dimen","android")));
            else
                layout.setPadding(0,0,0, 0);
        }else {
            layout.setPadding(0,0,0, 0);
        }
        return layout;
    }

}
