package repository.impl;

import models.Doctor;
import models.DoctorSpeciality;
import repository.IRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DoctorRepository implements IRepository<Doctor, UUID> {
    private Map<UUID, Doctor> map = new HashMap<>();

    @Override
    public Doctor save(Doctor entity) {
        map.put(entity.getId(), entity);
        return entity;
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
