package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class User {

  private UUID mId;
  private String mName;


  public UUID getmId() {
    return mId;
  }

  public void setId(UUID mId) {
    this.mId = mId;
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

  public long getTotalPoint() {
    return totalPoint;
  }

  public void setTotalPoint(long totalPoint) {
    this.totalPoint = totalPoint;
  }

  private String mPassword;
  private long totalPoint;


  public User(String name, String password) {
    mName = name;
    mPassword = password;

  }


}