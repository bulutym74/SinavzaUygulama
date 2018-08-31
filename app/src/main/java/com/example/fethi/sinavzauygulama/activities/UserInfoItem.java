package com.example.fethi.sinavzauygulama.activities;

import io.realm.RealmObject;

public class UserInfoItem extends RealmObject {

    private String token = "";
    private String email = "";
    private String password = "";
    private int tur = 0;
    private int id = 0;

    public UserInfoItem() {}

    public UserInfoItem(String token, String email, String password,int tur) {
        this.token = token;
        this.email = email;
        this.password = password;
        this.tur = tur;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTur() {
        return tur;
    }

    public void setTur(int tur) {
        this.tur = tur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
