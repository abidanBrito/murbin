/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 12:16
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 12:16
 */

package com.example.murbin.data.repositories;

import com.example.murbin.models.Zone;

import java.util.Map;

public interface ZonesDatabaseCrudRepository {

    /**
     * Create a zones in the database
     *
     * @param zones          Zone
     * @param createListener Listener to get response asynchronously
     */
    void create(Zone zones, CreateListener createListener);

    /**
     * Create a zones in the database
     *
     * @param idDoc          Document identifier
     * @param zones          Zone
     * @param createListener Listener to get response asynchronously
     */
    void create(String idDoc, Zone zones, CreateListener createListener);

    /**
     * Read a zones from the database
     *
     * @param id           Id from zones
     * @param readListener Listener to get User asynchronously
     */
    void read(String id, ReadListener readListener);

    /**
     * Update a database zones
     *
     * @param id             Id from zones
     * @param data           Custom map with data
     * @param updateListener Listener to get response asynchronously
     */
    void update(String id, Map<String, Object> data, UpdateListener updateListener);

    /**
     * Delete a zones from the database
     *
     * @param id             Id from zones
     * @param deleteListener Listener to get response asynchronously
     */
    void delete(String id, DeleteListener deleteListener);

    /**
     * Listener for Read to get Zone asynchronously
     */
    interface ReadListener {
        void onResponse(Zone zone);
    }

    /**
     * Listener for Create to get response asynchronously
     */
    interface CreateListener {
        void onResponse(String documentId);
    }

    /**
     * Listener for Update to get response asynchronously
     */
    interface UpdateListener {
        void onResponse(Boolean response);
    }

    /**
     * Listener for Delete to get response asynchronously
     */
    interface DeleteListener {
        void onResponse(Boolean response);
    }
}
