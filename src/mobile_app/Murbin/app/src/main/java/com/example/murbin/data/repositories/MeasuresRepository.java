/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.data.repositories;

import com.example.murbin.models.Measure;

/**
 * Interface Repository for Measure
 */
public interface MeasuresRepository {

    /**
     * Add the empty Measure object and return the position where it was added
     *
     * @return int Position where it has been added
     */
    int addEmptyElement();

    /**
     * Add the Measure object passed as a parameter
     *
     * @param measure Measure object with the data to create
     */
    void addElement(Measure measure);

    /**
     * Gets the Measure object at the position specified as a parameter
     *
     * @param position Position where the object is located
     * @return Measure
     */
    Measure readElement(int position); // Obtiene el elemento dado su id

    /**
     * Updates the Measure object at the indicated position with the data
     * from the received object.
     *
     * @param position Position where the object is located
     * @param measure  Measure object with the data to update
     */
    void updateElement(int position, Measure measure);

    /**
     * @param position Position where the object is located
     */
    void deleteElement(int position);

    /**
     * Returns the number of objects that are stored
     *
     * @return int Number of objects that are stored
     */
    int qtyElements();
}
