package com.damasingo.CLASS_UTIL;

import java.io.File;
import java.io.Serializable;

public class audio_massage implements Serializable {
    String url;

    public String getBase_64() {
        return base_64;
    }

    public void setBase_64(String base_64) {
        this.base_64 = base_64;
    }

    String base_64;
    int seconds;
    File file;





    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }

    public audio_massage(){}

    public audio_massage(String url, int seconds) {
        this.url = url;
        this.seconds = seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
