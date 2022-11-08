package com.damasingo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.ADAPTER.MainAdapter;
import com.damasingo.ADAPTER.MainAdapter_bluetooth;
import com.damasingo.CLASS_UTIL.CustomViewPage;
import com.damasingo.CLASS_UTIL.LockableNestedScrollView;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.damasingo.fragment_bluetooth.no_paired_devices;
import com.damasingo.fragment_bluetooth.paired_devices;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.fragment_play_online.reqeust_recive;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.local_match.local_game_space;
import com.damasingo.local_match.local_game_space_robot;
import com.damasingo.play_onlinge.score_fragment;
import com.damasingo.randomly.selecto_fragment;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

public class home extends Fragment {


    TabLayout tabLayout,tab_bluetooth;
    public CustomViewPage viewPager,view_pager_bluetooth;
    FrameLayout view_paget_randomly;

    public ImageView img_bluetooth,img_location;
    TextView txt_play_bluetooth;
    ImageButton btn_show_online,btn_show_bluetooth,btn_show_local,btn_show_online_,btn_info_online,btn_info_randomly
            ,btn_info_bluetooth,btn_info_lcal,btn_disco;
    Button btn_start_a_local_match,btn_start_a_local_match_robot,btn_copy,btn_add_fr;
    ConstraintLayout view_permission,online,bluetooth,local,randomly,a,b,c;
    public SwitchMaterial switch_bluetooth,switch_location;
    LottieAnimationView click;
    View v1,v2,v3,v4;

    public ArrayList<String> title=new ArrayList<>();
    public ArrayList<String> title_b=new ArrayList<>();
    public MainAdapter mainAdapter;
   // public adapter_capitale adapter_capitale;
    public MainAdapter_bluetooth main_adapter_bluetooth;
    sharedpreferences sharedpreferences;
    FrameLayout fram_online, fram_bluetooth;

    Context context;
    Activity activity;
    Praincipal m_mother_activty;
    public LockableNestedScrollView scroll_v;

    View mIndicator,mIndicator2;
    private int indicatorWidth,indicatorWidth2;

    

    ///////////////////////////////
    //  Bluetooth_element
    BluetoothAdapter bluetoothAdapter;
    RecyclerView list_pair,list_unpaired;
    ArrayList<BluetoothDevice> m_list_pair=new ArrayList<>(),m_list_unpaired=new ArrayList<>();

    sql_manager db_offline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        initial_view(view);

        add_title();
        up_tab();
        prepareViewPager(viewPager,title);
        Listner();
        detect_if_there_is_some_news();
        plain_view_pager_randomly();
        costum_view();
        anime();



