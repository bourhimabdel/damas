package com.damasingo.JSON_FILE_CONVERT;

import android.content.Context;

import com.damasingo.CLASS_UTIL.capital;
import com.damasingo.CLASS_UTIL.info_rule;
import com.damasingo.CLASS_UTIL.setting;
import com.damasingo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class convert_json_setting {

    public List<setting> get_setting(Context context) {

        List<setting> settings = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.setting);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();
            String json = new String(buffer, context.getResources().getString(R.string.UTF_8));


            JSONObject jsonObject = new JSONObject(json);

            JSONObject setting = jsonObject.getJSONObject("setting");

            for (int i = 1; i < 8; i++) {
                String ob_name = "setting_" + i;

                String name = setting.getJSONObject(ob_name).getString("name");
                String exact_choise = setting.getJSONObject(ob_name).getString("exact_choise");
                settings.add(new setting(name,exact_choise));
            }

            return settings;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<info_rule> get_info(Context context) {

        ArrayList<info_rule> info = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.info);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();
            String json = new String(buffer, context.getResources().getString(R.string.UTF_8));


            JSONObject jsonObject = new JSONObject(json);

            JSONObject setting = jsonObject.getJSONObject("info");

            for (int i = 1; i < 11; i++) {
                String ob_name = "info_" + i;

                String title = setting.getJSONObject(ob_name).getString("title");
                String description = setting.getJSONObject(ob_name).getString("description");
                String path = setting.getJSONObject(ob_name).getString("lien");

                info.add(new info_rule(title,description,path));
            }

            return info;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<info_rule> get_info(Context context,ArrayList<Integer> num) {

        ArrayList<info_rule> info = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.info);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();
            String json = new String(buffer, context.getResources().getString(R.string.UTF_8));


            JSONObject jsonObject = new JSONObject(json);

            JSONObject setting = jsonObject.getJSONObject("info");

            for (int i = 1; i < 10; i++) {
                String ob_name = "info_" + i;

                String title = setting.getJSONObject(ob_name).getString("title");
                String description = setting.getJSONObject(ob_name).getString("description");
                String path = setting.getJSONObject(ob_name).getString("lien");

                if(num.contains(i))
                    info.add(new info_rule(title,description,path));
            }

            return info;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<info_rule> get_rule(Context context) {

        ArrayList<info_rule> info = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.rule);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();
            String json = new String(buffer, context.getResources().getString(R.string.UTF_8));


            JSONObject jsonObject = new JSONObject(json);

            JSONObject setting = jsonObject.getJSONObject("rule");

            for (int i = 1; i < 9; i++) {
                String ob_name = "rule_" + i;

                String title = setting.getJSONObject(ob_name).getString("title");
                String description = setting.getJSONObject(ob_name).getString("description");
                String path = setting.getJSONObject(ob_name).getString("lien");

                info.add(new info_rule(title,description,path));
            }

            return info;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



}
