/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 12:16
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 12:16
 */

package com.example.murbin.data;

import com.example.murbin.App;
import com.example.murbin.data.repositories.UsersListRepository;
import com.example.murbin.models.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsersList implements UsersListRepository {

    private final Map<String, User> listUsers;

    /**
     * Constructor
     */
    public UsersList() {
        listUsers = new HashMap<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("role", App.ROLE_TECHNICIAN)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user;
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            user = document.toObject(User.class);
                            listUsers.put(user.getUid(), user);
                            /*
                             * I don't know why "user = document.toObject (User.class)" does
                             * not retrieve the date correctly, so the next two lines add the
                             * date correctly to the user. It would have to be investigated.
                             */
                            Timestamp ts = (Timestamp) document.getData().get("lastAccess");
                            user.setLastAccess(Objects.requireNonNull(ts).toDate());
                        }
                    } else {
                        //
                    }
                });
    }

    public Map<String, User> getListUsers() {
        return listUsers;
    }

    @Override
    public int addEmptyElement() {
        User user = new User();
        listUsers.put(user.getUid(), user);

        return (listUsers.size() - 1);
    }

    @Override
    public void addElement(User user) {
        listUsers.put(user.getUid(), user);
    }

    @Override
    public User readElement(String id) {
        return listUsers.get(id);
    }

    @Override
    public void updateElement(String id, User user) {
        listUsers.put(id, user);
    }

    @Override
    public void deleteElement(String id) {
        listUsers.remove(id);
    }

    @Override
    public int qtyElements() {
        return listUsers.size();
    }
}
