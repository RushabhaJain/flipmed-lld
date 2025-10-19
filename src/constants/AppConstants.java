package constants;

public class AppConstants {
    public static final double MAX_DOCTOR_RATING = 5.0;
    public static final double MIN_DOCTOR_RATING = 0.0;
    
    // Timeslot duration constants (in minutes)
    public static final int DEFAULT_TIMESLOT_DURATION_MINUTES = 60; // 1 hour
    public static final int THIRTY_MINUTE_SLOT = 30;
    public static final int ONE_HOUR_SLOT = 60;
    public static final int TWO_HOUR_SLOT = 120;
    
    // Time validation constants
    public static final int MIN_START_HOUR = 0;
    public static final int MAX_END_HOUR = 24;
    public static final int MIN_MINUTE = 0;
    public static final int MAX_MINUTE = 59;
    
    // Backward compatibility
    public static final int DEFAULT_TIMESLOT_DURATION_HOURS = 1;
    public static final int MIN_START_TIME = 0;
    public static final int MAX_END_TIME = 24;
}
