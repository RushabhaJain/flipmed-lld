package strategies.rankStrategy;

import models.DoctorSlot;

import java.util.List;

public interface RankStrategy {
    void rank(List<DoctorSlot> doctorSlots);
}
