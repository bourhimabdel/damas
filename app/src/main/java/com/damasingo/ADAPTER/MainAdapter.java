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

public class MainAdapter extends FragmentPagerAdapter {

    Context context;
    ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    ArrayList<String> stringArrayList=new ArrayList<>();
    FragmentManager fragmentManager;





    public MainAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> items, ArrayList<String> title,Context context) {
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
        return stringArrayList.get(position);
    }

    public CharSequence updatePageTitle(int position) {
        return stringArrayList.get(position)+" ●";
    }


}
