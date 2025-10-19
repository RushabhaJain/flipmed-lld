package repository.impl;

import models.Patient;
import repository.IPatientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PatientRepository implements IPatientRepository {

    Map<UUID, Patient> map = new HashMap<>();

    @Override
    public Patient save(Patient patient) {
        map.put(patient.getId(), patient);
        return patient;
    }

    @Override
    public Patient findById(UUID patientId) {
        return map.getOrDefault(patientId, null);
    }

    @Override
    public List<Patient> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public void delete(Patient patient) {
        this.deleteById(patient.getId());
    }

    @Override
    public void deleteById(UUID patientId) {
        map.remove(patientId);
    }
}
