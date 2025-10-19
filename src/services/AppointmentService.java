package services;

import exceptions.*;
import models.*;
import strategies.rankStrategy.RankStrategy;

import java.util.List;
import java.util.UUID;

public class AppointmentService {
    private final AppointmentBookingService bookingService;
    private final AppointmentCancellationService cancellationService;
    private final AppointmentQueryService queryService;
    private final DoctorSlotService slotService;

    public AppointmentService(
            AppointmentBookingService bookingService,
            AppointmentCancellationService cancellationService,
            AppointmentQueryService queryService,
            DoctorSlotService slotService
    ) {
        this.bookingService = bookingService;
        this.cancellationService = cancellationService;
        this.queryService = queryService;
        this.slotService = slotService;
    }

    public Appointment bookAppointment(
            UUID patientId,
            UUID doctorId,
            Timeslot timeslot
    ) throws PatientNotFoundException, DoctorNotFoundException, TimeslotAlreadyBookedException, TimeslotNotFoundException {
        return bookingService.bookAppointment(patientId, doctorId, timeslot);
    }

    public void cancelAppointment(UUID appointmentId) throws AppointmentNotFoundException, InvalidTimeslotException {
        cancellationService.cancelAppointment(appointmentId);
    }

    public List<Appointment> getPatientAppointments(UUID patientId) throws PatientNotFoundException {
        return queryService.getPatientAppointments(patientId);
    }

    public List<Appointment> getDoctorAppointments(UUID doctorId) throws DoctorNotFoundException {
        return queryService.getDoctorAppointments(doctorId);
    }

    public List<DoctorSlot> getAllFreeSlots(DoctorSpeciality speciality, RankStrategy rankStrategy) {
        return slotService.getAllFreeSlots(speciality, rankStrategy);
    }
}
