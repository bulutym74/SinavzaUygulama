package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ListItemPuanlarim extends RealmObject{

    @PrimaryKey
    private String puanAdi;
    private Date tarih;
    private Double obp,tytnet,mfnet,tmnet,tsnet,dilnet,tytham,mfham,tmham,tsham,dilham,tytyer,mfyer,tmyer,tsyer,dilyer = 0.0;

    public String getPuanAdi() {
        return puanAdi;
    }

    public void setPuanAdi(String puanAdi) {
        this.puanAdi = puanAdi;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public Double getObp() {
        return obp;
    }

    public void setObp(Double obp) {
        this.obp = obp;
    }

    public Double getTytnet() {
        return tytnet;
    }

    public void setTytnet(Double tytnet) {
        this.tytnet = tytnet;
    }

    public Double getMfnet() {
        return mfnet;
    }

    public void setMfnet(Double mfnet) {
        this.mfnet = mfnet;
    }

    public Double getTmnet() {
        return tmnet;
    }

    public void setTmnet(Double tmnet) {
        this.tmnet = tmnet;
    }

    public Double getTsnet() {
        return tsnet;
    }

    public void setTsnet(Double tsnet) {
        this.tsnet = tsnet;
    }

    public Double getDilnet() {
        return dilnet;
    }

    public void setDilnet(Double dilnet) {
        this.dilnet = dilnet;
    }

    public Double getTytham() {
        return tytham;
    }

    public void setTytham(Double tytham) {
        this.tytham = tytham;
    }

    public Double getMfham() {
        return mfham;
    }

    public void setMfham(Double mfham) {
        this.mfham = mfham;
    }

    public Double getTmham() {
        return tmham;
    }

    public void setTmham(Double tmham) {
        this.tmham = tmham;
    }

    public Double getTsham() {
        return tsham;
    }

    public void setTsham(Double tsham) {
        this.tsham = tsham;
    }

    public Double getDilham() {
        return dilham;
    }

    public void setDilham(Double dilham) {
        this.dilham = dilham;
    }

    public Double getTytyer() {
        return tytyer;
    }

    public void setTytyer(Double tytyer) {
        this.tytyer = tytyer;
    }

    public Double getMfyer() {
        return mfyer;
    }

    public void setMfyer(Double mfyer) {
        this.mfyer = mfyer;
    }

    public Double getTmyer() {
        return tmyer;
    }

    public void setTmyer(Double tmyer) {
        this.tmyer = tmyer;
    }

    public Double getTsyer() {
        return tsyer;
    }

    public void setTsyer(Double tsyer) {
        this.tsyer = tsyer;
    }

    public Double getDilyer() {
        return dilyer;
    }

    public void setDilyer(Double dilyer) {
        this.dilyer = dilyer;
    }
}
