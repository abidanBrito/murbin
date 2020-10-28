package com.example.murbin.model;

import android.net.Uri;

import com.example.murbin.presentation.LoginActivity;

public class UserModel {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserModel.class.getSimpleName();

    private Uri picture;
    private String name;
    private String email;
    private String phone;
    private String provider;
    private String uid;

    public UserModel() {
        //
    }

    public UserModel(Uri picture, String name, String email, String phone, String provider, String uid) {
        this.picture = picture;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.provider = provider;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "User{" +
                "picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", provider='" + provider + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
