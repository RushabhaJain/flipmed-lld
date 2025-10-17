package strategies.rankStrategy.impl;

import models.Doctor;
import models.DoctorSlot;
import strategies.rankStrategy.RankStrategy;

import java.util.Comparator;
import java.util.List;

public class StartTimeRankStrategy implements RankStrategy {
    @Override
    public void rank(List<DoctorSlot> doctorSlots) {
        doctorSlots.sort(Comparator.comparingInt(doctorSlot -> doctorSlot.getTimeslot().getStartTime()));
    }
}
