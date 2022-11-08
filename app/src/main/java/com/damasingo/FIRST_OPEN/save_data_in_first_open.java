package com.damasingo.FIRST_OPEN;

import android.content.Context;

import com.damasingo.CLASS_UTIL.setting;
import com.damasingo.JSON_FILE_CONVERT.convert_json_setting;
import com.damasingo.SQL_MANAGER.sql_manager;

import java.util.List;

public class save_data_in_first_open {
    public void save_all_setting(Context context) {
        sql_manager db = new sql_manager(context);
        List<setting> all_setting = new convert_json_setting().get_setting(context);
       // int i = 1;
        for (setting c : all_setting) {
            db.insert_data_into_Setting(c.getName(), c.getChoice());
           // String[] les_chois = c.getChoice();
           // for (String s : les_chois){
           //     db.insert_data_into_setting_choix(String.valueOf(i), s);
           // }
           // i++;
        }
    }
}
