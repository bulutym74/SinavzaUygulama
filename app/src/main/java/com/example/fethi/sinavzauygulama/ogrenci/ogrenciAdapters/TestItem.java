package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

public class TestItem {

    private String testAdi;
    private int soruSayisi;
    private String sonTarih;
    private int dogru;
    private int yanlis;
    private int id;
    private int status;

    public TestItem(String testAdi, int soruSayisi, String sonTarih,int id,int status) {
        this.testAdi = testAdi;
        this.soruSayisi = soruSayisi;
        this.sonTarih = sonTarih;
        this.id = id;
        this.status = status;
    }
    public TestItem(String testAdi, int status) {
        this.testAdi = testAdi;
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

    public String getSonTarih() {
        return sonTarih;
    }

    public void setSonTarih(String sonTarih) {
        this.sonTarih = sonTarih;
    }

    public int getDogru() {
        return dogru;
    }

    public void setDogru(int dogru) {
        this.dogru = dogru;
    }

    public int getYanlis() {
        return yanlis;
    }

    public void setYanlis(int yanlis) {
        this.yanlis = yanlis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
