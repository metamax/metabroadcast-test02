package com.test02.controller;

import com.google.common.base.Optional;
import com.test02.exception.ResourceNotFoundException;
import com.test02.model.Programme;
import com.test02.model.Schedule;
import com.test02.persistence.ProgrammeDataStore;
import com.test02.persistence.datafilter.PredicateRepository;
import com.test02.persistence.datafilter.Query;
import com.test02.validator.ProgrammeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/programmes")
public class ProgrammeController extends BaseController {

    @Autowired
    ProgrammeDataStore dataStore;

    @Autowired
    ProgrammeValidator validator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Schedule> programmes() {
        return new ResponseEntity<Schedule>(new Schedule(dataStore.getAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Programme> programmes(@PathVariable String id) throws ResourceNotFoundException {
        Optional<Programme> foundProgramme = dataStore.findById(checkAndParseId(id));
        if (!foundProgramme.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<Programme>(foundProgramme.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Programme> insertProgramme(@RequestBody Programme programme) throws IOException {
        validator.checkValidData(programme);

        Programme createdProgramme = dataStore.insert(programme);
        return new ResponseEntity<Programme>(createdProgramme, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProgramme(@PathVariable String id, @RequestBody Programme programme) throws IOException {
        validator.checkValidData(programme);

        Integer parsedId = checkAndParseId(id);
        checkIdForUpdate(parsedId, programme);
        programme.setId(parsedId);
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
    public ResponseEntity<Schedule> query(
			@RequestParam(required = false) String channel,
			@RequestParam(required = false) Date dateFrom,
			@RequestParam(required = false) Date dateTo) {

		Query<Programme> query = new Query<Programme>();

        if (channel != null)
		    query.addPredicate(PredicateRepository.withChannel(channel));
        if (dateFrom != null)
		    query.addPredicate(PredicateRepository.startDateFrom(dateFrom));
        if (dateTo != null)
		    query.addPredicate(PredicateRepository.startDateTo(dateTo));

		List<Programme> filteredProgrammes = dataStore.getFiltered(query);

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

    private void checkIdForUpdate(Integer parsedId, Programme programme) {
        if (programme.getId() != null && !parsedId.equals(programme.getId())) {
            throw new IllegalArgumentException("The resource id in the url doesn't match the id in the input data");
        }
    }

}
