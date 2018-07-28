package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class UserPassedLevel {

  private UUID id;
  private UUID userId;
  private String category;
  private String difficulty;


  public UserPassedLevel() {
    id = UUID.randomUUID();
  }


  public UserPassedLevel(UUID id) {
    this.id = id;
  }


  public UUID getId() {
    return id;
  }


  public UUID getUserId() {
    return userId;
  }


  public void setUserId(UUID userId) {
    this.userId = userId;
  }


  public String getCategory() {
    return category;
  }


  public void setCategory(String category) {
    this.category = category;
  }


  public String getDifficulty() {
    return difficulty;
  }


  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }
}
