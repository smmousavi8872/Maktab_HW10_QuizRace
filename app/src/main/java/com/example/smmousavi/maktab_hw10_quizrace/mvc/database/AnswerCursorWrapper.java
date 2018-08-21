package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;

import java.util.UUID;

public class AnswerCursorWrapper extends CursorWrapper {

  public AnswerCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public Answer getAnswer() {
    String uuid = getString(getColumnIndex(QuizSchema.AnswerTable.Cols.UUID.toString()));
    String text = getString(getColumnIndex(QuizSchema.AnswerTable.Cols.ANSWER_TEXT));
    String questionUUID = getString(getColumnIndex(QuizSchema.AnswerTable.Cols.QUESTION_ID.toString()));
    int intTrue = getInt(getColumnIndex(QuizSchema.AnswerTable.Cols.IS_TRUE));

    Answer answer = new Answer(UUID.fromString(uuid), text, intTrue == 1);
    answer.setRelatedQuestionId(UUID.fromString(questionUUID));

    return answer;
  }
}
