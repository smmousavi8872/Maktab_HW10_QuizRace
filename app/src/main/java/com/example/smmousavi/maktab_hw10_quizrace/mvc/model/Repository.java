package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.DatabaseHelper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.UserCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.CategoryTable;
import static com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserTable;

public class Repository {

  private List<Question> questions;
  private static Repository instance;
  DatabaseHelper db;

  private Repository(Context context) {
    //generateQuestionList();
    db = new DatabaseHelper(context);
    db.getWritableDatabase();
  } // end of Repository()


  public static Repository getInstance(Context context) {
    if (instance == null)
      instance = new Repository(context);

    return instance;
  }// end of getInstance()


  public List<Question> getQuestions() {
    return questions;

  }// end of getQuestions()


  public Question getQuestion(UUID questionId) {
    for (Question question : questions) {

      if (question.getId().equals(questionId))
        return question;
    }
    return null;
  } // end of getQuestion


  public long addUser(String uuid, String username, String password) {
    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(UserTable.COLUMN_UUID, uuid);
    values.put(UserTable.COLUMN_USER, username);
    values.put(UserTable.COLUMN_PASS, password);

    long id = sqLiteDatabase.insert(UserTable.NAME, null, values);

    sqLiteDatabase.close();
    return id;
  }// end of addUser


  public User getUser(String uuid) {
    SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.query(UserTable.NAME,
      new String[]{UserTable.COLUMN_ID, UserTable.COLUMN_USER, UserTable.COLUMN_PASS, UserTable.COLUMN_UUID},
      UserTable.COLUMN_UUID + " = ? ", new String[]{uuid}, null, null, null);

    String username = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_USER));
    String password = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_PASS));

    User user = new User(username, password);

    cursor.close();
    return user;
  }// end of getUser


  public List<User> getUserList() {
    List<User> userList = new ArrayList<>();
    UserCursorWrapper cursor = getQuery(UserTable.NAME, null, null);
    if (cursor.getCount() > 0) {
      try {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          userList.add(cursor.getAllUsers());
          cursor.moveToNext();

        }
      } finally {
        cursor.close();
      }
    }
    return userList;
  }// end of getUserList()


  public int getUsersCount() {
    String countQuery = "SELECT  * FROM " + UserTable.NAME;
    SQLiteDatabase sqliteDatabase = db.getReadableDatabase();
    Cursor cursor = sqliteDatabase.rawQuery(countQuery, null);
    int count = cursor.getCount();
    cursor.close();
    return count;
  }


  public int updateUser(User user) {
    SQLiteDatabase sqliteDatabase = db.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(UserTable.COLUMN_UUID, user.getmId().toString());
    values.put(UserTable.COLUMN_USER, user.getName());
    values.put(UserTable.COLUMN_PASS, user.getPassword());
    return sqliteDatabase.update(UserTable.NAME, values, UserTable.COLUMN_UUID + " = ? ",
      new String[]{user.getmId().toString()});
  }


  public void deleteUser(User user) {
    SQLiteDatabase sqliteDatabase = db.getWritableDatabase();
    String whereClause = UserTable.COLUMN_ID + " = ?";
    String[] whereAgrs = {user.getmId().toString()};
    sqliteDatabase.delete(UserTable.NAME, whereClause, whereAgrs);
    sqliteDatabase.close();
  }


  public long addCategory(String uuid, String name) {
    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(CategoryTable.COLUMN_UUID, uuid);
    values.put(CategoryTable.COLUMN_NAME, name);

    long id = sqLiteDatabase.insert(CategoryTable.NAME, null, values);

    sqLiteDatabase.close();
    return id;
  }


  public Category getCategory(String uuid) {
    SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.query(UserTable.NAME,
      new String[]{CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME, CategoryTable.COLUMN_UUID},
      CategoryTable.COLUMN_UUID + "=?", new String[]{uuid}, null, null, null);
    String name = cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NAME));
    Category category = new Category(UUID.fromString(uuid));
    category.setName(name);

    cursor.close();
    return category;
  }


  public UserCursorWrapper getQuery(String tableName, String whereClause, String[] whereArgs) {
    // ??? must be completed ??? //
    return null;
  }
}
