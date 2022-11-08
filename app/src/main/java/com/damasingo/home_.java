package com.damasingo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.damasingo.ADAPTER.MainAdapter;
import com.damasingo.CLASS_UTIL.CustomViewPage;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.fragment_play_online.reqeust_recive;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.home_fragments.bluetooth;
import com.damasingo.home_fragments.local;
import com.damasingo.home_fragments.online;
import com.damasingo.home_fragments.randomly;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Locale;


public class home_ extends Fragment {


    ConstraintLayout constraintLayout;
    public CustomViewPage viewPager;
    TabLayout tabLayout;
    ArrayList<String> title=new ArrayList<>();
    public MainAdapter mainAdapter;
    View mIndicator;
    private int indicatorWidth;
    Praincipal mother_activity;
    Context context;
    sharedpreferences sharedpreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_, container, false);

        initial_view(view);
        up_tab();
        prepareViewPager(viewPager,title);
        click_listner();
        detect_if_there_is_some_news();



        if(sharedpreferences.get_setting_type("first_tap_to_move_player_1")) {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(1,true);
                }
            }, 1500);

        }

        return view;
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> title) {

        ArrayList<Fragment> fr=new ArrayList<>();

        fr.add(new bluetooth());
        fr.add(new local());

        if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
            fr.add(new sign_ni());
            fr.add(new sign_ni());
        }else {
            fr.add(new randomly());
            fr.add(new online());
        }


        FragmentActivity activity =getActivity();
        assert activity != null;
        mainAdapter=new MainAdapter(activity.getSupportFragmentManager(),fr,title,getContext());

        viewPager.setAdapter(mainAdapter);

    }

    public void up_tab() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initial_view(View view) {

        mother_activity= (Praincipal) getActivity();
        context=getContext();
        sharedpreferences =new sharedpreferences(getContext());

        viewPager=view.findViewById(R.id.view_pager_home);
        tabLayout=view.findViewById(R.id.tab_home);
        mIndicator=view.findViewById(R.id.indicato);
        constraintLayout=view.findViewById(R.id.pg);

        title.add(context.getResources().getString(R.string.bluetoot));
        title.add(context.getResources().getString(R.string.local));
        title.add(context.getResources().getString(R.string.rando));
        title.add(context.getResources().getString(R.string.friend));

        viewPager.setOffscreenPageLimit(4);
    }

    private void click_listner() {

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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });


        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        int width = displayMetrics1.widthPixels;

        //indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
        indicatorWidth=(int)((width-(context.getResources().getDimension(R.dimen.dimens_18px)*2))/4);
        // Log.e("size",""+getResources().getDimension(R.dimen.dimens_32px));
        //Assign new width
        FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
        indicatorParams.width = indicatorWidth;
        mIndicator.setLayoutParams(indicatorParams);
       //Determine indicator width at runtime
     /*  tabLayout.post(new Runnable() {
           @Override
           public void run() {

           }
       });

      */



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
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_5_sp));
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }else {
                    tabTextView.setTextColor(getResources().getColor(R.color.color_text_petit));
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_4_sp));
                    tabTextView.setTypeface(null, Typeface.NORMAL);
                }

            }

        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();


                text.setText(title.get(tab.getPosition()));
                text.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                text.setTextSize(getResources().getDimension(R.dimen.txt_5_sp));
                text.setTypeface(null, Typeface.BOLD);


                switch (tab.getPosition()){
                    case 2:
                        mIndicator.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_bg_randomly));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.randomly___));
                        if(mother_activity.from_fragment.equals(home_.class.getName()))
                            mother_activity.button_home.setImageResource(R.drawable.ic_home_plain_a);
                        mother_activity.current_fragement_in_home=2;
                        break;
                    case 3:
                        mIndicator.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_bg));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.normal___));
                        if(mother_activity.from_fragment.equals(home_.class.getName()))
                            mother_activity.button_home.setImageResource(R.drawable.ic_home_plain_b);
                        mother_activity.current_fragement_in_home=3;
                        break;
                    case 0:
                        mIndicator.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_bg_bl));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.low____));
                        if(mother_activity.from_fragment.equals(home_.class.getName()))
                            mother_activity.button_home.setImageResource(R.drawable.ic_home_plain_c);
                        mother_activity.current_fragement_in_home=0;
                        break;
                    case 1:
                        mIndicator.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_bg_local));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.top___));
                        if(mother_activity.from_fragment.equals(home_.class.getName()))
                            mother_activity.button_home.setImageResource(R.drawable.ic_home_plain_d);
                        mother_activity.current_fragement_in_home=1;
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();


                text.setTextColor(getResources().getColor(R.color.color_text_petit));
                text.setTextSize(getResources().getDimension(R.dimen.txt_4_sp));
                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void notif_tab(int tab) {
        if(tabLayout.getSelectedTabPosition()!=tab){
            TabLayout.Tab tab1= tabLayout.getTabAt(tab);
            if (tab1 != null) {

                TextView tabTextView = (TextView) tab1.getCustomView();
                tab1.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(mainAdapter.updatePageTitle(tab));

                tabTextView.setTextColor(getResources().getColor(R.color.color_text_petit));
                tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_4_sp));
                tabTextView.setTypeface(null, Typeface.NORMAL);


            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver((mMessageReceiver),
                new IntentFilter("home")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            notif_tab(3);

            Fragment accuil = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
            if (accuil != null) {
                home_ home = (home_) accuil;
                home.viewPager.setPagingEnabled(true);
                online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                online.notif_tab(intent.getExtras().getInt("tab"));
            }
        }
    };



    public void detect_if_there_is_some_news() {
        if(sharedpreferences.get_tab_notification("tab0") || sharedpreferences.get_tab_notification("tab1") || sharedpreferences.get_tab_notification("tab2")){
            notif_tab(3);
        }

    }
}