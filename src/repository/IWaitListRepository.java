package repository;

import models.Appointment;

public interface IWaitListRepository {
    Appointment addToWaitList(Appointment appointment);
    Appointment getNextQueuedAppointment(Appointment appointment);
}
