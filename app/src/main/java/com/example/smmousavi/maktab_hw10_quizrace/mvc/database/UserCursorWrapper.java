package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
  public UserCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public User getUser() {

    String userId = getString(getColumnIndex(UserTable.Cols.UUID));
    String userName = getString(getColumnIndex(UserTable.Cols.USER_NAME));
    String userPassword = getString(getColumnIndex(UserTable.Cols.PASSWORD));
    long totalScore = getLong(getColumnIndex(UserTable.Cols.TOTAL_SCORE));

    User user = new User(UUID.fromString(userId), userName, userPassword);
    user.setTotalScore(totalScore);

    return user;

  }

}
