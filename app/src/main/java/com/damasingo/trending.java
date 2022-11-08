package com.damasingo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.damasingo.ADAPTER.MainAdapter;
import com.damasingo.CLASS_UTIL.CustomViewPage;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.home_fragments.bluetooth;
import com.damasingo.home_fragments.local;
import com.damasingo.home_fragments.online;
import com.damasingo.home_fragments.randomly;
import com.damasingo.trending_frg.country;
import com.damasingo.trending_frg.world;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class trending extends Fragment {


    public CustomViewPage viewPager;
    TabLayout tabLayout;
    TextView challenge_time;
    ArrayList<String> title=new ArrayList<>();
    public MainAdapter mainAdapter;
    View mIndicator;
    private int indicatorWidth;
    Praincipal mother_activity;
    Context context;
    sql_manager db_offline;
    sharedpreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trending, container, false);

        initial_view(view);
        up_tab();
        prepareViewPager(viewPager,title);
        click_listner();

        return view;
    }

    private void initial_view(View view) {

        time_left=new aide().how_many_time_to_day_end();
        mother_activity= (Praincipal) getActivity();
        context=getContext();
        db_offline=new sql_manager(getContext());
        sharedPreferences=new sharedpreferences(getContext());

        viewPager=view.findViewById(R.id.view_pager_trending);
        tabLayout=view.findViewById(R.id.tab_trending);
        mIndicator=view.findViewById(R.id.indicator);
        challenge_time=view.findViewById(R.id.txt_timing);

        title.add(new aide().get_my_name_country(db_offline.get__account().getCountry())+"\t"
                +new aide().country_to_emojie(db_offline.get__account().getCountry()));

        String unicode = "U+1F30D";
        title.add(context.getResources().getString(R.string.world)+"\t"+new aide().getEmojiByUnicode(Integer.parseInt(unicode.substring(2), 16)));


        viewPager.setOffscreenPageLimit(2);
        startTimer_logo();

    }

    public void up_tab() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> title) {

        ArrayList<Fragment> fr=new ArrayList<>();


        fr.add(new country());
        fr.add(new world());





        FragmentActivity activity =getActivity();
        assert activity != null;
        mainAdapter=new MainAdapter(activity.getSupportFragmentManager(),fr,title,getContext());

        viewPager.setAdapter(mainAdapter);

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

        indicatorWidth=(int)((width-(context.getResources().getDimension(R.dimen.dimens_18px)*2))/2);
        FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
        indicatorParams.width = indicatorWidth;
        mIndicator.setLayoutParams(indicatorParams);

        //Determine indicator width at runtime
       // tabLayout.post(new Runnable() {
       //     @Override
       //     public void run() {
       //         //indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
       //         indicatorWidth=(int)((width-(context.getResources().getDimension(R.dimen.dimens_18px)*2))/2);
       //         // Log.e("size",""+getResources().getDimension(R.dimen.dimens_32px));
       //         //Assign new width
       //         FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
       //         indicatorParams.width = indicatorWidth;
       //         mIndicator.setLayoutParams(indicatorParams);
       //     }
       // });



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
                }else {
                    tabTextView.setTextColor(getResources().getColor(R.color.color_text_petit));
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_6_sp));
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
                text.setTextSize(getResources().getDimension(R.dimen.txt_7_5_sp));
                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();


                text.setTextColor(getResources().getColor(R.color.color_text_petit));
                text.setTextSize(getResources().getDimension(R.dimen.txt_6_sp));
                text.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private CountDownTimer mCountDownTimer_logo;
    long time_end;
    long time_left;
    private void startTimer_logo() {
            time_end = new aide().getCurrentUTC().getTime() + time_left;
            mCountDownTimer_logo = new CountDownTimer(time_left, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time_left = millisUntilFinished;
                    updateCountDownText_logo();
                }
                @Override
                public void onFinish() {
                    time_left=new aide().how_many_time_to_day_end();
                    startTimer_logo();
                }
            }.start();
    }

    @SuppressLint("DefaultLocale")
    private void updateCountDownText_logo() {

        int heure = (int) (time_left / 1000) / 60 / 60;
        int minutes = (int) ((time_left / 1000) / 60)-(heure*60) ;
        int seconds = (int) ((time_left / 1000) % 60);

        String timeLeftFormatted;

        if(heure!=0)
            timeLeftFormatted = String.format( "%02d %s : %02d %s : %02d %s",heure,context.getResources().getString(R.string.h),minutes,context.getResources().getString(R.string.m),seconds,context.getResources().getString(R.string.seconde));
        else if(minutes != 0)
            timeLeftFormatted = String.format( "%02d %s : %02d %s : %02d %s",0,context.getResources().getString(R.string.h),minutes,context.getResources().getString(R.string.m),seconds,context.getResources().getString(R.string.seconde));
        else
            timeLeftFormatted = String.format( "%02d %s : %02d %s : %02d %s",0,context.getResources().getString(R.string.h),0,context.getResources().getString(R.string.m),seconds,context.getResources().getString(R.string.seconde));


        challenge_time.setText(String.format("\t%1$s\t",timeLeftFormatted));


    }
}