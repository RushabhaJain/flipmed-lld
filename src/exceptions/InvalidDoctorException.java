package exceptions;

public class InvalidDoctorException extends Exception {
    public InvalidDoctorException(String message) {
        super(message);
    }
    
    public InvalidDoctorException(String message, Throwable cause) {
        super(message, cause);
    }
}
