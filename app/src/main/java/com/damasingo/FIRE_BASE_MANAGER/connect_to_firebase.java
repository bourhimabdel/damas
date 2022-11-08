package com.damasingo.FIRE_BASE_MANAGER;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.audio_massage;
import com.damasingo.CLASS_UTIL.gain_day;
import com.damasingo.CLASS_UTIL.match_running;
import com.damasingo.CLASS_UTIL.pack_generator;
import com.damasingo.CLASS_UTIL.random_ob;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class connect_to_firebase {




     public void add_new_user(account client, final add_succeful add_succeful){
         FirebaseDatabase.getInstance().getReference()
                 .child("Users").child("User N°"+client.getId()).setValue(client).addOnSuccessListener(aVoid -> add_succeful.add_complet(true)).addOnCanceledListener(() -> add_succeful.add_complet(false));
     }

     public void update_data_user(account account, final add_succeful add_succeful){
         FirebaseDatabase.getInstance().getReference()
                 .child("Users").child("User N°"+account.getId()).setValue(account)
                 .addOnCompleteListener(task -> add_succeful.add_complet(true));
     }

    public void update_data_user_match_loser(String id, final add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Users").child("User N°"+id).child("victoir_loss")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int match=snapshot.getValue(Integer.class);
                        match++;
                        FirebaseDatabase.getInstance().getReference()
                                .child("Users").child("User N°"+id).child("victoir_loss")
                                .setValue(match).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void update_money(String id, double new_money, final add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Users").child("User N°"+id).child("money").setValue(new_money)
                .addOnCompleteListener(task -> add_succeful.add_complet(true));
    }

    public void update_country(String id,String  country){
        FirebaseDatabase.getInstance().getReference()
                .child("Users").child("User N°"+id).child("country").setValue(country);
    }

    public void update_image(String id,String image, final add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Users").child("User N°"+id).child("image").setValue(image)
                .addOnCompleteListener(task -> add_succeful.add_complet(true));
    }

    public void update_name(String id,String name, final add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Users").child("User N°"+id).child("name").setValue(name)
                .addOnCompleteListener(task -> add_succeful.add_complet(true));
    }

     public void user_exist(String number, final user_exist user_exist){
         FirebaseDatabase.getInstance().getReference().child("Users").child("User N°"+number).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()){
                     user_exist.exist(true,dataSnapshot.getValue(account.class));
                     }
                 else{
                     user_exist.exist(false,null);
                 }
             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 user_exist.exist(false,null);
             }
         });

     }

    public void retrive_url_img(String number, final URL url){
        FirebaseDatabase.getInstance().getReference().child("Users").child("User N°"+number)
                .child("image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    url.onCallback(dataSnapshot.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

     public void add_request_receive(String My_id,reqeust account,add_succeful add_succeful){
         FirebaseDatabase.getInstance().getReference()
                 .child("Request_receive").child("User N°"+account.getId())
                 .child(My_id).setValue(My_id).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
     }

    public void add_request_sent(String My_id,reqeust account,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Request_sent").child("User N°"+My_id)
                .child(account.getId()).setValue(account.getId()).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }


    public void delete_request(String My_id,String id_recive,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Request_receive").child("User N°"+My_id)
                .child(id_recive).removeValue().addOnSuccessListener(aVoid -> FirebaseDatabase.getInstance().getReference()
                        .child("Request_sent").child("User N°"+id_recive)
                        .child(My_id).removeValue().addOnSuccessListener(aVoid1 -> add_succeful.add_complet(true)));
    }

    public void add_friends_score(Context context, score score, add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Friends").child("friends_of_User N°"+new sql_manager(context).get__account().getId())
                .child(score.getId()).setValue(score.getScore()).addOnSuccessListener(aVoid -> initial_challenge(new sql_manager(context).get__account().getId(), score.getId(), etat -> add_succeful.add_complet(true)));
    }

    public void add_other_friends_score(Context context, score score, add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("Friends").child("friends_of_User N°"+score.getId())
                .child(new sql_manager(context).get__account().getId()).setValue(score.getScore()).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }

    public void retrive_Tocken(String Key, final token_is token) {
        FirebaseDatabase.getInstance().getReference().child("Tokens").child(Key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                token.onCallback((String) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void distroy_Tocken(String Key, final add_succeful add_succeful) {
        FirebaseDatabase.getInstance().getReference().child("Tokens").child(Key)
                .setValue("out_app").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                add_succeful.add_complet(true);
            }
        });
    }

    public void get_all_request_receive(String id, final key_user_recipe key_user_recipe){
        final ArrayList<String> req=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("Request_receive").child("User N°"+id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    req.add(snapshot.getKey());
                }
                key_user_recipe.on_callback(req);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_all_request_sent(String id, final key_user_recipe key_user_recipe){
        final ArrayList<String> req=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("Request_sent").child("User N°"+id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    req.add(snapshot.getKey());
                }
                key_user_recipe.on_callback(req);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_all_friends(String id, final get_friends key_user_recipe){
        final ArrayList<score> req=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("Friends").child("friends_of_User N°"+id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    req.add(new score(snapshot.getKey(),(String) snapshot.getValue()));
                }
                key_user_recipe.on_callback(req);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void randomly_match(String my_id,int amount,Random random){

        DatabaseReference server_reference=FirebaseDatabase.getInstance()
                .getReference().child("Randomly_matches").child("server_"+amount);
        final DatabaseReference[] server_to_close = new DatabaseReference[1];

       //DatabaseReference my_Server_reference=FirebaseDatabase.getInstance()
       //        .getReference().child("Randomly_matches").child("server_"+amount).child(my_id).child("state");

        final int[] a = {0};
         take_a_place(amount, new get_a_place() {
             @Override
             public void onCallback(int place) {

                 random.waiting_friend();

                 Log.e("place is "," "+place);
                 if(place!=1) {
                     if (a[0] == 0) {
                         a[0]++;
                         random_ob random_ob = new random_ob(place);

                         String key = server_reference.push().getKey();

                         server_to_close[0] =server_reference.child(key).child("state");

                         save_push(key, my_id, new pushing_saver() {
                             @Override
                             public void onCallBack() {
                                 server_reference.child(key).setValue(random_ob).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         get_element_in_previous_place(amount, place - 1, new user_listner() {

                                             @Override
                                             public void waiting_(String her_id) {
                                                 retrive_id_from_push(her_id, new Uid_is() {
                                                     @Override
                                                     public void onCallback(String id) {
                                                         Start_match(id, my_id, amount, etat -> {
                                                             server_reference.child(key).child("state").setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {

                                                                 }
                                                             });

                                                             Log.e("from_waiting_", "f " + id + "  s " + my_id);
                                                             random.get_players(id, my_id);
                                                         });
                                                         Start_match(my_id, id, amount, etat -> {
                                                         });

                                                     }
                                                 });
                                             }

                                             @Override
                                             public void closing_() {

                                                 if(!match_canceled) {
                                                     server_reference.child(key).child("state").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {
                                                             DatabaseReference next_player = FirebaseDatabase.getInstance()
                                                                     .getReference().child("Randomly_matches").child("server_" + amount);

                                                             Query query = next_player.orderByChild("num").equalTo(place + 1);
                                                             ChildEventListener childEventListener = new ChildEventListener() {
                                                                 @Override
                                                                 public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                                                                     //  Log.e("from_closing","f "+my_id+"  s "+snapshot.getKey());
                                                                     retrive_id_from_push(snapshot.getKey(), new Uid_is() {
                                                                         @Override
                                                                         public void onCallback(String id) {
                                                                             random.get_players(my_id, id);
                                                                         }
                                                                     });


                                                                 }

                                                                 @Override
                                                                 public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                                                 }

                                                                 @Override
                                                                 public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                                                 }

                                                                 @Override
                                                                 public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                                                 }

                                                                 @Override
                                                                 public void onCancelled(@NonNull DatabaseError error) {

                                                                 }
                                                             };

                                                             query.addChildEventListener(childEventListener);

                                                             random.listening_to_some_on(query, childEventListener, server_to_close[0]);
                                                         }
                                                     });
                                                 }else
                                                     server_to_close[0].setValue(2);


                                             }
                                         });
                                     }
                                 });
                             }
                         });
                     }
                 }
             }
         });


    }

    public Boolean match_canceled=false;
    public void take_a_place(int amount,get_a_place get_a_place){
        DatabaseReference count_ref=  FirebaseDatabase.getInstance().getReference()
                .child("Randomly_counts").child("server_"+amount).child("count");

        final int[] value_final = new int[1];
        count_ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    if (!match_canceled) {
                        mutableData.setValue(1);
                        value_final[0] = 1;
                    }
                }else {
                    if(!match_canceled) {
                        mutableData.setValue(currentValue + 1);
                        value_final[0] = currentValue + 1;
                    }
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                System.out.println("Transaction completed");
                if(!match_canceled) {
                    get_a_place.onCallback(value_final[0]);
                }
            }
        });

    }


    public void get_element_in_previous_place(int amount,int in_place,user_listner user_listner) {
        DatabaseReference server_reference = FirebaseDatabase.getInstance()
                .getReference().child("Randomly_matches").child("server_" + amount);

        server_reference.orderByChild("num").equalTo(in_place).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String key = null;
                if(snapshot.exists()){

                    if(snapshot.exists()) {
                        HashMap<String, account> jsonObject=(HashMap) snapshot.getValue();
                        for (Map.Entry mapentry : jsonObject.entrySet()) {
                            key=(String) mapentry.getKey();
                        }
                    }
                   // Log.e("key", String.valueOf(snapshot.getKey()));

                    random_ob ob=snapshot.getValue(random_ob.class);
                    if(ob.getState()==0){
                        DatabaseReference state_reference = FirebaseDatabase.getInstance()
                                .getReference().child("Randomly_matches").child("server_" + amount).child(key)
                                .child("state");

                        final boolean[] data_come = {false};
                        String finalKey = key;
                        ValueEventListener valueEventListener=new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Log.e("hooo", String.valueOf(snapshot.getValue()));


                                if(Integer.parseInt(snapshot.getValue().toString())==1){
                                    state_reference.removeEventListener(this);
                                    if(!match_canceled)
                                        user_listner.waiting_(finalKey);
                                    data_come[0] =true;
                                }else if(Integer.parseInt(snapshot.getValue().toString())==2){
                                    state_reference.removeEventListener(this);
                                    if(!match_canceled)
                                        user_listner.closing_();
                                    data_come[0] =true;
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        };
                        state_reference.addValueEventListener(valueEventListener);

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(!data_come[0]){
                                    state_reference.setValue(2);
                                }
                            }
                        },5000);

                    }else if(ob.getState()==1){
                        user_listner.waiting_(snapshot.getKey());
                    }else if(ob.getState()==2){
                        user_listner.closing_();
                    }
                }else {

                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            get_element_in_previous_place(amount,in_place,user_listner);
                        }
                    },1000);
                    //user_listner.closing_();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void incrument_online(Context context){
        DatabaseReference count_ref=  FirebaseDatabase.getInstance().getReference()
                .child("onlin_e");

        final int[] value_final = new int[1];
        count_ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    if (!match_canceled) {
                        mutableData.setValue(1);
                    }
                }else {
                    if(!match_canceled) {
                        mutableData.setValue(currentValue + 1);
                    }
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                    System.out.println("Transaction completed");
            }
        });

        DatabaseReference user_tracke=  FirebaseDatabase.getInstance().getReference()
                .child("User_tracker");


        if(new sql_manager(context).get__account().getId()!=null)
            user_tracke.child(new sql_manager(context).get__account().getId())
                .setValue(0);


    }
    public void desincrument_online(Context context){
        DatabaseReference count_ref=  FirebaseDatabase.getInstance().getReference()
                .child("onlin_e");

        final int[] value_final = new int[1];
        count_ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    if (!match_canceled) {
                        mutableData.setValue(0);
                    }
                }else {
                    if(!match_canceled) {
                        mutableData.setValue(currentValue - 1);
                    }
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                System.out.println("Transaction completed");
            }
        });

        DatabaseReference user_tracke=  FirebaseDatabase.getInstance().getReference()
                .child("User_tracker");

        if(new sql_manager(context).get__account().getId()!=null)
            user_tracke.child(new sql_manager(context).get__account().getId())
                .setValue(new aide().getCurrentUTC().getTime());
    }

    public void get_time_user(String id_user,time time){
        DatabaseReference user_tracke=  FirebaseDatabase.getInstance().getReference()
                .child("User_tracker");

        user_tracke.child(id_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    time.onCallback((Long) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void get_number_user_online(time time){
        DatabaseReference count_ref=  FirebaseDatabase.getInstance().getReference()
                .child("onlin_e");

        final Integer[] currentValue = new Integer[1];
        count_ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                currentValue[0] = mutableData.getValue(Integer.class);
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(
                DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                System.out.println("Transaction completed");
                time.onCallback(currentValue[0]);
            }
        });


    }









    public void save_push(String push,String my_id,pushing_saver pushing_saver){
         FirebaseDatabase.getInstance().getReference().child("pushing_saver")
                 .child(my_id).setValue(push).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 pushing_saver.onCallBack();
             }
         });
    }

    public void retrive_id_from_push(String push,Uid_is uid_is){
        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("pushing_saver");

        db.orderByValue().equalTo(push).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, String> jsonObject= (HashMap<String, String>) snapshot.getValue();
                        String key = null;
                        for (Map.Entry mapentry : jsonObject.entrySet()) {
                            key=(String) mapentry.getKey();
                        }
                        uid_is.onCallback(key);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }








    public void upgrade_my_score_day(String my_id,double gain,String country){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Day_gain").child(my_id);

        final gain_day[] gain_day = new gain_day[1];
        int day_of_year=new aide().get_day_of_year();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    gain_day[0]=dataSnapshot.getValue(gain_day.class);
                    if(day_of_year==gain_day[0].getDay()){
                        double new_value=gain_day[0].getGain()+gain;
                        databaseReference.child("gain").setValue(new_value);
                    }else {
                        gain_day[0] =new gain_day(country,gain,day_of_year);
                        databaseReference.setValue(gain_day[0]);
                    }
                }
                else{
                    gain_day[0] =new gain_day(country,gain,day_of_year);
                    databaseReference.setValue(gain_day[0]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void retrive_gain_day_of_country(String country_code,game_gain_list gain_list){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Day_gain");

        String m=country_code+"-"+new aide().get_day_of_year();

        Query query = databaseReference.orderByChild("country_day").equalTo(m);

        ArrayList<gain_day> list=new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    HashMap<String, HashMap> jsonObject= (HashMap<String, HashMap>) snapshot.getValue();
                    for (Map.Entry mapentry : jsonObject.entrySet()) {
                        String key=(String) mapentry.getKey();
                        HashMap<String, Object> ob= (HashMap<String, Object>) mapentry.getValue();

                        String[] country_day= new aide().country_day_converter((String) ob.get("country_day"));
                        gain_day f= new gain_day(country_day[0],((Number)ob.get("gain")).doubleValue(),Integer.parseInt(country_day[1]));
                        f.setId(key);
                        list.add(f);
                    }
                    gain_list.onCall_back(list);
                }else
                    gain_list.onCall_back(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                gain_list.onCall_back(list);
            }
        });


    }

    public void retrive_gain_day_total(game_gain_list gain_list){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Day_gain");

        Query query = databaseReference.orderByChild("day").equalTo(new aide().get_day_of_year());

        ArrayList<gain_day> list=new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    HashMap<String, HashMap> jsonObject = (HashMap<String, HashMap>) snapshot.getValue();
                    for (Map.Entry mapentry : jsonObject.entrySet()) {
                        String key = (String) mapentry.getKey();
                        HashMap<String, Object> ob = (HashMap<String, Object>) mapentry.getValue();

                        String[] country_day = new aide().country_day_converter((String) ob.get("country_day"));
                        gain_day f = new gain_day(country_day[0], ((Number) ob.get("gain")).doubleValue(), Integer.parseInt(country_day[1]));
                        f.setId(key);
                        list.add(f);
                    }
                    gain_list.onCall_back(list);
                }else
                    gain_list.onCall_back(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                gain_list.onCall_back(list);
            }
        });
    }



    public void retrive_top_score(String space,game_gain_list gain_list){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Top_score");
        DatabaseReference databaseReference_1= databaseReference.child("world");
        DatabaseReference databaseReference_2= databaseReference.child("country");

        ArrayList<gain_day> lists=new ArrayList<>();
        if(space.equals("world")){
                databaseReference_1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                lists.add(new gain_day(snapshot.getKey(), ((Number) snapshot.getValue()).doubleValue()));
                            }
                            gain_list.onCall_back(lists);
                        }else
                            gain_list.onCall_back(lists);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }else {
                databaseReference_2.child(space).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                lists.add(new gain_day(snapshot.getKey(), ((Number) snapshot.getValue()).doubleValue()));
                            }
                            gain_list.onCall_back(lists);
                        }else
                            gain_list.onCall_back(lists);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        }
    }



    public void initial_challenge(String my_id,String friend_id,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("challenges").child("user_id"+my_id)
                .child("from_"+friend_id).setValue("nothings").addOnSuccessListener(aVoid -> FirebaseDatabase.getInstance().getReference()
                        .child("challenges").child("user_id"+friend_id)
                        .child("from_"+my_id).setValue("nothings").addOnSuccessListener(aVoid1 -> add_succeful.add_complet(true)));
    }

    public void up_challenge(String my_id,String friend_id,int amount,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("challenges").child("user_id"+my_id)
                .child("from_"+friend_id).setValue("wait").addOnSuccessListener(aVoid -> FirebaseDatabase.getInstance().getReference()
                        .child("challenges").child("user_id"+friend_id)
                        .child("from_"+my_id).setValue("request "+amount).addOnSuccessListener(aVoid1 -> add_succeful.add_complet(true)));
    }


    public void accept_challenge(String my_id,String friend_id,int amount,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference()
                .child("challenges").child("user_id"+friend_id)
                .child("from_"+my_id).setValue("ok "+amount).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }




    public void listen_to_this_friend(String my_id,String friend_id,listener listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("challenges").child("user_id"+my_id)
                .child("from_"+friend_id);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.get_state((String)snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        listener.get_listener(valueEventListener);
        listener.get_references(databaseReference);
    }

    public void Start_match(String player_1,String player_2,int amount,add_succeful add_succeful){
        HashMap<String,Object> data=new HashMap<>();
        data.put("player_1","come");
        data.put("player_2","non");
        data.put("winner",-1);
        data.put("amount_to_play",amount);
        data.put("msg1","");
        data.put("msg2","");
        match_running match=new match_running();
        data.put("player1",match);
        data.put("player2",match);

        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .setValue(data).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }

    public void player_state_after_match(String player_1,String player_2,String player_child,String state,add_succeful add_succeful){

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2);
        match_running match=new match_running();
        databaseReference.child(player_child).setValue(state).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
        if(player_child.equals("player_1")){
            databaseReference.child("msg1").setValue("");
            databaseReference.child("player1").setValue(match);
        }else {
            databaseReference.child("msg2").setValue("");
            databaseReference.child("player2").setValue(match);
        }
        databaseReference.child("winner").setValue(-1);
    }

    public void player_come_match(String player_1,String player_2,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child("player_2").setValue("come").addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }

    public void update_win(String player_1,String player_2,int win,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child("winner").setValue(win).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }


    public void send_msg(String player_1,String player_2,String to,Object msg,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child(to).setValue(msg).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }

    public void listen_to_message(String player_1,String player_2,String from,listener2 listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child(from);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //HashMap<String,String> data= (HashMap<String, String>) snapshot.getValue();

                //listener.get_selected_piece(Integer.parseInt(data.get("piece_to_play")));
                try {
                    String m=snapshot.getValue(String.class);
                    listener.get_state(m);
                }catch (Exception e){
                    audio_massage audioMassage=snapshot.getValue(audio_massage.class);
                    listener.get_state(audioMassage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);
        listener.get_listener(valueEventListener);
        listener.get_references(databaseReference);
    }

    public void listen_to_this_match(String player_1,String player_2,listener_match_before_start listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                @SuppressWarnings("unchecked")
                HashMap<String,String> data= (HashMap<String, String>) snapshot.getValue();

                listener.get_player1(data.get("player_1"));
                listener.get_player2(data.get("player_2"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);
        listener.get_listener(valueEventListener);
        listener.get_references(databaseReference);
    }


   /* public void Start_running(String player_1,String player_2,int amount,add_succeful add_succeful){
        match_running match=new match_running();
        match.setAmount_to_play(amount);
        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child("player1").setValue(match).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                        .child("player2").setValue(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        add_succeful.add_complet(true);
                    }
                });
            }
        });
    }
    */



    public void player_game(String player_1,String player_2,String player,match_running match_running,add_succeful add_succeful) {
        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child(player).setValue(match_running).addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }

    public void listen_to_game(String player_1,String player_2,String player,listener_game listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child(player);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //HashMap<String,String> data= (HashMap<String, String>) snapshot.getValue();

                //listener.get_selected_piece(Integer.parseInt(data.get("piece_to_play")));
                listener.get_game(snapshot.getValue(match_running.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);
        listener.get_listener(valueEventListener);
        listener.get_references(databaseReference);
    }



    public void listen_to_winner(String player_1,String player_2,listener_winner listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .child("winner");

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //HashMap<String,String> data= (HashMap<String, String>) snapshot.getValue();

                //listener.get_selected_piece(Integer.parseInt(data.get("piece_to_play")));
                if (snapshot.getValue(Integer.class)!=-1)
                    listener.winner(snapshot.getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);
        listener.get_listener(valueEventListener);
        listener.get_references(databaseReference);
    }

    public void end_match(String player_1,String player_2,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference().child("Matches").child(player_1+"_and_"+player_2)
                .removeValue().addOnSuccessListener(aVoid -> add_succeful.add_complet(true));
    }


    public void connection_trigger(listener_trigger listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(".info/connected");

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("connected");
                } else {
                    System.out.println("not connected");
                }
                listener.connect(connected);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
                listener.connect(false);
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
        listener.get_listener(valueEventListener);
        listener.get_references(databaseReference);
    }

    public void get_actuel_pack(pack_is pack_is){
        FirebaseDatabase.getInstance().getReference().child("user_pack").child("generateur").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                   pack_is.onCallback(dataSnapshot.getValue(pack_generator.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void incrument_place_in_pack(pack_generator pack,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference().child("user_pack").child("generateur")
                .child("place").setValue(pack.getPlace()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                add_succeful.add_complet(true);
            }
        });
    }

    public void create_new_pack(pack_generator pack,add_succeful add_succeful){
        pack.setPlace(1);
        pack.setActuel_pack(pack.getActuel_pack()+1);
        FirebaseDatabase.getInstance().getReference().child("user_pack").child("generateur").setValue(pack).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                add_succeful.add_complet(true);
            }
        });
    }

    public void add_to_pack(pack_generator pack,String id,add_succeful add_succeful){
        FirebaseDatabase.getInstance().getReference().child("user_pack")
                .child("pack_number_"+pack.getActuel_pack()).child(pack.getPlace()+"")
                .setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                add_succeful.add_complet(true);
            }
        });
    }

    public void get_element_of_pack(int number_pack,key_user_recipe key_user_recipe){

        ArrayList<String> all_ID=new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("user_pack")
                .child("pack_number_"+number_pack).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    all_ID.add((String) snapshot.getValue());
                }
                key_user_recipe.on_callback(all_ID);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void put_audio(String p1,String p2,String msg, File filo,URL url){
       // Rewriting the example from the docs based on the answer given in Uploading MP3 file to Firebase Storage ends up being a video/mp3 file, it seems that a full working example of an audio upload would look something like this:

// File or Blob
        Uri uri = Uri.fromFile(filo);

// Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("audio/mpeg").build();

// Upload file and metadata to the path 'audio/audio.mp3'

        StorageReference ref=FirebaseStorage.getInstance().getReference()
                .child("matches").child(p1+"_and_"+p2).child(msg).child(new aide().getCurrentUTC().getTime()+"");
        UploadTask uploadTask = ref.putFile(uri, metadata);

// Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
              //  double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
              //  System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
             //   System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                   // Log.e("URL",downloadUri.toString());
                    url.onCallback(downloadUri.toString());
                } else {
                    url.onCallback(null);
                }
            }
        });
    }

    public void get_audio(Context context,String url,String player_msg,String name,File_ file){
       StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

       File mfile=get_local_file(context,player_msg+"_"+name);

       httpsReference.getFile(mfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                file.on_call_back(mfile);
                httpsReference.delete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                httpsReference.delete();
            }
        });
    }

    private File get_local_file(Context context,String name){
        ContextWrapper contextWrapper =new ContextWrapper(context);
        File mus=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(mus,name+".mp3");
        return file;
    }


    public interface time{
        void onCallback(long time);
    }
    public interface File_{
        void on_call_back(File file);
    }
    public interface URL{
        void onCallback(String url);
    }
    public interface add_succeful{
         void add_complet(boolean etat);
     }
     public interface user_exist{
         void exist(boolean etat,account r);
     }
    public interface token_is{
         void onCallback(String tocken);
    }
    public interface key_user_recipe{
         void on_callback(ArrayList<String> keys);
    }
    public interface get_friends{
         void on_callback(ArrayList<score> keys);
    }
    public interface pack_is{
        void onCallback(pack_generator pack);
    }

    public interface  listener{
         void get_listener(ValueEventListener listener);
         void get_references(DatabaseReference reference);
         void get_state(String state);
    }

    public interface  listener2{
        void get_listener(ValueEventListener listener);
        void get_references(DatabaseReference reference);
        void get_state(Object state);
    }

    public interface  listener_match_before_start{
        void get_listener(ValueEventListener listener);
        void get_references(DatabaseReference reference);
        void get_player1(String state);
        void get_player2(String state);
    }


    public interface  listener_game{
        void get_listener(ValueEventListener listener);
        void get_references(DatabaseReference reference);
        void get_game(match_running match_running);
    }

    public interface  listener_winner{
        void get_listener(ValueEventListener listener);
        void get_references(DatabaseReference reference);
        void winner(int state);
    }

    public interface  listener_trigger{
        void get_listener(ValueEventListener listener);
        void get_references(DatabaseReference reference);
        void connect(boolean i_connect);
    }




    public interface Random{
        void waiting_friend();
        void get_players(String f,String s);
        void listening_to_some_on(Query query,ChildEventListener listener,DatabaseReference ref_to_close);
    }

    public interface get_a_place{
        void onCallback(int place);
    }

    public interface user_listner{
        void waiting_(String her_id);
        void closing_();

    }

    public interface pushing_saver{
        void onCallBack();
    }

    public interface Uid_is{
        void onCallback(String tocken);
    }

    public interface game_gain_list{
        void onCall_back(ArrayList<gain_day> list);
    }
}
