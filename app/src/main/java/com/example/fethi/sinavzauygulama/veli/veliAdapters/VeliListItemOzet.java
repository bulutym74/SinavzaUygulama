package com.example.fethi.sinavzauygulama.veli.veliAdapters;

public class VeliListItemOzet {
    private String ders;
    private int test;
    private int soru;

    public VeliListItemOzet(String ders, int test, int soru) {
        this.ders = ders;
        this.test = test;
        this.soru = soru;
    }

    public String getDers() {
        return ders;
    }

    public void setDers(String ders) {
        this.ders = ders;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public int getSoru() {
        return soru;
    }

    public void setSoru(int soru) {
        this.soru = soru;
    }
}

