package services;

import models.Patient;
import repository.IPatientRepository;

import java.util.UUID;

public class PatientService {

    private final IPatientRepository patientRepository;

    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient register(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be null or empty");
        }
        UUID uniqueId = UUID.randomUUID();
        return patientRepository.save(new Patient(uniqueId, name));
    }
}
