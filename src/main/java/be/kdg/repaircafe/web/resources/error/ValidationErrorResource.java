package be.kdg.repaircafe.web.resources.error;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResource {
    private final List<FieldErrorResource> fieldErrors = new ArrayList<>();

    public void addFieldError(String path, String message) {
        FieldErrorResource error = new FieldErrorResource(path, message);
        fieldErrors.add(error);
    }

    public List<FieldErrorResource> getFieldErrors() {
        return fieldErrors;
    }
}