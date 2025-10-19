package validation;

import constants.AppConstants;
import models.Timeslot;

public class TimeslotValidator {
    
    public static boolean isValidTimeslot(Timeslot timeslot) {
        if (timeslot == null) {
            return false;
        }
        
        int startTime = timeslot.getStartTime();
        int endTime = timeslot.getEndTime();
        
        // Check if times are within valid range
        if (startTime < AppConstants.MIN_START_TIME || endTime > AppConstants.MAX_END_TIME) {
            return false;
        }
        
        // Check if start time is before end time
        if (startTime >= endTime) {
            return false;
        }
        
        // Check if duration is exactly 1 hour
        if (endTime - startTime != AppConstants.DEFAULT_TIMESLOT_DURATION_HOURS) {
            return false;
        }
        
        return true;
    }
    
    public static void validateTimeslot(Timeslot timeslot) throws exceptions.InvalidTimeslotException {
        if (!isValidTimeslot(timeslot)) {
            throw new exceptions.InvalidTimeslotException("Invalid timeslot: " + timeslot);
        }
    }
}
