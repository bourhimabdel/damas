package com.damasingo.home_fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.damasingo.ADAPTER.MainAdapter;
import com.damasingo.ADAPTER.MainAdapter_bluetooth;
import com.damasingo.CLASS_UTIL.CustomViewPage;
import com.damasingo.CLASS_UTIL.LockableNestedScrollView;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.Snack_bar.sandbar_info;
import com.damasingo.fragment_bluetooth.no_paired_devices;
import com.damasingo.fragment_bluetooth.paired_devices;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.fragment_play_online.reqeust_recive;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.google.android.material.tabs.TabLayout;

import java.security.Principal;
import java.util.ArrayList;


public class online extends Fragment {

    TabLayout tabLayout;
    View mIndicator;
    public CustomViewPage viewPager;
    Button btn_copy,btn_add_fr;
    public MainAdapter mainAdapter;
    private int indicatorWidth;
    sharedpreferences sharedpreferences;
    ImageButton btn_info_online;
    Praincipal m_mother_activty;
    public LockableNestedScrollView scroll_v;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_online, container, false);

       initial_view(view);
        up_tab();
        prepareViewPager(viewPager);
        click_listner();
        detect_if_there_is_some_news();






        return view;
    }

    private void initial_view(View view) {

        context=getContext();
        sharedpreferences=new sharedpreferences(getContext());
        tabLayout=view.findViewById(R.id.tab);
        viewPager=view.findViewById(R.id.view_pager);
        mIndicator = view.findViewById(R.id.indicator);
        btn_copy=view.findViewById(R.id.btn_id);
        btn_add_fr=view.findViewById(R.id.btn_add);
        btn_info_online=view.findViewById(R.id.btn_info_play_online);
        m_mother_activty=(Praincipal) getActivity();
        scroll_v=view.findViewById(R.id.ss);

        viewPager.setOffscreenPageLimit(3);
    }

    ArrayList<String> title=new ArrayList<>();
    public void prepareViewPager(ViewPager viewPager) {


        ArrayList<Fragment> fr=new ArrayList<>();


        fr.add(new freind_online());
        fr.add(new reqeust_sent());
        fr.add(new reqeust_recive());

        title.add(getString(R.string.play));
        title.add(getString(R.string.sent));
        title.add(getString(R.string.recieve));


        FragmentActivity activity =getActivity();
        assert activity != null;
        mainAdapter=new MainAdapter(activity.getSupportFragmentManager(),fr,title,getContext());

        viewPager.setAdapter(mainAdapter);

    }

    public void up_tab() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void click_listner() {
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

               // TabLayout.Tab a=tabLayout.getTabAt(position);
               // assert a != null;
               // a.setText(mainAdapter.getPageTitle(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });


        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        int width = displayMetrics1.widthPixels;
        //indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
        indicatorWidth=(int)((width-(context.getResources().getDimension(R.dimen.dimens_32px)*2)-(context.getResources().getDimension(R.dimen.dimens_18px)*2))/3);
        // Log.e("size",""+getResources().getDimension(R.dimen.dimens_32px));
        //Assign new width
        FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
        indicatorParams.width = indicatorWidth;
        mIndicator.setLayoutParams(indicatorParams);
        //Determine indicator width at runtime
       /* tabLayout.post(new Runnable() {
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

                sharedpreferences.put_tab_notification("tab"+tab.getPosition(),false);
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

        btn_info_online.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(2);
            nums.add(4);
            nums.add(5);
            nums.add(6);
            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });

        btn_copy.setOnClickListener(v -> {
            String share=new sql_manager(getContext()).get__account().getId();

            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setText(share)
                    .setType("text/plain")
                    .setChooserTitle(R.string.app_name)
                    .startChooser();
        });
        btn_add_fr.setOnClickListener(v -> {
            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_add_fiends(getContext(), (AppCompatActivity) getActivity());
            //  m_mother_activty.snack_bar.snack_bar_add_fiends(getContext(), (AppCompatActivity) activity);
            //m_mother_activty.snack_bar.snack_add_friends(getContext(), (AppCompatActivity) activity);
           //m_mother_activty.snack_bar.snack_select(getContext(), (AppCompatActivity) getActivity(), new sandbar_info.select() {
           //    @Override
           //    public void on_callback(int selected) {
           //        if(selected==0)
           //            m_mother_activty.snack_bar.snack_bar_add_fiends(getContext(), (AppCompatActivity) getActivity());
           //        else
           //            m_mother_activty.snack_bar.snack_add_friends(getContext(), (AppCompatActivity) getActivity());
           //    }
           //});
        });

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











}