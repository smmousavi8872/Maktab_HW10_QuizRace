package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserPassedLevels;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.UserPassedLevel;

import java.util.UUID;

public class UserPassedLevelsCursorWrapper extends CursorWrapper {
  public UserPassedLevelsCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public UserPassedLevel getUserPassedLevel() {
    String uuidStr = getString(getColumnIndex(UserPassedLevels.Cols.UUID.toString()));
    String userUUIDStr = getString(getColumnIndex(UserPassedLevels.Cols.USER_ID.toString()));
    String category = getString(getColumnIndex(UserPassedLevels.Cols.CATEGORY));
    String difficulty = getString(getColumnIndex(UserPassedLevels.Cols.DIFFICULTY));

    UserPassedLevel userPassedLevel = new UserPassedLevel(UUID.fromString(uuidStr));
    userPassedLevel.setUserId(UUID.fromString(userUUIDStr));
    userPassedLevel.setCategory(category);
    userPassedLevel.setDifficulty(difficulty);

    return userPassedLevel;
  }

}
