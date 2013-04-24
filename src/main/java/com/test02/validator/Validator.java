package com.test02.validator;

import com.test02.exception.NotValidDataException;

/**
 * Simple Validator
 * @param <T>
 */
public interface Validator<T extends Object> {

    /**
     * Check if the input data is valid or not
     * @param element
     * @throws NotValidDataException if the input data is not valid
     */
    public void checkValidData(T element);
}
