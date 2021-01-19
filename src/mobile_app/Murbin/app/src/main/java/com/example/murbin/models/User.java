/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.models;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static final String DEFAULT_ROLE = "user";

    private String role;
    private String uid;
    private String name;
    private String email;
    private String password;
    private String idSubzone;
    private Date lastAccess;

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
     * @param email      User email
     * @param lastAccess Last access user timestamp
     */
    public User(String role, String uid, String name, String email, String password, String idSubzone, Timestamp lastAccess) {
        this.role = ((role != null && !role.equals("")) ? role : DEFAULT_ROLE);
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.idSubzone = idSubzone;
        this.lastAccess = (lastAccess != null) ? lastAccess.toDate() : new Date(System.currentTimeMillis());
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

    public String getIdSubzone() {
        return idSubzone;
    }

    public void setIdSubzone(String idSubzone) {
        this.idSubzone = idSubzone;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String lastAccess_formatted = formatter.format(lastAccess);

        return "User{" +
                "role='" + role + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", idSubzone='" + idSubzone + '\'' +
                ", lastAccess=" + lastAccess_formatted +
                '}';
    }

    /**
     * Transform User to Map
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> parseToMap() {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("role", this.getRole());
        userMap.put("uid", this.getUid());
        userMap.put("name", this.getName());
        userMap.put("email", this.getEmail());
        userMap.put("password", this.getPassword());
        userMap.put("idSubzone", this.getIdSubzone());
        userMap.put("lastAccess", new Timestamp(this.getLastAccess()));

        return userMap;
    }
}
