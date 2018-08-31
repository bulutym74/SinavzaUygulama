package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

public class ListItemOzet {

    private String ders;
    private String test;
    private String soru;
    private int id;

    public ListItemOzet(String ders, String test, String soru,int id) {
        this.ders = ders;
        this.test = test;
        this.soru = soru;
        this.id = id;
    }

    public String getDers() {
        return ders;
    }

    public void setDers(String ders) {
        this.ders = ders;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