        return view;
    }



    final int paddingPx = 120;
    final float MIN_SCALE = 0.8f;
    final float MAX_SCALE = 1f;
    private void plain_view_pager_randomly(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_pager_randomly,new selecto_fragment(), selecto_fragment.class.getName()).commit();

      /*  adapter_capitale=new adapter_capitale(getContext(),(AppCompatActivity) getActivity());
        view_paget_randomly.setAdapter(adapter_capitale);


       ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
           @Override
           public void transformPage(View page, float position) {
               float pagerWidthPx = ((ViewPager) page.getParent()).getWidth();
               float pageWidthPx = pagerWidthPx - 2 * paddingPx;

               float maxVisiblePages = pagerWidthPx / pageWidthPx;
               float center = maxVisiblePages / 2f;

               float scale;
               if (position + 0.5f < center - 0.5f || position > center) {
                   scale = MIN_SCALE;
               } else {
                   float coef;
                   if (position + 0.5f < center) {
                       coef = (position + 1 - center) / 0.5f;
                   } else {
                       coef = (center - position) / 0.5f;
                   }
                   scale = coef * (MAX_SCALE - MIN_SCALE) + MIN_SCALE;
               }
               page.setScaleX(scale);
               page.setScaleY(scale);

           }
       };


       view_paget_randomly.setClipToPadding(false);
       view_paget_randomly.setPadding(paddingPx, 0, paddingPx, 0);
       view_paget_randomly.setPageTransformer(false, transformer);
        // recyclerView.setPageTransformer(false, new DefaultTransformer());
        // viewPager.setPageTransformer(false, new DefaultTransformer());


       */

    }

    private void anime() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                anime_start(online);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v1.setVisibility(View.VISIBLE);
                        anime_start(randomly);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v2.setVisibility(View.VISIBLE);
                                anime_start(bluetooth);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        v3.setVisibility(View.VISIBLE);

                                        anime_start(local);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                v4.setVisibility(View.VISIBLE);

                                                if(sharedpreferences.get_tab_info("click_online")) {
                                                    click.setVisibility(View.VISIBLE);
                                                    scroll_v.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            scroll_v.fullScroll(View.FOCUS_DOWN);
                                                        }
                                                    });
                                                }
                                            }
                                        },500);
                                    }
                                },500);
                            }
                        },500);
                    }
                },500);
            }
        },500);
    }

    Animation animFade;
    private void anime_start(View view){
        try {
            animFade = AnimationUtils.loadAnimation(getContext(), R.anim.entre_y);

            animFade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                }
            });

            view.startAnimation(animFade);
        }catch (Exception e){
            view.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("DefaultLocale")
    private void initial_view(View view) {
        sharedpreferences=new sharedpreferences(getContext());
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        db_offline=new sql_manager(getContext());
        click_add_coins=(Praincipal)getActivity();
        context=getContext();
        activity=getActivity();
        m_mother_activty= (Praincipal) activity;

        btn_disco=view.findViewById(R.id.image_search);
        tabLayout=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.view_pager);
        mIndicator = view.findViewById(R.id.indicator);
        mIndicator2= view.findViewById(R.id.indicator2);
       // view_search_result=view.findViewById(R.id.view_search_result);

        btn_show_online=view.findViewById(R.id.btn_show_online);
        btn_show_bluetooth=view.findViewById(R.id.btn_show_bluetooth);
        btn_show_local=view.findViewById(R.id.btn_show_local);

        online=view.findViewById(R.id.view_onligne);
        bluetooth=view.findViewById(R.id.view_bluetooth);
        local=view.findViewById(R.id.view_math_local);
        randomly=view.findViewById(R.id.view_math_online_);
        fram_online=view.findViewById(R.id.cc);
        fram_bluetooth=view.findViewById(R.id.uu);

      //  btn_search_bluetooth=view.findViewById(R.id.btn_search);
        btn_start_a_local_match=view.findViewById(R.id.btn_play_local_match);
        view_permission=view.findViewById(R.id.view_permession);
        a=view.findViewById(R.id.a);
        b=view.findViewById(R.id.b);
        c=view.findViewById(R.id.c);
        v1=view.findViewById(R.id.shadow1);
        v2=view.findViewById(R.id.shadow2);
        v3=view.findViewById(R.id.shadow3);
        v4=view.findViewById(R.id.shadow4);

        img_bluetooth=view.findViewById(R.id.image_bluetooth);
        img_location=view.findViewById(R.id.image_location);
        switch_bluetooth=view.findViewById(R.id.Switch_on_off);
        switch_location=view.findViewById(R.id.Switch_on_off_location);
        txt_play_bluetooth=view.findViewById(R.id.txt_play_bluetooth);
        list_pair=view.findViewById(R.id.list_paired);
        list_unpaired=view.findViewById(R.id.list_unpaired);
        btn_copy=view.findViewById(R.id.btn_id);
        btn_info_online=view.findViewById(R.id.btn_info_play_online);
        btn_info_bluetooth=view.findViewById(R.id.btn_info_play_bluetooth);
        btn_info_lcal=view.findViewById(R.id.btn_info_local_match);
        btn_add_fr=view.findViewById(R.id.btn_add);
        click=view.findViewById(R.id.lottie_select);
        btn_show_online_=view.findViewById(R.id.btn_show_online_);
        scroll_v=view.findViewById(R.id.ss);
        tab_bluetooth=view.findViewById(R.id.tab_bluetooth);
        view_pager_bluetooth=view.findViewById(R.id.view_pager_bluetooth);
        btn_start_a_local_match_robot=view.findViewById(R.id.btn_play_local_match_robot);
        btn_info_randomly=view.findViewById(R.id.btn_info_online2);
        view_paget_randomly=view.findViewById(R.id.view_pager_randomly);


        view_pager_bluetooth.setOffscreenPageLimit(2);
        viewPager.setOffscreenPageLimit(3);
        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        context.registerReceiver(mBroadcastReceiver4, filter);



        m_mother_activty.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
    }

    private void costum_view() {



        if(sharedpreferences.get_vew("bluetooth")==0){
            show_view("bluetooth");
        }else {
            hide_view("bluetooth");
        }
        if(sharedpreferences.get_vew("online")==0){
            show_view("online");
        }else {
            hide_view("online");
        }
        if(sharedpreferences.get_vew("local")==0){
            show_view("local");
        }else {
            hide_view("local");
        }
        if(sharedpreferences.get_vew("online_")==0){
            show_view("online_");
        }else {
            hide_view("online_");
        }


        if(bluetoothAdapter!=null) {
            if (bluetoothAdapter.isEnabled()) {
                switch_bluetooth.setChecked(true);
                img_bluetooth.setBackground(ContextCompat.getDrawable(context, R.drawable.bluetooth_on));
            } else {
                switch_bluetooth.setChecked(false);
                img_bluetooth.setBackground(ContextCompat.getDrawable(context, R.drawable.bluetooth_off));
            }
        }


        if (isLocationEnabled(context) && isPermission()) {
            switch_location.setChecked(true);
            switch_location.setEnabled(false);
            img_location.setBackground(ContextCompat.getDrawable(context, R.drawable.location_on));
        } else {
            switch_location.setChecked(false);
            img_location.setBackground(ContextCompat.getDrawable(context, R.drawable.location_off));
        }


    }

    public void detect_if_there_is_some_news() {
        if(sharedpreferences.get_tab_notification("tab0")){
            notif_tab(0);
        }
        if(sharedpreferences.get_tab_notification("tab1")){
            notif_tab(1);
        }
        if(sharedpreferences.get_tab_notification("tab2")){
            notif_tab(2);
        }
    }

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    private void Listner() {
        view_pager_bluetooth.setPagingEnabled(false);
        view_pager_bluetooth.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator2.getLayoutParams();


                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+i) * indicatorWidth2 ;
                params.leftMargin = (int) translationOffset;
                mIndicator2.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    b.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                }else {
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();


                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+i) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);

            }

            @Override
            public void onPageSelected(int position) {

                TabLayout.Tab a=tabLayout.getTabAt(position);
                assert a != null;
                a.setText(mainAdapter.getPageTitle(position));
                sharedpreferences.put_tab_notification("tab"+position,false);




            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });


        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        int width = displayMetrics1.widthPixels;

        //Determine indicator width at runtime
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                //indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
                indicatorWidth=(int)((width-(context.getResources().getDimension(R.dimen.dimens_32px)*2))/3);
               // Log.e("size",""+getResources().getDimension(R.dimen.dimens_32px));
                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });

        tab_bluetooth.post(new Runnable() {
            @Override
            public void run() {
                //indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
                indicatorWidth2=(int)((width-(getResources().getDimension(R.dimen.dimens_32px)*2))/2);
                // Log.e("size",""+getResources().getDimension(R.dimen.dimens_32px));
                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator2.getLayoutParams();
                indicatorParams.width = indicatorWidth2;
                mIndicator2.setLayoutParams(indicatorParams);
            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());

                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_7_5_sp));
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }

            }

        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();

                text.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                text.setTextSize(getResources().getDimension(R.dimen.txt_7_5_sp));
                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();

                text.setTextColor(getResources().getColor(R.color.color_text_petit));
                text.setTextSize(getResources().getDimension(R.dimen.txt_5_sp));
                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        for (int i = 0; i < tab_bluetooth.getTabCount(); i++) {

            TabLayout.Tab tab = tab_bluetooth.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());

                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_7_5_sp));
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }

            }

        }

        tab_bluetooth.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager_bluetooth.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();

                text.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                text.setTextSize(getResources().getDimension(R.dimen.txt_7_5_sp));
                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();

                text.setTextColor(getResources().getColor(R.color.color_text_petit));
                text.setTextSize(getResources().getDimension(R.dimen.txt_5_sp));
                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        online.setOnClickListener(view -> btn_show_online.performClick());

        bluetooth.setOnClickListener(view -> btn_show_bluetooth.performClick());

        local.setOnClickListener(view -> btn_show_local.performClick());

        randomly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_show_online_.performClick();
            }
        });


        btn_show_bluetooth.setOnClickListener(v -> {
            if(sharedpreferences.get_vew("bluetooth")==0){
                hide_view("bluetooth");
                sharedpreferences.put_view("bluetooth",1);
            }else {
                show_view("bluetooth");
                sharedpreferences.put_view("bluetooth",0);
            }
        });

        btn_show_online.setOnClickListener(v -> {
            if(sharedpreferences.get_vew("online")==0){
                hide_view("online");
                sharedpreferences.put_view("online",1);
            }else {
                show_view("online");
                sharedpreferences.put_view("online",0);
            }
        });

        btn_show_local.setOnClickListener(v -> {
            if(sharedpreferences.get_vew("local")==0){
                hide_view("local");
                sharedpreferences.put_view("local",1);
            }else {
                show_view("local");
                sharedpreferences.put_view("local",0);
            }
        });

        btn_show_online_.setOnClickListener(v -> {
            if(sharedpreferences.get_vew("online_")==0){
                hide_view("online_");
                sharedpreferences.put_view("online_",1);
            }else {
                show_view("online_");
                sharedpreferences.put_view("online_",0);
            }
        });

        switch_bluetooth.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                if(!bluetoothAdapter.isEnabled()){
                    switch_bluetooth.setChecked(false);
                    Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBTIntent);

                    IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                    context.registerReceiver(mBroadcastReceiver1, BTIntent);
                }
            }
            else{
                if(bluetoothAdapter.isEnabled()){
                    switch_bluetooth.setChecked(true);
                    bluetoothAdapter.disable();

                    IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                    context.registerReceiver(mBroadcastReceiver1, BTIntent);
                }
            }
        });

        switch_location.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                if(!isLocationEnabled(context) || !isPermission()){
                    switch_location.setChecked(false);
                    checkPermission();
                }
            }
        });

        btn_disco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isDiscovering()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    activity.startActivityForResult(intent, 3);
                }
            }
        });
        
     /*   btn_search_bluetooth.setOnClickListener(v -> {

            if(switch_location.isChecked() && bluetoothAdapter.isEnabled()){
                if(db_offline.get__account().getMoney()>=75) {
                    plain_the_paired_devices();
                    discover();
                    view_search_result.setVisibility(View.VISIBLE);
                }else
                {
                    String m=getResources().getString(R.string.bluetooth)+" "+String.format("%02d",75)
                            +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                    Toast.makeText(getContext(),m, Toast.LENGTH_SHORT).show();
                }

            }else if (!switch_location.isChecked() && !bluetoothAdapter.isEnabled()){
                Toast.makeText(getContext()," "+context.getResources().getString(R.string.oth_and_loc), Toast.LENGTH_SHORT).show();
            }else if (!switch_location.isChecked()){
                Toast.makeText(getContext(),""+context.getResources().getString(R.string.y), Toast.LENGTH_SHORT).show();
            }else if (!bluetoothAdapter.isEnabled()){

                Toast.makeText(getContext(), ""+context.getResources().getString(R.string.turn_on_bluetooth), Toast.LENGTH_SHORT).show();
            }
        });

      */

       // add_coins.setOnClickListener(v -> click_add_coins.clickAddCoinsHome());

        Activity activity1=getActivity();

        btn_copy.setOnClickListener(v -> {
            String share=db_offline.get__account().getId();
            assert activity1 != null;
            ShareCompat.IntentBuilder
                    .from(activity1)
                    .setText(share)
                    .setType("text/plain")
                    .setChooserTitle(R.string.app_name)
                    .startChooser();
        });
        btn_add_fr.setOnClickListener(v -> {
            m_mother_activty.press_back_tacker.add(0);
            //  m_mother_activty.snack_bar.snack_bar_add_fiends(getContext(), (AppCompatActivity) activity);
            //m_mother_activty.snack_bar.snack_add_friends(getContext(), (AppCompatActivity) activity);
            m_mother_activty.snack_bar.snack_select(getContext(), (AppCompatActivity) activity, new sandbar_info.select() {
                @Override
                public void on_callback(int selected) {
                    if(selected==0)
                        m_mother_activty.snack_bar.snack_bar_add_fiends(getContext(), (AppCompatActivity) activity);
                    else
                        m_mother_activty.snack_bar.snack_add_friends(getContext(), (AppCompatActivity) activity);
                }
            });
        });
        btn_start_a_local_match.setOnClickListener(v -> {
            if(sharedpreferences.get_tab_info("click_online")) {
                sharedpreferences.put_tab_info("click_online", true);
                click.setVisibility(View.GONE);
            }

            Number d;
            if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")) {
                d=new sharedpreferences(getContext()).get_score_offline();
            }else {
                d=db_offline.get__account().getMoney();
            }

            if((double)d>=25) {
                v.setEnabled(false);
                new sound().collect(getContext());

                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()-25);
                }else {
                    account account=db_offline.get__account();
                    double new_money=account.getMoney()-25;


                    new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                    });

                    account.setMoney(new_money);
                    db_offline.Update_V_A_acoount(getContext(),account);
                }

                Praincipal activity = (Praincipal)  getContext();
                assert activity != null;
                activity.after_pause=true;

                Toast.makeText(getContext(), String.format("- %01d %2$s",25,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), local_game_space.class));
            }else
            {
                String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",25)
                        +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);
            }

        });


        btn_start_a_local_match_robot.setOnClickListener(v -> {
            if(sharedpreferences.get_tab_info("click_online")) {
                sharedpreferences.put_tab_info("click_online", true);
                click.setVisibility(View.GONE);
            }

            Number d;
            if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")) {
                d=new sharedpreferences(getContext()).get_score_offline();
            }else {
                d=db_offline.get__account().getMoney();
            }

            if((double)d>=50) {
                v.setEnabled(false);
                new sound().collect(getContext());

                 if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                     new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()-50);
                 }else {
                     account account=db_offline.get__account();
                     double new_money=account.getMoney()-50;


                     new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                     });

                     account.setMoney(new_money);
                     db_offline.Update_V_A_acoount(getContext(),account);
                 }


                Praincipal activity = (Praincipal)  getContext();
                assert activity != null;
                activity.after_pause=true;

                Toast.makeText(getContext(), String.format("- %01d %2$s",50,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), local_game_space_robot.class));
            }else
            {
                String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",50)
                        +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);
            }

        });

        btn_info_online.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(2);
            nums.add(3);
            nums.add(4);
            nums.add(5);
            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });

        btn_info_bluetooth.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(6);

            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });

        btn_info_lcal.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(7);

            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });



    }

    public void prepareViewPager(ViewPager viewPager, ArrayList<String> title) {


        ArrayList<Fragment> fr=new ArrayList<>();

        fr.add(new freind_online());
        fr.add(new reqeust_sent());
        fr.add(new reqeust_recive());
        FragmentActivity activity =getActivity();
        assert activity != null;
        mainAdapter=new MainAdapter(activity.getSupportFragmentManager(),fr,title,getContext());

        viewPager.setAdapter(mainAdapter);

        ArrayList<Fragment> fr_b=new ArrayList<>();

        fr_b.add(new paired_devices());
        fr_b.add(new no_paired_devices());

        assert activity != null;
        main_adapter_bluetooth=new MainAdapter_bluetooth(activity.getSupportFragmentManager(),fr_b,title_b,getContext());

        view_pager_bluetooth.setAdapter(main_adapter_bluetooth);

    }

    public void up_tab() {
        tabLayout.setupWithViewPager(viewPager);
        tab_bluetooth.setupWithViewPager(view_pager_bluetooth);
    }

    private void add_title() {
        title.add(getString(R.string.play));
        title.add(getString(R.string.sent));
        title.add(getString(R.string.recieve));

        title_b.add(getString(R.string.paired_devices));
        title_b.add(getString(R.string.unpaired_devices));
    }

    private void notif_tab(int tab) {
        if(tabLayout.getSelectedTabPosition()!=tab){
            TabLayout.Tab tab1= tabLayout.getTabAt(tab);
            assert tab1 != null;
            tab1.setText(mainAdapter.updatePageTitle(tab));}
    }

    private void hide_view(String part) {
        if(sharedpreferences.get_tab_info("click_online") &&  part.equals("local")) {
            sharedpreferences.put_tab_info("click_online", true);
            click.setVisibility(View.GONE);
        }

        switch (part){
            case "bluetooth":
                btn_show_bluetooth.setImageResource(R.drawable.visibility);
                //btn_search_bluetooth.setVisibility(View.GONE);
                view_permission.setVisibility(View.GONE);
                view_pager_bluetooth.setVisibility(View.GONE);
                fram_bluetooth.setVisibility(View.GONE);
              //  view_search_result.setVisibility(View.GONE);
                btn_info_bluetooth.setVisibility(View.GONE);


                break;
            case "online":
                btn_show_online.setImageResource(R.drawable.visibility);
                fram_online.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                btn_copy.setVisibility(View.GONE);
                btn_info_online.setVisibility(View.GONE);
                btn_add_fr.setVisibility(View.GONE);
                break;
            case "local":
                btn_show_local.setImageResource(R.drawable.visibility);
                btn_start_a_local_match.setVisibility(View.GONE);
                btn_start_a_local_match_robot.setVisibility(View.GONE);
                btn_info_lcal.setVisibility(View.GONE);
                break;
            case "online_":
                btn_show_online_.setImageResource(R.drawable.visibility);
                btn_info_randomly.setVisibility(View.GONE);
                view_paget_randomly.setVisibility(View.GONE);

                break;
        }
    }

    private void show_view(String part) {
        if(sharedpreferences.get_tab_info("click_online") && !part.equals("local")) {
            sharedpreferences.put_tab_info("click_online", true);
            click.setVisibility(View.GONE);
        }
        switch (part){
            case "bluetooth":
                btn_show_bluetooth.setImageResource(R.drawable.visibility_off);
                if(bluetoothAdapter!=null) {
                   // btn_search_bluetooth.setVisibility(View.VISIBLE);

                  //  view_search_result.setVisibility(View.GONE);
                    fram_bluetooth.setVisibility(View.VISIBLE);
                    view_pager_bluetooth.setVisibility(View.VISIBLE);
                    view_permission.setVisibility(View.VISIBLE);
                    btn_info_bluetooth.setVisibility(View.VISIBLE);
                }else {
                  //  btn_search_bluetooth.setVisibility(View.GONE);
                    view_permission.setVisibility(View.GONE);
                 //   view_search_result.setVisibility(View.GONE);
                    fram_bluetooth.setVisibility(View.GONE);
                    view_pager_bluetooth.setVisibility(View.GONE);

                    btn_info_bluetooth.setVisibility(View.GONE);
                    txt_play_bluetooth.setText(R.string.b_i_n_a);
                }
                break;
            case "online":
                btn_show_online.setImageResource(R.drawable.visibility_off);
                fram_online.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                btn_copy.setVisibility(View.VISIBLE);
                btn_info_online.setVisibility(View.VISIBLE);
                btn_add_fr.setVisibility(View.VISIBLE);
                break;
            case "local":
                btn_show_local.setImageResource(R.drawable.visibility_off);
                btn_start_a_local_match.setVisibility(View.VISIBLE);
                btn_start_a_local_match_robot.setVisibility(View.VISIBLE);
                btn_info_lcal.setVisibility(View.VISIBLE);
                break;
            case "online_":
                btn_show_online_.setImageResource(R.drawable.visibility_off);
                btn_info_randomly.setVisibility(View.VISIBLE);
                view_paget_randomly.setVisibility(View.VISIBLE);
                break;
        }
    }



    private boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                enableLocationSettings();
            } else {
                Activity activity=getActivity();
                assert activity != null;
                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }
        }
    }

    public boolean isPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }else
            return true;
    }

    public void enableLocationSettings() {
        if(!isLocationEnabled(context)) {
            LocationRequest locationRequest = LocationRequest.create()
                    .setInterval(5000)
                    .setFastestInterval(5000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            Activity activity=getActivity();
            assert activity != null;
            LocationServices
                    .getSettingsClient(activity)
                    .checkLocationSettings(builder.build())
                    .addOnSuccessListener(getActivity(), (LocationSettingsResponse response) -> {
                        // startUpdatingLocation(...);
                    })
                    .addOnFailureListener(activity, ex -> {
                        if (ex instanceof ResolvableApiException) {
                            // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                                ResolvableApiException resolvable = (ResolvableApiException) ex;
                                resolvable.startResolutionForResult(getActivity(), 123);
                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error.
                            }
                        }
                    });
        }else {
            switch_location.setChecked(true);
            switch_location.setEnabled(false);
            img_location.setBackground(ContextCompat.getDrawable(context, R.drawable.location_on));
        }
    }



    //////// Broadcast //////

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            notif_tab(intent.getExtras().getInt("tab"));
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.e("stat","s");
            //When discovery finds a device
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        switch_bluetooth.setChecked(false);
                        img_bluetooth.setBackground(ContextCompat.getDrawable(context,R.drawable.bluetooth_off));

                        paired_devices p=((paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,0));
                        if(p!=null)
                            p.plain_list();

                        no_paired_devices pp=((no_paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,1));
                        if(pp!=null)
                            pp.plain_list();
                        break;
                    case BluetoothAdapter.STATE_ON:

                        switch_bluetooth.setChecked(true);
                        img_bluetooth.setBackground(ContextCompat.getDrawable(context,R.drawable.bluetooth_on));
                        paired_devices p2=((paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,0));
                        if(p2!=null)
                            p2.plain_list();

                        no_paired_devices pp2=((no_paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,1));
                        if(pp2!=null)
                            pp2.plain_list();
                        break;
                }
            }
        }
    };





    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //3 cases:
                //case1: bonded already
                assert mDevice != null;
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){

                    no_paired_devices p=((no_paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,1));

                    if(p!=null) {
                            p.my_friends.remove(mDevice);
                            p.plain_list();
                    }


                  //  int a=((no_paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,1))
                  //          .list_devices_non_pair.all_devices.indexOf(mDevice);
                  //  ((no_paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,1))
                  //          .list_devices_non_pair.notifyItemRemoved(a);

                    view_pager_bluetooth.setCurrentItem(0,true);


                    paired_devices pp=((paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,0));
                    if(pp!=null)
                        pp.plain_list();
                }
                //case2: creating a bone
               // if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
//
               // }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    no_paired_devices po=((no_paired_devices)main_adapter_bluetooth.getSubItemFragment(R.id.view_pager_bluetooth,1));
                    if(po!=null)
                        po.plain_list();
                }
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver((mMessageReceiver),
                new IntentFilter("home")
        );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
    }


    interface click_add_coins_home {
        void clickAddCoinsHome();
    }
    private click_add_coins_home click_add_coins;



}