package com.damasingo.SendNotificationPack;

public class Data {
    private String type;
    public final String id_user_send_request;
    private String etat;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Data(String type, String id_user_send_request) {
        this.type = type;
        this.id_user_send_request=id_user_send_request;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
