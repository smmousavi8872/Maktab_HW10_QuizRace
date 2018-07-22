package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Category {

    UUID uuid;
    String name;

    public Category(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
