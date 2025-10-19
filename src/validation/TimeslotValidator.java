package validation;

import constants.AppConstants;
import models.Timeslot;

public class TimeslotValidator {
    
    public static boolean isValidTimeslot(Timeslot timeslot) {
        if (timeslot == null) {
            return false;
        }
        
        int startHour = timeslot.getStartHour();
        int startMinute = timeslot.getStartMinute();
        int endHour = timeslot.getEndHour();
        int endMinute = timeslot.getEndMinute();
        
        // Check if hours and minutes are within valid range
        if (startHour < AppConstants.MIN_START_HOUR || startHour > AppConstants.MAX_END_HOUR ||
            endHour < AppConstants.MIN_START_HOUR || endHour > AppConstants.MAX_END_HOUR) {
            return false;
        }
        
        if (startMinute < AppConstants.MIN_MINUTE || startMinute > AppConstants.MAX_MINUTE ||
            endMinute < AppConstants.MIN_MINUTE || endMinute > AppConstants.MAX_MINUTE) {
            return false;
        }
        
        // Check if start time is before end time
        if (timeslot.getStartTimeInMinutes() >= timeslot.getEndTimeInMinutes()) {
            return false;
        }
        
        // Check if duration is positive (greater than 0)
        if (timeslot.getDurationInMinutes() <= 0) {
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
