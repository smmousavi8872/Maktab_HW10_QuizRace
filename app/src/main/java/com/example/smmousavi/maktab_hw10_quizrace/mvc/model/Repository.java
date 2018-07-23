package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
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
            Question question = new Question("Question number " + (i + 1));
            question.setAnswers(answers);

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
        sqliteDatabase.delete(UserTable.NAME, UserTable.COLUMN_UUID + " = ?",
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
        String name = cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NAME));
        Category category = new Category(UUID.fromString(uuid));
        category.setName(name);

        cursor.close();
        return category;
    }

    public List<Category> getCategoryList() {
        List<Category> categories = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + CategoryTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(UUID.fromString(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_UUID))));

                category.setName(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NAME)));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return categories;
    }

    public int getCategoriesCount() {
        String countQuery = "SELECT * FROM " + CategoryTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryTable.COLUMN_UUID, category.getUuid().toString());
        values.put(CategoryTable.COLUMN_NAME, category.getName());

        return sqLiteDatabase.update(CategoryTable.NAME, values, CategoryTable.COLUMN_UUID + " =?",
                new String[]{category.getUuid().toString()});
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(CategoryTable.NAME, CategoryTable.COLUMN_UUID + " =?",
                new String[]{category.getUuid().toString()});
        sqLiteDatabase.close();
    }

    public long insertQuestion(String uuid, String text, int catId, int level) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionTable.COLUMN_UUID, uuid);
        values.put(QuestionTable.COLUMN_QUESTION_TEXT, text);
        values.put(QuestionTable.COLUMN_CAT_ID, catId);
        values.put(QuestionTable.COLUMN_LEVEL, level);

        long id = sqLiteDatabase.insert(QuestionTable.NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public Question getQuestion(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(QuestionTable.NAME,
                new String[]{QuestionTable.COLUMN_ID, QuestionTable.COLUMN_UUID, QuestionTable.COLUMN_QUESTION_TEXT, QuestionTable.COLUMN_CAT_ID, QuestionTable.COLUMN_LEVEL},
                QuestionTable.COLUMN_UUID + "=?", new String[]{uuid}, null, null, null);

        Question question = new Question(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION_TEXT)));
        question.setId(UUID.fromString(uuid));
        question.setText(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION_TEXT)));
        question.setCategoryId(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CAT_ID)));
        question.setLevel(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_LEVEL)));

        cursor.close();
        return question;
    }

    public List<Question> getQuestionList() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM " + QuestionTable.NAME;

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION_TEXT)));
                question.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_UUID))));
                question.setText(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION_TEXT)));
                question.setCategoryId(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CAT_ID)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_LEVEL)));

                questions.add(question);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();
        cursor.close();

        return questions;
    }

    public int getQuestionsCount() {

        String countQuery = "SELECT * FROM " + QuestionTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateQuestion(Question question) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuestionTable.COLUMN_UUID, question.getId().toString());
        values.put(QuestionTable.COLUMN_QUESTION_TEXT, question.getText());
        values.put(QuestionTable.COLUMN_LEVEL, question.getLevel());
        values.put(QuestionTable.COLUMN_CAT_ID, question.getCategoryId());

        return sqLiteDatabase.update(QuestionTable.NAME, values, QuestionTable.COLUMN_UUID + " =?",
                new String[]{question.getId().toString()});
    }

    public void deleteQuestion(Question question) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(QuestionTable.NAME, QuestionTable.COLUMN_UUID + " =?",
                new String[]{question.getId().toString()});
        sqLiteDatabase.close();
    }

    public long insertAnswer(String uuid, String text, boolean isTrue, int questionId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AnswerTable.COLUMN_UUID, uuid);
        values.put(AnswerTable.COLUMN_ANSWER_TEXT, text);
        values.put(AnswerTable.COLUMN_QUESTION_ID, questionId);
        if (isTrue) {
            values.put(AnswerTable.COLUMN_IS_TRUE, 1);
        } else
            values.put(AnswerTable.COLUMN_IS_TRUE, 0);

        long id = sqLiteDatabase.insert(AnswerTable.NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public Answer getAnswer(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(AnswerTable.NAME,
                new String[]{AnswerTable.COLUMN_ID, AnswerTable.COLUMN_UUID, AnswerTable.COLUMN_ANSWER_TEXT, AnswerTable.COLUMN_IS_TRUE, AnswerTable.COLUMN_QUESTION_ID},
                AnswerTable.COLUMN_UUID + " =?", new String[]{uuid}, null, null, null);

        boolean is_true;
        if (cursor.getInt(cursor.getColumnIndex(AnswerTable.COLUMN_IS_TRUE)) == 1) {
            is_true = true;
        } else
            is_true = false;
        Answer answer = new Answer(cursor.getString(cursor.getColumnIndex(AnswerTable.COLUMN_ANSWER_TEXT)), is_true);
        answer.setRelatedQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(AnswerTable.COLUMN_QUESTION_ID))));
        answer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(AnswerTable.COLUMN_UUID))));

        cursor.close();
        return answer;
    }

    public List<Answer> getAnswerList() {
        List<Answer> answers = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + AnswerTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                boolean is_true;
                if (cursor.getInt(cursor.getColumnIndex(AnswerTable.COLUMN_IS_TRUE)) == 1)
                    is_true = true;
                else
                    is_true = false;
                Answer answer = new Answer(cursor.getString(cursor.getColumnIndex(AnswerTable.COLUMN_ANSWER_TEXT)),
                        is_true);
                answer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(AnswerTable.COLUMN_UUID))));
                answer.setRelatedQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(AnswerTable.COLUMN_QUESTION_ID))));

                answers.add(answer);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return answers;
    }

    public int getAnswerCount() {
        String countQuery = "SELECT * FROM " + QuestionTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int upadteAnswer(Answer answer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AnswerTable.COLUMN_UUID, answer.getId().toString());
        values.put(AnswerTable.COLUMN_ANSWER_TEXT, answer.getText());
        values.put(AnswerTable.COLUMN_QUESTION_ID, answer.getRelatedQuestionId().toString());
        if (answer.getTrue())
            values.put(AnswerTable.COLUMN_IS_TRUE, 1);
        else
            values.put(AnswerTable.COLUMN_IS_TRUE, 0);

        return sqLiteDatabase.update(AnswerTable.NAME, values, AnswerTable.COLUMN_UUID + " =?", new String[]{answer.getId().toString()});
    }

    public void deleteAnswer(Answer answer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(AnswerTable.NAME, AnswerTable.COLUMN_UUID + " =?", new String[]{answer.getId().toString()});
        sqLiteDatabase.close();
    }

    public long insertUserAnswer(String uuid, String userId, String questionId, String answerId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserAnswerTable.COLUMN_UUID, uuid);
        values.put(UserAnswerTable.COLUMN_USER_ID, userId);
        values.put(UserAnswerTable.COLUMN_QUESTION_ID, questionId);
        values.put(UserAnswerTable.COLUMN_ANSWER_ID, answerId);

        long id = sqLiteDatabase.insert(UserAnswerTable.NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public UserAnswer getUserAnswer(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(UserAnswerTable.NAME,
                new String[]{UserAnswerTable.COLUMN_ID, UserAnswerTable.COLUMN_UUID, UserAnswerTable.COLUMN_USER_ID, UserAnswerTable.COLUMN_QUESTION_ID, UserAnswerTable.COLUMN_ANSWER_ID},
                UserAnswerTable.COLUMN_UUID + " =?", new String[]{uuid}, null, null, null);

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_UUID))));
        userAnswer.setQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_QUESTION_ID))));
        userAnswer.setUserId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_USER_ID))));
        userAnswer.setAnswerId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_ANSWER_ID))));

        cursor.close();
        return userAnswer;
    }

    public List<UserAnswer> getUserAnswerList() {
        List<UserAnswer> userAnswers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + UserAnswerTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_UUID))));
                userAnswer.setUserId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_USER_ID))));
                userAnswer.setQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_QUESTION_ID))));
                userAnswer.setAnswerId(UUID.fromString(cursor.getString(cursor.getColumnIndex(UserAnswerTable.COLUMN_ANSWER_ID))));

                userAnswers.add(userAnswer);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return userAnswers;
    }

    public int getUserAnswerCount() {
        String countQuery = "SELECT * FROM " + UserAnswerTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateUserAnswer(UserAnswer userAnswer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserAnswerTable.COLUMN_UUID, userAnswer.getId().toString());
        values.put(UserAnswerTable.COLUMN_USER_ID, userAnswer.getUserId().toString());
        values.put(UserAnswerTable.COLUMN_QUESTION_ID, userAnswer.getQuestionId().toString());
        values.put(UserAnswerTable.COLUMN_ANSWER_ID, userAnswer.getAnswerId().toString());
        return sqLiteDatabase.update(UserAnswerTable.NAME, values, UserAnswerTable.COLUMN_UUID + " =?",
                new String[]{userAnswer.getId().toString()});
    }

    public void deleteUserAnswer(UserAnswer userAnswer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(UserAnswerTable.NAME, UserAnswerTable.COLUMN_UUID + " =?",
                new String[]{userAnswer.getId().toString()});
        sqLiteDatabase.close();
    }


}
