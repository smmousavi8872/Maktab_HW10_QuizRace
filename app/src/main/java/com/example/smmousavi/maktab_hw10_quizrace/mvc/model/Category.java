package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Category {

    UUID mId;
    String mName;
    int mSerie;

    public Category(String name) {
        mId = UUID.randomUUID();
        mName = name;
    }


    public Category(UUID id, String name) {
        mId = id;
        mName = name;
    }


    public UUID getId() {
        return mId;
    }


    public String getName() {
        return mName;
    }


    public void setName(String name) {
        mName = name;
    }

    public int getSerie() {
        return mSerie;
    }

    public void setSerie(int serie) {
        this.mSerie = serie;
    }
}
