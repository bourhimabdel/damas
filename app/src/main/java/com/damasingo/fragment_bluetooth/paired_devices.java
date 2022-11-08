package com.damasingo.fragment_bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.damasingo.ADAPTER.list_online_friends;
import com.damasingo.CLASS_UTIL.Support_save_data;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.bluetooth_match.list_devices_paired;
import com.damasingo.home;
import com.damasingo.home_;
import com.damasingo.home_fragments.bluetooth;
import com.damasingo.home_fragments.online;

import java.util.ArrayList;
import java.util.Set;

import custom.widget.SwipeRefreshLayout;

//import custom.widget.SwipeRefreshLayout;


public class paired_devices extends Fragment {


    TextView txt_list_empty;
    public RecyclerView list_friends;
     SwipeRefreshLayout swip_detect;

    list_online_friends list_adapter;
    ArrayList<BluetoothDevice> my_friends=new ArrayList<>();

    connect_to_firebase db_online=new connect_to_firebase();
    sql_manager db_offline;
    Support_save_data support_save_data;
    BluetoothAdapter bluetoothAdapter;
    AppCompatActivity activity;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.paired_devices, container, false);

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

        txt_list_empty=v.findViewById(R.id.txt_friend_online);
        list_friends=v.findViewById(R.id.list_freinds_online);
       swip_detect=v.findViewById(R.id.swip_detect);
        activity= (AppCompatActivity) getActivity();


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

        if(getActivity()!=null) {
            getActivity().runOnUiThread(() -> {

                if(bluetoothAdapter!=null) {
                    if (bluetoothAdapter.isEnabled()) {
                        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                        my_friends.clear();
                        my_friends.addAll(devices);
                        if (my_friends.size() != 0) {
                            txt_list_empty.setVisibility(View.GONE);
                            list_friends.setVisibility(View.VISIBLE);
                        } else {
                            txt_list_empty.setText(activity.getString(R.string.there_are_no_paired_devices));
                            txt_list_empty.setVisibility(View.VISIBLE);
                            return;

                        }

                        list_devices_paired list_devices_paired = new list_devices_paired(my_friends, getContext());
                        list_friends.setAdapter(list_devices_paired);
                        list_friends.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    } else if (!bluetoothAdapter.isEnabled()) {
                        txt_list_empty.setVisibility(View.VISIBLE);
                        list_friends.setVisibility(View.INVISIBLE);
                        txt_list_empty.setText(activity.getString(R.string.lml));
                    }
                }


            });
        }
    }






}