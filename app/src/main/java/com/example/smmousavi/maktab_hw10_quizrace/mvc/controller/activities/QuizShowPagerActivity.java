package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

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

  private ViewPager viewPager;
  private FragmentStatePagerAdapter adapter;
  private List<Question> questionList;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz_pager_show);
    viewPager = findViewById(R.id.quiz_show_view_pager);
    questionList = Repository.getInstance(QuizShowPagerActivity.this).getQuestions();

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
