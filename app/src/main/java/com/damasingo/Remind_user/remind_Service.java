package com.damasingo.Remind_user;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class remind_Service extends Service
{
    remind_Alarm alarm = new remind_Alarm();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e("err","StartCommand");
        alarm.setReminder(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        Log.e("err","Start");
        alarm.setReminder(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}