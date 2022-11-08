package com.damasingo.ADAPTER;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.damasingo.CLASS_UTIL.info_rule;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.activity_more_info;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

public class list_info_and_rule extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<info_rule> all_data;
    Context context;
    AppCompatActivity activity;
    sql_manager db_offline;
    String type_of_background;




    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_title,txt_description;
        private final WebView video_view;
        private final SelectableRoundedImageView image_view;
        private final LottieAnimationView lottie;
        private final Button btn_video;


        MenuItemViewHolder(View view) {
            super(view);
            txt_title= view.findViewById(R.id.title);
            txt_description= view.findViewById(R.id.txt_description);
            video_view= view.findViewById(R.id.videoView);
            image_view= view.findViewById(R.id.image_view);
            lottie= view.findViewById(R.id.lottie_wait);
            btn_video=view.findViewById(R.id.btn_video);
        }
    }

    public list_info_and_rule(ArrayList<info_rule> all_rule_or_info, Context context,AppCompatActivity activity) {
        all_data=all_rule_or_info;
        this.context=context;
        this.activity=activity;
        db_offline= new sql_manager(context);

        if (activity instanceof activity_more_info)
            type_of_background="normal";
        else
            type_of_background="alert";
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_info, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        info_rule info_rule =  all_data.get(position);

        menuItemHolder.txt_title.setText(info_rule.getTitle());
        menuItemHolder.txt_description.setText(info_rule.getDescription());

        if(info_rule.getPath().equals("null")){
            menuItemHolder.image_view.setVisibility(View.GONE);
            menuItemHolder.lottie.setVisibility(View.GONE);
            menuItemHolder.video_view.setVisibility(View.GONE);
        }
        else if(info_rule.getPath().equals("image")){
            menuItemHolder.image_view.setVisibility(View.VISIBLE);

            remplire_Image(b -> activity.runOnUiThread(() -> menuItemHolder.image_view.setImageBitmap(b)));

        }else {
            menuItemHolder.btn_video.setVisibility(View.GONE);
        }

      /*  menuItemHolder.btn_video.setOnClickListener(v -> {
            get_and_remplire_video(menuItemHolder.lottie, menuItemHolder.video_view, info_rule.getPath());
            v.setVisibility(View.GONE);
        });

       */

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void get_and_remplire_video(LottieAnimationView lottie, WebView video_view, String video_id) {


            String lien=get_complet_lien(video_id);
           // String frameVideo = "<html><body><br><iframe width=\"320\" height=\"200\" src=\"https://www.youtube.com/embed/XDYbEuY8nIc\" frameborder=\"0\" allowfullscreen></iframe></body></html>";


            final boolean[] loadingFinished = {true};
            final boolean[] redirect = {false};

            video_view.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                    if (!loadingFinished[0]) {
                        redirect[0] = true;
                    }

                    loadingFinished[0] = false;
                    view.loadUrl(urlNewString);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                    loadingFinished[0] = false;
                    //SHOW LOADING IF IT ISNT ALREADY VISIBLE
                    lottie.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if(!redirect[0]){
                        loadingFinished[0] = true;
                    }

                    if(loadingFinished[0] && !redirect[0]){
                        video_view.setVisibility(View.VISIBLE);
                        lottie.setVisibility(View.GONE);
                    } else{
                        redirect[0] = false;
                    }

                }

            });

            WebSettings webSettings = video_view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            video_view.loadData(lien, "text/html", "utf-8");


    }

    private String get_complet_lien(String video_id) {
        String frameVideo = "<html><head>";
        if(type_of_background.equals("normal")) {
            if (db_offline.get_all_setting().get(0).getChoice().equals("وضع ليلي")) {
                frameVideo += "<style>body {background-color:rgb(39, 39, 39);}</style>";
            } else {
                frameVideo += "<style>body {background-color:rgb(239, 237, 237);}</style>";
            }
        }else {
            if (db_offline.get_all_setting().get(0).getChoice().equals("وضع ليلي")) {
                frameVideo += "<style>body {background-color:rgb(0, 0, 0);}</style>";
            } else {
                frameVideo += "<style>body {background-color:rgb(255, 255, 255);}</style>";
            }
        }
        frameVideo += "</head>";
        frameVideo+="<body><br><iframe width=\"320\" height=\"200\" src=\"https://www.youtube.com/embed/";
        frameVideo+=video_id;
        frameVideo+="\" frameborder=\"0\" allowfullscreen></iframe></body></html>";


        return frameVideo;

    }


    @Override
    public int getItemCount() {
        return all_data.size();
    }


    int count=0;
    public void remplire_Image(image image){

        Glide.with(context)
                .load("https://img.freepik.com/photos-gratuite/restez-maison-concept-quarantaine-mains-jeune-enfant-jouant-au-jeu-table-dames-lit-jeu-societe-concept-loisirs-pour-enfants-du-temps-famille_97255-398.jpg")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(count<10)
                            remplire_Image(image);
                        count++;
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable bb, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        BitmapDrawable d=(BitmapDrawable)bb;
                        Bitmap resource=d.getBitmap();
                        image.onCallback(resource);
                        return false;
                    }
                }).submit();

    }

    public interface image{
        void onCallback(Bitmap b);
    }


}
