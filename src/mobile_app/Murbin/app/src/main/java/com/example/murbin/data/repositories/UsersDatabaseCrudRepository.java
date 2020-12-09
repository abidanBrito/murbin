/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 12:16
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 12:16
 */

package com.example.murbin.data.repositories;

import com.example.murbin.models.User;

import java.util.Map;

public interface UsersDatabaseCrudRepository {

    /**
     * Create a user in the database
     *
     * @param user           User
     * @param createListener Listener to get response asynchronously
     */
    void create(User user, CreateListener createListener);

    /**
     * Read a user from the database
     *
     * @param id           Id from user
     * @param readListener Listener to get User asynchronously
     */
    void read(String id, ReadListener readListener);

    /**
     * Update a database user
     *
     * @param id             Id from user
     * @param data           Custom map with data
     * @param updateListener Listener to get response asynchronously
     */
    void update(String id, Map<String, Object> data, UpdateListener updateListener);

    /**
     * Delete a user from the database
     *
     * @param id             Id from user
     * @param deleteListener Listener to get response asynchronously
     */
    void delete(String id, DeleteListener deleteListener);

    /**
     * Listener for Read to get User asynchronously
     */
    interface ReadListener {
        void onResponse(User user);
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
