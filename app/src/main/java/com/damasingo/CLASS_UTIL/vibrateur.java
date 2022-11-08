package com.damasingo.CLASS_UTIL;

import android.content.Context;
import android.os.Vibrator;

import com.damasingo.SQL_MANAGER.sql_manager;

public class vibrateur {
    Context context;
    public vibrateur(Context context) {
        this.context = context;
    }

    public void vibrate(int millis){
        if(new sql_manager(context).get_all_setting().get(6).getChoice().equals("مفعل")) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(millis);
        }
    }
}
