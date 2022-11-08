package com.damasingo.ADAPTER;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damasingo.CLASS_UTIL.aide;
import com.damasingo.CLASS_UTIL.star_data;
import com.damasingo.R;

import java.util.ArrayList;

public class list_start_info extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<star_data> all_data;


    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_description;
        private final ImageView img_star;


        MenuItemViewHolder(View view) {
            super(view);
            img_star= view.findViewById(R.id.img_level);
            txt_description= view.findViewById(R.id.txt_name);
        }
    }

    public list_start_info(Context context) {
        all_data=new aide().get_all_start(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.star_view, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        star_data info_rule =  all_data.get(position);

        menuItemHolder.img_star.setBackground(info_rule.getStar());
        menuItemHolder.txt_description.setText(info_rule.getDescription());
    }


    @Override
    public int getItemCount() {
        return 10;
    }




}
