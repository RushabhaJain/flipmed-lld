package models;

import java.util.UUID;

public class Appointment {
    private final UUID appointmentId;
    private final UUID doctorId;
    private final UUID patientId;
    private AppointmentStatus appointmentStatus;
    private final Timeslot timeslot;

    public Appointment(
            UUID appointmentId,
            UUID doctorId,
            UUID patientId,
            Timeslot timeslot
    ) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timeslot = timeslot;
        this.appointmentStatus = AppointmentStatus.BOOKED;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", appointmentStatus=" + appointmentStatus +
                ", timeslot=" + timeslot +
                '}';
    }
}
