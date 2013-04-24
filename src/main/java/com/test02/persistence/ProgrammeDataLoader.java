package com.test02.persistence;

import com.test02.model.Programme;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("programmeDataLoader")
public class ProgrammeDataLoader implements DataLoader<Programme> {

    public List<Programme> loadInitialData() {
        List<Programme> programmes = new ArrayList<Programme>();

        Programme programme1 = new Programme();
        programme1.setName("Big Bang Theory");
        programme1.setDescription("A woman who moves into an apartment across the hall from two brilliant but socially awkward physicists shows them how little they know about life outside of the laboratory");
        programme1.setStartTime(new DateTime(2013, 05, 02, 22, 30, 00).toDate());
        programme1.setChannel("bbc one");

        Programme programme2 = new Programme();
        programme2.setName("The Office");
        programme2.setDescription("The programme is about the day-to-day lives of office employees in the Slough branch of the fictitious Wernham Hogg Paper Company");
        programme2.setStartTime(new DateTime(2013, 05, 03, 21, 00, 00).toDate());
        programme2.setChannel("bbc two");

        Programme programme3 = new Programme();
        programme3.setName("The IT Crowd");
        programme3.setDescription("The show revolves around the three staff members of its IT department");
        programme3.setStartTime(new DateTime(2013, 04, 26, 20, 30, 00).toDate());
        programme3.setChannel("itv4");

        Programme programme4 = new Programme();
        programme4.setName("How I Met Your Mother");
        programme4.setDescription("Ted searches for the woman of his dreams in New York City with the help of his four best friends");
        programme4.setStartTime(new DateTime(2013, 04, 15, 21, 30, 00).toDate());
        programme4.setChannel("bbc two");

        programmes.add(programme1);
        programmes.add(programme2);
        programmes.add(programme3);
        programmes.add(programme4);

        return programmes;
    }
}
