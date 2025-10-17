package services;

import models.Patient;
import repository.IRepository;

import java.util.UUID;

public class PatientService {

    private final IRepository patientRepository;

    public PatientService(IRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient register(String name) {
        UUID uniqueId = UUID.randomUUID();
        return (Patient) patientRepository.save(new Patient(uniqueId, name));
    }
}
