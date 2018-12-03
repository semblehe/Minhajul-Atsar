package com.atsar.minhajul.model;

import com.google.gson.annotations.SerializedName;

public class ModelNotif {

    @SerializedName("notif")
    private String notif;

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }
}
