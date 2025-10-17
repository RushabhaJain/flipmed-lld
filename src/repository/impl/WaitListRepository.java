package repository.impl;

import models.Appointment;
import repository.IRepository;

import java.util.*;

public class WaitListRepository {
    private final Map<String, Queue<Appointment>> waitlist = new HashMap<>();

    public Appointment addToWaitList(Appointment appointment) {
        String key = appointment.getDoctorId().toString() + "-" + appointment.getTimeslot().getStartTime();
        if (this.waitlist.containsKey(key)) {
            this.waitlist.get(key).add(appointment);
        }
        Queue<Appointment> queue = new LinkedList<>();
        queue.add(appointment);
        this.waitlist.put(key, queue);
        return appointment;
    }

    public Appointment getNextQueuedAppointment(Appointment appointment) {
        String key = appointment.getDoctorId().toString() + "-" + appointment.getTimeslot().getStartTime();
        if (this.waitlist.containsKey(key) && this.waitlist.get(key).size() > 0) {
            return this.waitlist.get(key).poll();
        }
        return null;
    }

}
