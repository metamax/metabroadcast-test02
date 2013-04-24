package com.test02.validator;

import com.google.common.base.Preconditions;
import com.test02.exception.NotValidDataException;

public abstract class AbstractValidator<T> implements Validator<T> {

    @Override
    public void checkValidData(T element) {
        Preconditions.checkNotNull(element);

        ValidationResult validationResult  = new ValidationResult();
        checkData(element, validationResult);

        if (!validationResult.isValid())
            throw new NotValidDataException(validationResult);
    }

    protected abstract void checkData(T element, ValidationResult validationResult);
}
