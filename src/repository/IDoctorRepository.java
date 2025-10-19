package repository;

import models.Doctor;
import models.DoctorSpeciality;

import java.util.List;
import java.util.UUID;

public interface IDoctorRepository {
    Doctor save(Doctor doctor);
    Doctor findById(UUID doctorId);
    List<Doctor> findAll();
    void delete(Doctor doctor);
    void deleteById(UUID doctorId);
    List<Doctor> findAllBySpecialty(DoctorSpeciality speciality);
}
