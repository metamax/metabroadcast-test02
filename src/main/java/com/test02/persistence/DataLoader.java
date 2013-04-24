package com.test02.persistence;

import com.test02.model.Identifiable;

import java.util.List;

/**
 * A data loader
 * @param <T> the Type of the elements to load
 */
public interface DataLoader<T extends Identifiable> {
    /**
     * Load initial data
     * @return a list of elements
     */
    public List<T> loadInitialData();
}
