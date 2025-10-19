import models.*;
import repository.IAppointmentRepository;
import repository.IDoctorRepository;
import repository.IPatientRepository;
import repository.IWaitListRepository;
import repository.impl.AppointmentRepository;
import repository.impl.DoctorRepository;
import repository.impl.PatientRepository;
import repository.impl.WaitListRepository;
import services.*;
import strategies.rankStrategy.impl.StartTimeRankStrategy;
import utils.Logger;

import java.util.List;

public class Main {

    public static void printDoctorSlots(List<DoctorSlot> doctorSlots) {
        for(DoctorSlot doctorSlot: doctorSlots) {
            Logger.info("Doctor: " + doctorSlot.getDoctor().getName());
            Logger.info("Slot: " + doctorSlot.getTimeslot().getStartTime() + " - " + doctorSlot.getTimeslot().getEndTime());
            Logger.info("-------------------");
        }
    }
    
    public static void main(String[] args) throws Exception {
        // Initialize repositories
        IDoctorRepository doctorRepository = new DoctorRepository();
        IPatientRepository patientRepository = new PatientRepository();
        IAppointmentRepository appointmentRepository = new AppointmentRepository();
        IWaitListRepository waitListRepository = new WaitListRepository();
        
        // Initialize individual services with proper dependency injection
        DoctorService doctorService = new DoctorService(doctorRepository);
        PatientService patientService = new PatientService(patientRepository);
        
        // Initialize specialized appointment services
        AppointmentBookingService bookingService = new AppointmentBookingService(
            appointmentRepository, doctorRepository, patientRepository, waitListRepository);
        AppointmentCancellationService cancellationService = new AppointmentCancellationService(
            appointmentRepository, doctorRepository, waitListRepository);
        AppointmentQueryService queryService = new AppointmentQueryService(
            appointmentRepository, doctorRepository, patientRepository);
        DoctorSlotService slotService = new DoctorSlotService(doctorRepository);
        

        // Add doctors
        Logger.info("Registering doctors...");
        Doctor doctorRushabha = doctorService
                .register("Rushabha", DoctorSpeciality.CARDIOLOGIST);
        
        // Add availability slots for Rushabha
        doctorService.addAvailability(doctorRushabha.getId(), 9, 10);
        doctorService.addAvailability(doctorRushabha.getId(), 10, 11);
        doctorService.addAvailability(doctorRushabha.getId(), 11, 12);

        Doctor doctorSwati = doctorService
                .register("Swati", DoctorSpeciality.CARDIOLOGIST);
        doctorService.addAvailability(doctorSwati.getId(), 10, 11);

        // Register patients
        Logger.info("Registering patients...");
        Patient patientMitesh = patientService.register("Mitesh");
        Patient patientTejas = patientService.register("Tejas");

        // Book appointments
        Logger.info("Booking appointments...");
        Timeslot timeslot = new Timeslot(9, 10);
        Appointment mitestDoctorAppointment = bookingService.bookAppointment(
            patientMitesh.getId(), doctorRushabha.getId(), timeslot);
        Appointment tejasAppointment = bookingService.bookAppointment(
            patientTejas.getId(), doctorRushabha.getId(), new Timeslot(11, 12));

        // Display available slots
        Logger.info("Available doctor slots:");
        printDoctorSlots(slotService.getAllFreeSlots(DoctorSpeciality.CARDIOLOGIST, new StartTimeRankStrategy()));
        
        // Display patient appointments
        Logger.info("Patient appointments:");
        Logger.info("Mitesh's appointments: " + queryService.getPatientAppointments(patientMitesh.getId()));
        Logger.info("Tejas's appointments: " + queryService.getPatientAppointments(patientTejas.getId()));
        
        // Test cancellation
        Logger.info("Before cancelling Mitesh's appointment: " + mitestDoctorAppointment);
        cancellationService.cancelAppointment(mitestDoctorAppointment.getAppointmentId());
        Logger.info("After cancelling Mitesh's appointment:");
        Logger.info("Tejas's appointment: " + tejasAppointment);
        
        // Demonstrate using individual services directly
        Logger.info("\n=== Demonstrating individual service usage ===");
        
        // Using booking service directly
        Logger.info("Using AppointmentBookingService directly:");
        Appointment directBooking = bookingService.bookAppointment(
            patientMitesh.getId(), doctorSwati.getId(), new Timeslot(10, 11));
        Logger.info("Direct booking result: " + directBooking);
        
        // Using query service directly
        Logger.info("Using AppointmentQueryService directly:");
        List<Appointment> miteshAppointments = queryService.getPatientAppointments(patientMitesh.getId());
        Logger.info("Mitesh's appointments via query service: " + miteshAppointments);
        
        // Using slot service directly
        Logger.info("Using DoctorSlotService directly:");
        List<DoctorSlot> availableSlots = slotService.getAllFreeSlots(DoctorSpeciality.CARDIOLOGIST, new StartTimeRankStrategy());
        Logger.info("Available slots via slot service: " + availableSlots.size() + " slots found");
        
        // Using cancellation service directly
        Logger.info("Using AppointmentCancellationService directly:");
        cancellationService.cancelAppointment(directBooking.getAppointmentId());
        Logger.info("Direct cancellation completed");

    }
}
