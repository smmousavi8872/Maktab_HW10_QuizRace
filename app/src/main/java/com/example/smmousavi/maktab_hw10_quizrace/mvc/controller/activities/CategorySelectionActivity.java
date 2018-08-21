package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.CategorySelectionFragment;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.UserCategoryFragment;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategorySelectionActivity extends AppCompatActivity {

  public static final String EXTRA_USER_ID = "com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.extra_user_id";

  private TabLayout tabLayout;
  private ViewPager viewPager;
  private List<Fragment> fragments;
  private List<String> tabLayoutTitles;
  CategorySelectionFragment DefaultCategoriesFragment;
  UserCategoryFragment userCategoriesFragment;


  public static Intent newIntent(Context orgin, UUID userId) {
    Intent intent = new Intent(orgin, CategorySelectionActivity.class);
    intent.putExtra(EXTRA_USER_ID, userId);
    return intent;
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_pager_selection);
    setTitle("Quiz Race");
    getSupportActionBar().hide();

    viewPager = findViewById(R.id.category_selection_view_pager);
    tabLayout = findViewById(R.id.category_selection_tab_layout);
    fragments = new ArrayList<>();
    tabLayoutTitles = new ArrayList<>();
    DefaultCategoriesFragment = CategorySelectionFragment.newInstance(Repository.getInstance(this).getCurrentUser().getId());
    userCategoriesFragment = UserCategoryFragment.newInstance();

    addFragment(DefaultCategoriesFragment, "Default Categories");
    addFragment(userCategoriesFragment, "Your Categories");

    viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int i) {
        return fragments.get(i);
      }

      @Override
      public int getCount() {
        return fragments.size();
      }

      @Nullable
      @Override
      public CharSequence getPageTitle(int position) {
        return tabLayoutTitles.get(position);
      }
    });

    tabLayout.setupWithViewPager(viewPager);
  }


  private void addFragment(Fragment fragment, String title) {
    fragments.add(fragment);
    tabLayoutTitles.add(title);

  }
}