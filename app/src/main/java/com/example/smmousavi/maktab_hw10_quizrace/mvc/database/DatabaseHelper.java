package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.*;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, QuizSchema.DATABASE_NAME, null, QuizSchema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(CategoryTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(QuestionTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(AnswerTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(UserAnswerTable.CREATE_TABLE);
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

    public long insertUser(String uuid, String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_UUID, uuid);
        values.put(UserTable.COLUMN_USER, username);
        values.put(UserTable.COLUMN_PASS, password);

        long id = sqLiteDatabase.insert(UserTable.NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public User getUser(String uuid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
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
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_USER)),
                        cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_PASS)));

                user.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_UUID))));

                users.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        return users;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + UserTable.NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
