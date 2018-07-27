package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.QuizShowFragment;

public class QuizShowActivity extends SingleFragmentActivity {

  public static final String EXTRAS_QUESTION_CATEGORY =
    "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extras_question_category";

  public static final String EXTRAS_QUESTION_DIFFICULTY =
    "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extras_question_difficulty";


  public static Intent newIntent(Context orgin, String category, String difficulty) {
    Intent intent = new Intent(orgin, QuizShowActivity.class);
    intent.putExtra(EXTRAS_QUESTION_CATEGORY, category);
    intent.putExtra(EXTRAS_QUESTION_DIFFICULTY, difficulty);
    Log.i("TEST1", "QuizShowActivity Intent Category: " + category + "Difficulty: " + difficulty);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().hide();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

  }

  @Override
  public Fragment createFragment() {
    Bundle bundle = getIntent().getExtras();
    String category = bundle.getString(EXTRAS_QUESTION_CATEGORY);
    String difficulty = bundle.getString(EXTRAS_QUESTION_DIFFICULTY);
    Log.i("TEST1", "QuizShowActivity createFragment() Category: " + category + "Difficulty: " + difficulty);
    return QuizShowFragment.newInstance(category, difficulty);
  }

}
