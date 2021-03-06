package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class User {

  private UUID mId;
  private String mName;
  private String mPassword;
  private long totalScore;

  public User(String name, String password) {
    mId = UUID.randomUUID();
    mName = name;
    mPassword = password;
  }

  public User(UUID id, String name, String password) {
    mId = id;
    mName = name;
    mPassword = password;
  }

  public UUID getId() {
    return mId;
  }

  public void setId(UUID id) {
    this.mId = id;
  }

  public String getName() {
    return mName;
  }

  public void setName(String mName) {
    this.mName = mName;
  }

  public String getPassword() {
    return mPassword;
  }

  public void setPassword(String mPassword) {
    this.mPassword = mPassword;
  }

  public long getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(long totalPoint) {
    this.totalScore = totalPoint;
  }


}