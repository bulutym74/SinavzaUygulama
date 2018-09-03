package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class OdevSecKonuItem {
    private String konuAdi;
    private ArrayList<OdevSecTestItem> testler;
    private int id = 0;
    private boolean selected = false;

    public OdevSecKonuItem(String konuAdi, ArrayList<OdevSecTestItem> testler) {
        this.konuAdi = konuAdi;
        this.testler = testler;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public ArrayList<OdevSecTestItem> getTestler() {
        return testler;
    }

    public void setTestler(ArrayList<OdevSecTestItem> testler) {
        this.testler = testler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
