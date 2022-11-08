package com.damasingo.fragment_bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.damasingo.ADAPTER.list_online_friends;
import com.damasingo.CLASS_UTIL.Support_save_data;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.bluetooth_match.list_devices_non_pair;
import com.damasingo.bluetooth_match.list_devices_paired;
import com.damasingo.home;
import com.damasingo.home_;
import com.damasingo.home_fragments.bluetooth;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

import custom.widget.SwipeRefreshLayout;

//import custom.widget.SwipeRefreshLayout;


public class no_paired_devices extends Fragment {


    TextView txt_list_empty;
    public RecyclerView list_friends;
     SwipeRefreshLayout swip_detect;

    list_online_friends list_adapter;
    public ArrayList<BluetoothDevice> my_friends=new ArrayList<>();

    connect_to_firebase db_online=new connect_to_firebase();
    sql_manager db_offline;
    Support_save_data support_save_data;
    BluetoothAdapter bluetoothAdapter;
    Context context;
    AppCompatActivity activity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.no_paired_devices, container, false);

        initial_view(v);
        plain_list();
        refresh();


        return v;
    }

    private void refresh(){
         swip_detect.setOnRefreshListener(() -> {
             swip_detect.setRefreshing(false);
             plain_list();
         });
    }

    private void initial_view(View v) {
        db_offline=new sql_manager(getContext());
        context =getContext();
        activity = (AppCompatActivity) getActivity();

        txt_list_empty=v.findViewById(R.id.txt_friend_online);
        list_friends=v.findViewById(R.id.list_freinds_online);
        swip_detect=v.findViewById(R.id.swip_detect);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list_friends.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Fragment accuil = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                    if (newState==RecyclerView.SCROLL_STATE_IDLE) {
                        if (accuil != null) {
                            home_ home = (home_) accuil;
                            home.viewPager.setPagingEnabled(true);
                            bluetooth bluetooth=(bluetooth) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,0);
                            bluetooth.scroll_v.setScrollingEnabled(true);
                        }
                    }else if (newState==RecyclerView.SCROLL_STATE_DRAGGING) {

                        if (accuil != null) {
                            home_ home = (home_) accuil;
                            home.viewPager.setPagingEnabled(false);
                            bluetooth bluetooth=(bluetooth) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,0);
                            bluetooth.scroll_v.setScrollingEnabled(false);
                        }
                    }
                }
            });
        }
    }


    public void plain_list(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if(bluetoothAdapter!=null) {
            if (getActivity() != null) {
                home_ home = (home_) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                bluetooth bluetooth = (bluetooth) home.mainAdapter.getSubItemFragment(R.id.view_pager_home, 0);
                getActivity().runOnUiThread(() -> {
                    if (home != null) {
                        if (bluetoothAdapter.isEnabled() && bluetooth.switch_location.isChecked()) {
                            txt_list_empty.setText(activity.getString(R.string.we_could_not_detect_any_unpaired_device));
                            txt_list_empty.setVisibility(View.VISIBLE);
                            list_friends.setAdapter(null);
                            discover();
                        } else {
                            txt_list_empty.setVisibility(View.VISIBLE);
                            list_friends.setVisibility(View.INVISIBLE);
                            txt_list_empty.setText(activity.getString(R.string.req));
                        }
                    }


                });
            }
        }
    }


    public void discover() {
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            bluetoothAdapter.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mBroadcastReceiver3, filter);
        }
        if(!bluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }


    public list_devices_non_pair list_devices_non_pair;
    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                if(!my_friends.contains(device) && !bluetoothAdapter.getBondedDevices().contains(device))
                    my_friends.add(device);

                if (my_friends.size() != 0) {
                    txt_list_empty.setVisibility(View.GONE);
                    list_friends.setVisibility(View.VISIBLE);
                }else {
                    txt_list_empty.setText(activity.getString(R.string.we_could_not_detect_any_unpaired_device));
                    txt_list_empty.setVisibility(View.VISIBLE);
                    return;
                }
                list_devices_non_pair= new list_devices_non_pair(my_friends,getContext());
                list_friends.setAdapter(list_devices_non_pair);
                list_friends.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
            }


        }
    };





}