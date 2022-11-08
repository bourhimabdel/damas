package com.damasingo.CLASS_UTIL;

import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_info {

    public account get_info_account(){

        FirebaseUser ff= FirebaseAuth.getInstance().getCurrentUser();

        account account = new account();

        assert ff != null;
        account.setId(ff.getUid());

        if(ff.getDisplayName()!=null)
            account.setName(ff.getDisplayName());
        else {
            account.setName("Visitor "+getDeviceName());
        }

        if(ff.getPhotoUrl()!=null)
            account.setImage(ff.getPhotoUrl().toString());
        else
            account.setImage("https://www.photoprof.fr/images_dp/photographes/profil_vide.jpg");
            //account.setImage("https://www.photoprof.fr/images_dp/photographes/profil_vide.jpg");



       // account.setBio(null);
        return account;
    }

    public String toString(account account){
        String m="id "+account.getId();
        m+="\nname "+account.getName();
        m+="\nimage "+account.getImage();
        return m ;
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


}
