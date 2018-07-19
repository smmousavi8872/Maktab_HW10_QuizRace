package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.Context;
import android.util.Log;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


}
