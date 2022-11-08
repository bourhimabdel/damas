package com.damasingo.CLASS_UTIL;

public class pack_generator {
    int actuel_pack,place;

    public pack_generator(){}

    public pack_generator(int actuel_pack, int place) {
        this.actuel_pack = actuel_pack;
        this.place = place;
    }

    public int getActuel_pack() {
        return actuel_pack;
    }

    public void setActuel_pack(int actuel_pack) {
        this.actuel_pack = actuel_pack;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
