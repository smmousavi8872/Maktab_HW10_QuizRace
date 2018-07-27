package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class AnsweredQuestion {

  private UUID Id;
  private UUID userId;
  private UUID questionId;
  private UUID answerId;
  private String questionCategory;
  private String questionDifficulty;
  private int savedTime;

  public AnsweredQuestion(UUID userId, UUID questionId, UUID answerId) {
    Id = UUID.randomUUID();
    this.userId = userId;
    this.questionId = questionId;
    this.answerId = answerId;
  }// end ofAnsweredQuestion()


  public AnsweredQuestion(UUID answeredQuestionId, UUID userId, UUID questionId, UUID answerId) {
    this.Id = answeredQuestionId;
    this.userId = userId;
    this.questionId = questionId;
    this.answerId = answerId;
  }// end ofAnsweredQuestion()


  public UUID getId() {
    return Id;
  }


  public UUID getUserId() {
    return userId;
  }


  public UUID getQuestionId() {
    return questionId;
  }


  public UUID getAnswerId() {
    return answerId;
  }


  public String getQuestionCategory() {
    return questionCategory;
  }


  public void setQuestionCategory(String questionCategory) {
    this.questionCategory = questionCategory;
  }


  public String getQuestionDifficulty() {
    return questionDifficulty;
  }


  public void setQuestionDifficulty(String questionDifficulty) {
    this.questionDifficulty = questionDifficulty;
  }

  public int getSavedTime() {
    return savedTime;
  }

  public void setSavedTime(int savedTime) {
    this.savedTime = savedTime;
  }
}
