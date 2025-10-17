package models;

public class DoctorSlot {
    private final Doctor doctor;
    private final Timeslot timeslot;

    public DoctorSlot(Doctor doctor, Timeslot timeslot) {
        this.doctor = doctor;
        this.timeslot = timeslot;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "DoctorSlot{" +
                "doctor=" + doctor +
                ", timeslot=" + timeslot +
                '}';
    }
}
