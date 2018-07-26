package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptC;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.AnswerTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.CategoryTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.QuestionTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserAnswerTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.UserTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.QuestionBank.*;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, QuizSchema.DATABASE_NAME, null, QuizSchema.DATABASE_VERSION);
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
        db = sqLiteDatabase;
        intialized();
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

    public void intialized() {
        pushToDb(Science.Easy.Q1.text, Science.Easy.Q1.trueNumber, Science.Easy.Q1.answersText, Science.Easy.text, Science.text);
        pushToDb(Science.Easy.Q2.text, Science.Easy.Q2.trueNumber, Science.Easy.Q2.answersText, Science.Easy.text, Science.text);
        pushToDb(Science.Easy.Q3.text, Science.Easy.Q3.trueNumber, Science.Easy.Q3.answersText, Science.Easy.text, Science.text);
        pushToDb(Science.Easy.Q4.text, Science.Easy.Q4.trueNumber, Science.Easy.Q4.answersText, Science.Easy.text, Science.text);
        pushToDb(Science.Easy.Q5.text, Science.Easy.Q5.trueNumber, Science.Easy.Q5.answersText, Science.Easy.text, Science.text);

        pushToDb(Science.Moderate.Q1.text, Science.Moderate.Q1.trueNumber, Science.Moderate.Q1.answersText, Science.Moderate.text, Science.text);
        pushToDb(Science.Moderate.Q2.text, Science.Moderate.Q2.trueNumber, Science.Moderate.Q2.answersText, Science.Moderate.text, Science.text);
        pushToDb(Science.Moderate.Q3.text, Science.Moderate.Q3.trueNumber, Science.Moderate.Q3.answersText, Science.Moderate.text, Science.text);
        pushToDb(Science.Moderate.Q4.text, Science.Moderate.Q4.trueNumber, Science.Moderate.Q4.answersText, Science.Moderate.text, Science.text);
        pushToDb(Science.Moderate.Q5.text, Science.Moderate.Q5.trueNumber, Science.Moderate.Q5.answersText, Science.Moderate.text, Science.text);

        pushToDb(Science.Tough.Q1.text, Science.Tough.Q1.trueNumber, Science.Tough.Q1.answersText, Science.Tough.text, Science.text);
        pushToDb(Science.Tough.Q2.text, Science.Tough.Q2.trueNumber, Science.Tough.Q2.answersText, Science.Tough.text, Science.text);
        pushToDb(Science.Tough.Q3.text, Science.Tough.Q3.trueNumber, Science.Tough.Q3.answersText, Science.Tough.text, Science.text);
        pushToDb(Science.Tough.Q4.text, Science.Tough.Q4.trueNumber, Science.Tough.Q4.answersText, Science.Tough.text, Science.text);
        pushToDb(Science.Tough.Q5.text, Science.Tough.Q5.trueNumber, Science.Tough.Q5.answersText, Science.Tough.text, Science.text);

        pushToDb(Sport.Easy.Q1.text, Sport.Easy.Q1.trueNumber, Sport.Easy.Q1.answersText, Sport.Easy.text, Sport.text);
        pushToDb(Sport.Easy.Q2.text, Sport.Easy.Q2.trueNumber, Sport.Easy.Q2.answersText, Sport.Easy.text, Sport.text);
        pushToDb(Sport.Easy.Q3.text, Sport.Easy.Q3.trueNumber, Sport.Easy.Q3.answersText, Sport.Easy.text, Sport.text);
        pushToDb(Sport.Easy.Q4.text, Sport.Easy.Q4.trueNumber, Sport.Easy.Q4.answersText, Sport.Easy.text, Sport.text);
        pushToDb(Sport.Easy.Q5.text, Sport.Easy.Q5.trueNumber, Sport.Easy.Q5.answersText, Sport.Easy.text, Sport.text);

        pushToDb(Sport.Moderate.Q1.text, Sport.Moderate.Q1.trueNumber, Sport.Moderate.Q1.answersText, Sport.Moderate.text, Sport.text);
        pushToDb(Sport.Moderate.Q2.text, Sport.Moderate.Q2.trueNumber, Sport.Moderate.Q2.answersText, Sport.Moderate.text, Sport.text);
        pushToDb(Sport.Moderate.Q3.text, Sport.Moderate.Q3.trueNumber, Sport.Moderate.Q3.answersText, Sport.Moderate.text, Sport.text);
        pushToDb(Sport.Moderate.Q4.text, Sport.Moderate.Q4.trueNumber, Sport.Moderate.Q4.answersText, Sport.Moderate.text, Sport.text);
        pushToDb(Sport.Moderate.Q5.text, Sport.Moderate.Q5.trueNumber, Sport.Moderate.Q5.answersText, Sport.Moderate.text, Sport.text);

        pushToDb(Sport.Tough.Q1.text, Sport.Tough.Q1.trueNumber, Sport.Tough.Q1.answersText, Sport.Tough.text, Sport.text);
        pushToDb(Sport.Tough.Q2.text, Sport.Tough.Q2.trueNumber, Sport.Tough.Q2.answersText, Sport.Tough.text, Sport.text);
        pushToDb(Sport.Tough.Q3.text, Sport.Tough.Q3.trueNumber, Sport.Tough.Q3.answersText, Sport.Tough.text, Sport.text);
        pushToDb(Sport.Tough.Q4.text, Sport.Tough.Q4.trueNumber, Sport.Tough.Q4.answersText, Sport.Tough.text, Sport.text);
        pushToDb(Sport.Tough.Q5.text, Sport.Tough.Q5.trueNumber, Sport.Tough.Q5.answersText, Sport.Tough.text, Sport.text);

        pushToDb(Technology.Easy.Q1.text, Technology.Easy.Q1.trueNumber, Technology.Easy.Q1.answersText, Technology.Easy.text, Technology.text);
        pushToDb(Technology.Easy.Q2.text, Technology.Easy.Q2.trueNumber, Technology.Easy.Q2.answersText, Technology.Easy.text, Technology.text);
        pushToDb(Technology.Easy.Q3.text, Technology.Easy.Q3.trueNumber, Technology.Easy.Q3.answersText, Technology.Easy.text, Technology.text);
        pushToDb(Technology.Easy.Q4.text, Technology.Easy.Q4.trueNumber, Technology.Easy.Q4.answersText, Technology.Easy.text, Technology.text);
        pushToDb(Technology.Easy.Q5.text, Technology.Easy.Q5.trueNumber, Technology.Easy.Q5.answersText, Technology.Easy.text, Technology.text);

        pushToDb(Technology.Moderate.Q1.text, Technology.Moderate.Q1.trueNumber, Technology.Moderate.Q1.answersText, Technology.Moderate.text, Technology.text);
        pushToDb(Technology.Moderate.Q2.text, Technology.Moderate.Q2.trueNumber, Technology.Moderate.Q2.answersText, Technology.Moderate.text, Technology.text);
        pushToDb(Technology.Moderate.Q3.text, Technology.Moderate.Q3.trueNumber, Technology.Moderate.Q3.answersText, Technology.Moderate.text, Technology.text);
        pushToDb(Technology.Moderate.Q4.text, Technology.Moderate.Q4.trueNumber, Technology.Moderate.Q4.answersText, Technology.Moderate.text, Technology.text);
        pushToDb(Technology.Moderate.Q5.text, Technology.Moderate.Q5.trueNumber, Technology.Moderate.Q5.answersText, Technology.Moderate.text, Technology.text);

        pushToDb(Technology.Tough.Q1.text, Technology.Tough.Q1.trueNumber, Technology.Tough.Q1.answersText, Technology.Tough.text, Technology.text);
        pushToDb(Technology.Tough.Q2.text, Technology.Tough.Q2.trueNumber, Technology.Tough.Q2.answersText, Technology.Tough.text, Technology.text);
        pushToDb(Technology.Tough.Q3.text, Technology.Tough.Q3.trueNumber, Technology.Tough.Q3.answersText, Technology.Tough.text, Technology.text);
        pushToDb(Technology.Tough.Q4.text, Technology.Tough.Q4.trueNumber, Technology.Tough.Q4.answersText, Technology.Tough.text, Technology.text);
        pushToDb(Technology.Tough.Q5.text, Technology.Tough.Q5.trueNumber, Technology.Tough.Q5.answersText, Technology.Tough.text, Technology.text);

    }

    public void pushToDb(String text, int trueNumber, List<String> answersText, String level, String category) {
        Question question = new Question(text);
        question.setCategory(category);
        question.setLevel(level);
        ContentValues qValues = new ContentValues();
        qValues.put(QuestionTable.Cols.UUID, question.getId().toString());
        qValues.put(QuestionTable.Cols.QUESTION_TEXT, text);
        qValues.put(QuestionTable.Cols.CATEGORY, category);
        qValues.put(QuestionTable.Cols.LEVEL, level);
        db.insert(QuestionTable.NAME, null, qValues);
        int count = 1;
        for (String answerText : answersText) {
            Answer answer;
            if (trueNumber == count) {
                answer = new Answer(answerText, true);
            } else {
                answer = new Answer(answerText, false);
            }
            answer.setRelatedQuestionId(question.getId());
            count++;
            ContentValues aValues = new ContentValues();
            aValues.put(AnswerTable.Cols.UUID, answer.getId().toString());
            aValues.put(AnswerTable.Cols.ANSWER_TEXT, answerText);
            aValues.put(AnswerTable.Cols.QUESTION_ID, question.getId().toString());
            if (answer.getTrue())
                aValues.put(AnswerTable.Cols.IS_TRUE, 1);
            else
                aValues.put(AnswerTable.Cols.IS_TRUE, 0);
            db.insert(AnswerTable.NAME, null, aValues);
        }
    }


}
