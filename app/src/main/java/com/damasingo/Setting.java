package com.damasingo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.app.ShareCompat;
import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.damasingo.CLASS_UTIL.setting;

import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.REGLAGE.adapter_setting;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;

import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;

import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Setting extends Fragment {


    LoginButton fb_lo;
    TextView txt_theme,txt_bottom_bar,txt_language,txt_son,txt_vibrate,txt_chrono;
    SwitchMaterial switch_theme,switch_bottom_bar,switch_son,switch_vibreur,switch_chrono;
    ImageButton btn_language;
    Button btn_store,btn_share,btn_learn_more,btn_disconnect,btn_facebook,btn_google;
    sql_manager db;
    LottieAnimationView lottie;

    Intent st;

    ///objet_login_google//////////////////////
    private GoogleSignInClient mgoogleSignInClient;
    private FirebaseAuth mAuth;
    private final int RC_SIGN_IN=1;
    ////////////////////////


    CallbackManager mCallbackManager;
    Activity activity;
    Context context;
    sharedpreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_setting, container, false);

        get_Views(view);
        inicial_some_variable();
        get_Objet_setting();
        listener_to_switch();
        listner_to_button();


        if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
            btn_disconnect.setVisibility(View.INVISIBLE);
            btn_facebook.setVisibility(View.GONE);
            btn_google.setVisibility(View.GONE);
        }
        //init_revie_info();



        return view;
    }



    private void listner_to_button() {

        btn_store.setOnClickListener(v -> {
            final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });

        btn_share.setOnClickListener(v -> {

            String share=getString(R.string.share_text);
            share +="\n\n";
            share+=getString(R.string.Lien_text);
            share+="\n\n";
            share+="https://play.google.com/store/apps/details?id=" + context.getPackageName();

            ShareCompat.IntentBuilder
                    .from(activity)
                    .setText(share)
                    .setType("text/plain")
                    .setChooserTitle(R.string.app_name)
                    .startChooser();
        });

        btn_language.setOnClickListener(this::showMenu_Langue);

        btn_disconnect.setOnClickListener(v -> deconnecter());

        btn_learn_more.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), activity_more_info.class);
            startActivity(intent);
            activity.overridePendingTransition(R.anim.entrer, R.anim.exit);
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        btn_facebook.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.con);
            builder.setMessage(getString(R.string.i_accept));
            builder.setPositiveButton(R.string.i, (dialog, which) -> fb_lo.performClick());
            builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
//
            AlertDialog dialog = builder.create();
