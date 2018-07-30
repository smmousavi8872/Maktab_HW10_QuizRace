package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.AnswerCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.AnsweredQuestionCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.CategoryCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.DatabaseHelper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuestionCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.AnswerTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.AnsweredQuestionTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.QuestionTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserPassedLevels;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.UserCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.UserPassedLevelsCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.CategoryTable;
import static com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserTable;

public class Repository {

  private static Repository instance;
  private SQLiteDatabase database;
  private User currentUser;


  private Repository(Context context) {
    DatabaseHelper db = new DatabaseHelper(context); /* this must be always at the first line */
    database = db.getWritableDatabase(); /* this must be always at the second line */

    //generateQuestions(); /* this generates a set of random quesions for test delete when real database added*/
  } // end of Repository()


  public static Repository getInstance(Context context) {
    if (instance == null)
      instance = new Repository(context);

    return instance;
  }// end of getInstance()


  public void addQuestion(Question question) {
    ContentValues values = getQuestionContentValue(question);
    database.insert(QuestionTable.NAME, null, values);
  }// end of addQuesiont()


  public User getCurrentUser() {
    return currentUser;
  }


  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }


  public Question getQuestion(UUID questionId) {
    String whereClause = QuestionTable.Cols.UUID + " = ? ";
    String[] whereArgs = {questionId.toString()};
    QuestionCursorWrapper cursor = getQuestionQuery(QuestionTable.NAME, whereClause, whereArgs);
    try {
      if (cursor.getCount() == 0)
        return null;

      cursor.moveToFirst();
      return cursor.getQuestion();

    } finally {
      cursor.close();
    }
  }// end of questionId()


  public List<Question> getQuestionsList(String category, String level) {
    List<Question> questions = new ArrayList<>();
    String whereClause = QuestionTable.Cols.CATEGORY + " = ? and "
      + QuestionTable.Cols.DIFFICULTY + " = ? ";
    String[] whereArgs = {category, level};
    QuestionCursorWrapper cursor = getQuestionQuery(QuestionTable.NAME, whereClause, whereArgs);
    if (cursor.getCount() > 0) {
      try {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          questions.add(cursor.getQuestion());
          cursor.moveToNext();
        }

      } finally {
        cursor.close();
      }
    }
    return questions;
  }// end of getQuestionsList()


  public void addAnswer(Answer answer) {
    ContentValues values = getAnswerContentValue(answer);
    database.insert(AnswerTable.NAME, null, values);
  }// end of addAnswer()


  public void addAnsweredQuestion(AnsweredQuestion answeredQuestion) {
    ContentValues values = getAnsweredQuestionContentValue(answeredQuestion);
    database.insert(AnsweredQuestionTable.NAME, null, values);
  }// addAnsweredQuestion()


  public List<AnsweredQuestion> getAnsweredQuestionList(UUID currentUserId, String category, String difficulty) {
    List<AnsweredQuestion> answeredQuestionList = new ArrayList<>();
    String whereClause = AnsweredQuestionTable.Cols.USER_ID + " = ? and "
      + AnsweredQuestionTable.Cols.CATEGORY + " = ? and "
      + AnsweredQuestionTable.Cols.DIFFICULTY + " = ?";
    String[] whereArgs = {currentUserId.toString(), category, difficulty};
    AnsweredQuestionCursorWrapper cursor = getAnsweredQuestionQuery(AnsweredQuestionTable.NAME, whereClause, whereArgs);
    if (cursor.getCount() > 0) {
      try {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          answeredQuestionList.add(cursor.getAnsweredQuestion());
          cursor.moveToNext();
        }

      } finally {
        cursor.close();
      }
    }
    return answeredQuestionList;
  }// end of getAnsweredQuestionList()


  public AnsweredQuestion getAnsweredQuestion(UUID answeredQutesionId) {
    String whereClause = AnsweredQuestionTable.Cols.UUID + " = ? ";
    String[] whereArgs = {answeredQutesionId.toString()};
    AnsweredQuestionCursorWrapper cursor = getAnsweredQuestionQuery(AnsweredQuestionTable.NAME, whereClause, whereArgs);
    try {
      if (cursor.getCount() == 0)
        return null;

      cursor.moveToFirst();
      return cursor.getAnsweredQuestion();

    } finally {
      cursor.close();
    }
  }// end of getAnsweredQuestion()


  public List<Answer> getAnswersList(UUID mRelatedQuestionId) {
    List<Answer> questionAnswers = new ArrayList<>();
    String whereClause = AnswerTable.Cols.QUESTION_ID + " = ? ";
    String[] whereArgs = {mRelatedQuestionId.toString()};
    AnswerCursorWrapper cursor = getAnswerQuery(AnswerTable.NAME, whereClause, whereArgs);
    if (cursor.getCount() > 0) {
      try {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
          questionAnswers.add(cursor.getAnswer());
          cursor.moveToNext();
        }

      } finally {
        cursor.close();
      }
    }
    return questionAnswers;
  }// end of getAnswer()

    public List<Category> getCategoryList() {
        List<Category> categories = new ArrayList<>();
        CategoryCursorWrapper cursor = getCategoryQuery(CategoryTable.NAME, null, null);
        Log.e("cursercount", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    categories.add(cursor.getCategory());
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }
        return categories;
    }

  public void addUser(User user) {
    ContentValues values = getUserContentValue(user);
    database.insert(UserTable.NAME, null, values);
  }// end of addUser


  public User getUser(UUID userId) {
    String whereClause = UserTable.Cols.UUID + " = ?";
    String[] whereArgs = {userId.toString()};
    UserCursorWrapper cursor = getUserQuery(UserTable.NAME, whereClause, whereArgs);
    try {
      if (cursor.getCount() == 0)
        return null;

      cursor.moveToFirst();
      return cursor.getUser();

    } finally {
      cursor.close();
    }
  }// end of getUser()


  public User getUser(String username, String password) {
    String whereClause = UserTable.Cols.USER_NAME + " = ? and "
      + UserTable.Cols.PASSWORD + " = ? ";
    String[] whereArgs = {username, password};
    UserCursorWrapper cursor = getUserQuery(UserTable.NAME, whereClause, whereArgs);
    try {
      if (cursor.getCount() == 0)
        return null;

      cursor.moveToFirst();
      return cursor.getUser();

    } finally {
      cursor.close();

    }
  }


  public List<User> getUsersList() {
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
  }// end of getUsersList()

    public List<UserPassedLevel> getUserPassedLevelList() {
        List<UserPassedLevel> userPassedLevelList = new ArrayList<>();
        UserPassedLevelsCursorWrapper cursor = getUserPassedLevelsQuery(UserPassedLevels.NAME, null, null);
        if (cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    userPassedLevelList.add(cursor.getUserPassedLevel());
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }
        return userPassedLevelList;
    }


  public void updateUser(User user) {
    ContentValues values = getUserContentValue(user);
    String whereClause = UserTable.Cols.UUID + " = ? ";
    String[] whereArgs = {user.getId().toString()};
    database.update(UserTable.NAME, values, whereClause, whereArgs);
  }// end of updateUser()


  public void deleteUser(User user) {
    String whereClause = UserTable.Cols.UUID + " = ? ";
    String[] whereAgrs = {user.getId().toString()};
    database.delete(UserTable.NAME, whereClause, whereAgrs);
  }


  public void addUserPassedLevel(UserPassedLevel level) {
    ContentValues values = getUserPassedLevelContentValue(level);
    database.insert(UserPassedLevels.NAME, null, values);
  }


  public List<UserPassedLevel> getUserPassedLevels(UUID userId) {
    List<UserPassedLevel> userPassedLevels = new ArrayList<>();
    String whereClause = UserPassedLevels.Cols.USER_ID + " = ?";
    String[] whereArgs = {userId.toString()};
    UserPassedLevelsCursorWrapper cursorWrapper = getUserPassedLevelsQuery(UserPassedLevels.NAME, whereClause, whereArgs);

    if (cursorWrapper.getCount() > 0) {
      try {
        cursorWrapper.moveToFirst();
        while (!cursorWrapper.isAfterLast()) {
          userPassedLevels.add(cursorWrapper.getUserPassedLevel());
          cursorWrapper.moveToNext();

        }
      } finally {
        cursorWrapper.close();

      }
    }
    return userPassedLevels;
  }// end of getUserPassedLevel()


  public void addCategory(Category category) {
    ContentValues values = getCategoryContentValue(category);
    database.insert(CategoryTable.NAME, null, values);
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
    values.put(UserTable.Cols.TOTAL_SCORE, user.getTotalScore());

    return values;
  }// end of getUserContentValue()


  public ContentValues getCategoryContentValue(Category category) {
    ContentValues values = new ContentValues();
    values.put(CategoryTable.Cols.UUID, category.getId().toString());
    values.put(CategoryTable.Cols.CATEGORY_NAME, category.getName());
    return values;
  }// end of getCategoryContentValue()


  public ContentValues getQuestionContentValue(Question question) {
    ContentValues values = new ContentValues();
    values.put(QuestionTable.Cols.UUID, question.getId().toString());
    values.put(QuestionTable.Cols.QUESTION_TEXT, question.getText());
    values.put(QuestionTable.Cols.CATEGORY, question.getCategory());
    values.put(QuestionTable.Cols.DIFFICULTY, question.getDifficulty());

    return values;
  }// end of getQuestionContentValue()


  public ContentValues getAnsweredQuestionContentValue(AnsweredQuestion answeredQuestion) {
    ContentValues values = new ContentValues();
    values.put(AnsweredQuestionTable.Cols.UUID, answeredQuestion.getId().toString());
    values.put(AnsweredQuestionTable.Cols.USER_ID, answeredQuestion.getUserId().toString());
    values.put(AnsweredQuestionTable.Cols.QUESTION_ID, answeredQuestion.getQuestionId().toString());
    values.put(AnsweredQuestionTable.Cols.ANSWER_ID, answeredQuestion.getAnswerId().toString());
    values.put(AnsweredQuestionTable.Cols.CATEGORY, answeredQuestion.getQuestionCategory());
    values.put(AnsweredQuestionTable.Cols.DIFFICULTY, answeredQuestion.getQuestionDifficulty());
    values.put(AnsweredQuestionTable.Cols.SAVED_TIME, answeredQuestion.getSavedTime());

    return values;
  }// end of qetAnswerdQuestionContentValue()


  public ContentValues getAnswerContentValue(Answer answer) {
    ContentValues values = new ContentValues();
    values.put(AnswerTable.Cols.UUID, answer.getId().toString());
    values.put(AnswerTable.Cols.ANSWER_TEXT, answer.getText());
    values.put(AnswerTable.Cols.QUESTION_ID, answer.getRelatedQuestionId().toString());
    values.put(AnswerTable.Cols.IS_TRUE, answer.isTrue());

    return values;
  }// end of getAnswerContentValue()


  public ContentValues getUserPassedLevelContentValue(UserPassedLevel levels) {
    ContentValues values = new ContentValues();
    values.put(UserPassedLevels.Cols.UUID, levels.getId().toString());
    values.put(UserPassedLevels.Cols.USER_ID, levels.getUserId().toString());
    values.put(UserPassedLevels.Cols.CATEGORY, levels.getCategory());
    values.put(UserPassedLevels.Cols.DIFFICULTY, levels.getDifficulty());

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
  }// end of getUserQuery()


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
  }// end of getCategoryQuery()


  public QuestionCursorWrapper getQuestionQuery(String tableName, String whereClause, String[] whereArgs) {
    Cursor cursor = database.query(
      tableName,
      null,
      whereClause,
      whereArgs,
      null,
      null,
      null);

    return new QuestionCursorWrapper(cursor);
  }// end of getQuestoinQuery()


  public AnswerCursorWrapper getAnswerQuery(String tableName, String whereClause, String[] whereArgs) {

    Cursor cursor = database.query(
      tableName,
      null,
      whereClause,
      whereArgs,
      null,
      null,
      null);
    return new AnswerCursorWrapper(cursor);
  }// end of getAnswerQuery()


  public AnsweredQuestionCursorWrapper getAnsweredQuestionQuery(String tableName, String whereClause, String[] whereArgs) {
    Cursor cursor = database.query(
      tableName,
      null,
      whereClause,
      whereArgs,
      null,
      null,
      null);

    return new AnsweredQuestionCursorWrapper(cursor);
  }


  public UserPassedLevelsCursorWrapper getUserPassedLevelsQuery(String tableName, String whereClause, String[] whereArgs) {
    Cursor cursor = database.query(
      tableName,
      null,
      whereClause,
      whereArgs,
      null,
      null,
      null,
      null);

    return new UserPassedLevelsCursorWrapper(cursor);
  }// end of UserPassedLevelsCursorWrapper()
}