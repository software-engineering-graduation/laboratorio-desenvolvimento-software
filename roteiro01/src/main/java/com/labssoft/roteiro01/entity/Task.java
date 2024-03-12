package com.labssoft.roteiro01.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String description;
    private Boolean status;

    public UUID getUuid() {
        return this.uuid;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
