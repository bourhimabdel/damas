package com.damasingo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.Snack_bar.sandbar_info;
import com.joooonho.SelectableRoundedImageView;


public class fragment_account extends Fragment {

    public SelectableRoundedImageView img_profile;
    public TextView txt_current_level_1,txt_next_level,txt_name,txt_point_experience,
    txt_id,txt_total_win,txt_rank,txt_game_win,txt_win_porcentage,img_country;
    ImageButton btn_copy,btn_info;
    Button btn_edit;
    ProgressBar progressBar_next_level;
    //ConstraintLayout edit;


    account info_account;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_account, container, false);

        Initial_view(v);
        charger_data();
        click_listner();

        return v;
    }

    private void click_listner() {
        Activity activity=getActivity();

        btn_copy.setOnClickListener(v -> {
            String share=info_account.getId();
            assert activity != null;
            ShareCompat.IntentBuilder
                    .from(activity)
                    .setText(share)
                    .setType("text/plain")
                    .setChooserTitle(R.string.app_name)
                    .startChooser();
        });

        //btn_add_coins_wallet.setOnClickListener(v -> click_add_coins.clickAddCoins());

        btn_info.setOnClickListener(v -> {
            Praincipal m_mother_activty= (Praincipal) getActivity();
            assert m_mother_activty != null;
            m_mother_activty.press_back_tacker.add(0);
            m_mother_activty.snack_bar.snack_bar_start(getContext(),m_mother_activty);

        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Praincipal m_mother_activty= (Praincipal) getActivity();
                assert m_mother_activty != null;
                m_mother_activty.press_back_tacker.add(0);
                m_mother_activty.snack_bar.snack_select_update(getContext(), (AppCompatActivity) activity, new sandbar_info.select() {
                    @Override
                    public void on_callback(int selected){
                        if(selected==0)
                            m_mother_activty.snack_bar.snack_bar_update_name(getContext(),m_mother_activty);
                        else
                            m_mother_activty.snack_bar.snack_bar_select_puc(getContext(),m_mother_activty);
                    }
                });
            }
        });

      // edit.setOnClickListener(new View.OnClickListener() {
      //     @Override
      //     public void onClick(View view) {
      //         btn_edit.performClick();
      //     }
      // });

        txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Praincipal m_mother_activty= (Praincipal) getActivity();
                assert m_mother_activty != null;
                m_mother_activty.press_back_tacker.add(0);
                m_mother_activty.snack_bar.snack_bar_update_name(getContext(),m_mother_activty);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Praincipal m_mother_activty= (Praincipal) getActivity();
                assert m_mother_activty != null;
                m_mother_activty.press_back_tacker.add(0);
                m_mother_activty.snack_bar.snack_bar_select_puc(getContext(),m_mother_activty);
            }
        });
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void charger_data() {

        byte[] tof_saved=info_account.getPhoto_saved();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(tof_saved, 0, tof_saved.length);
        img_profile.setImageBitmap(decodedByte);

        txt_name.setText(info_account.getName());


        txt_current_level_1.setText(String.format("%01d",info_account.getLevel()));
        txt_current_level_1.setBackground(new aide().get_the_star_compatible(info_account.getLevel(),getContext()));
        txt_next_level.setText(String.format("%01d",(info_account.getLevel()+1)));
        txt_next_level.setBackground(new aide().get_the_star_compatible(info_account.getLevel()+1,getContext()));
        txt_point_experience.setText(String.format("\t%02d /%02d\t",info_account.getPoint_experience(),new aide().get_max_point_experience((info_account.getLevel()+1))));
        txt_id.setText(info_account.getId());
        txt_total_win.setText(new aide().get_the_string_compatible(info_account.getTotal_money_win(),context));
        //txt_coins_wallet.setText(String.format("\t%02d\t",info_account.getMoney()));
        txt_rank.setText(new aide().get_rank(info_account.getLevel(),context));
        txt_game_win.setText(String.format("%02d /%02d",info_account.getVictoir_gain(),(info_account.getVictoir_gain()+info_account.getVictoir_loss())));
        txt_win_porcentage.setText(String.format("%.2f %2$s",new aide().get_porcentage(info_account.getVictoir_gain(),info_account.getVictoir_loss()),"%"));
        img_country.setText(new aide().get_my_name_country(info_account.getCountry())+"\t"+new aide().country_to_emojie(info_account.getCountry()));



        progressBar_next_level.setMax(new aide().get_max_point_experience((info_account.getLevel()+1)));
        progressBar_next_level.setProgress(info_account.getPoint_experience());
        progressBar_next_level.setProgressDrawable(new aide().get_the_progress_compatible(info_account.getLevel()+1,getContext()));
    }

    private void Initial_view(View view) {

        info_account=new sql_manager(getContext()).get__account();
        click_add_coins=(Praincipal)getActivity();
        context=getContext();

        img_profile=view.findViewById(R.id.image_profil);
        txt_current_level_1=view.findViewById(R.id.img_current_level);
        txt_next_level=view.findViewById(R.id.img_next_level);
        txt_name=view.findViewById(R.id.txt_name);
        txt_point_experience=view.findViewById(R.id.txt_point_experience);
        txt_id=view.findViewById(R.id.txt_id);
        txt_total_win=view.findViewById(R.id.text_total_win);
        txt_rank=view.findViewById(R.id.text_rank);
        txt_game_win=view.findViewById(R.id.text_Games_won);
        txt_win_porcentage=view.findViewById(R.id.text_Win_percentage);
        btn_copy=view.findViewById(R.id.btn_share_id);
        progressBar_next_level=view.findViewById(R.id.progressbar);
        btn_info=view.findViewById(R.id.btn_info);
        btn_edit=view.findViewById(R.id.btn_edit);
       // edit=view.findViewById(R.id.level_container);
        img_country=view.findViewById(R.id.img_country);
        //img_country2=view.findViewById(R.id.img_country2);
    }


    interface click_add_coins {
          void clickAddCoins();
    }
    private click_add_coins click_add_coins;
}