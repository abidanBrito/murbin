/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 11:55
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 11:55
 */

package com.example.murbin.data;

import androidx.annotation.NonNull;

import com.example.murbin.data.repositories.UsersDatabaseCrudRepository;
import com.example.murbin.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class UsersDatabaseCrud implements UsersDatabaseCrudRepository {

    private final CollectionReference users;

    /**
     * Constructor
     */
    public UsersDatabaseCrud() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        users = db.collection("users");
    }

    @Override
    public void create(User user, CreateListener createListener) {
        users.add(user.parseToMap()).addOnSuccessListener(documentReference -> {
            createListener.onResponse(documentReference.getId());
        }).addOnFailureListener(e -> {
            createListener.onResponse("");
        });
    }

    @Override
    public void read(String id, ReadListener readListener) {
        users.document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().toObject(User.class);
                readListener.onResponse(user);
            } else {
                readListener.onResponse(null);
            }
        });
    }

    @Override
    public void update(String id, Map<String, Object> data, UpdateListener updateListener) {
        users.document(id).update(data).addOnSuccessListener(aVoid -> {
            updateListener.onResponse(true);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                updateListener.onResponse(false);
            }
        });
    }

    @Override
    public void delete(String id, DeleteListener deleteListener) {
        users.document(id).delete().addOnSuccessListener(aVoid -> {
            deleteListener.onResponse(true);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteListener.onResponse(false);
            }
        });
    }

    public Query getByRole(String role) {
        return users.whereEqualTo("role", role);
    }
}
