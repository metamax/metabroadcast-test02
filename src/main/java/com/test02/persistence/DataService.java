package com.test02.persistence;

import com.google.common.base.Optional;
import com.test02.model.Programme;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service("dataService")
@Scope(value = "singleton")
public class DataService {

    private AtomicInteger id = new AtomicInteger(0);
    private ConcurrentMap<Integer, Programme> programmes = new ConcurrentHashMap<Integer, Programme>();

    public DataService() {
        Programme programme1 = new Programme();
        programme1.setId(generateNewId());
        programme1.setName("Big Bang Theory");
        programme1.setDescription("A woman who moves into an apartment across the hall from two brilliant but socially awkward physicists shows them how little they know about life outside of the laboratory");

        Programme programme2 = new Programme();
        programme2.setId(generateNewId());
        programme2.setName("The Office");
        programme2.setDescription("The programme is about the day-to-day lives of office employees in the Slough branch of the fictitious Wernham Hogg Paper Company");

        Programme programme3 = new Programme();
        programme3.setId(generateNewId());
        programme3.setName("The IT Crowd");
        programme3.setDescription("The show revolves around the three staff members of its IT department");

        Programme programme4 = new Programme();
        programme4.setId(generateNewId());
        programme4.setName("How I Met Your Mother");
        programme4.setDescription("Ted searches for the woman of his dreams in New York City with the help of his four best friends");

        programmes.put(programme1.getId(), programme1);
        programmes.put(programme2.getId(), programme2);
        programmes.put(programme3.getId(), programme3);
        programmes.put(programme4.getId(), programme4);
    }

    private Integer generateNewId() {
        return id.incrementAndGet();
    }

    public Optional<Programme> findProgrammeById(Integer id) {
        return Optional.fromNullable(programmes.get(id));
    }

    public void insertProgramme(Programme programme) {
        checkNotNull(programme);

        Integer programmeId = generateNewId();
        programme.setId(programmeId);
        programmes.put(programmeId, programme);
    }

    public synchronized void updateProgramme(Programme programme) {
        checkArgument(programmes.containsKey(programme.getId()));

        programmes.put(programme.getId(), programme);
    }

    public synchronized void deleteProgramme(Integer id) {
        checkArgument(programmes.containsKey(id));

        programmes.remove(id);
    }
}
