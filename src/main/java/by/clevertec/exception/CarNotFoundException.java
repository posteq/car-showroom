package by.clevertec.exception;

import java.security.InvalidParameterException;

public class CarNotFoundException extends InvalidParameterException {
    public CarNotFoundException(String message) {
        super(message);
    }
}
