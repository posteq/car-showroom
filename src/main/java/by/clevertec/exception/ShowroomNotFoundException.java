package by.clevertec.exception;

public class ShowroomNotFoundException extends IllegalArgumentException {
    public ShowroomNotFoundException(String message) {
        super(message);
    }
}
