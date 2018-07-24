package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Question {

  private UUID mId;
  private String mText;
  private String mCategory;
  private String level;


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
  }// end of getId()


  public void setId(UUID id) {
    this.mId = id;
  } // end of setId


  public String getText() {
    return mText;
  }// end of getText()


  public void setText(String mText) {
    this.mText = mText;
  }// end of setText


  public String getCategory() {
    return mCategory;
  }// end of getCategoryId()


  public void setCategory(String categoryId) {
    this.mCategory = categoryId;
  }// end of setCategory()


  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }
}