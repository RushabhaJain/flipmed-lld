package strategies.timeslotValidationStrategy;

import models.Timeslot;

public interface TimeslotValidationStrategy {
    boolean validate(Timeslot timeslot);
}
