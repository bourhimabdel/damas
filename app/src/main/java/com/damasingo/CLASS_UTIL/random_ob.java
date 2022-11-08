package com.damasingo.CLASS_UTIL;

public class random_ob {
    int num;
    int state;

    public random_ob(){}

    public random_ob(int num, int state) {
        this.num = num;
        this.state = state;
    }

    public random_ob(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
