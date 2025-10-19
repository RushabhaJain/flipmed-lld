package repository.impl;

import models.Doctor;
import models.DoctorSpeciality;
import repository.IDoctorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DoctorRepository implements IDoctorRepository {
    private Map<UUID, Doctor> map = new HashMap<>();

    @Override
    public Doctor save(Doctor doctor) {
        map.put(doctor.getId(), doctor);
        return doctor;
    }

    @Override
    public Doctor findById(UUID doctorId) {
        return map.getOrDefault(doctorId, null);
    }

    @Override
    public List<Doctor> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public void delete(Doctor doctor) {
        this.deleteById(doctor.getId());
    }

    @Override
    public void deleteById(UUID doctorId) {
        map.remove(doctorId);
    }

    public List<Doctor> findAllBySpecialty(DoctorSpeciality speciality) {
        return map.values()
                .stream()
                .filter(doctor -> doctor.getSpeciality().equals(speciality))
                .toList();
    }
}
