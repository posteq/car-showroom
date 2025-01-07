package by.clevertec.exception;

public class ShowroomNotFoundException extends RuntimeException {
    public ShowroomNotFoundException(Long id) {
        super("Showroom not found with id : " + id);
    }
}
