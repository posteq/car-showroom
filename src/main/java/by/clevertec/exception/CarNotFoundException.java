package by.clevertec.exception;

public class CarNotFoundException extends IllegalArgumentException {
    public CarNotFoundException(Long id) {
        super("");
    }
}
