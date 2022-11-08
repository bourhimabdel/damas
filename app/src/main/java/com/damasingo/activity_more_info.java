package com.damasingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.damasingo.ADAPTER.list_info_and_rule;
import com.damasingo.CLASS_UTIL.info_rule;
import com.damasingo.JSON_FILE_CONVERT.convert_json_setting;
import com.damasingo.REGLAGE.adapter_setting;

import java.util.ArrayList;


public class activity_more_info extends AppCompatActivity {

    RecyclerView list_info,list_rule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
       //    new adapter_setting().set_setting_language(this,this);
       //}
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_more_info);


        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);

        int height = displayMetrics1.heightPixels;
        int width = displayMetrics1.widthPixels;


        Log.e("size :", " width :"+width+" height"+height +" Device Density "+this.getResources().getDisplayMetrics().densityDpi);


        getview();
        plain_data();

    }

    private void plain_data() {

        //// info
        ArrayList<info_rule> info=new convert_json_setting().get_info(this);
        list_info_and_rule adapter = new list_info_and_rule(info,this,this);
        list_info.setAdapter(adapter);
        list_info.setLayoutManager(new LinearLayoutManager(this));


        //// rule
        ArrayList<info_rule> rule=new convert_json_setting().get_rule(this);
        list_info_and_rule adapter2 = new list_info_and_rule(rule,this,this);
        list_rule.setAdapter(adapter2);
        list_rule.setLayoutManager(new LinearLayoutManager(this));


    }

    private void getview() {
        list_info=findViewById(R.id.list_paired);
        list_rule=findViewById(R.id.list_unpaired);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new adapter_setting().Control_navigate_bottom(this);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition( R.anim.entre_r,R.anim.exit_r);
    }
}