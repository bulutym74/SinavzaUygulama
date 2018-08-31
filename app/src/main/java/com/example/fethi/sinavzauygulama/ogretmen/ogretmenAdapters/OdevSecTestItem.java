package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class OdevSecTestItem {
    private String testAdi = "";
    private int soruSayisi = 0;
    private int id = 0;
    private boolean selected = false;
    private int status = 0;

    public OdevSecTestItem(String testAdi, int soruSayisi, int id,int status) {
        this.testAdi = testAdi;
        this.soruSayisi = soruSayisi;
        this.id = id;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTestAdi() {
        return testAdi;
    }

    public void setTestAdi(String testAdi) {
        this.testAdi = testAdi;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
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
