package com.damasingo.CLASS_UTIL;

public class account {

    String image;
    String name;
    String id;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    String country;
    int point_experience;
    double money;
    double total_money_win;



    int level;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getTotal_money_win() {
        return total_money_win;
    }

    public void setTotal_money_win(double total_money_win) {
        this.total_money_win = total_money_win;
    }

    int victoir_gain;
    int victoir_loss;
    byte[] photo_saved;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public byte[] getPhoto_saved() {
        return photo_saved;
    }

    public void setPhoto_saved(byte[] photo_saved) {
        this.photo_saved = photo_saved;
    }



    public account(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoint_experience() {
        return point_experience;
    }

    public void setPoint_experience(int point_experience) {
        this.point_experience = point_experience;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVictoir_gain() {
        return victoir_gain;
    }

    public void setVictoir_gain(int victoir_gain) {
        this.victoir_gain = victoir_gain;
    }

    public int getVictoir_loss() {
        return victoir_loss;
    }

    public void setVictoir_loss(int victoir_loss) {
        this.victoir_loss = victoir_loss;
    }



}
