package com.test02.validator;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidationResult {
    private Map<String, String> errors = new HashMap<String, String>();

    public Set<String> getFields() {
        return errors.keySet();
    }

    public String addError(String fieldName, String errorMsg) {
        return errors.put(fieldName, errorMsg);
    }

    public String getError(String fieldName) {
        return errors.get(fieldName);
    }

    public List<String> getErrorMessages() {
        return Lists.newArrayList(errors.values());
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
