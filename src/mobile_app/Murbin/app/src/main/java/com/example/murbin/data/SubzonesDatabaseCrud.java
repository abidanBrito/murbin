/*
 * Created by Francisco Javier Paños Madrona on 19/11/20 11:55
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 19/11/20 11:55
 */

package com.example.murbin.data;

import androidx.annotation.NonNull;

import com.example.murbin.data.repositories.SubzonesDatabaseCrudRepository;
import com.example.murbin.models.Subzone;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SubzonesDatabaseCrud implements SubzonesDatabaseCrudRepository {

    private final CollectionReference subzones;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * Constructor
     *
     * Default zone Gandia
     */
    public SubzonesDatabaseCrud() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        subzones = db.collection("zones")
                .document("Gandia")
                .collection("subzones");
    }

    /**
     * Constructor
     *
     * @param idDocumentZone Id Name of the document indicating the zone
     */
    public SubzonesDatabaseCrud(String idDocumentZone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        subzones = db.collection("zones")
                .document(idDocumentZone)
                .collection("subzones");
    }

    @Override
    public void create(Subzone subzone, CreateListener createListener) {
        subzones.add(subzone.parseToMap()).addOnSuccessListener(documentReference -> {
            createListener.onResponse(documentReference.getId());
        }).addOnFailureListener(e -> {
            createListener.onResponse("");
        });
    }

    @Override
    public void read(String id, ReadListener readListener) {
        subzones.document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Subzone subzone = (Objects.requireNonNull(task.getResult())).toObject(Subzone.class);
                readListener.onResponse(subzone);
            } else {
                readListener.onResponse(null);
            }
        });
    }

    @Override
    public void update(String id, Map<String, Object> data, UpdateListener updateListener) {
        subzones.document(id).update(data).addOnSuccessListener(aVoid -> {
            updateListener.onResponse(true);
        }).addOnFailureListener(e -> updateListener.onResponse(false));
    }

    @Override
    public void delete(String id, DeleteListener deleteListener) {
        subzones.document(id).delete().addOnSuccessListener(aVoid -> {
            deleteListener.onResponse(true);
        }).addOnFailureListener(e -> deleteListener.onResponse(false));
    }

    public CollectionReference getSubzones() {
        return subzones;
    }

    public Query getSubzonesFromTechnicians(List<String> listSubzones) {
        return subzones.whereIn("name", listSubzones);
    }
}