//
            dialog.show();
        });
        fb_lo.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(getActivity(),
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
                    Toast.makeText(getContext(), getResources().getString(R.string.try_again)+"", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("FB", "facebook:onError", error);
                    hide("error");
                    Toast.makeText(getContext(), error.toString()+"", Toast.LENGTH_SHORT).show();
                }
            });


        });
        fb_lo.setFragment(this);

        btn_google.setOnClickListener(v -> signIn());
    }


    connect_to_firebase db_online=new connect_to_firebase();
    sql_manager db_offline;

    private void deconnecter() {



     /*   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.log_out);
        builder.setMessage(R.string.log_out_q);
        builder.setPositiveButton(R.string.yes_log_out, (dialog, which) -> {
            // FirebaseAuth.getInstance().signOut();

//
            hide("wait");

//
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient mgoogleSignInClient;
            mgoogleSignInClient = GoogleSignIn.getClient(context,gso);
//
            boolean a=true;
            FirebaseUser f=FirebaseAuth.getInstance().getCurrentUser();
            assert f != null;
            for (UserInfo user: f.getProviderData()) {
                if (user.getProviderId().equals("facebook.com")) {
                    //For linked facebook account
                    db_online.distroy_Tocken(db_offline.get__account().getId(), new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {
                            FirebaseAuth.getInstance().signOut();
                            disconnectFromFacebook();
                        }
                    });


                    a=false;
                } else if (user.getProviderId().equals("google.com")) {
                    //For linked Google account
                    db_online.distroy_Tocken(db_offline.get__account().getId(), new connect_to_firebase.add_succeful() {
                        @Override
                        public void add_complet(boolean etat) {
                            FirebaseAuth.getInstance().signOut();
                            mgoogleSignInClient.signOut().addOnSuccessListener(aVoid -> {
//
                                new connect_to_firebase().desincrument_online(getContext());
                                new sql_manager(getContext()).clear();
                                activity.finish();
                                startActivity(new Intent(getActivity(),sign_in.class));
                            });

                        }
                    });
                    a=false;
                }
            }

            if(a){
                db_online.distroy_Tocken(db_offline.get__account().getId(), new connect_to_firebase.add_succeful() {
                    @Override
                    public void add_complet(boolean etat) {
                        new connect_to_firebase().desincrument_online(getContext());
                        FirebaseAuth.getInstance().signOut();
                        new sql_manager(getContext()).clear();
                        activity.finish();
                        startActivity(new Intent(getActivity(),sign_in.class));
                    }
                });

            }

        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
//
        AlertDialog dialog = builder.create();
//
        dialog.show();

      */

        new Msg_box().oui_non_alert(context.getResources().getString(R.string.log_out)
                ,context.getResources().getString(R.string.log_out_q),
                context.getResources().getString(R.string.yes_log_out),
                context.getResources().getString(R.string.no),
                context, new Msg_box.go_back() {
                    @Override
                    public void onCallBack(int action) {
                        if(action==0){
                            hide("wait");

//
                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.default_web_client_id))
                                    .requestEmail()
                                    .build();
                            GoogleSignInClient mgoogleSignInClient;
                            mgoogleSignInClient = GoogleSignIn.getClient(context,gso);
//
                            boolean a=true;
                            FirebaseUser f=FirebaseAuth.getInstance().getCurrentUser();
                            assert f != null;
                            for (UserInfo user: f.getProviderData()) {
                                if (user.getProviderId().equals("facebook.com")) {
                                    //For linked facebook account
                                    db_online.distroy_Tocken(db_offline.get__account().getId(), new connect_to_firebase.add_succeful() {
                                        @Override
                                        public void add_complet(boolean etat) {
                                            FirebaseAuth.getInstance().signOut();
                                            disconnectFromFacebook();
                                        }
                                    });


                                    a=false;
                                } else if (user.getProviderId().equals("google.com")) {
                                    //For linked Google account
                                    db_online.distroy_Tocken(db_offline.get__account().getId(), new connect_to_firebase.add_succeful() {
                                        @Override
                                        public void add_complet(boolean etat) {
                                            FirebaseAuth.getInstance().signOut();
                                            mgoogleSignInClient.signOut().addOnSuccessListener(aVoid -> {
//
                                                new connect_to_firebase().desincrument_online(getContext());
                                                new sql_manager(getContext()).clear();
                                                activity.finish();
                                                startActivity(new Intent(getActivity(),sign_in.class));
                                            });

                                        }
                                    });
                                    a=false;
                                }
                            }

                            if(a){
                                db_online.distroy_Tocken(db_offline.get__account().getId(), new connect_to_firebase.add_succeful() {
                                    @Override
                                    public void add_complet(boolean etat) {
                                        new connect_to_firebase().desincrument_online(getContext());
                                        FirebaseAuth.getInstance().signOut();
                                        new sql_manager(getContext()).clear();
                                        activity.finish();
                                        startActivity(new Intent(getActivity(),sign_in.class));
                                    }
                                });

                            }
                        }
                    }
                });
    }

    public void  disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, graphResponse -> {

            new connect_to_firebase().desincrument_online(getContext());
            new sql_manager(getContext()).clear();
            activity.finish();
            startActivity(new Intent(getActivity(),sign_in.class));

        }).executeAsync();
    }

    private void inicial_some_variable() {
        db=new sql_manager(getContext());
    }

    private void listener_to_switch() {
        final Praincipal praincipal_activity=(Praincipal) getActivity();



        switch_bottom_bar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.performClick();
            buttonView.setOnClickListener(v -> {
                if(isChecked) {
                    db.Update_data_setting("5", "مفعل");
                    txt_bottom_bar.setText(R.string.activated);
                }
                else{
                    db.Update_data_setting("5","غير مفعل");
                    txt_bottom_bar.setText(R.string.not_activated);
                }
                new adapter_setting().Control_navigate_bottom((AppCompatActivity) getActivity());
                assert praincipal_activity != null;
                praincipal_activity.recreate();
            });
        });




        switch_theme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.performClick();
            buttonView.setOnClickListener(v -> {
                if(isChecked) {
                    db.Update_data_setting("1","وضع ليلي");
                    txt_theme.setText(R.string.night);
                }
                else{
                    db.Update_data_setting("1","وضع نهاري");
                    txt_theme.setText(R.string.light);
                }
                new adapter_setting().set_setting_theme(getContext());
            });
        });

        switch_son.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                db.Update_data_setting("6", "مفعل");
                txt_son.setText(R.string.activated);
            }
            else{
                db.Update_data_setting("6","غير مفعل");
                txt_son.setText(R.string.not_activated);
            }
        });

        switch_vibreur.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                db.Update_data_setting("7", "مفعل");
                txt_vibrate.setText(R.string.activated);
            }
            else{
                db.Update_data_setting("7","غير مفعل");
                txt_vibrate.setText(R.string.not_activated);
            }
        });

        switch_chrono.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                sharedpreferences.put_setting_type("chrono",true);
                txt_chrono.setText(R.string.virc);
            }
            else{
                sharedpreferences.put_setting_type("chrono",false);
                txt_chrono.setText(R.string.text);
            }
        });
    }

    private void get_Views(View view){

        activity=getActivity();
        context=getContext();
        db_offline=new sql_manager(getContext());
        sharedpreferences=new sharedpreferences(context);

        st=activity.getIntent();
        lottie=view.findViewById(R.id.lottie_download);
        txt_theme=view.findViewById(R.id.theme_text_P);
        txt_bottom_bar=view.findViewById(R.id.bottom_bar_text_P);
        txt_language=view.findViewById(R.id.language_text_P);
        switch_theme=view.findViewById(R.id.Switch_day_or_night);
        switch_bottom_bar=view.findViewById(R.id.Switch_bottom_bar);
        btn_language=view.findViewById(R.id.spinner_language);
        btn_store=view.findViewById(R.id.btn_rate);
        btn_share=view.findViewById(R.id.btn_share_us);
        btn_disconnect=view.findViewById(R.id.btn_deconexion);
        btn_learn_more=view.findViewById(R.id.btn_more_info);
        switch_son=view.findViewById(R.id.Switch_soon);
        switch_vibreur=view.findViewById(R.id.Switch_viibreur);
        txt_son=view.findViewById(R.id.soon_text_P);
        txt_vibrate=view.findViewById(R.id.viibreur_text_P);
        btn_facebook=view.findViewById(R.id.fb_log);
        btn_google=view.findViewById(R.id.google_log);
        fb_lo=view.findViewById(R.id.b);
        switch_chrono=view.findViewById(R.id.Switch_chrono);
        txt_chrono=view.findViewById(R.id.chrono_text_P);

        FirebaseUser f=FirebaseAuth.getInstance().getCurrentUser();

        if(f!=null)
            for (UserInfo user : f.getProviderData()) {
                if (user.getProviderId().equals("google.com") || user.getProviderId().equals("facebook.com")) {
                    btn_facebook.setVisibility(View.GONE);
                    btn_google.setVisibility(View.GONE);
                }
            }


        mAuth = FirebaseAuth.getInstance();


        if(!new sandbar_info().isNavigationBarAvailable()){
            switch_bottom_bar.setChecked(true);
            switch_bottom_bar.setEnabled(false);
        }

    }

    public void get_Objet_setting(){

        sql_manager db=new sql_manager(getContext());
        ArrayList<setting> listo=db.get_all_setting();


        if(sharedpreferences.get_setting_type("chrono")){
            switch_chrono.setChecked(true);
            txt_chrono.setText(R.string.virc);
        }else {
            switch_chrono.setChecked(false);
            txt_chrono.setText(R.string.text);
        }


        int a=0;
        for(setting so:listo){

            switch (a){
                case 0:
                    //  G_text1.setText(new adapter_setting().adapter_number(res.getString(1),getContext()));

                    switch (so.getChoice()){
                        case "وضع ليلي":
                            switch_theme.setChecked(true);
                            txt_theme.setText(R.string.night);
                            break;
                        case "وضع نهاري":
                            switch_theme.setChecked(false);
                            txt_theme.setText(R.string.light);
                            break;
                    }
                    break;
                case 1:
                    //   G_text2.setText(new adapter_setting().adapter_number(res.getString(1),getContext()));
                    //   P_text2.setText(new adapter_setting().adapter_number(res.getString(2),getContext()));
                    //   switch (res.getString(2)){
                    //       case "عربي":
                    //           switch2.setChecked(true);
                    //           break;
                    //       case "عجمي":
                    //           switch2.setChecked(false);
                    //           break;
                    //   }
                    break;
                case 2:
                    //G_text3.setText(new adapter_setting().adapter_number(res.getString(1),getContext()));
                    //P_text3.setText(new adapter_setting().adapter_number(res.getString(2),getContext()));

                    switch (so.getChoice()){
                        case"en":
                            txt_language.setText(R.string.english);
                            btn_language.setImageResource(R.drawable.english_logo);
                            break;
                        case"fr":
                            txt_language.setText(R.string.french);
                            btn_language.setImageResource(R.drawable.french_logo);
                            break;
                        case"es":
                            txt_language.setText(R.string.spanish);
                            btn_language.setImageResource(R.drawable.spain_logo);
                            break;
                        case"ar":
                            txt_language.setText(R.string.arabic);
                            btn_language.setImageResource(R.drawable.arabe_logo);
                            break;
                        case"hi":
                            txt_language.setText(R.string.hindi);
                            btn_language.setImageResource(R.drawable.hindi_logo);
                            break;
                        case"ja":
                            txt_language.setText(R.string.japanese);
                            btn_language.setImageResource(R.drawable.japon_logo);
                            break;
                        case"ru":
                            txt_language.setText(R.string.russian);
                            btn_language.setImageResource(R.drawable.ressu_logo);
                            break;
                        default:
                            txt_language.setText(R.string.def);
                            btn_language.setImageResource(R.drawable.language);
                            break;
                    }

                    break;
                case 3:
                    // G_text2.setText(new adapter_setting().adapter_number(res.getString(1));
                    // p_text_notif.setText(new adapter_setting().adapter_number(res.getString(2),getContext()));
                    // switch (res.getString(2)){
                    //     case "مفعلة":
                    //         switch_notif.setChecked(true);
                    //         break;
                    //     case "غير مفعلة":
                    //         switch_notif.setChecked(false);
                    //         break;
                    // }
                    break;
                case 4:
                    // G_text2.setText(new adapter_setting().adapter_number(res.getString(1));
                    switch (so.getChoice()){
                        case "مفعل":
                            switch_bottom_bar.setChecked(true);
                            txt_bottom_bar.setText(R.string.activated);
                            break;
                        case "غير مفعل":
                            switch_bottom_bar.setChecked(false);
                            txt_bottom_bar.setText(R.string.not_activated);
                            break;
                    }
                    break;
                case 5:
                    switch (so.getChoice()){
                        case "مفعل":
                            switch_son.setChecked(true);
                            txt_son.setText(R.string.activated);
                            break;
                        case "غير مفعل":
                            switch_son.setChecked(false);
                            txt_son.setText(R.string.not_activated);
                            break;
                    }
                    break;
                case 6:
                    switch (so.getChoice()){
                        case "مفعل":
                            switch_vibreur.setChecked(true);
                            txt_vibrate.setText(R.string.activated);
                            break;
                        case "غير مفعل":
                            switch_vibreur.setChecked(false);
                            txt_vibrate.setText(R.string.not_activated);
                            break;
                    }
                    break;
            }
            a++;
        }
    }

    @SuppressLint("RestrictedApi")
    private void showMenu_Langue(View view){

        MenuBuilder menuBuilder =new MenuBuilder(context);
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.langue, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
        optionsMenu.setForceShowIcon(true);
// Set Item Click Listener
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.english: // Handle option1 Click
                        setLocale(activity,"en");
                        restart_activity();
                        return true;
                    case R.id.french: // Handle option2 Click
                        setLocale(activity,"fr");
                        restart_activity();
                        return true;
                    case R.id.spanish: // Handle option1 Click
                        setLocale(activity,"es");
                        restart_activity();
                        return true;
                    case R.id.arab: // Handle option2 Click
                        setLocale(activity,"ar");
                        restart_activity();
                        return true;
                    case R.id.hindi: // Handle option2 Click
                        setLocale(activity,"hi");
                        restart_activity();
                        return true;
                    case R.id.japon: // Handle option2 Click
                        setLocale(activity,"ja");
                        restart_activity();
                        return true;
                    case R.id.russu: // Handle option2 Click
                        setLocale(activity,"ru");
                        restart_activity();
                        return true;
                    case R.id.default_l: // Handle option2 Click
                        String m=ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0).getLanguage();
                        db.Update_data_setting("3",m);
                        restart_activity();
                        return true;
                    default:
                        return false;
                }
            }

            private void restart_activity() {

                Intent intent=new Intent(getActivity(),Praincipal.class);
                intent.putExtra("fragment_name",Setting.class.getName());
                activity.finish();
                //startActivity(st);


                startActivity(intent);


            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {}
        });


        optionsMenu.show();
    }

    public void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        db.Update_data_setting("3",languageCode);
    }




    ///objet_login_google//////////////////////
    public void signIn() {
        ///objet_login_google//////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogleSignInClient = GoogleSignIn.getClient(context,gso);


        ////////////////////////////////

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
           // sign_in.setClickable(false);
            firebaseAuthWithGoogle(account);

            // UpdateToken();
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            hide("error");
            Log.e("error",""+e.toString());
            Toast.makeText(getContext(), getResources().getString(R.string.try_again)+"", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        // Log.e("account",account.getEmail());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseUser f=mAuth.getCurrentUser();
        assert f != null;
        f.linkWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        hide("ok");
                        Toast.makeText(getContext(), "linked successful", Toast.LENGTH_SHORT).show();
                    } else {
                        hide("error");
                        mgoogleSignInClient.signOut().addOnSuccessListener(aVoid -> {
                        });
                        Toast.makeText(getContext(), "we can't linked to this account", Toast.LENGTH_SHORT).show();
                    }
                });


    }





    //////////// Object Login Facebook /////////////
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FB", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseUser f=mAuth.getCurrentUser();
        assert f != null;
        f.linkWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        hide("ok");
                        Toast.makeText(getContext(), "linked successful", Toast.LENGTH_SHORT).show();
                    } else {
                        hide("error");
                        if (AccessToken.getCurrentAccessToken() == null) {
                            return; // already logged out
                        }
                        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, graphResponse -> {

                        }).executeAsync();
                        Toast.makeText(getContext(), "we can't linked to this account", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void hide(String etat){
        activity.runOnUiThread(() -> {
            switch(etat)
            {
                case "wait":
                    btn_facebook.setVisibility(View.INVISIBLE);
                    btn_google.setVisibility(View.INVISIBLE);
                    btn_disconnect.setVisibility(View.INVISIBLE);
                    lottie.setVisibility(View.VISIBLE);
                    break;
                case "ok":
                    btn_facebook.setVisibility(View.GONE);
                    btn_google.setVisibility(View.GONE);
                    btn_disconnect.setVisibility(View.VISIBLE);
                    lottie.setVisibility(View.GONE);
                    break;
                case "error":
                    btn_facebook.setVisibility(View.VISIBLE);
                    btn_google.setVisibility(View.VISIBLE);
                    btn_disconnect.setVisibility(View.VISIBLE);
                    lottie.setVisibility(View.INVISIBLE);
                    break;
            }
        });
    }


}