package com.test02.controller.exceptionHandler;

import com.test02.exception.NotValidDataException;
import com.test02.exception.ResourceNotFoundException;
import com.test02.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleException(IllegalArgumentException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleException(ResourceNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorMessage> handleException(NotValidDataException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getValidationResult().getErrorMessages());
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
