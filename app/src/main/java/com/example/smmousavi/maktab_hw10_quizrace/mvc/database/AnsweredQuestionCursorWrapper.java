package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.AnsweredQuestionTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.AnsweredQuestion;

import java.util.UUID;

public class AnsweredQuestionCursorWrapper extends CursorWrapper {
  public AnsweredQuestionCursorWrapper(Cursor cursor) {
    super(cursor);
  }


  public AnsweredQuestion getAnsweredQuestion() {

    String answeredQuestionIdStr = getString(getColumnIndex(AnsweredQuestionTable.Cols.UUID.toString()));
    String userIdStr = getString(getColumnIndex(AnsweredQuestionTable.Cols.USER_ID.toString()));
    String questionIdStr = getString(getColumnIndex(AnsweredQuestionTable.Cols.QUESTION_ID.toString()));
    String answerIdStr = getString(getColumnIndex(AnsweredQuestionTable.Cols.ANSWER_ID.toString()));
    String questionCategory = getString(getColumnIndex(AnsweredQuestionTable.Cols.CATEGORY));
    String questionDifficulty = getString(getColumnIndex(AnsweredQuestionTable.Cols.DIFFICULTY));
    int savedTime = getInt(getColumnIndex(AnsweredQuestionTable.Cols.SAVED_TIME));

    AnsweredQuestion answeredQuestion = new AnsweredQuestion(UUID.fromString(answeredQuestionIdStr), UUID.fromString(userIdStr)
      , UUID.fromString(questionIdStr), UUID.fromString(answerIdStr));

    answeredQuestion.setQuestionCategory(questionCategory);
    answeredQuestion.setQuestionDifficulty(questionDifficulty);
    answeredQuestion.setSavedTime(savedTime);

    return answeredQuestion;

  }


}
