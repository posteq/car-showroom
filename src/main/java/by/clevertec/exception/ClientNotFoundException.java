package by.clevertec.exception;

public class ClientNotFoundException extends IllegalArgumentException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
