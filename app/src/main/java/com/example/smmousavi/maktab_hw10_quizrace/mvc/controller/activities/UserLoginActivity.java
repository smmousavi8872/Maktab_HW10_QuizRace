package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.UserLoginFragment;

public class UserLoginActivity extends SingleFragmentActivity {

  @Override
  public Fragment createFragment() {
    return UserLoginFragment.newInstance();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_fragment);


  }


}
