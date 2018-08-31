package com.example.fethi.sinavzauygulama.activities;

import android.os.Parcel;
import android.os.Parcelable;

public class OgrenciSinifSec implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public  OgrenciSinifSec createFromParcel(Parcel in) {
            return new OgrenciSinifSec(in);
        }

        public OgrenciSinifSec[] newArray(int size) {
            return new OgrenciSinifSec[size];
        }
    };


    private String sinif;
    private int id = 0;

    public OgrenciSinifSec(String sinif, int id) {
        this.sinif = sinif;
        this.id = id;
    }

    public String getSinif() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sinif);
        parcel.writeInt(this.id);
    }

    public OgrenciSinifSec(Parcel in) {
        this.id = in.readInt();
        this.sinif = in.readString();
    }
}
