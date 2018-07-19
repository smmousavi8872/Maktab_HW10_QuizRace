package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.smmousavi.maktab_hw10_quizrace.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

  public abstract Fragment createFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_fragment);

    FragmentManager fm = getSupportFragmentManager();
    Fragment foundFragment = fm.findFragmentById(R.id.fragment_container);

    if (foundFragment == null) {
      foundFragment = createFragment();
      fm.beginTransaction()
        .add(R.id.fragment_container, foundFragment)
        .commit();
    }
  }
}
