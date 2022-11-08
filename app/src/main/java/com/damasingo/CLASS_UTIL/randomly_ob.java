package com.damasingo.CLASS_UTIL;

public class randomly_ob {

    String player_1;
    String player_2;
    String etat;
    boolean open;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }




    public randomly_ob(String player_1, String player_2,String etat,boolean open) {
        this.player_1 = player_1;
        this.player_2 = player_2;
        this.etat=etat;
        this.open=open;
    }

    public randomly_ob() { }

    public String getPlayer_1() {
        return player_1;
    }

    public void setPlayer_1(String player_1) {
        this.player_1 = player_1;
    }

    public String getPlayer_2() {
        return player_2;
    }

    public void setPlayer_2(String player_2) {
        this.player_2 = player_2;
    }
}
