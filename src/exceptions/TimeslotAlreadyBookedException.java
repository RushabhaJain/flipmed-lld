package exceptions;

public class TimeslotAlreadyBookedException extends Exception {
    public TimeslotAlreadyBookedException(String message) {
        super(message);
    }
}
