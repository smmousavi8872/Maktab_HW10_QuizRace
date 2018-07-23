package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.DatabaseHelper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema;
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
        user.setId(UUID.fromString(uuid));

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
        values.put(QuizSchema.QuestionTable.COLUMN_UUID, uuid);
        values.put(QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT, text);
        values.put(QuizSchema.QuestionTable.COLUMN_CAT_ID, catId);
        values.put(QuizSchema.QuestionTable.COLUMN_LEVEL, level);

        long id = sqLiteDatabase.insert(QuizSchema.QuestionTable.NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public Question getQuestion(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(QuizSchema.QuestionTable.NAME,
                new String[]{QuizSchema.QuestionTable.COLUMN_ID, QuizSchema.QuestionTable.COLUMN_UUID, QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT, QuizSchema.QuestionTable.COLUMN_CAT_ID, QuizSchema.QuestionTable.COLUMN_LEVEL},
                QuizSchema.QuestionTable.COLUMN_UUID + "=?", new String[]{uuid}, null, null, null);

        Question question = new Question(cursor.getString(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT)));
        question.setId(UUID.fromString(uuid));
        question.setText(cursor.getString(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT)));
        question.setCategoryId(cursor.getInt(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_CAT_ID)));
        question.setLevel(cursor.getInt(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_LEVEL)));

        cursor.close();
        return question;
    }

    public List<Question> getQuestionList() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM " + QuizSchema.QuestionTable.NAME;

        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(cursor.getString(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT)));
                question.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_UUID))));
                question.setText(cursor.getString(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT)));
                question.setCategoryId(cursor.getInt(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_CAT_ID)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(QuizSchema.QuestionTable.COLUMN_LEVEL)));

                questions.add(question);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();
        cursor.close();

        return questions;
    }

    public int getQuestionsCount() {

        String countQuery = "SELECT * FROM " + QuizSchema.QuestionTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateQuestion(Question question) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuizSchema.QuestionTable.COLUMN_UUID, question.getId().toString());
        values.put(QuizSchema.QuestionTable.COLUMN_QUESTION_TEXT, question.getText());
        values.put(QuizSchema.QuestionTable.COLUMN_LEVEL, question.getLevel());
        values.put(QuizSchema.QuestionTable.COLUMN_CAT_ID, question.getCategoryId());

        return sqLiteDatabase.update(QuizSchema.QuestionTable.NAME, values, QuizSchema.QuestionTable.COLUMN_UUID + " =?",
                new String[]{question.getId().toString()});
    }

    public void deleteQuestion(Question question) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(QuizSchema.QuestionTable.NAME, QuizSchema.QuestionTable.COLUMN_UUID + " =?",
                new String[]{question.getId().toString()});
        sqLiteDatabase.close();
    }

    public long insertAnswer(String uuid, String text, boolean isTrue, int questionId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuizSchema.AnswerTable.COLUMN_UUID, uuid);
        values.put(QuizSchema.AnswerTable.COLUMN_ANSWER_TEXT, text);
        values.put(QuizSchema.AnswerTable.COLUMN_QUESTION_ID, questionId);
        if (isTrue) {
            values.put(QuizSchema.AnswerTable.COLUMN_IS_TRUE, 1);
        } else
            values.put(QuizSchema.AnswerTable.COLUMN_IS_TRUE, 0);

        long id = sqLiteDatabase.insert(QuizSchema.AnswerTable.NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public Answer getAnswer(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(QuizSchema.AnswerTable.NAME,
                new String[]{QuizSchema.AnswerTable.COLUMN_ID, QuizSchema.AnswerTable.COLUMN_UUID, QuizSchema.AnswerTable.COLUMN_ANSWER_TEXT, QuizSchema.AnswerTable.COLUMN_IS_TRUE, QuizSchema.AnswerTable.COLUMN_QUESTION_ID},
                QuizSchema.AnswerTable.COLUMN_UUID + " =?", new String[]{uuid}, null, null, null);

        boolean is_true;
        if (cursor.getInt(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_IS_TRUE)) == 1) {
            is_true = true;
        } else
            is_true = false;
        Answer answer = new Answer(cursor.getString(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_ANSWER_TEXT)), is_true);
        answer.setRelatedQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_QUESTION_ID))));
        answer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_UUID))));

        cursor.close();
        return answer;
    }

    public List<Answer> getAnswerList() {
        List<Answer> answers = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + QuizSchema.AnswerTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                boolean is_true;
                if (cursor.getInt(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_IS_TRUE)) == 1)
                    is_true = true;
                else
                    is_true = false;
                Answer answer = new Answer(cursor.getString(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_ANSWER_TEXT)),
                        is_true);
                answer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_UUID))));
                answer.setRelatedQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.AnswerTable.COLUMN_QUESTION_ID))));

                answers.add(answer);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return answers;
    }

    public int getAnswerCount() {
        String countQuery = "SELECT * FROM " + QuizSchema.QuestionTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int upadteAnswer(Answer answer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuizSchema.AnswerTable.COLUMN_UUID, answer.getId().toString());
        values.put(QuizSchema.AnswerTable.COLUMN_ANSWER_TEXT, answer.getText());
        values.put(QuizSchema.AnswerTable.COLUMN_QUESTION_ID, answer.getRelatedQuestionId().toString());
        if (answer.getTrue())
            values.put(QuizSchema.AnswerTable.COLUMN_IS_TRUE, 1);
        else
            values.put(QuizSchema.AnswerTable.COLUMN_IS_TRUE, 0);

        return sqLiteDatabase.update(QuizSchema.AnswerTable.NAME, values, QuizSchema.AnswerTable.COLUMN_UUID + " =?", new String[]{answer.getId().toString()});
    }

    public void deleteAnswer(Answer answer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(QuizSchema.AnswerTable.NAME, QuizSchema.AnswerTable.COLUMN_UUID + " =?", new String[]{answer.getId().toString()});
        sqLiteDatabase.close();
    }

    public long insertUserAnswer(String uuid, String userId, String questionId, String answerId) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuizSchema.UserAnswerTable.COLUMN_UUID, uuid);
        values.put(QuizSchema.UserAnswerTable.COLUMN_USER_ID, userId);
        values.put(QuizSchema.UserAnswerTable.COLUMN_QUESTION_ID, questionId);
        values.put(QuizSchema.UserAnswerTable.COLUMN_ANSWER_ID, answerId);

        long id = sqLiteDatabase.insert(QuizSchema.UserAnswerTable.NAME, null, values);

        sqLiteDatabase.close();
        return id;
    }

    public UserAnswer getUserAnswer(String uuid) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(QuizSchema.UserAnswerTable.NAME,
                new String[]{QuizSchema.UserAnswerTable.COLUMN_ID, QuizSchema.UserAnswerTable.COLUMN_UUID, QuizSchema.UserAnswerTable.COLUMN_USER_ID, QuizSchema.UserAnswerTable.COLUMN_QUESTION_ID, QuizSchema.UserAnswerTable.COLUMN_ANSWER_ID},
                QuizSchema.UserAnswerTable.COLUMN_UUID + " =?", new String[]{uuid}, null, null, null);

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_UUID))));
        userAnswer.setQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_QUESTION_ID))));
        userAnswer.setUserId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_USER_ID))));
        userAnswer.setAnswerId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_ANSWER_ID))));

        cursor.close();
        return userAnswer;
    }

    public List<UserAnswer> getUserAnswerList() {
        List<UserAnswer> userAnswers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + QuizSchema.UserAnswerTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_UUID))));
                userAnswer.setUserId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_USER_ID))));
                userAnswer.setQuestionId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_QUESTION_ID))));
                userAnswer.setAnswerId(UUID.fromString(cursor.getString(cursor.getColumnIndex(QuizSchema.UserAnswerTable.COLUMN_ANSWER_ID))));

                userAnswers.add(userAnswer);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return userAnswers;
    }

    public int getUserAnswerCount() {
        String countQuery = "SELECT * FROM " + QuizSchema.UserAnswerTable.NAME;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateUserAnswer(UserAnswer userAnswer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuizSchema.UserAnswerTable.COLUMN_UUID, userAnswer.getId().toString());
        values.put(QuizSchema.UserAnswerTable.COLUMN_USER_ID, userAnswer.getUserId().toString());
        values.put(QuizSchema.UserAnswerTable.COLUMN_QUESTION_ID, userAnswer.getQuestionId().toString());
        values.put(QuizSchema.UserAnswerTable.COLUMN_ANSWER_ID, userAnswer.getAnswerId().toString());
        return sqLiteDatabase.update(QuizSchema.UserAnswerTable.NAME, values, QuizSchema.UserAnswerTable.COLUMN_UUID + " =?",
                new String[]{userAnswer.getId().toString()});
    }

    public void deleteUserAnswer(UserAnswer userAnswer) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(QuizSchema.UserAnswerTable.NAME, QuizSchema.UserAnswerTable.COLUMN_UUID + " =?",
                new String[]{userAnswer.getId().toString()});
        sqLiteDatabase.close();
    }

    public UserCursorWrapper getQuery(String tableName, String whereClause, String[] whereArgs) {
        // ??? must be completed ??? //
        return null;
    }
}
