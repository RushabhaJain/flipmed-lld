package services;

import models.DoctorSlot;
import models.DoctorSpeciality;
import repository.IDoctorRepository;
import strategies.rankStrategy.RankStrategy;

import java.util.ArrayList;
import java.util.List;

public class DoctorSlotService {
    private final IDoctorRepository doctorRepository;

    public DoctorSlotService(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorSlot> getAllFreeSlots(DoctorSpeciality speciality, RankStrategy rankStrategy) {
        List<DoctorSlot> slots = new ArrayList<>();
        List<models.Doctor> doctors = doctorRepository.findAllBySpecialty(speciality);
        
        for (models.Doctor doctor : doctors) {
            slots.addAll(doctor.getDoctorSlots());
        }
        
        if (rankStrategy != null) {
            rankStrategy.rank(slots);
        }
        
        return slots;
    }
}
