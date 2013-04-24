package com.test02.persistence;

import com.test02.model.Programme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service("programmeDataStore")
@Scope(value = "singleton")
public class ProgrammeDataStore extends MapDataStore<Programme> {

    @Autowired
    private DataLoader programmeDataLoader;

    private AtomicInteger id = new AtomicInteger(0);

    @Override
    @PostConstruct
    public void initialize() {
        loadData();
    }

    @Override
    public Integer generateId() {
        return id.incrementAndGet();
    }

    private void loadData() {
        List<Programme> programmes = programmeDataLoader.loadInitialData();
        for (Programme programme: programmes) {
            insert(programme);
        }
    }
}
