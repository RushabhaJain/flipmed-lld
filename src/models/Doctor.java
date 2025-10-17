package models;

import exceptions.InvalidTimeslotException;
import exceptions.TimeslotNotFoundException;
import strategies.timeslotValidationStrategy.TimeslotValidationStrategy;

import java.util.*;

public class Doctor {
    private final UUID id;
    private final String name;
    private final List<Timeslot> freeTimeslots;
    private final double rating;
    private final DoctorSpeciality speciality;

    public Doctor(UUID id, String name, double rating, DoctorSpeciality speciality) {
        this.id = id;
        this.name = name;
        freeTimeslots = new ArrayList<>();
        this.rating = rating;
        this.speciality = speciality;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public DoctorSpeciality getSpeciality() {
        return speciality;
    }

    public void removeFreeSlot(Timeslot slot) throws TimeslotNotFoundException {
        if (!this.freeTimeslots.contains(slot)) {
            throw new TimeslotNotFoundException();
        }
        this.freeTimeslots.remove(slot);
    }

    public void addFreeTimeslot(Timeslot slot, TimeslotValidationStrategy timeslotValidationStrategy) throws InvalidTimeslotException{
        if (!timeslotValidationStrategy.validate(slot)) {
            throw new InvalidTimeslotException();
        }
        this.freeTimeslots.add(slot);
    }

    public List<DoctorSlot> getDoctorSlots() {
        List<DoctorSlot> doctorSlots = new ArrayList<>();
        for (Timeslot timeslot: freeTimeslots) {
            doctorSlots.add(new DoctorSlot(this, timeslot));
        }
        return doctorSlots;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "speciality=" + speciality +
                ", rating=" + rating +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
