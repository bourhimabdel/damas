package com.damasingo.fragment_play_online;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import custom.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damasingo.ADAPTER.list_online_friends;
import com.damasingo.CLASS_UTIL.Support_save_data;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.home;
import com.damasingo.home_;
import com.damasingo.home_fragments.bluetooth;
import com.damasingo.home_fragments.online;

import java.util.ArrayList;

import custom.widget.SwipeRefreshLayout;


public class freind_online extends Fragment {


    TextView txt_list_empty;
    public RecyclerView list_friends;
    SwipeRefreshLayout swip_detect;

    list_online_friends list_adapter;
    ArrayList<reqeust> my_friends;

    connect_to_firebase db_online=new connect_to_firebase();
    sql_manager db_offline;
    Support_save_data support_save_data;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_freind_online, container, false);

        initial_view(v);
        plain_list();
        refresh();


        return v;
    }

    private void refresh(){
        swip_detect.setOnRefreshListener(() -> db_online.get_all_friends(db_offline.get__account().getId(), keys -> support_save_data.add_this_collect_to_friends(keys, () -> {
            plain_list();
            swip_detect.setRefreshing(false);
        })));
    }

    private void initial_view(View v) {
        support_save_data=new Support_save_data(getContext());
        db_offline=new sql_manager(getContext());

        txt_list_empty=v.findViewById(R.id.txt_friend_online);
        list_friends=v.findViewById(R.id.list_freinds_online);
         swip_detect=v.findViewById(R.id.swip_detect);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list_friends.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Fragment accuil = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_accuil);
                    if (newState==RecyclerView.SCROLL_STATE_IDLE) {
                        if (accuil != null) {
                            home_ home = (home_) accuil;
                            home.viewPager.setPagingEnabled(true);
                            online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                            online.scroll_v.setScrollingEnabled(true);
                        }
                    }else if (newState==RecyclerView.SCROLL_STATE_DRAGGING) {

                        if (accuil != null) {
                            home_ home = (home_) accuil;
                            home.viewPager.setPagingEnabled(false);
                            online online=(online) home.mainAdapter.getSubItemFragment(R.id.view_pager_home,3);
                            online.scroll_v.setScrollingEnabled(false);
                        }
                    }
                }
            });
        }
    }

    public void plain_list(){
        if(getActivity()!=null) {
            getActivity().runOnUiThread(() -> {
                my_friends = new sql_manager(getContext()).get__freinds();
                Log.e("num", "" + my_friends.size());
                if (my_friends.size() != 0) {
                    txt_list_empty.setVisibility(View.GONE);
                } else {
                    txt_list_empty.setVisibility(View.VISIBLE);
                }
                list_adapter = new list_online_friends(my_friends, getContext(), (AppCompatActivity) getActivity());
                list_friends.setAdapter(list_adapter);
                list_friends.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

            });
        }
    }

    public void refrech_list(){
        getActivity().runOnUiThread(() -> {
            ArrayList<reqeust> f=new sql_manager(getContext()).get__freinds();
            if(f.size()==1) {
                txt_list_empty.setVisibility(View.GONE);
            }
            ((list_online_friends)list_friends.getAdapter()).all_friend_online.clear();
            ((list_online_friends)list_friends.getAdapter()).all_friend_online.addAll(f);
            list_friends.getAdapter().notifyDataSetChanged();
        });
    }


    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refrech_list();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((mMessageReceiver),
                new IntentFilter("online")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        list_adapter.end_listen();
    }

}