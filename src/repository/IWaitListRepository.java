package repository;

import models.Appointment;

public interface IWaitListRepository {
    void addToWaitList(Appointment appointment);
    Appointment getNextQueuedAppointment(Appointment appointment);
}
