package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.UserLoginFragment;

public class UserLoginActivity extends SingleFragmentActivity {

  static UserLoginFragment geustFragment;

  @Override
  public Fragment createFragment() {
    geustFragment = UserLoginFragment.newInstance();
    return geustFragment;
  }

  @Override
  public String getTag() {
    return UserLoginFragment.USER_LOGIN_FRAGMENT;
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();
    showGeustFragmentViews();
  }

  public static void showGeustFragmentViews() {
    geustFragment.showViews();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_fragment);
    getSupportActionBar().hide();


  }


}
