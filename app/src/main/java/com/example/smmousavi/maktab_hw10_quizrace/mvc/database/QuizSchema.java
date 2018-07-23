package com.example.smmousavi.maktab_hw10_quizrace.mvc.database;

public class QuizSchema {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quiz_db";

    public static final class UserTable {

        public static final String NAME = "users";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_PASS = "pass";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UUID + " TEXT,"
                + COLUMN_USER + " TEXT,"
                + COLUMN_PASS + " TEXT"
                + ")";

    }

    public static final class QuestionTable {

        public static final String NAME = "questions";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_QUESTION_TEXT = "question_text";
        public static final String COLUMN_CAT_ID = "cat_id";
        public static final String COLUMN_LEVEL = "level";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UUID + " TEXT,"
                + COLUMN_QUESTION_TEXT + " TEXT,"
                + COLUMN_CAT_ID + " TEXT,"
                + COLUMN_LEVEL + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_CAT_ID + ") REFERENCES " + CategoryTable.NAME + "(" + CategoryTable.COLUMN_UUID + ")"
                + ")";
    }

    public static final class CategoryTable {

        public static final String NAME = "categories";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_NAME = "name";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UUID + " TEXT,"
                + COLUMN_NAME + " TEXT"
                + ")";

    }

    public static final class AnswerTable {

        public static final String NAME = "answers";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_ANSWER_TEXT = "answer_text";
        public static final String COLUMN_IS_TRUE = "is_true";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UUID + " TEXT,"
                + COLUMN_ANSWER_TEXT + " TEXT,"
                + COLUMN_IS_TRUE + " INTEGER,"
                + COLUMN_QUESTION_ID + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_QUESTION_ID + ") REFERENCES " + QuestionTable.NAME + "(" + QuestionTable.COLUMN_UUID + ")"
                + ")";
    }

    public static final class UserAnswerTable {

        public static final String NAME = "users_questions";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_ANSWER_ID = "answeer_id";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UUID + " TEXT,"
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_QUESTION_ID + " TEXT,"
                + COLUMN_ANSWER_ID + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + UserTable.NAME + "(" + UserTable.COLUMN_UUID + "),"
                + "FOREIGN KEY (" + COLUMN_QUESTION_ID + ") REFERENCES " + QuestionTable.NAME + "(" + QuestionTable.COLUMN_UUID + "),"
                + "FOREIGN KEY (" + COLUMN_ANSWER_ID + ") REFERENCES " + AnswerTable.NAME + "(" + AnswerTable.COLUMN_UUID + ")"
                + ")";
    }
}
