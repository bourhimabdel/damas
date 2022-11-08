package com.damasingo;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;

import com.bumptech.glide.request.RequestListener;

import com.bumptech.glide.request.target.Target;

import com.damasingo.CLASS_UTIL.Support_save_data;
import com.damasingo.CLASS_UTIL.account;

import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.pack_generator;
import com.damasingo.CLASS_UTIL.user_info;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.REGLAGE.adapter_setting;

import com.damasingo.SQL_MANAGER.sql_manager;

import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;


import pl.pawelkleczkowski.customgauge.CustomGauge;

public class sign_in extends AppCompatActivity {


    int multi=10000;
    Button fb_LOG,google_LOG,anonymous_LOG,go_back;
    TextView sign_in;
    CallbackManager mCallbackManager;
    ImageButton btn_info;

    public FirebaseAuth mAuth_f;

    LottieAnimationView lottie_download,lottie_wait;
    CustomGauge progress_download;
    TextView t1,t2;

    ///objet_login_google//////////////////////
    private GoogleSignInClient mgoogleSignInClient;
    private FirebaseAuth mAuth;
    private final int RC_SIGN_IN=1;
    ////////////////////////

    public connect_to_firebase db_online =new connect_to_firebase();
    public sql_manager db_offline;
    private Support_save_data support_save_data;

    sandbar_info snack_bar=new sandbar_info();
    public boolean snack_showed=false;
    boolean start_load=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new adapter_setting().set_setting_theme(this);
        setContentView(R.layout.activity_sign_in);

