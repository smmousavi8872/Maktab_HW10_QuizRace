package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Question {

  private UUID mId;
  private String mText;
  private int mCategoryId;
  private Answer[] mAnswers;


  public Question(String text, Answer[] answers) {
    mId = UUID.randomUUID();
    mText = text;
    mAnswers = answers;

  }// end of Question()


  public UUID getId() {
    return mId;
  }// end of getId()


  public String getText() {
    return mText;
  }// end of getText()


  public void setText(String mText) {
    this.mText = mText;
  }// end of setText


  public int getCategoryId() {
    return mCategoryId;
  }// end of getCategoryId()


  public void setCategoryId(int categoryId) {
    this.mCategoryId = categoryId;
  }// end of setCategoryId()


  public Answer[] getAnswers() {
    return mAnswers;
  }// end of getAnswers()


  public void setAnswers(Answer[] answers) {
    this.mAnswers = answers;
  }// end of setAnswers()


}