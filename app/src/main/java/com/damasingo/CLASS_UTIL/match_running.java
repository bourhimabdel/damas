package com.damasingo.CLASS_UTIL;

import java.io.Serializable;
import java.util.ArrayList;

public class match_running implements Serializable {
    int piece;
    ArrayList<Integer> place;
    int operation;



    public match_running(){
        this.piece=0;
        this.operation=0;
        this.place=new ArrayList<>();
    }


    public String toString(){
        String m="piece "+this.getPiece();
        if(this.getOperation()==0)
            m+="\noperation "+"move";
        else
            m+="\noperation "+"eat";

        return m;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public ArrayList<Integer> getPlace() {
        return place;
    }

    public void setPlace(ArrayList<Integer> place) {
        this.place = place;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

}
