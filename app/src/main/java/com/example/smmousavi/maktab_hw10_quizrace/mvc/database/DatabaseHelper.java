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


}
