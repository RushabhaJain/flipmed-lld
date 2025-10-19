package exceptions;

public class InvalidTimeslotException extends Exception {
    public InvalidTimeslotException(String message) {
        super(message);
    }
    
    public InvalidTimeslotException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidTimeslotException() {
        super("Invalid timeslot provided");
    }
}
