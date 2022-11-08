package com.damasingo.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class sharedpreferences {

    Context context;
    public sharedpreferences(Context context){
        this.context=context;
    }

    public void put_app_state(String state){
        SharedPreferences data_loaded =  context.getSharedPreferences("app_state_details", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putString("app_state",state);
        editor_low.apply();
    }

    public String get_app_state(){
        SharedPreferences data_loaded = context.getSharedPreferences("app_state_details", MODE_PRIVATE);
        return  data_loaded.getString("app_state", "");
    }


    public void put_tab_notification(String tab_name,boolean b){
        SharedPreferences data_loaded =  context.getSharedPreferences("tabbed", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putBoolean(tab_name,b);
        editor_low.apply();
    }

    public boolean get_tab_notification(String tab){
        SharedPreferences data_loaded = context.getSharedPreferences("tabbed", MODE_PRIVATE);
        return data_loaded.getBoolean(tab, false);
    }

    public void put_view(String view,int a){
        SharedPreferences data_loaded =  context.getSharedPreferences("view_brain", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putInt(view,a);
        editor_low.apply();
    }

    public int get_vew(String view){
        SharedPreferences data_loaded = context.getSharedPreferences("view_brain", MODE_PRIVATE);
        return data_loaded.getInt(view,1);
    }


    public void put_tab_info(String tab_name,boolean b){
        SharedPreferences data_loaded =  context.getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putBoolean(tab_name,b);
        editor_low.apply();
    }


    public boolean get_tab_info(String tab){
        SharedPreferences data_loaded = context.getSharedPreferences("info", MODE_PRIVATE);
        return !data_loaded.getBoolean(tab, false);
    }


    public void put_setting_type(String tab_name,boolean b){
        SharedPreferences data_loaded =  context.getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putBoolean(tab_name,b);
        editor_low.apply();
    }


    public boolean get_setting_type(String tab){
        SharedPreferences data_loaded = context.getSharedPreferences("setting", MODE_PRIVATE);
        return data_loaded.getBoolean(tab,true);
    }


    public void this_image_opened(int image){
        SharedPreferences data_loaded =  context.getSharedPreferences("image", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putBoolean("num"+image,true);
        editor_low.apply();
    }

    public boolean is_this_image_opened(int image){
        if(image<=2)
            return true;

        SharedPreferences data_loaded = context.getSharedPreferences("image", MODE_PRIVATE);
        return data_loaded.getBoolean("num"+image, false);
    }



    public void update_score_offline(int a){
        SharedPreferences data_loaded =  context.getSharedPreferences("score_offline", MODE_PRIVATE);
        SharedPreferences.Editor editor_low = data_loaded.edit();
        editor_low.putInt("score",a);
        editor_low.apply();
    }

    public int get_score_offline(){
        SharedPreferences data_loaded = context.getSharedPreferences("score_offline", MODE_PRIVATE);
        return data_loaded.getInt("score",250);
    }
}
