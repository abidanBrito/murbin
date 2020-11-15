/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.model;

import android.net.Uri;

public class UserModel {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserModel.class.getSimpleName();

    private String rol;
    private String uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String provider;
    private Uri picture;

    /**
     * Constructor Empty
     */
    public UserModel() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param rol
     * @param uid
     * @param name
     * @param surname
     * @param email
     * @param phone
     * @param provider
     * @param picture
     */
    public UserModel(String rol, String uid, String name, String surname, String email, String phone, String provider, Uri picture) {
        this.rol = rol;
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.provider = provider;
        this.picture = picture;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "rol='" + rol + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", provider='" + provider + '\'' +
                ", picture=" + picture +
                '}';
    }
}
