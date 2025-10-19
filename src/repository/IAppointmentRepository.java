package repository;

import models.Appointment;

import java.util.List;
import java.util.UUID;

public interface IAppointmentRepository {
    Appointment save(Appointment appointment);
    Appointment findById(UUID appointmentId);
    List<Appointment> findAll();
    void delete(Appointment appointment);
    void deleteById(UUID appointmentId);
    List<Appointment> getAllAppointmentsOfPatient(UUID patientId);
    List<Appointment> getAllAppointmentsOfDoctor(UUID doctorId);
}
