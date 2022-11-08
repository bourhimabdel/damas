package com.damasingo.CLASS_UTIL;

import android.graphics.drawable.Drawable;

public class star_data {
    Drawable star;
    String description;

    public star_data(Drawable star, String description) {
        this.star = star;
        this.description = description;
    }

    public Drawable getStar() {
        return star;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
