package com.test02.persistence;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.test02.persistence.datafilter.PredicateRepository;
import com.test02.exception.ResourceNotFoundException;
import com.test02.model.Programme;
import com.test02.persistence.DataLoader;
import com.test02.persistence.DataStore;
import com.test02.persistence.ProgrammeDataStore;
import com.test02.persistence.datafilter.Query;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProgrammeDataStoreTest {

    @InjectMocks
    private DataStore<Programme> dataStore = new ProgrammeDataStore();

    @Mock
    private DataLoader programmeDataLoader;

    private List<Programme> initialData = new ArrayList<Programme>();
    private Programme programme1;
    private Programme programme2;
    private final Integer NOT_EXISTENT_ID = 999;
    private final String BBC_ONE = "bbc one";
    private final String BBC_TWO = "bbc two";


    @Before
    public void setUp() {
        given(programmeDataLoader.loadInitialData()).willReturn(initialData);

        programme1 = new Programme();
        programme1.setName("Big Bang Theory");
        programme1.setDescription("A woman who moves into an apartment across the hall from two brilliant but socially awkward physicists shows them how little they know about life outside of the laboratory");
        programme1.setStartTime(new DateTime(2013, 05, 02, 22, 30, 00).toDate());
        programme1.setChannel("bbc one");

        programme2 = new Programme();
        programme2.setName("The Office");
        programme2.setDescription("The programme is about the day-to-day lives of office employees in the Slough branch of the fictitious Wernham Hogg Paper Company");
        programme2.setStartTime(new DateTime(2013, 05, 03, 21, 00, 00).toDate());
        programme2.setChannel("bbc two");

        initialData.add(programme1);
        initialData.add(programme2);

        dataStore.initialize();
    }

    @Test
    public void testGetAll() {
        assertThat(dataStore.getAll()).hasSize(initialData.size());
    }

    @Test
    public void testFindByIdShouldReturnAnElementWithCorrectData() {
        Optional<Programme> programme = dataStore.findById(programme1.getId());

        assertThat(programme.isPresent()).isTrue();
        assertThat(programme.get().getName()).isEqualTo(programme1.getName());
    }

    @Test
    public void testFindByIdShouldReturnAnAbsent() {
        Optional<Programme> programme = dataStore.findById(NOT_EXISTENT_ID);

        assertThat(programme.isPresent()).isFalse();
    }

    @Test
    public void testInsert() {
        Programme programme3 = new Programme();
        programme3.setName("The IT Crowd");
        programme3.setDescription("The show revolves around the three staff members of its IT department");
        programme3.setStartTime(new DateTime(2013, 04, 26, 20, 30, 00).toDate());
        programme3.setChannel("itv4");

        Programme programme = dataStore.insert(programme3);

        assertThat(dataStore.getAll().size()).isEqualTo(initialData.size() + 1);
        assertThat(dataStore.findById(programme.getId()).isPresent()).isTrue();
    }

    @Test
    public void testUpdate() {
        Programme programme2Updated = new Programme();
        programme2Updated.setId(programme2.getId());
        programme2Updated.setName("The Office new Serie");
        programme2Updated.setDescription("The programme is about the day-to-day lives of office employees in the Slough branch of the fictitious Wernham Hogg Paper Company");
        programme2Updated.setStartTime(new DateTime(2013, 05, 03, 21, 00, 00).toDate());
        programme2Updated.setChannel("bbc two");

        dataStore.update(programme2Updated);

        assertThat(dataStore.getAll().size()).isEqualTo(initialData.size());

        Optional<Programme> foundProgramme2 = dataStore.findById(programme2.getId());
        assertThat(foundProgramme2.isPresent()).isTrue();
        assertThat(foundProgramme2.get().getName()).isEqualTo(programme2Updated.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateShouldThrowAResourceNotFoundException() {
        Programme programme2Updated = new Programme();
        programme2Updated.setId(NOT_EXISTENT_ID);
        programme2Updated.setName("The Office new Serie");
        programme2Updated.setDescription("The programme is about the day-to-day lives of office employees in the Slough branch of the fictitious Wernham Hogg Paper Company");
        programme2Updated.setStartTime(new DateTime(2013, 05, 03, 21, 00, 00).toDate());
        programme2Updated.setChannel("bbc two");

        dataStore.update(programme2Updated);
    }

    @Test
    public void testDeleteShouldDeleteTheExistentElement() {
        dataStore.delete(programme1.getId());

        Optional<Programme> foundProgramme1 = dataStore.findById(programme1.getId());
        assertThat(foundProgramme1.isPresent()).isFalse();
        assertThat(dataStore.getAll().size()).isEqualTo(initialData.size() - 1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteShouldThrowAResourceNotFoundException() {
        dataStore.delete(NOT_EXISTENT_ID);
    }

    @Test
    public void testGetFilteredWithOnePredicate() {
		Query<Programme> query = new Query<Programme>();
		query.addPredicate(PredicateRepository.withChannel(BBC_ONE));

        assertThat(dataStore.getFiltered(query).size()).isEqualTo(1);
    }

    @Test
    public void testGetFilteredWithComplexQuery() {
        Query<Programme> query = new Query<Programme>();
        query.addPredicate(PredicateRepository.withChannel(BBC_TWO));

        DateTime dateFrom = new DateTime(2013, 05, 03, 00, 00, 00);
        query.addPredicate(PredicateRepository.startDateFrom(dateFrom.toDate()));

        assertThat(dataStore.getFiltered(query).size()).isEqualTo(1);
    }

    @Test
    public void testGenerateIdShouldReturnAnIncrementedId() {
        assertThat(dataStore.generateId()).isEqualTo(programme2.getId() + 1);
    }

}
