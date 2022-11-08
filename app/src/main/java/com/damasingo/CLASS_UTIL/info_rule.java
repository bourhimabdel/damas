package com.damasingo.CLASS_UTIL;

public class info_rule {

    String title,description,path;

    public info_rule(String title, String description, String path) {
        this.title = title;
        this.description = description;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
