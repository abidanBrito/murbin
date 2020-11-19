/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.models;

public class User {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = User.class.getSimpleName();

    private static final String DEFAULT_ROLE = "user";

    private String role;
    private String uid;
    private String name;
    private String surname;
    private String email;
    private long lastAccess;

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
     * @param uid        Unique uid of the user in firebase
     * @param name       User name
     * @param surname    User last name
     * @param email      User email
     * @param lastAccess Last access user datetime
     */
    public User(String role, String uid, String name, String surname, String email, String lastAccess) {
        this.role = ((role != null && !role.equals("")) ? role : DEFAULT_ROLE);
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.lastAccess = ((lastAccess != null && !lastAccess.equals("")) ? Long.parseLong(lastAccess) : System.currentTimeMillis());
    }

    public static String getDefaultRole() {
        return DEFAULT_ROLE;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", lastAccess=" + lastAccess +
                '}';
    }
}
