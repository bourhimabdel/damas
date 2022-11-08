package com.damasingo.CLASS_UTIL;

import android.content.Context;

import java.util.HashMap;

public class gain_day implements Comparable<gain_day>{
    String id;
    double gain;
    String country_day;
    int day;

    public String getCountry_day() {
        return country_day;
    }

    public void setCountry_day(String country_day) {
        this.country_day = country_day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public gain_day(String country, double gain, int day) {
        this.gain = gain;
        this.country_day=country+"-"+day;
        this.day=day;
    }

    public gain_day(String id, double gain) {
        this.gain = gain;
        this.id=id;
    }

    public gain_day(){}

    public gain_day(String country, double gain, String id,int h) {
        this.gain = gain;
        this.country_day=country+"-"+new aide().get_day_of_year();
        this.day=new aide().get_day_of_year();
        this.id=id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    @Override
    public int compareTo(gain_day gain_day) {
        return (int) (this.gain - gain_day.gain);
    }
}
