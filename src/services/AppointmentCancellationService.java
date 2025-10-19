package services;

import exceptions.AppointmentNotFoundException;
import exceptions.InvalidTimeslotException;
import models.Appointment;
import models.AppointmentStatus;
import models.Doctor;
import repository.IAppointmentRepository;
import repository.IDoctorRepository;
import repository.IWaitListRepository;

import java.util.UUID;

public class AppointmentCancellationService {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorRepository doctorRepository;
    private final IWaitListRepository waitListRepository;

    public AppointmentCancellationService(
            IAppointmentRepository appointmentRepository,
            IDoctorRepository doctorRepository,
            IWaitListRepository waitListRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.waitListRepository = waitListRepository;
    }

    public void cancelAppointment(UUID appointmentId) throws AppointmentNotFoundException, InvalidTimeslotException {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
        }

        // Check if there's a next appointment in the waitlist
        Appointment nextQueuedAppointment = waitListRepository.getNextQueuedAppointment(appointment);
        
        if (nextQueuedAppointment != null) {
            // Promote the next appointment from waitlist to booked
            nextQueuedAppointment.setAppointmentStatus(AppointmentStatus.BOOKED);
            appointmentRepository.save(nextQueuedAppointment);
        } else {
            // No waitlist appointment, add the timeslot back to doctor's free slots
            Doctor doctor = doctorRepository.findById(appointment.getDoctorId());
            if (doctor != null) {
                doctor.addFreeTimeslot(appointment.getTimeslot());
                doctorRepository.save(doctor);
            }
        }
        
        // Remove the cancelled appointment
        appointmentRepository.delete(appointment);
    }
}
