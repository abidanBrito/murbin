/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.models;

import android.net.Uri;

public class User {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = User.class.getSimpleName();

    private String role;
    private String idDocument;
    private String uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String providers;
    private Uri photo;

    /**
     * Constructor Default
     */
    public User() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param role       User role
     * @param idDocument idDocument user
     * @param uid        Unique uid of the user in firebase
     * @param name       User name
     * @param surname    User last name
     * @param email      User email
     * @param phone      User phone
     * @param providers  Providers you have logged in with
     * @param photo      User Photo
     */
    public User(String role, String idDocument, String uid, String name, String surname, String email, String phone, String providers, Uri photo) {
        this.role = role;
        this.idDocument = idDocument;
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.providers = providers;
        this.photo = photo;
    }

    public String getRol() {
        return role;
    }

    public void setRol(String role) {
        this.role = role;
    }

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
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
        return providers;
    }

    public void setProvider(String providers) {
        this.providers = providers;
    }

    public Uri getPicture() {
        return photo;
    }

    public void setPicture(Uri photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", providers='" + providers + '\'' +
                ", photo=" + photo +
                '}';
    }
}
