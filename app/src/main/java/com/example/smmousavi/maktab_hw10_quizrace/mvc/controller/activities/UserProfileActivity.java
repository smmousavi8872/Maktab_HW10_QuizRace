package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.UserProfileFragment;

public class UserProfileActivity extends SingleFragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    View keyboardView = getCurrentFocus();
    if (keyboardView != null) {
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(keyboardView.getWindowToken(), 0);

    }
  }


  @Override
  public Fragment createFragment() {
    return UserProfileFragment.newInstance();
  }


  @Override
  public String getTag() {
    return UserProfileFragment.USER_PROFILE_FRAGMENT_TAG;
  }
}
