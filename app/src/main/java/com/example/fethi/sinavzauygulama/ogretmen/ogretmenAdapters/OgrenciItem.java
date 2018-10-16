package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class OgrenciItem {

    private String ogrAdi;
    private Boolean selected = false;
    private int onaylanacakOdev = 0;
    private int id;

    public OgrenciItem(String ogrAdi,int id) {
        this.ogrAdi = ogrAdi;
        this.id = id;
    }

    public int getOnaylanacakOdev() {
        return onaylanacakOdev;
    }

    public void setOnaylanacakOdev(int onaylanacakOdev) {
        this.onaylanacakOdev = onaylanacakOdev;
    }

    public String getOgrAdi() {
        return ogrAdi;
    }

    public void setOgrAdi(String ogrAdi) {
        this.ogrAdi = ogrAdi;
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
