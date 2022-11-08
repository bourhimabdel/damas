package com.damasingo.home_fragments;

import android.Manifest;
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
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.damasingo.ADAPTER.MainAdapter_bluetooth;
import com.damasingo.CLASS_UTIL.CustomViewPage;
import com.damasingo.CLASS_UTIL.LockableNestedScrollView;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.fragment_bluetooth.no_paired_devices;
import com.damasingo.fragment_bluetooth.paired_devices;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class bluetooth extends Fragment {


    TabLayout tab_bluetooth;
    public CustomViewPage view_pager_bluetooth;
    public ImageView img_bluetooth,img_location;
    ImageView btn_disco,btn_info_bluetooth;
    ConstraintLayout a,b,c;
    public SwitchMaterial switch_bluetooth,switch_location;
    View  mIndicator2;
    private int  indicatorWidth2;
    public MainAdapter_bluetooth main_adapter_bluetooth;
    BluetoothAdapter bluetoothAdapter;
    AppCompatActivity activity;
    Praincipal m_mother_activty;
    public LockableNestedScrollView scroll_v;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bluetooth, container, false);

        inicial_view(view);
        up_tab();
        prepareViewPager(view_pager_bluetooth);
        click_listner();



        return view;
    }


    private void inicial_view(View view) {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        context=getContext();
        activity= (AppCompatActivity) getActivity();
        m_mother_activty= (Praincipal) getActivity();

        btn_disco=view.findViewById(R.id.image_search);
        mIndicator2= view.findViewById(R.id.indicator2);
        a=view.findViewById(R.id.a);
        b=view.findViewById(R.id.b);
        c=view.findViewById(R.id.c);
        img_bluetooth=view.findViewById(R.id.image_bluetooth);
        img_location=view.findViewById(R.id.image_location);
        switch_bluetooth=view.findViewById(R.id.Switch_on_off);
        switch_location=view.findViewById(R.id.Switch_on_off_location);
        view_pager_bluetooth=view.findViewById(R.id.view_pager_bluetooth);
        btn_info_bluetooth=view.findViewById(R.id.btn_info_play_bluetooth);
        tab_bluetooth=view.findViewById(R.id.tab_bluetooth);
        scroll_v=view.findViewById(R.id.bb);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        context.registerReceiver(mBroadcastReceiver4, filter);
    }

    public void up_tab() {
        tab_bluetooth.setupWithViewPager(view_pager_bluetooth);
    }

    private void prepareViewPager(CustomViewPage view_pager_bluetooth) {

        ArrayList<Fragment> fr_b=new ArrayList<>();
        ArrayList<String> title_b=new ArrayList<>();

        title_b.add(getString(R.string.paired_devices));
        title_b.add(getString(R.string.unpaired_devices));

        fr_b.add(new paired_devices());
        fr_b.add(new no_paired_devices());

        main_adapter_bluetooth=new MainAdapter_bluetooth(getActivity().getSupportFragmentManager(),fr_b,title_b,getContext());

        view_pager_bluetooth.setAdapter(main_adapter_bluetooth);

    }

    private void click_listner() {

        view_pager_bluetooth.setPagingEnabled(false);
        view_pager_bluetooth.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        int width = displayMetrics1.widthPixels;

        //indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
        indicatorWidth2=(int)((width-(context.getResources().getDimension(R.dimen.dimens_32px)*2)-(getResources().getDimension(R.dimen.dimens_18px)*2))/2);
        // Log.e("size",""+getResources().getDimension(R.dimen.dimens_32px));
        //Assign new width
        FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator2.getLayoutParams();
        indicatorParams.width = indicatorWidth2;
        mIndicator2.setLayoutParams(indicatorParams);

      /*  tab_bluetooth.post(new Runnable() {
            @Override
            public void run() {

            }
        });
*/

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
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_5_sp));
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }else {
                    tabTextView.setTextColor(getResources().getColor(R.color.color_text_petit));
                    tabTextView.setTextSize(getResources().getDimension(R.dimen.txt_4_sp));
                    tabTextView.setTypeface(null, Typeface.NORMAL);
                }

            }

        }

        tab_bluetooth.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager_bluetooth.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();

                text.setTextColor(getResources().getColor(R.color.color_text_login_and_cadre));
                text.setTextSize(getResources().getDimension(R.dimen.txt_5_sp));
                text.setTypeface(null, Typeface.BOLD);
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


        switch_bluetooth.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(bluetoothAdapter!=null) {
                if (isChecked) {
                    if (!bluetoothAdapter.isEnabled()) {
                        switch_bluetooth.setChecked(false);
                        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivity(enableBTIntent);

                        IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                        context.registerReceiver(mBroadcastReceiver1, BTIntent);
                    }
                } else {
                    if (bluetoothAdapter.isEnabled()) {
                        switch_bluetooth.setChecked(true);
                        bluetoothAdapter.disable();

                        IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                        context.registerReceiver(mBroadcastReceiver1, BTIntent);
                    }
                }
            }else
                switch_bluetooth.setChecked(false);
        });

        switch_location.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(bluetoothAdapter!=null) {
                if(isChecked) {
                    if(!isLocationEnabled(context) || !isPermission()){
                        switch_location.setChecked(false);
                        checkPermission();
                    }
            }
            }else
                switch_location.setChecked(false);
        });

        btn_disco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter!=null)
                    if (!bluetoothAdapter.isDiscovering()) {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        activity.startActivityForResult(intent, 3);
                    }
            }
        });


        btn_info_bluetooth.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(7);

            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });






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
}