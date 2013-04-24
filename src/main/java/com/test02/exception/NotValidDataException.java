package com.test02.exception;

import com.test02.validator.ValidationResult;

public class NotValidDataException extends RuntimeException {
    private ValidationResult validationResult;

    public NotValidDataException(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return this.validationResult;
    }
}
