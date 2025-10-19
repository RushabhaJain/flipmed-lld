package services;

import constants.AppConstants;
import exceptions.DoctorNotFoundException;
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

    public void addAvailability(UUID doctorId, int startTime, int endTime) throws InvalidTimeslotException, DoctorNotFoundException {
        addAvailability(doctorId, startTime, 0, endTime, 0);
    }
    
    public void addAvailability(UUID doctorId, int startHour, int startMinute, int endHour, int endMinute) throws InvalidTimeslotException, DoctorNotFoundException {
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + doctorId);
        }
        doctor.addFreeTimeslot(new Timeslot(startHour, startMinute, endHour, endMinute));
        doctorRepository.save(doctor);
    }
}
