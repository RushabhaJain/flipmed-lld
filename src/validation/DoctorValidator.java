package validation;

import constants.AppConstants;
import models.Doctor;

public class DoctorValidator {
    
    public static boolean isValidRating(double rating) {
        return rating >= AppConstants.MIN_DOCTOR_RATING && rating <= AppConstants.MAX_DOCTOR_RATING;
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    public static void validateDoctor(Doctor doctor) throws exceptions.InvalidDoctorException {
        if (doctor == null) {
            throw new exceptions.InvalidDoctorException("Doctor cannot be null");
        }
        
        if (!isValidName(doctor.getName())) {
            throw new exceptions.InvalidDoctorException("Doctor name cannot be null or empty");
        }
        
        if (!isValidRating(doctor.getRating())) {
            throw new exceptions.InvalidDoctorException("Doctor rating must be between " + 
                AppConstants.MIN_DOCTOR_RATING + " and " + AppConstants.MAX_DOCTOR_RATING);
        }
    }
}
