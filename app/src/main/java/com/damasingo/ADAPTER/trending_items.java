package com.damasingo.ADAPTER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.gain_day;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.FIRE_BASE_MANAGER.connect_to_firebase;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.fragment_play_online.freind_online;
import com.damasingo.fragment_play_online.reqeust_sent;
import com.damasingo.home_;
import com.damasingo.home_fragments.online;
import com.damasingo.sign_in;
import com.damasingo.trending;
import com.damasingo.trending_frg.country;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class trending_items extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ArrayList<gain_day> all_gains=new ArrayList<>();
    public ArrayList<String> all_gains_brain=new ArrayList<>();
    public int Original_place=-1;
    Context context;
    Praincipal praincipal;
    connect_to_firebase db_online=new connect_to_firebase();
    account[] all_account_loaded;
    sql_manager db_offline;



    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_name,txt_level,txt_score,txt_country,txt_class,img_class;
        private final SelectableRoundedImageView image_profile;
        private final ConstraintLayout view_,view_pr;
        private final ShimmerFrameLayout shimmerFrameLayout;

        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            image_profile= view.findViewById(R.id.image_profil);
            txt_level= view.findViewById(R.id.img_level);
            txt_score= view.findViewById(R.id.txt_collect_score);
            txt_country= view.findViewById(R.id.txt_country);
            txt_class= view.findViewById(R.id.txt_calassment);
            img_class= view.findViewById(R.id.img_calassment);
            view_= view.findViewById(R.id.vi);
            view_pr= view.findViewById(R.id.pr);
            shimmerFrameLayout= view.findViewById(R.id.shimmerLayout);
        }
    }

    public trending_items(ArrayList<gain_day> all_gains, Context context, AppCompatActivity activity) {
        Collections.sort(all_gains);
        Collections.reverse(all_gains);

        this.context=context;
        this.all_gains = all_gains;
        praincipal = (Praincipal) activity;
        all_account_loaded=new account[all_gains.size()];
        db_offline=new sql_manager(context);

        for (gain_day g:all_gains) {
            all_gains_brain.add(g.getId());
        }

        for (gain_day g:all_gains) {
            if (db_offline.get__account().getId().equals(g.getId())) {
                if(all_gains.indexOf(g)!=0 && all_gains.indexOf(g)!=1 && all_gains.indexOf(g)!=2 && all_gains.indexOf(g)!=3) {
                    Original_place = all_gains.indexOf(g) + 1;
                    all_gains.remove(g);
                    all_gains.add(3, g);
                }
                if(all_gains.indexOf(g)==3)
                    Original_place = 4;
                break;
            }
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_tending, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        if(all_gains.size()>0) {
            gain_day items = all_gains.get(position);

            menuItemHolder.txt_score.setText(new aide().get_the_string_compatible(items.getGain(), context));

            String country_name= (String) new aide().country_day_converter(items.getCountry_day())[0];
            menuItemHolder.txt_country.setText(new aide().get_my_name_country(country_name) + "\t" + new aide().country_to_emojie(country_name));
            switch (position) {
                case 0:
                    menuItemHolder.img_class.setVisibility(View.VISIBLE);
                    menuItemHolder.img_class.setBackground(ContextCompat.getDrawable(context, R.drawable.o_first_place));
                    break;
                case 1:
                    menuItemHolder.img_class.setVisibility(View.VISIBLE);
                    menuItemHolder.img_class.setBackground(ContextCompat.getDrawable(context, R.drawable.o_second_place));
                    break;
                case 2:
                    menuItemHolder.img_class.setVisibility(View.VISIBLE);
                    menuItemHolder.img_class.setBackground(ContextCompat.getDrawable(context, R.drawable.o_thrid_place));
                    break;
                default:
                    menuItemHolder.txt_class.setVisibility(View.VISIBLE);
                    menuItemHolder.txt_class.setText(String.format("%01d",all_gains_brain.indexOf(items.getId()) + 1));


                    break;
            }


            get_ob_from_data_base(items.getId(), menuItemHolder, position);
            click_listner(menuItemHolder.view_,position);
        }
    }

    private void click_listner(ConstraintLayout view_, int position) {
        view_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                praincipal.press_back_tacker.add(0);
                praincipal.snack_bar.show_this_data("", context, praincipal,null,all_account_loaded[position]);
            }
        });
    }

    Animation animFade;
    public void anim(View view){
        animFade = AnimationUtils.loadAnimation(context, R.anim.scale_trending_me);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) { }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animFade);
    }

    private void get_ob_from_data_base(String id,MenuItemViewHolder menuItemHolder,int position) {
        db_online.user_exist(id, new connect_to_firebase.user_exist() {
            @Override
            public void exist(boolean etat, account acc) {
                menuItemHolder.txt_name.setText(acc.getName());
                menuItemHolder.txt_level.setText(String.format("%01d",acc.getLevel()));
                menuItemHolder.txt_level.setBackground(new aide().get_the_star_compatible(acc.getLevel(),context));


                download_Image(acc.getImage(), new image_ready() {
                    @Override
                    public void onCallback(Drawable bitmap) {

                        complete_load_image_work(bitmap,acc,position,menuItemHolder);
                    }
                });

            }
        });
    }

    public void download_Image(String url,image_ready image_ready){
        // Log.e("image is","gg "+account.getImage());
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        download_Image(url,image_ready);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        image_ready.onCallback(bb);
                        return false;
                    }
                }).submit();

    }

    private void complete_load_image_work(Drawable bb,account account,int position,MenuItemViewHolder menuItemHolder){
        BitmapDrawable d=(BitmapDrawable)bb;
        final Bitmap resource=d.getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        final byte[] b = baos.toByteArray();
        account.setPhoto_saved(b);

        all_account_loaded[position]=account;

        praincipal.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuItemHolder.image_profile.setImageBitmap(resource);
                menuItemHolder.shimmerFrameLayout.stopShimmer();
                menuItemHolder.shimmerFrameLayout.setVisibility(View.GONE);
                menuItemHolder.view_.setVisibility(View.VISIBLE);

                if(db_offline.get__account().getId().equals(account.getId())){
                    menuItemHolder.view_pr.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_items_tending_me));
                    menuItemHolder.txt_class.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_items_tending_me));
                   // menuItemHolder.txt_class.setText(""+Original_place);
                    menuItemHolder.view_pr.setScaleX(1.04f);
                    menuItemHolder.view_pr.setScaleY(1.04f);
                    anim( menuItemHolder.view_pr);
                }

            }
        });
    }

    public interface image_ready{
        void onCallback(Drawable bitmap);
    }

    @Override
    public int getItemCount() {
        if(all_gains.size()==0)
            return 3;
        return all_gains.size();
    }


}
