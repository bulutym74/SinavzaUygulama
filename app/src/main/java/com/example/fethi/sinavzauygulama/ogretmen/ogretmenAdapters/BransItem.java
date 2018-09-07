package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class BransItem {

    private String bransAdi;
    private Boolean selected = false;
    private int id;

    public BransItem(String bransAdi, int id) {
        this.bransAdi = bransAdi;
        this.id = id;
    }

    public String getBransAdi() {
        return bransAdi;
    }

    public void setBransAdi(String bransAdi) {
        this.bransAdi = bransAdi;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
