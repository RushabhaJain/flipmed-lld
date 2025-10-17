package strategies.timeslotValidationStrategy.impl;

import models.Timeslot;
import strategies.timeslotValidationStrategy.TimeslotValidationStrategy;

public class OneHourTimeslotValidationStrategy implements TimeslotValidationStrategy {
    @Override
    public boolean validate(Timeslot timeslot) {
        return timeslot.getEndTime() - timeslot.getStartTime() == 1;
    }
}
