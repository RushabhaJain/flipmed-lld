package services;

import exceptions.InvalidTimeslotException;
import models.Doctor;
import models.DoctorSpeciality;
import models.Timeslot;
import repository.IRepository;
import strategies.timeslotValidationStrategy.impl.OneHourTimeslotValidationStrategy;

import java.util.UUID;

public class DoctorService {
    private final IRepository doctorRepository;

    public DoctorService(IRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor register(String doctorName, DoctorSpeciality speciality) {
        Doctor doctor = new Doctor(UUID.randomUUID(), doctorName, Math.random() * 6, speciality);
        return (Doctor) this.doctorRepository.save(doctor);
    }

    public void addAvailability(UUID doctorId, int startTime, int endTime) throws InvalidTimeslotException {
        Doctor doctor = (Doctor) doctorRepository.findById(doctorId);
        doctor.addFreeTimeslot(
                new Timeslot(startTime, endTime),
                new OneHourTimeslotValidationStrategy()
        );
        doctorRepository.save(doctor);
    }
}
