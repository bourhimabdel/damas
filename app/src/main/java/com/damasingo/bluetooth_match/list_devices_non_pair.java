package com.damasingo.bluetooth_match;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.damasingo.R;


import java.util.ArrayList;


public class list_devices_non_pair extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ArrayList<BluetoothDevice> all_devices;
    Context context;




    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_name,txt_id;
        private final Button btn_pair;



        MenuItemViewHolder(View view) {
            super(view);
            txt_name= view.findViewById(R.id.txt_name);
            txt_id= view.findViewById(R.id.txt_id);
            btn_pair= view.findViewById(R.id.btn_pear);
        }
    }

    public list_devices_non_pair(ArrayList<BluetoothDevice> all_friend_online, Context context) {
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

        menuItemHolder.btn_pair.setVisibility(View.VISIBLE);
        menuItemHolder.txt_id.setText(device.getAddress());
        menuItemHolder.txt_name.setText(device.getName());
        listner(menuItemHolder,device);

    }

    private void listner(MenuItemViewHolder menuItemHolder,BluetoothDevice device){

        menuItemHolder.btn_pair.setOnClickListener(v -> {
            menuItemHolder.btn_pair.setEnabled(false);
            menuItemHolder.btn_pair.setText(R.string.pairing);

            //create the bond.
            //NOTE: Requires API 17+? I think this is JellyBean
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
                device.createBond();
            }
        });

    }

    @Override
    public int getItemCount() {
        return all_devices.size();
    }


}
