package services;

import exceptions.*;
import models.*;
import repository.IRepository;
import repository.impl.AppointmentRepository;
import repository.impl.DoctorRepository;
import repository.impl.PatientRepository;
import repository.impl.WaitListRepository;
import strategies.rankStrategy.RankStrategy;
import strategies.timeslotValidationStrategy.impl.OneHourTimeslotValidationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentService {
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private IRepository appointmentRepository;
    private WaitListRepository waitListRepository;

    public AppointmentService(
            IRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            WaitListRepository waitListRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.waitListRepository = waitListRepository;
    }

    public Appointment bookAppointment(
            UUID patientId,
            UUID doctorId,
            Timeslot timeslot
    ) throws PatientNotFoundException, DoctorNotFoundException, TimeslotAlreadyBookedException, TimeslotNotFoundException {
        Patient patient = patientRepository.findById(patientId);
        if (patient == null) {
            throw new PatientNotFoundException();
        }
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException();
        }

        // Same time slot should not be booked
        List<Appointment> patientAppointments = getPatientAppointments(patientId);
        for (Appointment patientAppointment: patientAppointments) {
            if (patientAppointment.getTimeslot().equals(timeslot)) {
                throw new TimeslotAlreadyBookedException();
            }
        }
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                doctorId,
                patientId,
                timeslot
        );

        // If the appointment for doctor already exists, put the appointment in waiting state
        List<Appointment> doctorAppointments = getDoctorAppointments(doctorId);
        System.out.println("Booking appointment for: " + patient.getName());
        System.out.println("DoctorAppointments:" + doctorAppointments.size());
        for (Appointment doctorAppointment: doctorAppointments) {
            if (doctorAppointment.getTimeslot().equals(timeslot)) {
                appointment.setAppointmentStatus(AppointmentStatus.WAIT_LISTED);
                waitListRepository.addToWaitList(appointment);
            } else {
                appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
            }
        }

//        doctor.removeFreeSlot(timeslot);
//        doctorRepository.save(doctor);

        return (Appointment) appointmentRepository.save(appointment);
    }

    public void cancelAppointment(UUID appointmentId) throws AppointmentNotFoundException, InvalidTimeslotException {
        Appointment appointment = (Appointment) appointmentRepository.findById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException();
        }
        Appointment nextQueuedAppointment = waitListRepository.getNextQueuedAppointment(appointment);
        if (nextQueuedAppointment != null) {
            nextQueuedAppointment.setAppointmentStatus(AppointmentStatus.BOOKED);
            appointmentRepository.save(nextQueuedAppointment);
        } else {
            Doctor doctor = doctorRepository.findById(appointment.getDoctorId());
            doctor.addFreeTimeslot(appointment.getTimeslot(), new OneHourTimeslotValidationStrategy());
            doctorRepository.save(doctor);
        }
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getPatientAppointments(UUID patientId) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(patientId);
        if (patient == null) {
            throw new PatientNotFoundException();
        }
        return ((AppointmentRepository) appointmentRepository)
                .getAllAppointmentsOfPatient(patientId);
    }

    public List<Appointment> getDoctorAppointments(UUID doctorId) throws DoctorNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException();
        }
        return ((AppointmentRepository) appointmentRepository)
                .getAllAppointmentsOfDoctor(doctorId);
    }

    public List<DoctorSlot> getAllFreeSlots(DoctorSpeciality speciality, RankStrategy rankStrategy) {
        List<DoctorSlot> slots = new ArrayList<>();
        List<Doctor> doctors = doctorRepository.findAllBySpecialty(speciality);
        for (Doctor doctor: doctors) {
            slots.addAll(doctor.getDoctorSlots());
        }
        rankStrategy.rank(slots);
        return slots;
    }
}
