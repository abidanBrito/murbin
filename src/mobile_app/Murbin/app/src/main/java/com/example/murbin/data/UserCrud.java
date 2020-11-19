/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 11:55
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 11:55
 */

package com.example.murbin.data;

import androidx.annotation.NonNull;

import com.example.murbin.data.repositories.UserCrudRepository;
import com.example.murbin.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UserCrud implements UserCrudRepository {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = UserCrud.class.getSimpleName();

    private final CollectionReference users;

    /**
     * Constructor
     */
    public UserCrud() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        users = db.collection("users");
    }

    @Override
    public String create(User user) {
        //

        return "";
    }

    @Override
    public void read(String id, ReadListener readListener) {
        users.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    User user = Objects.requireNonNull(task.getResult()).toObject(User.class);
                    readListener.onResponse(user);
                } else {
//                    Log.e(TAG, "Error al leer", task.getException());
                    readListener.onResponse(null);
                }
            }
        });
    }

    @Override
    public void update(String id, User user) {
        //
    }

    @Override
    public void delete(String id) {
        //
    }
}
