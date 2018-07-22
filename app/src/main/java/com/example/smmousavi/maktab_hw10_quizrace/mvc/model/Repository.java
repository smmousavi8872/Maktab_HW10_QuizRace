package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.DatabaseHelper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.*;

public class Repository {

    private List<Question> questions;
    private static Repository instance;
    DatabaseHelper db;

    private Repository(Context context) {
        generateQuestionList();
        db = new DatabaseHelper(context);
        db.getWritableDatabase();
    } // end of Repository()


    private void generateQuestionList() {
        questions = new ArrayList<>();
        Answer[] answers = new Answer[4];

        for (int j = 0; j < 3; j++)
            answers[j] = new Answer("Answer" + "_" + (j + 1), false);

        answers[3] = new Answer("Answer " + "_4", true);

        for (int i = 0; i < 10; i++) {
            Question question = new Question("Question number " + (i + 1), answers);

            questions.add(question);
        }
    } // end of generateQuestionList()


    public static Repository getInstance(Context context) {
        if (instance == null)
            instance = new Repository(context);

        return instance;
    }// end of getInstance()


    public List<Question> getQuestions() {
        return questions;

    }// end of getQuestions()


    public Question getQuestion(UUID questionId) {
        Log.i("TAG", "received question id is: " + questionId);

        for (Question question : questions) {
            Log.i("TAG", "question id is: " + question.getId());

            if (question.getId().equals(questionId))
                return question;
        }
        return null;
    } // end of getQuestion


    public long insertUser(String uuid, String username, String password) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_UUID, uuid);
        values.put(UserTable.COLUMN_USER, username);
        values.put(UserTable.COLUMN_PASS, password);

        long id = sqLiteDatabase.insert(UserTable.NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public User getUser(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(UserTable.NAME,
                new String[]{UserTable.COLUMN_ID, UserTable.COLUMN_USER, UserTable.COLUMN_PASS, UserTable.COLUMN_UUID},
                UserTable.COLUMN_UUID + "=?", new String[]{uuid}, null, null, null);

        String username = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_USER));
        String password = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_PASS));

        User user = new User(username, password);
        user.setId(UUID.fromString(uuid));

        cursor.close();
        return user;
    }

    public List<User> getUserList() {
        List<User> users = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + UserTable.NAME;
        SQLiteDatabase sqliteDatabase = db.getWritableDatabase();
        Cursor cursor = sqliteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_USER)),
                        cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_PASS)));

                user.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_UUID))));

                users.add(user);
            } while (cursor.moveToNext());
        }

        sqliteDatabase.close();
        cursor.close();
        return users;
    }

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
        return sqliteDatabase.update(UserTable.NAME, values, UserTable.COLUMN_UUID + " = ?",
                new String[]{user.getmId().toString()});
    }

    public void deleteUser(User user) {
        SQLiteDatabase sqliteDatabase = db.getWritableDatabase();
        sqliteDatabase.delete(UserTable.NAME, UserTable.COLUMN_ID + " = ?",
                new String[]{user.getmId().toString()});
        sqliteDatabase.close();
    }

    public long insertCategory(String uuid, String name) {
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
        String name=cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NAME));
        Category category=new Category(UUID.fromString(uuid));
        category.setName(name);

        cursor.close();
        return category;
    }
}
