package com.damasingo.CLASS_UTIL;

import java.io.Serializable;

public class conected_bluetouth_ob implements Serializable {
    String time_in;
    int type_match;

    public conected_bluetouth_ob(String time_in, int type_match) {
        this.time_in = time_in;
        this.type_match = type_match;
    }


    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public int getType_match() {
        return type_match;
    }

    public void setType_match(int type_match) {
        this.type_match = type_match;
    }
}
