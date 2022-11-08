package com.damasingo.ADAPTER;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.damasingo.R;

import java.util.ArrayList;

public class MainAdapter_bluetooth extends FragmentPagerAdapter {

    Context context;
    ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    ArrayList<String> stringArrayList=new ArrayList<>();
    int[] image={R.drawable.ic_home_plean_,R.drawable.bluetooth_searching};
    FragmentManager fragmentManager;





    public MainAdapter_bluetooth(FragmentManager supportFragmentManager, ArrayList<Fragment> items, ArrayList<String> title, Context context) {
        super(supportFragmentManager);
        this.context=context;
        fragmentManager=supportFragmentManager;
        fragmentArrayList.addAll(items);
        stringArrayList.addAll(title);
    }

    public Fragment getSubItemFragment(int viewPagerId, int fragmentPosition) {
        return fragmentManager.findFragmentByTag("android:switcher:" + viewPagerId + ":" + fragmentPosition);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


       // Drawable d= ContextCompat.getDrawable(context,image[position]);
//
       // assert d != null;
       // d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
//
//
       // SpannableString spannableString=new SpannableString("   "+stringArrayList.get(position));
       // ImageSpan imageSpan=new ImageSpan(d,ImageSpan.ALIGN_BOTTOM);
//
       // spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
       // return spannableString;

        return stringArrayList.get(position);
    }

    public CharSequence updatePageTitle(int position) {


        Drawable d= ContextCompat.getDrawable(context,image[position]);

        assert d != null;
        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());


        SpannableString spannableString=new SpannableString("   "+stringArrayList.get(position)+" ●");
        ImageSpan imageSpan=new ImageSpan(d,ImageSpan.ALIGN_BOTTOM);

        spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


}
