package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;

import java.util.UUID;

public class QuestionCursorWrapper extends CursorWrapper {

  public QuestionCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public Question getQuestion() {
    String uuid = getString(getColumnIndex(QuizSchema.QuestionTable.Cols.UUID.toString()));
    String text = getString(getColumnIndex(QuizSchema.QuestionTable.Cols.QUESTION_TEXT));
    String category = getString(getColumnIndex(QuizSchema.QuestionTable.Cols.CATEGORY));
    String level = getString(getColumnIndex(QuizSchema.QuestionTable.Cols.DIFFICULTY));

    Question question = new Question(UUID.fromString(uuid), text);
    question.setCategory(category);
    question.setDifficulty(level);

    return question;
  }
}
