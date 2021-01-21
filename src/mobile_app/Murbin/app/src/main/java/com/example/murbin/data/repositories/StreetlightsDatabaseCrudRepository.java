package com.example.murbin.data.repositories;

import com.example.murbin.models.Streetlight;

import java.util.Map;

public interface StreetlightsDatabaseCrudRepository {
    /**
     * Create a subzone in the database
     *
     * @param streetlight    Streetlight
     * @param createListener Listener to get response asynchronously
     */
    void create(Streetlight streetlight, StreetlightsDatabaseCrudRepository.CreateListener createListener);

    /**
     * Create a subzone in the database with custom id
     *
     * @param idDoc          String
     * @param streetlight    Streetlight
     * @param createListener Listener to get response asynchronously
     */
    void create(String idDoc, Streetlight streetlight, StreetlightsDatabaseCrudRepository.CreateListener createListener);

    /**
     * Read a subzone from the database
     *
     * @param id           Id from subzone
     * @param readListener Listener to get User asynchronously
     */
    void read(String id, StreetlightsDatabaseCrudRepository.ReadListener readListener);

    /**
     * Update a database subzone
     *
     * @param id             Id from subzone
     * @param data           Custom map with data
     * @param updateListener Listener to get response asynchronously
     */
    void update(String id, Map<String, Object> data, StreetlightsDatabaseCrudRepository.UpdateListener updateListener);

    /**
     * Delete a subzone from the database
     *
     * @param id             Id from subzone
     * @param deleteListener Listener to get response asynchronously
     */
    void delete(String id, StreetlightsDatabaseCrudRepository.DeleteListener deleteListener);

    /**
     * Listener for Read to get Subzone asynchronously
     */
    interface ReadListener {
        void onResponse(Streetlight streetlight);
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
