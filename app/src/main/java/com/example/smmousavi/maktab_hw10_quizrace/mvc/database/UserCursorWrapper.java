package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

public class UserCursorWrapper extends CursorWrapper {
  public UserCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public User getAllUsers() {

    return null;
  }

}
