package com.damasingo.home_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.randomly.selecto_fragment;

import java.util.ArrayList;

public class randomly extends Fragment {


    ImageButton btn_info_randomly;
    FrameLayout fram_randomly;
    Praincipal m_mother_activty;
    sql_manager db_offline;
    Button user_online;
    ConstraintLayout shimmer;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_randomly, container, false);

        iniciale_view(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        click_listner();
    }

    @SuppressLint("DefaultLocale")
    private void iniciale_view(View view) {
        context=getContext();
        fram_randomly = view.findViewById(R.id.view_pager_randomly);
        btn_info_randomly=view.findViewById(R.id.btn_info_online2);
        user_online=view.findViewById(R.id.num);
        shimmer=view.findViewById(R.id.shimmer);

        m_mother_activty= (Praincipal) getActivity();
        db_offline=new sql_manager(getContext());


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_pager_randomly,new selecto_fragment(), selecto_fragment.class.getName()).commit();

        m_mother_activty.txt_score.setText(new aide().get_the_string_compatible(db_offline.get__account().getMoney(),context));
    }


    private void click_listner() {

        new connect_to_firebase().get_number_user_online(new connect_to_firebase.time() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onCallback(long time) {
                user_online.setText(String.format("%1d",time));
                shimmer.setVisibility(View.GONE);
                user_online.setVisibility(View.VISIBLE);
                anim(user_online);
            }
        });

        btn_info_randomly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> nums=new ArrayList<>();
                nums.add(3);

                m_mother_activty.press_back_tacker.add(0);
                m_mother_activty.snack_bar.snack_bar_reglage("info",nums,getContext(),(AppCompatActivity) getActivity());
            }
        });
    }

    Animation animFade;
    public void anim(View view){
        animFade = AnimationUtils.loadAnimation(context, R.anim.fide_out_btn);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animFade);
    }

}