package com.damasingo.CLASS_UTIL;

public class reqeust implements Comparable<reqeust> {
    String id;
    String name;
    String image;
    String state;
    String type;
    int score_you_and_me;
    byte[] photo_saved;


    public int getScore_you_and_me() {
        return score_you_and_me;
    }

    public void setScore_you_and_me(int score_you_and_me) {
        this.score_you_and_me = score_you_and_me;
    }


    public reqeust(String id, String state,String type) {
        this.id = id;
        this.state = state;
        this.type=type;
    }
    public reqeust() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }





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

    public byte[] getPhoto_saved() {
        return photo_saved;
    }

    public void setPhoto_saved(byte[] photo_saved) {
        this.photo_saved = photo_saved;
    }

    @Override
    public int compareTo(reqeust reqeust) {
        return this.score_you_and_me-reqeust.score_you_and_me;
    }
}
