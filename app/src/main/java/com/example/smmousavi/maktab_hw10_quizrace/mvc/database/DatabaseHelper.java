package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.AnswerTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.CategoryTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.QuestionTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserAnswerTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.QuestionBank;

public class DatabaseHelper extends SQLiteOpenHelper {

  Context ctx;
  public DatabaseHelper(Context context) {
    super(context, QuizSchema.DATABASE_NAME, null, QuizSchema.DATABASE_VERSION);
    this.ctx=context;
  }

  public static final String CREATE_USER_TABLE = "CREATE TABLE " + UserTable.NAME + "("
    + UserTable.Cols.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
    + UserTable.Cols.UUID + ", "
    + UserTable.Cols.USER_NAME + ", "
    + UserTable.Cols.PASSWORD + ", "
    + UserTable.Cols.TOTAL_SCORE
    + ")";

  public static final String CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionTable.NAME + "("
    + QuestionTable.Cols.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
    + QuestionTable.Cols.UUID + ", "
    + QuestionTable.Cols.QUESTION_TEXT + ", "
    + QuestionTable.Cols.CATEGORY + ", "
    + QuestionTable.Cols.LEVEL + ", "
    + "FOREIGN KEY (" + QuestionTable.Cols.CATEGORY + ") REFERENCES " + CategoryTable.NAME + "(" + QuestionTable.Cols.COLUMN_ID + " )"
    + ")"; // check the foreign key again


  public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryTable.NAME + "("
    + CategoryTable.Cols.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
    + CategoryTable.Cols.UUID + ", "
    + CategoryTable.Cols.CATEGORY_NAME
    + ")";


  public static final String CREATE_ANSWER_TABLE = "CREATE TABLE " + AnswerTable.NAME + "("
    + AnswerTable.Cols.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
    + AnswerTable.Cols.UUID + ", "
    + AnswerTable.Cols.ANSWER_TEXT + ", "
    + AnswerTable.Cols.IS_TRUE + ", "
    + AnswerTable.Cols.QUESTION_ID + ", "
    + "FOREIGN KEY (" + AnswerTable.Cols.QUESTION_ID + ") REFERENCES " + QuestionTable.NAME + "(" + AnswerTable.Cols.COLUMN_ID + ")"
    + ")"; // check the foreign key again


  public static final String CREATE_USER_ANSWERS_TABLE = "CREATE TABLE " + UserAnswerTable.NAME + "("
    + UserAnswerTable.Cols.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
    + UserAnswerTable.Cols.USER_ID + ", "
    + UserAnswerTable.Cols.QUESTION_ID + ", "
    + UserAnswerTable.Cols.ANSWER_ID + ", "
    + "FOREIGN KEY (" + UserAnswerTable.Cols.USER_ID + ") REFERENCES " + UserTable.NAME + "(" + UserAnswerTable.Cols.COLUMN_ID + "),"
    + "FOREIGN KEY (" + UserAnswerTable.Cols.QUESTION_ID + ") REFERENCES " + QuestionTable.NAME + "(" + UserAnswerTable.Cols.COLUMN_ID + "),"
    + "FOREIGN KEY (" + UserAnswerTable.Cols.ANSWER_ID + ") REFERENCES " + AnswerTable.NAME + "(" + UserAnswerTable.Cols.COLUMN_ID + ")"
    + ")";// check the foreign keys again


  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    sqLiteDatabase.execSQL(CREATE_QUESTION_TABLE);
    sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
    sqLiteDatabase.execSQL(CREATE_ANSWER_TABLE);
    sqLiteDatabase.execSQL(CREATE_USER_ANSWERS_TABLE);
    //QuestionBank.getInstance(ctx).intialized();
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserAnswerTable.NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AnswerTable.NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QuestionTable.NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CategoryTable.NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserTable.NAME);
    onCreate(sqLiteDatabase);
  }


}
