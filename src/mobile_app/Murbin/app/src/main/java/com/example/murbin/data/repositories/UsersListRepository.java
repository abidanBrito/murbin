/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 11:56
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 11:56
 */

package com.example.murbin.data.repositories;

import com.example.murbin.models.User;

public interface UsersListRepository {

    /**
     * Add the empty User object and return the position where it was added
     *
     * @return int Position where it has been added
     */
    int addEmptyElement();

    /**
     * Add the User object passed as a parameter
     *
     * @param user User object with the data to create
     */
    void addElement(User user);

    /**
     * Gets the User object at the position specified as a parameter
     *
     * @param id Id where the object is located
     * @return User
     */
    User readElement(String id);

    /**
     * Updates the User object at the indicated position with the data
     * from the received object.
     *
     * @param id   Id where the object is located
     * @param user User object with the data to update
     */
    void updateElement(String id, User user);

    /**
     * @param id Id where the object is located
     */
    void deleteElement(String id);

    /**
     * Returns the number of objects that are stored
     *
     * @return int Number of objects that are stored
     */
    int qtyElements();

}
