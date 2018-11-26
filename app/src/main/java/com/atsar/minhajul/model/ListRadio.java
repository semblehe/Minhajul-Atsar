package com.atsar.minhajul.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListRadio {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("radio")
    @Expose
    private List<ModelRadio> radio = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ModelRadio> getData() {
        return radio;
    }

    public void setData(List<ModelRadio> data) {
        this.radio = data;
    }
}
