package com.damasingo.CLASS_UTIL;

public class setting {

    int id;
    String name,choice;

    public setting(int id, String name, String choice) {
        this.id = id;
        this.name = name;
        this.choice = choice;
    }

    public setting() {
    }

    public setting(String name, String choice) {
        this.name = name;
        this.choice = choice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
