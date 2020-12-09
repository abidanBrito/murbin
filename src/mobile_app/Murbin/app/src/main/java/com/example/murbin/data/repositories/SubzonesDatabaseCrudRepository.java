/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 12:16
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 12:16
 */

package com.example.murbin.data.repositories;

import com.example.murbin.models.Subzone;

import java.util.Map;

public interface SubzonesDatabaseCrudRepository {

    /**
     * Create a subzone in the database
     *
     * @param subzone        Subzone
     * @param createListener Listener to get response asynchronously
     */
    void create(Subzone subzone, CreateListener createListener);

    /**
     * Read a subzone from the database
     *
     * @param id           Id from subzone
     * @param readListener Listener to get User asynchronously
     */
    void read(String id, ReadListener readListener);

    /**
     * Update a database subzone
     *
     * @param id             Id from subzone
     * @param data           Custom map with data
     * @param updateListener Listener to get response asynchronously
     */
    void update(String id, Map<String, Object> data, UpdateListener updateListener);

    /**
     * Delete a subzone from the database
     *
     * @param id             Id from subzone
     * @param deleteListener Listener to get response asynchronously
     */
    void delete(String id, DeleteListener deleteListener);

    /**
     * Listener for Read to get Subzone asynchronously
     */
    interface ReadListener {
        void onResponse(Subzone subzone);
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
