package com.damasingo.CLASS_UTIL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.splach_screen;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class push_notification {

    private final NotificationManager notificationManager;
    sql_manager db_offline;
    Context context;
    int[] images={R.mipmap.logo_game,R.mipmap.logo_game,R.mipmap.logo_game,R.mipmap.logo_game};
    int[] colors={R.color.c,R.color.a2,R.color.a2,R.color.a2};

    public push_notification(Context context){
        this.context=context;
        db_offline=new sql_manager(context);
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public push_notification(){
        this.notificationManager = null;
    }

    public void generate_request(String id_uesr){

        reqeust r=db_offline.get_this_request(id_uesr);

        String channel = null;
        int number=0;
        String description= null;

        if(r.getType().equals("sent")){
            ///// request sent refused
            if(r.getState().equals("accept")){
                ///// request sent accepted
                channel=context.getResources().getString(R.string.channel_request_Accepted);
                number=0;
                description=context.getResources().getString(R.string.yoa)+" "+r.getName()+" "+context.getResources().getString(R.string.are);
            }else {
                channel = context.getResources().getString(R.string.channel_request_refused);
                number = 1;
                description = r.getName() +" "+ context.getResources().getString(R.string.invit);
            }
        }else if(r.getType().equals("receive")){
            ///// request receive
            channel=context.getResources().getString(R.string.channel_request_received);
            number=2;
            description=r.getName()+" "+context.getResources().getString(R.string.wants);
        }


        String title=context.getResources().getString(R.string.app_name);
        byte[] image_recipe=r.getPhoto_saved();
        Bitmap puc_friends = BitmapFactory.decodeByteArray(image_recipe, 0, image_recipe.length);
        push(title,description,puc_friends,number,channel);
    }

    public void invit_to_play(String id_user){
        reqeust r=db_offline.get_this_request(id_user);

        String channel =context.getResources().getString(R.string.channel_invite);
        int number=3;
        String description= context.getResources().getString(R.string.with);
        String title=r.getName();

        byte[] image_recipe=r.getPhoto_saved();
        if(image_recipe!=null) {
            Bitmap puc_friends = BitmapFactory.decodeByteArray(image_recipe, 0, image_recipe.length);
            push(title, description, puc_friends, number, channel);
        }
    }

    public void accept_invit_to_play(String id_user){
        reqeust r=db_offline.get_this_request(id_user);

        String channel =context.getResources().getString(R.string.channel_accept);
        int number=3;
        String description= context.getResources().getString(R.string.aiting_);
        String title=r.getName();

        byte[] image_recipe=r.getPhoto_saved();
        if(image_recipe!=null) {
            Bitmap puc_friends = BitmapFactory.decodeByteArray(image_recipe, 0, image_recipe.length);
            push(title, description, puc_friends, number, channel);
        }
    }

    public void coins_ready(){

        String channel =context.getResources().getString(R.string.channel_periodic_reward);
        int number=3;
        String description= context.getResources().getString(R.string.Rewar);
        String title=context.getResources().getString(R.string.Reward);

        push(title,description,null,number,channel);
    }
    int aaa=0;
    public void classment_day(String space,int place){

        String channel ;
        int number=3;

        Bitmap puc;
        String title=context.getResources().getString(R.string.today_t);

        Drawable d=null;
        String description="";
        String mo="";
        if(space.equals("world")){
           mo=context.getResources().getString(R.string.world);
           channel=context.getResources().getString(R.string.channel_top_user);
        }else {
            account info_account=db_offline.get__account();
            mo=new aide().get_my_name_country(info_account.getCountry())+"\t"+new aide().country_to_emojie(info_account.getCountry());
            channel=context.getResources().getString(R.string.channel_top_user_);
            aaa=4;
        }
        switch (place){
            case 0:
                description=context.getResources().getString(R.string.congrate)+" "+mo;
               d= ContextCompat.getDrawable(context,R.drawable.o_first_place);
                break;
            case 1:
                description=context.getResources().getString(R.string.con_g)+" "+mo;
                d= ContextCompat.getDrawable(context,R.drawable.o_second_place);
                break;
            case 2:
                description=context.getResources().getString(R.string.congra)+" "+mo;
                d= ContextCompat.getDrawable(context,R.drawable.o_thrid_place);
                break;
        }

        assert d != null;
        puc = get_Bitmap(d);
        push(title,description,puc,number+aaa,channel);
    }

    public void remind_user(){

       String channel =context.getResources().getString(R.string.channel_remind_user);
       int number=3;
       String description= get_title();
       String title=context.getResources().getString(R.string.hi);

       push(title, description,null, number, channel);
    }

    private void push(String title,String description,Bitmap image,int number,String chanel_name){

        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(context, splach_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(chanel_name, chanel_name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Notification notification =
                new NotificationCompat.Builder(context)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(description))
                        .setContentText(description)
                        .setLargeIcon(getCircleBitmap(image))
                        .setNumber(number)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(images[number-aaa])
                        .setColor(colors[number-aaa])
                        .setChannelId(chanel_name)
                        .build();


        //notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear  the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE;//Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound


        if (!new sharedpreferences(context).get_app_state().equals("start"))
            notificationManager.notify(number,notification);
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        if (bitmap==null)
            return null;

        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    private Bitmap get_Bitmap(Drawable drawable){
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }

    private String get_title(){
        String text=context.getResources().getString(R.string.We)+" ";

        Date date=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch(dayOfWeek)
        {
            case 1:
                text+=context.getResources().getString(R.string.sunday)+" \uD83E\uDD74";
                break;
            case 2:
                text+=context.getResources().getString(R.string.monday)+" \uD83D\uDE16";
                break;
            case 3:
                text+=context.getResources().getString(R.string.tuesday)+" \uD83D\uDE0F";
                break;
            case 4:
                text+=context.getResources().getString(R.string.wednesday)+" \uD83D\uDE0C";
                break;
            case 5:
                text+=context.getResources().getString(R.string.thursday)+" \uD83D\uDE0A";
                break;
            case 6:
                text+=context.getResources().getString(R.string.friday)+" \uD83D\uDE1C";
                break;
            default:
                text+=context.getResources().getString(R.string.saturday)+" \uD83D\uDE1D";
                break;
        }

        text+=" "+context.getResources().getString(R.string.well);
        return text;
    }
}
