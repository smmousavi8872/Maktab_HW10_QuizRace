package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Category {

  UUID mId;
  String mName;

  public Category(String name) {
    mId = UUID.randomUUID();
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
}
