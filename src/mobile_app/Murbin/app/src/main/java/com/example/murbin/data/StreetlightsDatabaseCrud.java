package com.example.murbin.data;

import com.example.murbin.data.repositories.StreetlightsDatabaseCrudRepository;
import com.example.murbin.models.Streetlight;
import com.example.murbin.models.Subzone;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

public class StreetlightsDatabaseCrud implements StreetlightsDatabaseCrudRepository {

    private final CollectionReference streetlights;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * Constructor
     *
     * Default zone Gandia
     */
    public StreetlightsDatabaseCrud() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        streetlights = db.collection("zones")
                .document("Gandia")
                .collection("subzones");
    }

    /**
     * Constructor
     *
     * @param idDocumentZone Id Name of the document indicating the zone
     */
    public StreetlightsDatabaseCrud(String idDocumentZone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        streetlights = db.collection("zones")
                .document(idDocumentZone)
                .collection("subzones");
    }

    @Override
    public void create(Streetlight streetlight, CreateListener createListener) {
        streetlights.add(streetlight.parseToMap()).addOnSuccessListener(documentReference -> {
            createListener.onResponse(documentReference.getId());
        }).addOnFailureListener(e -> {
            createListener.onResponse("");
        });
    }

    @Override
    public void read(String id, ReadListener readListener) {
        streetlights.document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Streetlight streetlight = (Objects.requireNonNull(task.getResult())).toObject(Streetlight.class);
                readListener.onResponse(streetlight);
            } else {
                readListener.onResponse(null);
            }
        });
    }

    @Override
    public void update(String id, Map<String, Object> data, UpdateListener updateListener) {
        streetlights.document(id).update(data).addOnSuccessListener(aVoid -> {
            updateListener.onResponse(true);
        }).addOnFailureListener(e -> updateListener.onResponse(false));
    }

    @Override
    public void delete(String id, DeleteListener deleteListener) {
        streetlights.document(id).delete().addOnSuccessListener(aVoid -> {
            deleteListener.onResponse(true);
        }).addOnFailureListener(e -> deleteListener.onResponse(false));
    }
}