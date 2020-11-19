/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 12:16
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 12:16
 */

package com.example.murbin.data.repositories;

import com.example.murbin.models.User;

public interface UserCrudRepository {

    /**
     * Create a user in the database
     *
     * @param user User
     * @return String
     */
    String create(User user);

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
     * @param id   Id from user
     * @param user User
     */
    void update(String id, User user);

    /**
     * Delete a user from the database
     *
     * @param id Id from user
     */
    void delete(String id);

    /**
     * Listener to get User asynchronously
     */
    interface ReadListener {
        void onResponse(User user);
    }
}
