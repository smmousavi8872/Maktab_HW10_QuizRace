package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Question {

  private UUID mId;
  private String mText;
  private String mCategory;
  private String difficulty;


  public Question(String text) {
    mId = UUID.randomUUID();
    mText = text;

  }// end of Question()


  public Question(UUID id, String text) {
    mId = id;
    mText = text;
  }


  public UUID getId() {
    return mId;
  }


  public void setId(UUID id) {
    this.mId = id;
  }


  public String getText() {
    return mText;
  }


  public void setText(String mText) {
    this.mText = mText;
  }


  public String getCategory() {
    return mCategory;
  }


  public void setCategory(String categoryId) {
    this.mCategory = categoryId;
  }


  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }
}