package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import java.util.UUID;

public class Answer {

    private UUID mId;
    private String mText;
    private UUID mRelatedQuestionId;
    private boolean mIsTrue;

    public Answer(String text, boolean isTrue) {
        mId = UUID.randomUUID();
        mText = text;
        mIsTrue = isTrue;

    }// end of Answer()

    public UUID getId() {
        return mId;
    }// end of getId()

    public void setId(UUID id) {
        this.mId = id;
    }


    public String getText() {
        return mText;
    }// end of getText()


    public void setText(String mText) {
        this.mText = mText;
    }// end of setText()


    public UUID getRelatedQuestionId() {
        return mRelatedQuestionId;
    }// end of getRelatedQuestionId()


    public void setRelatedQuestionId(UUID mRelatedQuestionId) {
        this.mRelatedQuestionId = mRelatedQuestionId;
    }// end of setRelatedQuestionId


    public boolean isTrue() {
        return mIsTrue;
    }// end of isTrue()


    public void setTrue(boolean isTrue) {
        this.mIsTrue = isTrue;
    }// end of setTrue()

    public boolean getTrue(){return mIsTrue;}
}
