package com.test02.persistence;

import com.google.common.base.Optional;
import com.test02.exception.ResourceNotFoundException;
import com.test02.model.Identifiable;
import com.test02.persistence.datafilter.Query;

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
     * @return the created element
     */
    public T insert(T element);

    /**
     * Update an element if exists in the data store
     * @param element the element to update
     * @return the updated element
     * @throws ResourceNotFoundException if the resource to update is not present
     */
    public T update(T element);

    /**
     * Delete and element from the data store
     * @param id the identifier of the element to delete
     * @throws ResourceNotFoundException if the resource to delete is not present
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
	 * 
	 * @param query the query to apply for filtering
	 * @return the list of elements that satisfy the query
	 */
	public List<T> getFiltered(Query<T> query);
}
