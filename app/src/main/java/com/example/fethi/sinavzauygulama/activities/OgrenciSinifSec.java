package com.example.fethi.sinavzauygulama.activities;

public class OgrenciSinifSec{

    private String sinif;
    private int id;

    public OgrenciSinifSec(String sinif, int id) {
        this.sinif = sinif;
        this.id = id;
    }

    public String getSinif() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
