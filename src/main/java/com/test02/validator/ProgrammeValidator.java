package com.test02.validator;

import com.test02.model.Programme;
import org.springframework.stereotype.Service;

@Service("programmeValidator")
public class ProgrammeValidator extends AbstractValidator<Programme> {

    @Override
    protected void checkData(Programme programme, ValidationResult validationResult) {
        checkRequiredName(programme, validationResult);
        checkRequiredChannel(programme, validationResult);
        checkRequiredStartTime(programme, validationResult);
    }

    private void checkRequiredName(Programme programme, ValidationResult validationResult) {
        if (programme.getName() == null) {
            validationResult.addError("name", "Field <name> is required");
        }
    }

    private void checkRequiredChannel(Programme programme, ValidationResult validationResult) {
        if (programme.getChannel() == null) {
            validationResult.addError("channel", "Field <channel> is required");
        }
    }

    private void checkRequiredStartTime(Programme programme, ValidationResult validationResult) {
        if (programme.getStartTime() == null) {
            validationResult.addError("startTime", "Field <startTime> is required");
        }
    }
}
