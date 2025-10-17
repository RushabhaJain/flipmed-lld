package models;

import java.util.UUID;

public class Patient {
    private final UUID id;
    private final String name;

    public Patient(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
