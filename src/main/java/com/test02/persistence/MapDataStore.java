package com.test02.persistence;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.test02.exception.ResourceNotFoundException;
import com.test02.model.Identifiable;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class MapDataStore<T extends Identifiable> implements DataStore<T> {

    private ConcurrentMap<Serializable, T> elements = new ConcurrentHashMap<Serializable, T>();

    @Override
    public Optional<T> findById(Serializable id) {
        return Optional.fromNullable(elements.get(id));
    }

    @Override
    public T insert(T element) {
        checkNotNull(element);
        Serializable id = generateId();
        element.setId(id);
        elements.put(id, element);
        return element;
    }

    @Override
    public synchronized void update(T element) {
        checkNotNull(element);
        checkResourceExistent(element.getId());

        elements.put(element.getId(), element);
    }

    @Override
    public synchronized void delete(Serializable id) {
        checkNotNull(id);
        checkResourceExistent(id);

        elements.remove(id);
    }

    @Override
    public List<T> getAll() {
        return Lists.newArrayList(elements.values());
    }

    @Override
    public List<T> getAll(Predicate predicate) {
        Iterable<T> filtered = Iterables.filter(elements.values(), predicate);
        return Lists.newArrayList(filtered);
    }

    private void checkResourceExistent(Serializable id) {
        if (!elements.containsKey(id))
            throw new ResourceNotFoundException();
    }

}
