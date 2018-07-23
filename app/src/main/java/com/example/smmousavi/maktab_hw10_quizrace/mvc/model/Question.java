package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Question {

    private UUID mId;
    private String mText;
    private int mCategoryId;
    private int level;
    private Answer[] mAnswers;


    public Question(String text) {
        mId = UUID.randomUUID();
        mText = text;

    }// end of Question()


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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}