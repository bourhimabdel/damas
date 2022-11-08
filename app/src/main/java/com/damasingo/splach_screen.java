package com.damasingo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ConfigurationCompat;


import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.gain_day;
import com.damasingo.CLASS_UTIL.random_ob;
import com.damasingo.CLASS_UTIL.randomly_ob;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.FIRST_OPEN.save_data_in_first_open;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.Remind_user.remind_Service;
import com.damasingo.SQL_MANAGER.sql_manager;

import com.damasingo.SharedPreferences.sharedpreferences;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class splach_screen extends Activity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Splash_screen);
        super.onCreate(savedInstanceState);


        //printKeyHash(this);
        FirebaseApp.initializeApp(this);


        final String PREFS_NAME = "damas_App_PrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        clear_saved_audio();


        if (settings.getBoolean("damas_App_first_time", true)) {
            //new save_data_in_first_open().save_all_catugorie(this);
            new save_data_in_first_open().save_all_setting(this);
            new adapter_setting().set_setting_theme(this);

            //new sql_manager(this).insert_data_into_pointer();
            FirebaseAuth.getInstance().signOut();

            startService(new Intent(this, remind_Service.class));

            settings.edit().putBoolean("damas_App_first_time", false).apply();
            new sharedpreferences(this).put_view("local",0);

            SharedPreferences data_loaded =  this.getSharedPreferences("data_loaded", MODE_PRIVATE);
            SharedPreferences.Editor editor_low = data_loaded.edit();
            editor_low.putString("data", "NO");
            editor_low.apply();

            String m= ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0).getLanguage();
            new sql_manager(this).Update_data_setting("3",m);

        }else {
            new adapter_setting().set_setting_theme(this);
           // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
           //     new adapter_setting().set_setting_language(this,this);
           // }
        }


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

       // Log.e("current user",currentUser.getEmail());
        SharedPreferences data_loaded = this.getSharedPreferences("data_loaded", MODE_PRIVATE);
        String if_data_loaded = data_loaded.getString("data", "");



      //  if(currentUser!=null && if_data_loaded.equals("YES")) {
            //Uri uri = getIntent().getData();
            //if (uri != null) {
            //    List<String> parameters = uri.getPathSegments();
//
            //    String param = parameters.get(0);
//
            //    Intent intent = new Intent(getBaseContext(), open_form_Lien.class);
            //    intent.putExtra("recipe",param);
            //    startActivity(intent);
            //}
            //else
               startActivity(new Intent( this,Praincipal.class));
       //}else {
       //    new sql_manager(this).clear();
       //    startActivity(new Intent(this,sign_in.class));
       //}
        this.finish();

    }

  /*   public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

   */


    private void clear_saved_audio(){
        ContextWrapper contextWrapper =new ContextWrapper(getApplicationContext());
        File mus=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if(mus!=null)
        if (mus.isDirectory()) {
            String[] children = mus.list();
            for (int i = 0; i < children.length; i++) {
                new File(mus, children[i]).delete();
            }
        }
    }

}
