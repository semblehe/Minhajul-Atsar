package com.atsar.minhajul.model;

import com.google.gson.annotations.SerializedName;

public class ModelRadio {
    @SerializedName("judul")
    private String judul;

    @SerializedName("url")
    private String url;

    @SerializedName("pembicara")
    private String pembicara;

    @SerializedName("gambar")
    private String gambar;

    @SerializedName("siaran")
    private String siaran;

    @SerializedName("pendengar")
    private String pendengar;

    public String getJudul() {
        return judul;
    }

    public void getJudul(String judul) {
        this.judul = judul;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPembicara() {
        return pembicara;
    }

    public void setPembicara(String pembicara) {
        this.pembicara = pembicara;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSiaran() {
        return siaran;
    }

    public void setSiaran (String siaran) {
        this.siaran = siaran;
    }

    public String getPendengar() {
        return pendengar;
    }

    public void setPendengar (String siaran) {
        this.pendengar = pendengar;
    }
}
