package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class GenelKitapItem {
    private String kitapAdi = "";
    private int testSayisi = 0;

    public GenelKitapItem(String kitapAdi, int testSayisi) {
        this.kitapAdi = kitapAdi;
        this.testSayisi = testSayisi;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public int getTestSayisi() {
        return testSayisi;
    }

    public void setTestSayisi(int testSayisi) {
        this.testSayisi = testSayisi;
    }
}
