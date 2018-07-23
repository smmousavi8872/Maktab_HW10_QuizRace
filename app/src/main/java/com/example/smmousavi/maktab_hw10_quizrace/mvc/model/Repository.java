package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.CategoryCursorWrapper;
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
  private SQLiteDatabase database;

  private Repository(Context context) {
    //generateQuestionList();
    DatabaseHelper db = new DatabaseHelper(context);
    database = db.getWritableDatabase();
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


  public void addUser(User user) {
    ContentValues values = getUserContentValue(user);
    database.insert(UserTable.NAME, null, values);
    database.close();
  }// end of addUser


  public User getUser(UUID uuid) {
    String whereClause = UserTable.Cols.UUID + " = ? ";
    String[] whereArgs = {uuid.toString()};
    UserCursorWrapper cursor = getUserQuery(UserTable.NAME, whereClause, whereArgs);

    return cursor.getUser();

  }// end of getUser


  public List<User> getUserList() {
    List<User> userList = new ArrayList<>();
    UserCursorWrapper cursor = getUserQuery(UserTable.NAME, null, null);
    if (cursor.getCount() > 0) {
      try {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          userList.add(cursor.getUser());
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
    Cursor cursor = database.rawQuery(countQuery, null);
    int count = cursor.getCount();
    cursor.close();
    return count;
  }// end of getUsersCount()


  public int updateUser(User user) {
    ContentValues values = getUserContentValue(user);
    String whereClause = UserTable.Cols.UUID + " = ? ";
    String[] whereArgs = {user.getId().toString()};
    return database.update(UserTable.NAME, values, whereClause, whereArgs);
  }// end of updateUser()


  public void deleteUser(User user) {
    String whereClause = UserTable.Cols.UUID + " = ? ";
    String[] whereAgrs = {user.getId().toString()};
    database.delete(UserTable.NAME, whereClause, whereAgrs);
    database.close();
  }


  public void addCategory(Category category) {
    ContentValues values = getCategoryContentValue(category);
    database.insert(CategoryTable.NAME, null, values);
    database.close();
  }


  public Category getCategory(UUID uuid) {
    String whereClause = CategoryTable.Cols.UUID + " = ?";
    String[] whereArgs = {uuid.toString()};
    CategoryCursorWrapper cursor = getCategoryQuery(UserTable.NAME, whereClause, whereArgs);
    Category category = cursor.getCategory();
    cursor.close();
    return category;
  }


  public ContentValues getUserContentValue(User user) {
    ContentValues values = new ContentValues();
    values.put(UserTable.Cols.UUID, user.getId().toString());
    values.put(UserTable.Cols.USER_NAME, user.getName());
    values.put(UserTable.Cols.PASSWORD, user.getPassword());

    return values;
  }


  public ContentValues getCategoryContentValue(Category category) {
    ContentValues values = new ContentValues();
    values.put(CategoryTable.Cols.UUID, category.getId().toString());
    values.put(CategoryTable.Cols.CATEGORY_NAME, category.getName());
    return values;
  }


  public UserCursorWrapper getUserQuery(String tableName, String whereClause, String[] whereArgs) {
    Cursor cursor = database.query(
      tableName,
      null,
      whereClause,
      whereArgs,
      null,
      null,
      null);

    return new UserCursorWrapper(cursor);
  }

  public CategoryCursorWrapper getCategoryQuery(String tableName, String whereClause, String[] whereArgs) {
    Cursor cursor = database.query(
      tableName,
      null,
      whereClause,
      whereArgs,
      null,
      null,
      null);

    return new CategoryCursorWrapper(cursor);
  }
}
