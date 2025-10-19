package repository;

import models.Patient;

import java.util.List;
import java.util.UUID;

public interface IPatientRepository {
    Patient save(Patient patient);
    Patient findById(UUID patientId);
    List<Patient> findAll();
    void delete(Patient patient);
    void deleteById(UUID patientId);
}
