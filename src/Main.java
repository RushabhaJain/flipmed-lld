import models.*;
import repository.impl.AppointmentRepository;
import repository.impl.DoctorRepository;
import repository.impl.PatientRepository;
import repository.impl.WaitListRepository;
import services.AppointmentService;
import services.DoctorService;
import services.PatientService;
import strategies.rankStrategy.impl.StartTimeRankStrategy;
import strategies.timeslotValidationStrategy.impl.OneHourTimeslotValidationStrategy;

import java.util.List;

public class Main {

    public static void printDoctorSlots(List<DoctorSlot> doctorSlots) {
        for(DoctorSlot doctorSlot: doctorSlots) {
            System.out.println("Doctor: " + doctorSlot.getDoctor().getName());
            System.out.println("Slot: " + doctorSlot.getTimeslot().getStartTime() + " - " + doctorSlot.getTimeslot().getEndTime());
            System.out.println("-------------------");
        }
    }
    public static void main(String[] args) throws Exception {
        DoctorRepository doctorRepository = new DoctorRepository();
        PatientRepository patientRepository = new PatientRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        WaitListRepository waitListRepository = new WaitListRepository();

        DoctorService doctorService = new DoctorService(doctorRepository);
        PatientService patientService = new PatientService(patientRepository);
        AppointmentService appointmentService = new AppointmentService(
                appointmentRepository,
                doctorRepository,
                patientRepository,
                waitListRepository
        );

        // Add 3 doctors
        Doctor doctorRushabha = doctorService
                .register("Rushabha", DoctorSpeciality.CARDIOLOGIST);
        Timeslot timeslot = new Timeslot(9, 10);
        doctorRushabha.addFreeTimeslot(
                timeslot,
                new OneHourTimeslotValidationStrategy()
        );
        doctorRushabha.addFreeTimeslot(
                new Timeslot(10, 11),
                new OneHourTimeslotValidationStrategy()
        );
        doctorRushabha.addFreeTimeslot(
                new Timeslot(11, 12),
                new OneHourTimeslotValidationStrategy()
        );

        Doctor doctorSwati = doctorService
                .register("Swati", DoctorSpeciality.CARDIOLOGIST);
        doctorSwati.addFreeTimeslot(
                new Timeslot(10, 11),
                new OneHourTimeslotValidationStrategy()
        );

        Patient patientMitesh = patientService.register("Mitesh");
        Patient patientTejas = patientService.register("Tejas");

        Appointment mitestDoctorAppointment = appointmentService.bookAppointment(patientMitesh.getId(), doctorRushabha.getId(), timeslot);
        Appointment tejasAppointment = appointmentService.bookAppointment(patientTejas.getId(), doctorRushabha.getId(), new Timeslot(11, 12));

        printDoctorSlots(appointmentService.getAllFreeSlots(DoctorSpeciality.CARDIOLOGIST, new StartTimeRankStrategy()));
        System.out.println(appointmentService.getPatientAppointments(patientMitesh.getId()));
        System.out.println(appointmentService.getPatientAppointments(patientTejas.getId()));
        System.out.println("Before cancelling tejas event - ");
        System.out.println(tejasAppointment);
        appointmentService.cancelAppointment(mitestDoctorAppointment.getAppointmentId());
        System.out.println("After cancelling tejas event -");
        System.out.println(tejasAppointment);


    }
}
