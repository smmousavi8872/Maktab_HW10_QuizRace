package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.QuizResultReviewFragment;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.AnsweredQuestion;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

import java.util.List;

public class QuizResultReviewPagerActivity extends AppCompatActivity {

  public static final String EXTRA_INTENT_CATEGORY =
    "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extra_intent_category";

  public static final String EXTRA_INTENT_DIFFICULTY =
    "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extra_intent_difficulty";

  private ViewPager viewPager;
  private FragmentStatePagerAdapter adapter;
  private List<AnsweredQuestion> answeredQuestionList;
  private User currentUser;
  public static long scoreSum; // this field belogs with Fragment of this Activity


  public static Intent newIntent(Context orgin, String category, String difficulty) {
    Intent intent = new Intent(orgin, QuizResultReviewPagerActivity.class);
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
    currentUser = Repository.getInstance(this).getCurrentUser();


    viewPager = findViewById(R.id.quiz_show_view_pager);
    answeredQuestionList = Repository.getInstance(QuizResultReviewPagerActivity.this).getAnsweredQuestionList(currentUser.getId(), category, difficulty);
    Log.i("TAG3", "Answered Question List: " + answeredQuestionList.size());

    adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int i) {
        AnsweredQuestion AQ = answeredQuestionList.get(i);
        Log.i("TAG3", "Answered Question ID: " + AQ.getId());
        return QuizResultReviewFragment.newInstance(AQ.getId(), i + 1);
      }

      @Override
      public int getCount() {
        return answeredQuestionList.size();
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
