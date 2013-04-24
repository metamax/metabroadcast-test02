package com.test02.persistence;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.test02.model.Identifiable;

import java.io.Serializable;
import java.util.List;

/**
 * A simple data store
 * @param <T> the Type of the element contained in the data store
 */
public interface DataStore<T extends Identifiable> {

    /**
     * Initialize the data store (i.e. for loading initial data)
     */
    public void initialize();

    /**
     * Return an optional element identified by the id
     * @param id the identifier of the element
     * @return the Optional containing (or not) the element
     */
    public Optional<T> findById(Serializable id);

    /**
     * Insert an element in the data store
     * @param element
     * @return the inserted element
     */
    public T insert(T element);

    /**
     * Update an element if exists in the data store
     * @param element the element to update
     */
    public void update(T element);

    /**
     * Delete and element from the data store
     * @param id the identifier of the element to delete
     */
    public void delete(Serializable id);

    /**
     * Generate a new and unique identifier
     * @return the identifier
     */
    public Serializable generateId();

    /**
     * Return all the element present in the data store
     * @return the list of the elements
     */
    public List<T> getAll();

    /**
     * Return all the element in the data store that satisfy a predicate
     * @param predicate the predicate used for filtering
     * @return the list of the element that satisfy the predicate
     */
    public List<T> getAll(Predicate predicate);
}
