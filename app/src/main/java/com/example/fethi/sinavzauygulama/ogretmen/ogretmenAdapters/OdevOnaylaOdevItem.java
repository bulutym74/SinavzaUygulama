package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class OdevOnaylaOdevItem {

    private String testAdi;
    private String kitapAdi;
    private String konuAdi;
    private int id;
    private boolean selected = false;

    public OdevOnaylaOdevItem(String testAdi, String kitapAdi, String konuAdi, int id) {
        this.testAdi = testAdi;
        this.kitapAdi = kitapAdi;
        this.konuAdi = konuAdi;
        this.id = id;
    }

    public String getTestAdi() {
        return testAdi;
    }

    public void setTestAdi(String testAdi) {
        this.testAdi = testAdi;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
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
