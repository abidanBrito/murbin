/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:30
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:30
 */

package com.example.murbin.models;

import java.util.HashMap;
import java.util.Map;

public class Subzone {

    private String name;
    private boolean status;

    /**
     * Constructor Default
     */
    public Subzone() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param name   Subzone name [ID]
     * @param status Status of the streetlights in the subzone [On/Off]
     */
    public Subzone(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subzone{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * Transform Subzone to Map
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> parseToMap() {
        Map<String, Object> subzoneMap = new HashMap<>();

        subzoneMap.put("name", this.getName());
        subzoneMap.put("status", this.isStatus());

        return subzoneMap;
    }
}
