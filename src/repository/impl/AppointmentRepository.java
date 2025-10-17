package repository.impl;

import models.Appointment;
import repository.IRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AppointmentRepository implements IRepository<Appointment, UUID> {
    Map<UUID, Appointment> map = new HashMap<>();

    @Override
    public Appointment save(Appointment appointment) {
        map.put(appointment.getAppointmentId(), appointment);
        System.out.println("Saving appointment");
        System.out.println(map.get(appointment.getAppointmentId()));
        return appointment;
    }

    @Override
    public Appointment findById(UUID appointmentId) {
        return map.getOrDefault(appointmentId, null);
    }

    @Override
    public List<Appointment> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public void delete(Appointment appointment) {
        deleteById(appointment.getAppointmentId());
    }

    @Override
    public void deleteById(UUID appointmentId) {
        map.remove(appointmentId);
    }

    public List<Appointment> getAllAppointmentsOfPatient(UUID patientId) {
        return map.values()
                .stream()
                .filter(
                        appointment -> appointment.getPatientId() == patientId
                )
                .toList();
    }

    public List<Appointment> getAllAppointmentsOfDoctor(UUID doctorId) {
        System.out.println("Fetching all appointments of doctor: " + doctorId);
        return map.values()
                .stream()
                .filter(
                        appointment -> appointment.getDoctorId().equals(doctorId)
                )
                .toList();
    }
}
