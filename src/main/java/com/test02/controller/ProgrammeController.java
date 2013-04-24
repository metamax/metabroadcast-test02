package com.test02.controller;

import com.google.common.base.Optional;
import com.test02.exception.ResourceNotFoundException;
import com.test02.model.Programme;
import com.test02.model.Schedule;
import com.test02.persistence.DataStore;
import com.test02.persistence.datafilter.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/programmes")
public class ProgrammeController {

    @Autowired
    @Qualifier("programmeDataStore")
    DataStore dataStore;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Schedule> programmes() {
        return new ResponseEntity<Schedule>(new Schedule(dataStore.getAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Programme> programmes(@PathVariable String id) {
        Optional<Programme> programme = dataStore.findById(checkAndParseId(id));
        if (!programme.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<Programme>(programme.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity insertProgramme(@RequestBody Programme programme) throws IOException {
        dataStore.insert(programme);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProgramme(@PathVariable String id, @RequestBody Programme programme) throws IOException {
        programme.setId(checkAndParseId(id));
        dataStore.update(programme);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProgramme(@PathVariable String id) throws IOException {
        dataStore.delete(checkAndParseId(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/query.xml*", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Schedule> query(@RequestParam(required = false) String channel) {
        List<Programme> filteredProgrammes = dataStore.getAll(Predicates.filteredByChannel(channel));
        return new ResponseEntity<Schedule>(new Schedule(filteredProgrammes), HttpStatus.OK);
    }

    private Integer checkAndParseId(String id) {
        try {
            return Integer.parseInt(id);
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("The resource id should be a number");
        }
    }

}
