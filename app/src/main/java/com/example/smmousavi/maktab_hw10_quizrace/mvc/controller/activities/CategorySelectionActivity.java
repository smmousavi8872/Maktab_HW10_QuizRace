package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.CategorySelectionFragment;

import java.util.UUID;

public class CategorySelectionActivity extends SingleFragmentActivity {

  public static final String EXTRA_USER_ID = "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extra_user_id";


  public static Intent newIntent(Context orgin, UUID userId) {
    Intent intent = new Intent(orgin, CategorySelectionActivity.class);
    intent.putExtra(EXTRA_USER_ID, userId);
    return intent;
  }

  @Override
  protected void onResume() {
    super.onResume();
    setTitle("Quiz Race");
  }

  @Override
  public Fragment createFragment() {
    UUID userID = (UUID) getIntent().getExtras().getSerializable(EXTRA_USER_ID);
    return CategorySelectionFragment.newInstance(userID);
  }

}