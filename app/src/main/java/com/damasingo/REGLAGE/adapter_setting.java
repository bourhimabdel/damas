package com.damasingo.REGLAGE;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.damasingo.CLASS_UTIL.setting;
import com.damasingo.SQL_MANAGER.sql_manager;

import java.util.ArrayList;

public class adapter_setting {

    public void set_setting_theme(Context context){
        ArrayList<setting> list_setting=new sql_manager(context).get_all_setting();

        switch (list_setting.get(0).getChoice()){
            case "وضع ليلي":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "وضع نهاري":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "تلقائيا":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

    }

   /* @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void set_setting_language(Activity activity, Context context){
        sql_manager db=new sql_manager(context);
        ArrayList<setting> listo=db.get_all_setting();
        setting so=listo.get(2);
        switch (so.getChoice()){
            case"en":
                setLocale(activity,"en");
                break;
            case"fr":
                setLocale(activity,"fr");
                break;
            case"es":
                setLocale(activity,"es");
                break;
            case"ar":
                setLocale(activity,"ar");
                break;
            case"hi":
                setLocale(activity,"hi");
                break;
            case"ja":
                setLocale(activity,"ja");
                break;
            case"ru":
                setLocale(activity,"ru");
                break;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setLocale(Activity activity, String languageCode) {
        Locale locale;
        if(languageCode.equals("ar"))
             locale= new Locale(languageCode,"SA");
        else
             locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
*/
    public void Control_navigate_bottom(AppCompatActivity context) {
          ArrayList<setting> list_setting=new sql_manager(context).get_all_setting();

          if(list_setting.get(4).getChoice().equals("مفعل"))
          {
              context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
          }else {
        ///Hide_bottom_bar//////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
          }
    }
}
