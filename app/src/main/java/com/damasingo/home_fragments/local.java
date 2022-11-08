package com.damasingo.home_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.sound;
import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.local_match.local_game_space;
import com.damasingo.local_match.local_game_space_robot;

import java.util.ArrayList;


public class local extends Fragment {


    public Button btn_start_a_local_match,btn_start_a_local_match_robot;
    ImageButton btn_info_lcal;
    Praincipal  m_mother_activty;
    //LottieAnimationView click;
    sharedpreferences sharedpreferences;
    sql_manager db_offline;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_local, container, false);

        Initial_view(view);
        click_listner();



        return view;
    }

    private void Initial_view(View view) {

        context=getContext();
        db_offline=new sql_manager(getContext());
        m_mother_activty= (Praincipal) getActivity();
        sharedpreferences=new sharedpreferences(getContext());

        btn_start_a_local_match=view.findViewById(R.id.btn_play_local_match);
        btn_info_lcal=view.findViewById(R.id.btn_info_local_match);
        btn_start_a_local_match_robot=view.findViewById(R.id.btn_play_local_match_robot);
        //click=view.findViewById(R.id.lottie_select);
    }

    private void click_listner() {

   /*     btn_start_a_local_match.setOnClickListener(v -> {
            if(sharedpreferences.get_tab_info("click_online")) {
                sharedpreferences.put_tab_info("click_online", true);
               // click.setVisibility(View.GONE);
            }


            if(db_offline.get__account().getMoney()>=25) {
                v.setEnabled(false);
                new sound().collect(getContext());

                account account=db_offline.get__account();
                double new_money=account.getMoney()-25;

                new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                });

                account.setMoney(new_money);
                db_offline.Update_V_A_acoount(getContext(),account);

                Praincipal activity = (Praincipal)  getContext();
                assert activity != null;
                activity.after_pause=true;

                Toast.makeText(getContext(), String.format("- %01d %2$s",25,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), local_game_space.class));
            }else
            {
                String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",25)
                        +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);
            }

        });


    */

        btn_start_a_local_match.setOnClickListener(v -> {
            if(sharedpreferences.get_tab_info("click_online")) {
                sharedpreferences.put_tab_info("click_online", true);
             //   click.setVisibility(View.GONE);
            }

            double d;
            if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")) {
                d=new sharedpreferences(getContext()).get_score_offline();
            }else {
                d=db_offline.get__account().getMoney();
            }

            if(d>=25) {
                v.setEnabled(false);
                new sound().collect(getContext());

                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()-25);
                }else {
                    account account=db_offline.get__account();
                    double new_money=account.getMoney()-25;


                    new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                    });

                    account.setMoney(new_money);
                    db_offline.Update_V_A_acoount(getContext(),account);
                }

                Praincipal activity = (Praincipal)  getContext();
                assert activity != null;
                activity.after_pause=true;

                Toast.makeText(getContext(), String.format("- %01d %2$s",25,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), local_game_space.class));
            }else
            {
                String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",25)
                        +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);
            }

        });

       /* btn_start_a_local_match_robot.setOnClickListener(v -> {
            if(sharedpreferences.get_tab_info("click_online")) {
                sharedpreferences.put_tab_info("click_online", true);
                //click.setVisibility(View.GONE);
            }


            if(db_offline.get__account().getMoney()>=50) {
                v.setEnabled(false);
                new sound().collect(getContext());

                account account=db_offline.get__account();
                double new_money=account.getMoney()-50;

                new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                });

                account.setMoney(new_money);
                db_offline.Update_V_A_acoount(getContext(),account);

                Praincipal activity = (Praincipal)  getContext();
                assert activity != null;
                activity.after_pause=true;

                Toast.makeText(getContext(), String.format("- %01d %2$s",50,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), local_game_space_robot.class));
            }else
            {
                String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",50)
                        +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);
            }

        });

        */

        btn_start_a_local_match_robot.setOnClickListener(v -> {
            if(sharedpreferences.get_tab_info("click_online")) {
                sharedpreferences.put_tab_info("click_online", true);
               // click.setVisibility(View.GONE);
            }

            double d;
            if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")) {
                d=new sharedpreferences(getContext()).get_score_offline();
            }else {
                d=db_offline.get__account().getMoney();
            }

            if((double)d>=50) {
                v.setEnabled(false);
                new sound().collect(getContext());

                if(new sharedpreferences(getContext()).get_setting_type("user_is_not_sign_in")){
                    new sharedpreferences(getContext()).update_score_offline(new sharedpreferences(getContext()).get_score_offline()-50);
                }else {
                    account account=db_offline.get__account();
                    double new_money=account.getMoney()-50;


                    new connect_to_firebase().update_money(account.getId(), new_money, etat -> {

                    });

                    account.setMoney(new_money);
                    db_offline.Update_V_A_acoount(getContext(),account);
                }


                Praincipal activity = (Praincipal)  getContext();
                assert activity != null;
                activity.after_pause=true;

                Toast.makeText(getContext(), String.format("- %01d %2$s",50,getResources().getString(R.string.coins)), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), local_game_space_robot.class));
            }else
            {
                String m=getResources().getString(R.string.toplay)+" "+String.format("%02d",50)
                        +" "+getResources().getString(R.string.coins)+" "+getResources().getString(R.string.tocollect);
                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);
            }

        });

        btn_info_lcal.setOnClickListener(v -> {
            ArrayList<Integer> nums=new ArrayList<>();
            nums.add(8);

            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
        });
    }
}