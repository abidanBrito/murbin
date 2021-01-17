/*
 * Created by Francisco Javier Paños Madrona on 15/01/21 9:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11/12/20 20:12
 */

package com.example.murbin.data;

import com.example.murbin.data.repositories.ZonesDatabaseCrudRepository;
import com.example.murbin.models.Zone;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

public class ZonesDatabaseCrud implements ZonesDatabaseCrudRepository {

    private final CollectionReference zones;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * Constructor
     */
    public ZonesDatabaseCrud() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        zones = db.collection("zones");
    }

    @Override
    public void create(Zone zone, CreateListener createListener) {
        zones.add(zone.parseToMap()).addOnSuccessListener(documentReference -> {
            createListener.onResponse(documentReference.getId());
        }).addOnFailureListener(e -> {
            createListener.onResponse("");
        });
    }

    @Override
    public void create(String idDoc, Zone zone, CreateListener createListener) {
        zones.document(idDoc).set(zone.parseToMap()).addOnSuccessListener(documentReference -> {
            createListener.onResponse(idDoc);
        }).addOnFailureListener(e -> {
            createListener.onResponse("");
        });
    }

    @Override
    public void read(String id, ReadListener readListener) {
        zones.document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Zone zone = (Objects.requireNonNull(task.getResult())).toObject(Zone.class);
                readListener.onResponse(zone);
            } else {
                readListener.onResponse(null);
            }
        });
    }

    @Override
    public void update(String id, Map<String, Object> data, UpdateListener updateListener) {
        zones.document(id).update(data).addOnSuccessListener(aVoid -> {
            updateListener.onResponse(true);
        }).addOnFailureListener(e -> updateListener.onResponse(false));
    }

    @Override
    public void delete(String id, DeleteListener deleteListener) {
        zones.document(id).delete().addOnSuccessListener(aVoid -> {
            deleteListener.onResponse(true);
        }).addOnFailureListener(e -> deleteListener.onResponse(false));
    }
}
