package com.atsar.minhajul.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListNotif {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("notif")
    @Expose
    private List<ModelNotif> notif = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ModelNotif> getData() {
        return notif;
    }

    public void setData(List<ModelNotif> data) {
        this.notif = data;
    }
}
