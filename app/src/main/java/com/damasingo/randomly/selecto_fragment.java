package com.damasingo.randomly;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.play_onlinge.game_space;

import java.util.ArrayList;

public class selecto_fragment extends Fragment  {


     Button button1,button2
            ,button3
            ,button4
            ,button5
            ,button6
            ,button7
            ,button8
            ,button9;

    TextView lock_3,lock_4,lock_5,lock_6,lock_7,lock_8,lock_9;
    ArrayList<Integer> price=new ArrayList<>();
    Praincipal mother_activity;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.select_price_to_play_, container, false);

        initial_view(view);
        click_lisner();
        return view;
    }

    private void click_lisner() {

        Intent table_game=new Intent(getContext(), before_rundomly_match.class);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();

                table_game.putExtra("amount",0);
                startActivity(table_game);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",1);
                startActivity(table_game);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",2);
                startActivity(table_game);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",3);
                startActivity(table_game);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",4);
                startActivity(table_game);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",5);
                startActivity(table_game);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",6);
                startActivity(table_game);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",7);
                startActivity(table_game);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disabled_button();
                table_game.putExtra("amount",8);
                startActivity(table_game);
            }
        });

    }

    private void check_if_this_button_on(int index,Button button) {
        if (price.get(index)>new sql_manager(context).get__account().getMoney())
            button.setEnabled(false);
        else
            button.setEnabled(true);
    }

    @SuppressLint("DefaultLocale")
    private void initial_view(View view) {

        mother_activity= (Praincipal) getActivity();
        context=getContext();

        button1=view.findViewById(R.id.p_0);
        button2=view.findViewById(R.id.p_1);
        button3=view.findViewById(R.id.p_2);
        button4=view.findViewById(R.id.p_3);
        button5=view.findViewById(R.id.p_4);
        button6=view.findViewById(R.id.p_5);
        button7=view.findViewById(R.id.p_6);
        button8=view.findViewById(R.id.p_7);
        button9=view.findViewById(R.id.p_8);

        lock_3=view.findViewById(R.id.view_3);
        lock_4=view.findViewById(R.id.view_4);
        lock_5=view.findViewById(R.id.view_5);
        lock_6=view.findViewById(R.id.view_6);
        lock_7=view.findViewById(R.id.view_7);
        lock_8=view.findViewById(R.id.view_8);
        lock_9=view.findViewById(R.id.view_9);


        price.add(100);
        price.add(500);
        price.add(2500);
        price.add(10000);
        price.add(50000);
        price.add(100000);
        price.add(500000);
        price.add(2500000);
        price.add(10000000);


       enabled__buttons();

        button1.setText(String.format("%02d",100));
        button2.setText(String.format("%02d",500));
        button3.setText(String.format("%.1f %2$s",2.5,getResources().getString(R.string.K)));
        button4.setText(String.format("%02d %2$s",10,getResources().getString(R.string.K)));
        button5.setText(String.format("%02d %2$s",50,getResources().getString(R.string.K)));
        button6.setText(String.format("%02d %2$s",100,getResources().getString(R.string.K)));
        button7.setText(String.format("%02d %2$s",500,getResources().getString(R.string.K)));
        button8.setText(String.format("%.1f %2$s",2.5,getResources().getString(R.string.M)));
        button9.setText(String.format("%02d %2$s",10,getResources().getString(R.string.M)));

        write_into_lock();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void write_into_lock() {
        lock_3.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",5));
        lock_4.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",20));
        lock_5.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",25));
        lock_6.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",30));
        lock_7.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",35));
        lock_8.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",40));
        lock_9.setText(getResources().getString(R.string.lev)+" "+String.format("%01d",45));
    }

    public void disabled_button(){
        mother_activity.after_pause=true;
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
    }

    public void enabled__buttons(){
        check_if_this_button_on(0,button1);
        check_if_this_button_on(1,button2);

        int m=new sql_manager(context).get__account().getLevel();
        if(m>=5){
            check_if_this_button_on(2,button3);
            lock_3.setVisibility(View.GONE);
        }else {
            lock_3.setVisibility(View.VISIBLE);
            button3.setEnabled(false);
        }
        if(m>=20){
            check_if_this_button_on(3,button4);
            lock_4.setVisibility(View.GONE);
        }else {
            lock_4.setVisibility(View.VISIBLE);
            button4.setEnabled(false);
        }
        if(m>=25){
            check_if_this_button_on(4,button5);
            lock_5.setVisibility(View.GONE);
        }else {
            lock_5.setVisibility(View.VISIBLE);
            button5.setEnabled(false);
        }
        if(m>=30){
            check_if_this_button_on(5,button6);
            lock_6.setVisibility(View.GONE);
        }else {
            lock_6.setVisibility(View.VISIBLE);
            button6.setEnabled(false);
        }
        if(m>=35){
            check_if_this_button_on(6,button7);
            lock_7.setVisibility(View.GONE);
        }else {
            lock_7.setVisibility(View.VISIBLE);
            button7.setEnabled(false);
        }
        if(m>=40){
            check_if_this_button_on(7,button8);
            lock_8.setVisibility(View.GONE);
        }else {
            lock_8.setVisibility(View.VISIBLE);
            button8.setEnabled(false);
        }
        if(m>=45){
            check_if_this_button_on(8,button9);
            lock_9.setVisibility(View.GONE);
        }else {
            lock_9.setVisibility(View.VISIBLE);
            button9.setEnabled(false);
        }







    }


}