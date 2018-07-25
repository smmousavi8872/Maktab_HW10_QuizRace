package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.AnswerCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.CategoryCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.DatabaseHelper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuestionCursorWrapper;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.AnswerTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.QuizSchema.QuestionTable;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.UserCursorWrapper;

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


    public void generateQuestions() {
        for (int i = 0; i < 45; i++) {
            Question question = new Question("Queston number" + i);
            if (i % 4 == 0) {
                question.setCategory("science");
                question.setLevel("tough");

            } else if (i % 4 == 1) {
                question.setCategory("sport");
                question.setLevel("moderate");

            } else if (i % 4 == 2) {
                question.setCategory("technology");
                question.setLevel("easy");

            } else
                question.setCategory("general");

            for (int j = 0; j < 4; j++) {
                Answer answer;
                if (j == 3)
                    answer = new Answer("Answer number " + j + " of Question number" + i, true);

                else
                    answer = new Answer("Answer number " + j + " of Question number" + i, false);

                answer.setRelatedQuestionId(question.getId());

                addAnswer(answer);
            }

            addQuestion(question);
        }
    }// end of generateQuestions()



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
                + QuestionTable.Cols.LEVEL + " = ? ";
        String[] whereArgs = {category, level};
        QuestionCursorWrapper cursor = getQuestionQuery(QuestionTable.NAME, whereClause, whereArgs);
        Log.i("TAG2", cursor.getCount() + "");
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


    public List<Answer> getAnswersList(UUID mQuestionId) {
        List<Answer> questionAnswers = new ArrayList<>();
        String whereClause = AnswerTable.Cols.QUESTION_ID + " = ? ";
        String[] whereArgs = {mQuestionId.toString()};
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
    }


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
        values.put(QuestionTable.Cols.LEVEL, question.getLevel());

        return values;
    }// end of getQuestionContentValue()


    public ContentValues getAnswerContentValue(Answer answer) {
        ContentValues values = new ContentValues();
        values.put(AnswerTable.Cols.UUID, answer.getId().toString());
        values.put(AnswerTable.Cols.ANSWER_TEXT, answer.getText());
        values.put(AnswerTable.Cols.QUESTION_ID, answer.getRelatedQuestionId().toString());
        values.put(AnswerTable.Cols.IS_TRUE, answer.isTrue());

        return values;
    }// end of getAnswerContentValue()


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
                null
        );
        return new AnswerCursorWrapper(cursor);
    }// end of getAnswerQuery()
}