/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 12:16
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 12:16
 */

package com.example.murbin.data;

import com.example.murbin.data.repositories.UserListRepository;
import com.example.murbin.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserList implements UserListRepository {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserList.class.getSimpleName();

    protected List<User> listUsers;

    /**
     * Constructor
     */
    public UserList() {
        listUsers = new ArrayList<User>();
    }

    @Override
    public int addEmptyElement() {
        User user = new User();
        listUsers.add(user);

        return (listUsers.size() - 1);
    }

    @Override
    public void addElement(User user) {
        listUsers.add(user);
    }

    @Override
    public User readElement(int position) {
        return null;
    }

    @Override
    public void updateElement(int position, User user) {
        listUsers.set(position, user);
    }

    @Override
    public void deleteElement(int position) {
        listUsers.remove(position);
    }

    @Override
    public int qtyElements() {
        return listUsers.size();
    }

}