        //hide_bottom_bar();
        inicial_views();
        click_listner();



    }



    private void inicial_views() {

        db_offline=new sql_manager(this);
        support_save_data=new Support_save_data(this);
        ///objet_login_google//////////////////////
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);

        ////////////////////////////////


        FacebookSdk.sdkInitialize(getApplicationContext());
        mAuth_f = FirebaseAuth.getInstance();



        anonymous_LOG=findViewById(R.id.anynomse_log);
        sign_in=findViewById(R.id.sign);
        fb_LOG=findViewById(R.id.fb_log);
        google_LOG=findViewById(R.id.google_log);
        lottie_download = findViewById(R.id.lottie_download);
        lottie_wait = findViewById(R.id.lottie_wait);
        progress_download = findViewById(R.id.progressbar);
        t1= findViewById(R.id.pourcentage);
        t2= findViewById(R.id.pourcent);
        btn_info=findViewById(R.id.btn_info);
        go_back=findViewById(R.id.btn_go_back);
    }

    private void click_listner() {
        google_LOG.setOnClickListener(v -> signIn());

        anonymous_LOG.setOnClickListener(v -> {

            hide("wait");
            anonymous_log();
        });

        mCallbackManager = CallbackManager.Factory.create();
        fb_LOG.setOnClickListener(v -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.con);
            builder.setMessage(getString(R.string.i_accept));
            builder.setPositiveButton(R.string.i, (dialog, which) -> {
                LoginManager.getInstance().logInWithReadPermissions(sign_in.this,
                        Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FB", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("FB", "facebook:onCancel");
                        hide("error");
                        Toast.makeText(sign_in.this, getResources().getString(R.string.try_again)+"", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FB", "facebook:onError", error);
                        hide("error");
                        Toast.makeText(sign_in.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
//
            AlertDialog dialog = builder.create();
//
            dialog.show();



        });

        btn_info.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(1);

           // new Msg_box().get_info("info",nums,sign_in.this,sign_in.this);
            snack_bar.snack_bar_reglage("info",nums,sign_in.this,sign_in.this);
            snack_showed=true;
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onBackPressed();
            }
        });
    }

    //////////// Object Login Facebook /////////////
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FB", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        hide("ok");
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FB", "signInWithCredential:success");
                        UpdateToken();
                    } else {
                        hide("error");
                        // If sign in fails, display a message to the user.
                        Log.w("FB", "signInWithCredential:failure", task.getException());
                        Toast.makeText(sign_in.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }





    ///objet_login_google//////////////////////
    public void signIn() {
        Intent signInIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        hide("wait");
        if (requestCode == RC_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInresult(task);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInresult(Task<GoogleSignInAccount> task) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            // sign_in.setText("يتم تسجيل الدخول ...");
            sign_in.setClickable(false);
            firebaseAuthWithGoogle(account);

            // UpdateToken();
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            hide("error");
            Log.e("error",""+e.toString());
            Toast.makeText(this, getResources().getString(R.string.try_again)+"", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        // Log.e("account",account.getEmail());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        hide("ok");
                        UpdateToken();

                        //   startActivity(new Intent(signin.this,MainActivity.class));
                    } else {
                        hide("error");
                        Exception e=task.getException();
                        assert e != null;
                        Log.e("error",e.toString());
                    }
                });
    }



    private void anonymous_log(){
        Task<AuthResult> resultTask=mAuth.signInAnonymously();
        resultTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                hide("ok");
                UpdateToken();

                //   startActivity(new Intent(signin.this,MainActivity.class));
            } else {
                hide("error");
                Exception e=task.getException();
                assert e != null;
                Log.e("error",e.toString());
            }
        });
    }



    @SuppressLint("DefaultLocale")
    private void UpdateToken(){

        start_load=true;
        progress_download.setEndValue(5*multi);
        progress_download.setVisibility(View.VISIBLE);
        t1.setVisibility(View.VISIBLE);
        t1.setText(String.format("%02d",0));
        t2.setVisibility(View.VISIBLE);

        refrechFCMtoken(() -> {
            t1.setText(String.format("%02d",20));
            progress_aniation(1, 2, () -> {
                t1.setText(String.format("%02d",40));
                 account account = new user_info().get_info_account();
                new connect_to_firebase().user_exist(account.getId(), (etat, r) -> {
                    if(etat)
                    {
                        r.setCountry(new aide().get_my_country(this));
                        db_online.update_country(r.getId(),new aide().get_my_country(this));

                        db_online.get_all_friends(r.getId(), keys0 -> {
                            support_save_data.add_this_collect_to_friends(keys0, () -> {

                            });

                            db_online.get_all_request_receive(r.getId(), keys -> {

                                support_save_data.add_this_collect_to_receive(keys, () -> {

                                });

                                db_online.get_all_request_sent(r.getId(), keys2 -> {
                                    support_save_data.add_this_collect_to_sent(keys2, () -> {

                                    });

                                    remplire_Image(bitmap -> complete_load_image_work(true,sign_in.this,r,bitmap),r);

                                });
                            });
                        });


                    }
                    else{
                        account.setPoint_experience(0);
                        account.setLevel(0);
                        account.setMoney(750);
                        account.setVictoir_gain(0);
                        account.setVictoir_loss(0);
                        account.setTotal_money_win(0);
                        account.setCountry(new aide().get_my_country(this));


                       // add_user_to_pack(account);
                        //remplire_Image(false,sign_in.this,account);
                        remplire_Image(bitmap -> complete_load_image_work(false,sign_in.this,account,bitmap),account);
                    }
                });

            });
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new adapter_setting().Control_navigate_bottom(this);
        }
    }

    public void progress_aniation(int from, int to, final endanimation endanimation){
        ObjectAnimator animation = ObjectAnimator.ofInt(progress_download, "Value", from*multi,to*multi); // see this max value coming back here, we animate towards that value
        animation.setDuration(500); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());

         animation.start();

         Handler handler=new Handler();
         handler.postDelayed(endanimation::oncallback, 200);
    }

    public void remplire_Image(image_ready image_ready,account account){
        Log.e("image is","gg "+account.getImage());
        Glide.with(this)
                .load(account.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        remplire_Image(image_ready, account);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        image_ready.onCallback(bb);
                        return false;
                    }
                }).submit();

    }

    @SuppressLint("DefaultLocale")
    private void complete_load_image_work(final boolean etat, final Context context, final account account, Drawable bb){
        BitmapDrawable d=(BitmapDrawable)bb;
        Bitmap resource=d.getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        final byte[] b = baos.toByteArray();
        account.setPhoto_saved(b);


        if (etat){
            runOnUiThread(() -> {
                sign_in.setText(R.string.login_success);
                t1.setText(R.string._100);
                progress_aniation(2, 5, () -> {

                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    sql_manager db = new sql_manager(context);
                    //   db.insert_data_into_account(account.getAccount_ID(),account.getName(),account.getBio(),b, account.getNumber_recipe(),account.getImage());
                    //Toast.makeText(signin.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();

                    db.insert_data_into_account(account);
                    lottie_download.setVisibility(View.VISIBLE);
                    lottie_download.playAnimation();
                    progress_download.setVisibility(View.INVISIBLE);
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        SharedPreferences data_loaded =  getSharedPreferences("data_loaded", MODE_PRIVATE);
                        SharedPreferences.Editor editor_low = data_loaded.edit();
                        editor_low.putString("data", "YES");
                        editor_low.apply();

                        startActivity(new Intent(sign_in.this,Praincipal.class));
                        new sharedpreferences(sign_in.this).
                                put_setting_type("user_is_not_sign_in",false);
                        finish();
                    }, 2000);

                });
            });

            // }



        }else{
            runOnUiThread(() -> progress_aniation(2, 3, () -> {
                t1.setText(String.format("%02d",60));
                account.setPhoto_saved(null);
                new connect_to_firebase().add_new_user(account, etat1 -> {
                    sign_in.setText(R.string.login_success);
                    progress_aniation(3, 5, () -> {
                        t1.setText(String.format("%02d",100));
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        sql_manager db = new sql_manager(context);


                        account.setPhoto_saved(b);
                        db.insert_data_into_account(account);
                        // Toast.makeText(signin.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                        lottie_download.setVisibility(View.VISIBLE);
                        lottie_download.playAnimation();
                        progress_download.setVisibility(View.INVISIBLE);
                        Handler handler=new Handler();
                        handler.postDelayed(() -> {
                            SharedPreferences data_loaded =  getSharedPreferences("data_loaded", MODE_PRIVATE);
                            SharedPreferences.Editor editor_low = data_loaded.edit();
                            editor_low.putString("data", "YES");
                            editor_low.apply();
                            startActivity(new Intent(sign_in.this,Praincipal.class));
                            new sharedpreferences(sign_in.this).
                                    put_setting_type("user_is_not_sign_in",false);
                            finish();
                        }, 2000);
                    });
                });
            }));


        }
    }

    interface endanimation {
        void oncallback();
    }

    public void hide(String etat){

        switch (etat) {
            case "wait":
                lottie_wait.setVisibility(View.VISIBLE);
                fb_LOG.setVisibility(View.INVISIBLE);
                google_LOG.setVisibility(View.INVISIBLE);
                anonymous_LOG.setVisibility(View.INVISIBLE);
                break;
            case "ok":
                sign_in.setVisibility(View.VISIBLE);
                lottie_wait.setVisibility(View.INVISIBLE);
                fb_LOG.setVisibility(View.INVISIBLE);
                google_LOG.setVisibility(View.INVISIBLE);
                anonymous_LOG.setVisibility(View.INVISIBLE);
                break;
            case "error":
                lottie_wait.setVisibility(View.INVISIBLE);
                fb_LOG.setVisibility(View.VISIBLE);
                google_LOG.setVisibility(View.VISIBLE);
                anonymous_LOG.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void refrechFCMtoken(refrech_complet refrech_complet) {

            final String[] token = {""};
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                try {
                if (task.isComplete()) {
                    token[0] = task.getResult();
                    Log.e("AppConstants", "onComplete: new Token got: " + token[0]);
                    String refreshToken = token[0];

                    FirebaseUser f = FirebaseAuth.getInstance().getCurrentUser();
                    assert f != null;
                    FirebaseDatabase.getInstance().getReference().child("Tokens")
                            .child("" + f.getUid())
                            .setValue(refreshToken).addOnSuccessListener(aVoid -> progress_aniation(0, 1, refrech_complet::complet));
                }
                }catch (Exception e){
                    FirebaseUser f = FirebaseAuth.getInstance().getCurrentUser();
                    assert f != null;
                    FirebaseDatabase.getInstance().getReference().child("Tokens")
                            .child("" + f.getUid())
                            .setValue("nothing").addOnSuccessListener(aVoid -> progress_aniation(0, 1, refrech_complet::complet));

                }
            });


    }


 /*   public void add_user_to_pack(account account){
        db_online.get_actuel_pack(new connect_to_firebase.pack_is() {
            @Override
            public void onCallback(pack_generator pack) {
                if(pack.getPlace()<5){
                    pack_generator b=new pack_generator(pack.getActuel_pack(),pack.getPlace()+1);
                    db_online.incrument_place_in_pack(b, new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {
                            db_online.add_to_pack(pack, account.getId(), new connect_to_firebase.add_succeful() {
                                @Override
                                public void add_complet(boolean etat) {

                                }
                            });
                        }
                    });
                }else {
                    db_online.create_new_pack(pack, new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {
                            pack_generator b=new pack_generator(pack.getActuel_pack()+1,2);
                            db_online.incrument_place_in_pack(b, new connect_to_firebase.add_succeful() {
                                @Override
                                public void add_complet(boolean etat) {
                                    db_online.add_to_pack(pack, account.getId(), new connect_to_firebase.add_succeful() {
                                        @Override
                                        public void add_complet(boolean etat) {

                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }


  */

    public interface refrech_complet{
        void complet();
    }

    public interface data_account{
        void on_callback(account account);
    }

    public interface image_ready{
        void onCallback(Drawable bitmap);
    }

    @Override
    public void onBackPressed() {
        if(!start_load) {
           // if (snack_showed)
           //     snack_bar.dimiss_snackbar_bar(this);
           // else
                startActivity(new Intent( this,Praincipal.class));
        }
    }
}