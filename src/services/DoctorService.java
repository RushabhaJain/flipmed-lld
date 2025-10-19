package services;

import constants.AppConstants;
import exceptions.InvalidDoctorException;
import exceptions.InvalidTimeslotException;
import models.Doctor;
import models.DoctorSpeciality;
import models.Timeslot;
import repository.IDoctorRepository;
import validation.DoctorValidator;

import java.util.UUID;

public class DoctorService {
    private final IDoctorRepository doctorRepository;

    public DoctorService(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor register(String doctorName, DoctorSpeciality speciality) throws InvalidDoctorException {
        double rating = Math.random() * AppConstants.MAX_DOCTOR_RATING;
        Doctor doctor = new Doctor(UUID.randomUUID(), doctorName, rating, speciality);
        DoctorValidator.validateDoctor(doctor);
        return this.doctorRepository.save(doctor);
    }

    public void addAvailability(UUID doctorId, int startTime, int endTime) throws InvalidTimeslotException {
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new exceptions.DoctorNotFoundException("Doctor not found with ID: " + doctorId);
        }
        doctor.addFreeTimeslot(new Timeslot(startTime, endTime));
        doctorRepository.save(doctor);
    }
}
