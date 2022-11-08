package com.damasingo.CLASS_UTIL;

public class score {
    /////// id of the other player
    String id;

    ////// score like 4-5 or 0-1
    String score;

    public score(String id, String score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
