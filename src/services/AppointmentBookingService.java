package services;

import exceptions.*;
import models.*;
import repository.IAppointmentRepository;
import repository.IDoctorRepository;
import repository.IPatientRepository;
import repository.IWaitListRepository;

import java.util.List;
import java.util.UUID;

public class AppointmentBookingService {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorRepository doctorRepository;
    private final IPatientRepository patientRepository;
    private final IWaitListRepository waitListRepository;

    public AppointmentBookingService(
            IAppointmentRepository appointmentRepository,
            IDoctorRepository doctorRepository,
            IPatientRepository patientRepository,
            IWaitListRepository waitListRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.waitListRepository = waitListRepository;
    }

    public Appointment bookAppointment(
            UUID patientId,
            UUID doctorId,
            Timeslot timeslot
    ) throws PatientNotFoundException, DoctorNotFoundException, TimeslotAlreadyBookedException, TimeslotNotFoundException {
        
        // Validate patient exists
        Patient patient = patientRepository.findById(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found with ID: " + patientId);
        }
        
        // Validate doctor exists
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + doctorId);
        }

        // Check if patient already has an appointment at the same time
        List<Appointment> patientAppointments = appointmentRepository.getAllAppointmentsOfPatient(patientId);
        for (Appointment patientAppointment : patientAppointments) {
            if (patientAppointment.getTimeslot().equals(timeslot)) {
                throw new TimeslotAlreadyBookedException("Patient already has an appointment at this time");
            }
        }

        // Create new appointment
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                doctorId,
                patientId,
                timeslot
        );

        // Check if doctor already has an appointment at the same time
        List<Appointment> doctorAppointments = appointmentRepository.getAllAppointmentsOfDoctor(doctorId);
        boolean doctorSlotAvailable = true;
        
        for (Appointment doctorAppointment : doctorAppointments) {
            if (doctorAppointment.getTimeslot().equals(timeslot)) {
                // Doctor slot is not available, put appointment in waitlist
                appointment.setAppointmentStatus(AppointmentStatus.WAIT_LISTED);
                waitListRepository.addToWaitList(appointment);
                doctorSlotAvailable = false;
                break;
            }
        }
        
        if (doctorSlotAvailable) {
            appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
            // Remove the slot from doctor's free slots
            doctor.removeFreeSlot(timeslot);
            doctorRepository.save(doctor);
        }

        return appointmentRepository.save(appointment);
    }
}
