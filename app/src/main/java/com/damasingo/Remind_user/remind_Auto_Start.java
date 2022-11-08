package com.damasingo.Remind_user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class remind_Auto_Start extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            context.startService(new Intent(context, remind_Service.class));
        }
    }
}
