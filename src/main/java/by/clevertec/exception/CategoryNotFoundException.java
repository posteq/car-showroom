package by.clevertec.exception;

import java.security.InvalidParameterException;

public class CategoryNotFoundException extends InvalidParameterException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
