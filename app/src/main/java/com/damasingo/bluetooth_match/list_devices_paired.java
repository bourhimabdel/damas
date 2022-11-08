package com.damasingo.bluetooth_match;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damasingo.CLASS_UTIL.vibrateur;
import com.damasingo.Praincipal;
import com.damasingo.R;
import com.damasingo.SQL_MANAGER.sql_manager;
import com.damasingo.SharedPreferences.sharedpreferences;

import java.util.ArrayList;

public class list_devices_paired extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<BluetoothDevice> all_devices;
    Context context;




    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_name,txt_id;
        private final Button btn_play;



        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            txt_id= view.findViewById(R.id.txt_id);
            btn_play= view.findViewById(R.id.btn_play);
        }
    }

    public list_devices_paired(ArrayList<BluetoothDevice> all_friend_online, Context context) {
        all_devices=all_friend_online;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_object_bluetooth, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        BluetoothDevice device =  all_devices.get(position);

        menuItemHolder.btn_play.setVisibility(View.VISIBLE);
        menuItemHolder.txt_id.setText(device.getAddress());
        menuItemHolder.txt_name.setText(device.getName());

        click_listner(menuItemHolder.btn_play,device);
    }

    @SuppressLint("DefaultLocale")
    private void click_listner(Button btn_play, BluetoothDevice device) {
        btn_play.setOnClickListener(v -> {

            double d;
            if(new sharedpreferences(context).get_setting_type("user_is_not_sign_in")) {
                d=new sharedpreferences(context).get_score_offline();
            }else {
                d=new sql_manager(context).get__account().getMoney();
            }

            if(d>=75) {
                if(BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    Praincipal activity = (Praincipal) context;
                    activity.after_pause = true;

                    Intent table_game = new Intent(context, bluetooth_game_space.class);
                    table_game.putExtra("address", device.getAddress());
                    table_game.putExtra("count", 0);
                 //   Toast.makeText(context, String.format("- %01d %2$s",75,R.string.coins), Toast.LENGTH_SHORT).show();

                    context.startActivity(table_game);
                }else {
                    Toast.makeText(context, ""+context.getResources().getString(R.string.turn_on_bluetooth), Toast.LENGTH_LONG).show();
                    new vibrateur(context).vibrate(100);
                }
            }else
            {
                String m=context.getResources().getString(R.string.bluetooth)+" "+String.format("%02d",75)
                        +" "+context.getResources().getString(R.string.coins)+" "+context.getResources().getString(R.string.tocollect);

                Toast.makeText(context,m, Toast.LENGTH_LONG).show();
                new vibrateur(context).vibrate(100);

            }
        });
    }


    @Override
    public int getItemCount() {
        return all_devices.size();
    }


}
