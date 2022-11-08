package com.damasingo.trending_frg;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.damasingo.ADAPTER.list_online_friends;
import com.damasingo.ADAPTER.trending_items;
import com.damasingo.CLASS_UTIL.gain_day;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Msg_alert.Msg_box;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.damasingo.trending;

import java.util.ArrayList;
import java.util.Collections;

public class country extends Fragment {

    public RecyclerView list_view;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView list_description;
    trending_items mtrending_items;
    Context context;
    connect_to_firebase db_online=new connect_to_firebase();
    sql_manager db_offline;
    Button btn_top_score;

    ArrayList<gain_day> list_data=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_country, container, false);

        initial_view(view);
        click_listner();
        plain_list();
        refresh();



        return view;
    }



    private void initial_view(View view) {
        list_view=view.findViewById(R.id.list_trending_country);
        swipeRefreshLayout=view.findViewById(R.id.swip_f);
        list_description=view.findViewById(R.id.txt_list);
        btn_top_score=view.findViewById(R.id.btn_top_score);
        context=getContext();
        db_offline=new sql_manager(getContext());
    }


    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                plain_list();
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void plain_list(){
        if(getActivity()!=null) {
            getActivity().runOnUiThread(() -> {

                mtrending_items = new trending_items(new ArrayList<>(), getContext(), (AppCompatActivity) getActivity());
                list_view.setAdapter(mtrending_items);
                list_view.setLayoutManager(new LinearLayoutManager(getContext()));

                db_online.retrive_gain_day_of_country(db_offline.get__account().getCountry(),new connect_to_firebase.game_gain_list() {
                    @Override
                    public void onCall_back(ArrayList<gain_day> list) {

                        list_data=list;
                        Log.e("num", "" + list_data.size());
                        if (list_data.size() != 0) {
                            list_description.setVisibility(View.GONE);
                            mtrending_items = new trending_items(list_data, getContext(), (AppCompatActivity) getActivity());
                            list_view.setAdapter(mtrending_items);
                        } else {
                            list_description.setVisibility(View.VISIBLE);
                            list_description.setText(context.getResources().getString(R.string.no_data_yet));
                            list_view.setAdapter(null);
                        }

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });



            });
        }
    }



    private void click_listner() {

        btn_top_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Msg_box().show_classment(new sql_manager(context).get__account().getCountry(),getContext(), (AppCompatActivity) getActivity());
            }
        });

    }

}