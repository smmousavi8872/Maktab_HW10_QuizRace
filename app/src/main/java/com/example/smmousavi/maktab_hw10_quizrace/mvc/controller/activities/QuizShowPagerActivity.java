package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.QuizShowFragment;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.List;

public class QuizShowPagerActivity extends AppCompatActivity {

  public static final String EXTRA_INTENT_CATEGORY =
    "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extra_intent_category";

  public static final String EXTRA_INTENT_DIFFICULTY =
    "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extra_intent_difficulty";

  private ViewPager viewPager;
  private FragmentStatePagerAdapter adapter;
  private List<Question> questionList;
  public static long scoreSum; // this field belogs with Fragment of this Activity


  public static Intent newIntent(Context orgin, String category, String difficulty) {
    Intent intent = new Intent(orgin, QuizShowPagerActivity.class);
    intent.putExtra(EXTRA_INTENT_CATEGORY, category);
    intent.putExtra(EXTRA_INTENT_DIFFICULTY, difficulty);

    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz_pager_show);

    String category = getIntent().getStringExtra(EXTRA_INTENT_CATEGORY);
    String difficulty = getIntent().getStringExtra(EXTRA_INTENT_DIFFICULTY);


    viewPager = findViewById(R.id.quiz_show_view_pager);
    questionList = Repository.getInstance(QuizShowPagerActivity.this).getQuestionsList(category, difficulty);

    adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int i) {
        Question q = questionList.get(i);
        return QuizShowFragment.newInstance(q.getId());
      }

      @Override
      public int getCount() {
        return questionList.size();
      }
    };

    viewPager.setAdapter(adapter);

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int i, float v, int i1) {

      }

      @Override
      public void onPageSelected(int i) {
        adapter.getItem(i).onResume();

      }

      @Override
      public void onPageScrollStateChanged(int i) {

      }
    });


  }

}
