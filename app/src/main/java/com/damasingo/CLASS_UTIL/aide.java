package com.damasingo.CLASS_UTIL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.damasingo.R;
import com.damasingo.SendNotificationPack.APIService;
import com.damasingo.SendNotificationPack.Client;
import com.damasingo.SendNotificationPack.Data;
import com.damasingo.SendNotificationPack.MyResponse;
import com.damasingo.SendNotificationPack.NotificationSender;
import com.damasingo.SharedPreferences.sharedpreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aide {

    private APIService apiService;

    public aide() {
    }

    public int get_max_point_experience(int level){
        int point=1000;
        point+=level*100;
        return point;
    }



    public String get_rank(int level, Context context){

        ArrayList<String> ranks = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.rang_name)));



        if (level>=0 && level<=2){
            return ranks.get(0);
        }else if(level>=3 && level<=5){
            return ranks.get(1);
        }else if(level>=6 && level<=9){
            return ranks.get(2);
        }else if(level>=10 && level<=14){
            return ranks.get(3);
        }else if(level>=15 && level<=20){
            return ranks.get(4);
        }else if(level>=21 && level<=27){
            return ranks.get(5);
        }else if(level>=28 && level<=35){
            return ranks.get(6);
        }else if(level>=36 && level<=44){
            return ranks.get(7);
        }else if(level>=45 && level<=54){
            return ranks.get(8);
        }else if(level>=55 && level<=65){
            return ranks.get(9);
        }else if(level>=66 && level<=77){
            return ranks.get(10);
        }else if(level>=78 && level<=90){
            return ranks.get(11);
        }else if(level>=91 && level<=104){
            return ranks.get(12);
        }else if(level>=105 && level<=119){
            return ranks.get(13);
        }else if(level>=120 && level<=136){
            return ranks.get(14);
        }else if(level>=137){
            return ranks.get(15);
        }

        return "";

    }

    public double get_porcentage(int game_won,int game_lose){
        return  Math.floor(((game_won* 1.0 * 100) / (game_lose* 1.0 + game_won* 1.0)) * 100) / 100;
    }

    public reqeust get_Object_request(account account){
        reqeust reqeust=new reqeust();

        reqeust.setId(account.getId());
        reqeust.setName(account.getName());
        reqeust.setImage(account.getImage());

        return reqeust;
    }

    public void sendNotifications(String usertoken,String type,String id_user) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        Data data = new Data(type,id_user);


        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().success != 1) {
                        Log.e("notification send","failed");
                    }else {
                        Log.e("notification send","success");
                    }

                }
            }
            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    public void sendNotifications_request_repence(String usertoken,String type,String id_user,String etat) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        Data data = new Data(type,id_user);
        data.setEtat(etat);

        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().success != 1) {
                        Log.e("notification send","failed");
                    }else {
                        Log.e("notification send","success");
                    }
                }
            }
            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    public void sendNotifications_lets_play(String usertoken,String type,String id_user) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        Data data = new Data(type,id_user);
        data.setEtat("lets");


        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().success != 1) {
                        Log.e("notification send","failed");
                    }else {
                        Log.e("notification send","success");
                    }

                }
            }
            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    public void sendNotifications_I_accept_your_challenge(String usertoken,String type,String id_user) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        Data data = new Data(type,id_user);
        data.setEtat("okey");

        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().success != 1) {
                        Log.e("notification send","failed");
                    }else {
                        Log.e("notification send","success");
                    }

                }
            }
            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    public Date getCurrentUTC(){
        Date date = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        date = cal.getTime();
        return date;
    }

    public Drawable get_the_star_compatible(int level,Context context){
        if (level==0){
            return ContextCompat.getDrawable(context,R.drawable.start_1);
        }else if(level>=1 && level<=14){
            return ContextCompat.getDrawable(context,R.drawable.start_2);
        }else if(level>=15 && level<=19){
            return ContextCompat.getDrawable(context,R.drawable.start_3);
        }else if(level>=20 && level<=24){
            return ContextCompat.getDrawable(context,R.drawable.start_4);
        }else if(level>=25 && level<=34){
            return ContextCompat.getDrawable(context,R.drawable.start_5);
        }else if(level>=35 && level<=44){
            return ContextCompat.getDrawable(context,R.drawable.start_6);
        }else if(level>=45 && level<=54){
            return ContextCompat.getDrawable(context,R.drawable.start_7);
        }else if(level>=55 && level<=74){
            return ContextCompat.getDrawable(context,R.drawable.start_8);
        }else if(level>=75 && level<=99){
            return ContextCompat.getDrawable(context,R.drawable.start_9);
        }else if(level>=100)
            return ContextCompat.getDrawable(context,R.drawable.start_10);

        return null;
    }

    public Drawable get_the_progress_compatible(int level,Context context){
        if (level==0){
            return ContextCompat.getDrawable(context,R.drawable.progress_1);
        }else if(level>=1 && level<=14){
            return ContextCompat.getDrawable(context,R.drawable.progress_2);
        }else if(level>=15 && level<=19){
            return ContextCompat.getDrawable(context,R.drawable.progress_3);
        }else if(level>=20 && level<=24){
            return ContextCompat.getDrawable(context,R.drawable.progress_4);
        }else if(level>=25 && level<=34){
            return ContextCompat.getDrawable(context,R.drawable.progress_5);
        }else if(level>=35 && level<=44){
            return ContextCompat.getDrawable(context,R.drawable.progress_6);
        }else if(level>=45 && level<=54){
            return ContextCompat.getDrawable(context,R.drawable.progress_7);
        }else if(level>=55 && level<=74){
            return ContextCompat.getDrawable(context,R.drawable.progress_8);
        }else if(level>=75 && level<=99){
            return ContextCompat.getDrawable(context,R.drawable.progress_9);
        }else if(level>=100)
            return ContextCompat.getDrawable(context,R.drawable.progress_10);

        return null;
    }

    @SuppressLint("DefaultLocale")
    public String  get_the_string_compatible(double Total, Context context){

        if (Total>1000000000000000d){
            double b=1000000000000000d;
            double d=Total/b;
            return String.format("%.2f",d)+" "+context.getResources().getString(R.string.q);
        }else if (Total>1000000000000d){
            double b=1000000000000d;
            double d=Total/b;
            return String.format("%.2f",d)+" "+context.getResources().getString(R.string.t);
        }else if (Total>1000000000){
            double b=1000000000;
            double d=Total/b;
            return String.format("%.2f",d)+" "+context.getResources().getString(R.string.b);
        }else if (Total>1000000){
            double b=1000000;
            double d=Total/b;
           return String.format("%.2f",d)+" "+context.getResources().getString(R.string.M);
        }else if(Total>1000){
            double b=1000;
            double d=Total/b;
            return String.format("%.2f",d)+" "+context.getResources().getString(R.string.K);
        }else {
            return String.format("%.0f",Total);
        }
    }

    @SuppressLint("DefaultLocale")
    public ArrayList<star_data> get_all_start(Context context){
        ArrayList<star_data> all_stars=new ArrayList<>();
        Drawable d=null;
        String s="";
        for (int i=1;i<=11;i++){
          switch (i){
              case 1:
                  d=ContextCompat.getDrawable(context,R.drawable.start_1);
                  s=String.format("%02d",0);
                  break;
              case 2:
                  d=ContextCompat.getDrawable(context,R.drawable.start_2);
                  s=String.format("%02d - %02d",1,14);
                  break;
              case 3:
                  d=ContextCompat.getDrawable(context,R.drawable.start_3);
                  s=String.format("%02d - %02d",15,19);
                  break;
              case 4:
                  d=ContextCompat.getDrawable(context,R.drawable.start_4);
                  s=String.format("%02d - %02d",20,24);
                  break;
              case 5:
                  d=ContextCompat.getDrawable(context,R.drawable.start_5);
                  s=String.format("%02d - %02d",25,34);
                  break;
              case 6:
                  d=ContextCompat.getDrawable(context,R.drawable.start_6);
                  s=String.format("%02d - %02d",35,44);
                  break;
              case 7:
                  d=ContextCompat.getDrawable(context,R.drawable.start_7);
                  s=String.format("%02d - %02d",45,54);
                  break;
              case 8:
                  d=ContextCompat.getDrawable(context,R.drawable.start_8);
                  s=String.format("%02d - %02d",55,74);
                  break;
              case 9:
                  d=ContextCompat.getDrawable(context,R.drawable.start_9);
                  s=String.format("%02d - %02d",75,99);
                  break;
              case 10:
                  d=ContextCompat.getDrawable(context,R.drawable.start_10);
                  s=String.format(">= %02d",100);
                  break;

          }
          all_stars.add(new star_data(d,s));
        }
        return all_stars;
    }

    @SuppressLint("DefaultLocale")
    public ArrayList<image_profile_data> get_all_imege(Context context) {
        ArrayList<image_profile_data> all_image = new ArrayList<>();
        sharedpreferences sharedpreferences = new sharedpreferences(context);

        for (int i = 1; i <= 23; i++) {

            image_profile_data br=new image_profile_data();
            br.setId_image(i);
            br.setOpened(sharedpreferences.is_this_image_opened(i));
            switch (i) {
                case 1:
                    br.setPrix(0);
                    br.setStr_prix("0");
                    br.setLien_image("https://wallpaper.dog/large/20486533.jpg");

                    break;
                case 2:
                    br.setPrix(0);
                    br.setStr_prix("0");
                    br.setLien_image("https://i.pinimg.com/564x/f0/c6/1d/f0c61df8db05d76ca17d8bffef627434.jpg");

                    break;
                case 3:
                    br.setPrix(500);
                    br.setStr_prix(String.format("%01d",500));
                    br.setLien_image("https://i.pinimg.com/236x/cf/cf/44/cfcf44bdb4af4877ae3c98f4d7177993.jpg");

                    break;
                case 4:
                    br.setPrix(1000);
                    br.setStr_prix(String.format("%01d %2$s",1,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/6a/35/0a/6a350a1fe092d26107903cb11f67ccea.jpg");
                    break;
                case 5:
                    br.setPrix(3000);
                    br.setStr_prix(String.format("%01d %2$s",3,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/b1/92/4d/b1924dce177345b5485bb5490ab3441f.jpg");
                    break;
                case 6:
                    br.setPrix(10000);
                    br.setStr_prix(String.format("%02d %2$s",10,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/6d/41/9c/6d419ce5e471ace5e90e8b9121c2a4b3.jpg");
                    break;
                case 7:
                    br.setPrix(20000);
                    br.setStr_prix(String.format("%02d %2$s",20,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/02/a5/0b/02a50b563ba791756d5657a7fdbe51e9.jpg");
                    break;
                case 8:
                    br.setPrix(40000);
                    br.setStr_prix(String.format("%02d %2$s",40,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/8d/02/ed/8d02edb5b00a2d11a398fefc56ac45e7.jpg");
                    break;
                case 9:
                    br.setPrix(100000);
                    br.setStr_prix(String.format("%03d %2$s",100,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/03/52/af/0352af0a0e04778edd0fb6c09998d1ec.jpg");
                    break;
                case 10:
                    br.setPrix(200000);
                    br.setStr_prix(String.format("%03d %2$s",200,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/17/09/8f/17098f736a57a9f88217ca09dcf88744.jpg");
                    break;
                case 11:
                    br.setPrix(300000);
                    br.setStr_prix(String.format("%03d %2$s",300,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/08/27/23/082723ad570164eb39b670dbad5ee92a.jpg");
                    break;
                case 12:
                    br.setPrix(400000);
                    br.setStr_prix(String.format("%03d %2$s",400,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/64/1b/78/641b78573f8c582a2ec587809072dfd9.jpg");
                    break;
                case 13:
                    br.setPrix(500000);
                    br.setStr_prix(String.format("%03d %2$s",500,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/bd/6f/e7/bd6fe7564e30edcf0ddc80f15e14fd61.jpg");
                    break;
                case 14:
                    br.setPrix(600000);
                    br.setStr_prix(String.format("%03d %2$s",600,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/51/96/b3/5196b34be5aec2079e4b68190299a544.jpg");
                    break;
                case 15:
                    br.setPrix(700000);
                    br.setStr_prix(String.format("%03d %2$s",700,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/6b/f4/5f/6bf45fc77bfc0daebd3f2f1f3745163b.jpg");
                    break;
                case 16:
                    br.setPrix(800000);
                    br.setStr_prix(String.format("%03d %2$s",800,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/cc/df/f9/ccdff9c062d210b52e7081ac53d38712.jpg");
                    break;
                case 17:
                    br.setPrix(900000);
                    br.setStr_prix(String.format("%03d %2$s",900,context.getResources().getString(R.string.K)));
                    br.setLien_image("https://i.pinimg.com/236x/5c/41/bc/5c41bcd77b3e14f7668b15581d96ed7c.jpg");
                    break;
                case 18:
                    br.setPrix(1000000);
                    br.setStr_prix(String.format("%01d %2$s",1,context.getResources().getString(R.string.M)));
                    br.setLien_image("https://i.pinimg.com/236x/1d/58/ee/1d58ee368ea910aaf2442015c3509a3f.jpg");
                    break;
                case 19:
                    br.setPrix(2000000);
                    br.setStr_prix(String.format("%01d %2$s",2,context.getResources().getString(R.string.M)));
                    br.setLien_image("https://i.pinimg.com/236x/c5/f6/ff/c5f6fff009e18801bf5bb0fd1e8247b0.jpg");
                    break;
                case 20:
                    br.setPrix(3000000);
                    br.setStr_prix(String.format("%01d %2$s",3,context.getResources().getString(R.string.M)));
                    br.setLien_image("https://i.pinimg.com/236x/63/71/66/637166dde773929f238b2171ad6c1064.jpg");
                    break;

                case 21:
                    br.setPrix(9000000);
                    br.setStr_prix(String.format("%01d %2$s",9,context.getResources().getString(R.string.M)));
                    br.setLien_image("https://i.pinimg.com/564x/b9/d5/40/b9d540049241483daed00aa07440cca6.jpg");
                    break;
                case 22:
                    br.setPrix(12000000);
                    br.setStr_prix(String.format("%01d %2$s",12,context.getResources().getString(R.string.M)));
                    br.setLien_image("https://i.pinimg.com/236x/27/d0/c6/27d0c6d885302d93442319418f180673.jpg");
                    break;
                case 23:
                    br.setPrix(39000000);
                    br.setStr_prix(String.format("%01d %2$s",39,context.getResources().getString(R.string.M)));
                    br.setLien_image("https://i.pinimg.com/736x/69/63/fd/6963fde9c4843b28eaa0abc3b647fba2.jpg");
                    break;
            }

            all_image.add(br);
        }

        return all_image;
    }

    public String get_my_country(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkCountryIso();
    }

    public String country_to_emojie(String countryCode) {
        countryCode=countryCode.toUpperCase();
        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    public String get_my_name_country(String countryCode) {
        countryCode=countryCode.toUpperCase();
        Locale loc = new Locale("",countryCode);
        return loc.getDisplayCountry();
    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public int get_day_of_year(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getCurrentUTC());

        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    @SuppressLint("SimpleDateFormat")
    public long how_many_time_to_day_end(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getCurrentUTC());
        long time=calendar.getTimeInMillis();

        long time_big=0;

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date_s = sdf.format(getCurrentUTC());
        date_s+=" "+"23:59:59 PM";
        try {
           // date_s= arabic_num_to_other(date_s);
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date dateObject =simpleDateFormat.parse(date_s);
            time_big=dateObject.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time_big-time;
    }

    public String[] country_day_converter(String score){
        return score.split("-");
    }


    public String arabic_num_to_other(String original) {
        if (original == null)
            return "";
        return original.replaceAll("٠", "0")
                .replaceAll("١", "1")
                .replaceAll("٢", "2")
                .replaceAll("٣", "3")
                .replaceAll("٤", "4")
                .replaceAll("٥", "5")
                .replaceAll("٦", "6")
                .replaceAll("٧", "7")
                .replaceAll("٨", "8")
                .replaceAll("٩", "9");
    }

}
