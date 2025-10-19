package services;

import exceptions.DoctorNotFoundException;
import exceptions.PatientNotFoundException;
import models.Appointment;
import repository.IAppointmentRepository;
import repository.IDoctorRepository;
import repository.IPatientRepository;

import java.util.List;
import java.util.UUID;

public class AppointmentQueryService {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorRepository doctorRepository;
    private final IPatientRepository patientRepository;

    public AppointmentQueryService(
            IAppointmentRepository appointmentRepository,
            IDoctorRepository doctorRepository,
            IPatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public List<Appointment> getPatientAppointments(UUID patientId) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found with ID: " + patientId);
        }
        return appointmentRepository.getAllAppointmentsOfPatient(patientId);
    }

    public List<Appointment> getDoctorAppointments(UUID doctorId) throws DoctorNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + doctorId);
        }
        return appointmentRepository.getAllAppointmentsOfDoctor(doctorId);
    }
}
