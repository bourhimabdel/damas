package com.damasingo.CLASS_UTIL;

public class image_profile_data {
    int id_image;
    String lien_image;
    String str_prix;
    int prix;
    boolean opened;


    public image_profile_data() {
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public String getStr_prix() {
        return str_prix;
    }

    public void setStr_prix(String str_prix) {
        this.str_prix = str_prix;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public String getLien_image() {
        return lien_image;
    }

    public void setLien_image(String lien_image) {
        this.lien_image = lien_image;
    }
}
