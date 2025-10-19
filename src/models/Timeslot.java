package models;

import java.util.Objects;

public class Timeslot {
    private final int startHour;
    private final int startMinute;
    private final int endHour;
    private final int endMinute;

    public Timeslot(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    // Convenience constructor for hour-only slots (backward compatibility)
    public Timeslot(int startHour, int endHour) {
        this(startHour, 0, endHour, 0);
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    // Get total minutes from start of day for easier comparison
    public int getStartTimeInMinutes() {
        return startHour * 60 + startMinute;
    }

    public int getEndTimeInMinutes() {
        return endHour * 60 + endMinute;
    }

    // Get duration in minutes
    public int getDurationInMinutes() {
        return getEndTimeInMinutes() - getStartTimeInMinutes();
    }

    // Backward compatibility methods
    public int getStartTime() {
        return startHour;
    }

    public int getEndTime() {
        return endHour;
    }

    @Override
    public String toString() {
        return String.format("Timeslot{%02d:%02d - %02d:%02d}", 
            startHour, startMinute, endHour, endMinute);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return startHour == timeslot.startHour && 
               startMinute == timeslot.startMinute &&
               endHour == timeslot.endHour && 
               endMinute == timeslot.endMinute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startHour, startMinute, endHour, endMinute);
    }
}
