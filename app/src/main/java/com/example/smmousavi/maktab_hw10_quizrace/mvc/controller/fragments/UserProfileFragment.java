package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizResultReviewPagerActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.UserPassedLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
  public static final String USER_PROFILE_FRAGMENT_TAG = "user_profile_fragment_tag";


  private Spinner reviewCategorySelction;
  private Spinner reviewLevelSelction;
  private Button addQuestionDialogBtn;
  private Button addCategoryDialogBtn;
  private Button reviewBtn;
  private Repository repository;

  private EditText editProfileUsername;
  private Button editProfileApply;
  private Button editProfileChangePasswordBtn;
  private TextView editProfieTotalScore;

  private List<String> existCategories = new ArrayList<>();
  private List<String> existLevels = new ArrayList<>();
  private String[] categories;
  private String[] levels;
  private Button profileEditBtn;


  public UserProfileFragment() {
    // Required empty public constructor
  }


  public static UserProfileFragment newInstance() {

    Bundle args = new Bundle();

    UserProfileFragment fragment = new UserProfileFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
    findViews(view);

    categories = new String[]{getString(R.string.defalut_category_science), getString(R.string.default_category_sport), getString(R.string.default_category_technology)};
    levels = new String[]{getString(R.string.default_level_easy), getString(R.string.default_level_moderate), getString(R.string.default_level_tough)};

    //setCategorySpinner();

    //setLevelSpinner();

    setReviewCategorySelction();

    setUpdateProfile();

    setAddQuestion();

    setViewsDisabled(editProfileUsername, reviewLevelSelction, editProfileChangePasswordBtn);

    setReviewBtn();

    profileEditBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        profileEditBtn.setVisibility(View.GONE);
        editProfileApply.setVisibility(View.VISIBLE);
        setViewsEnabled(editProfileUsername, editProfileChangePasswordBtn);

      }
    });


    return view;
  }// end of onCreate()


  private void setViewsDisabled(View... views) {
    for (View view : views) {
      view.setEnabled(false);
    }
  }


  private void setViewsEnabled(View... views) {
    for (View view : views) {
      view.setEnabled(true);
    }
  }// end of enableViews()


  private void findViews(View view) {
    repository = Repository.getInstance(getActivity());
    reviewCategorySelction = view.findViewById(R.id.review_category_selection);
    reviewLevelSelction = view.findViewById(R.id.review_level_selection);
    editProfileUsername = view.findViewById(R.id.edit_profile_username);
    editProfileApply = view.findViewById(R.id.edit_profile_apply_btn);
    editProfileApply.setVisibility(View.GONE);
    profileEditBtn = view.findViewById(R.id.edt_profile_edit);
    editProfileChangePasswordBtn = view.findViewById(R.id.edit_profile_change_password_btn);
    addQuestionDialogBtn = view.findViewById(R.id.add_question_dialog_btn);
    addCategoryDialogBtn = view.findViewById(R.id.add_category_dialog_btn);
    editProfieTotalScore = view.findViewById(R.id.edit_profile_total_score);
    reviewBtn = view.findViewById(R.id.review_btn);
  }// end of findViews()


  private void setReviewBtn() {
    reviewBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (reviewCategorySelction.getSelectedItemPosition() == 0 || reviewLevelSelction.getSelectedItemPosition() == 0) {
          Snackbar.make(getView(), "First, You should chose category and level.", Snackbar.LENGTH_SHORT).show();
          return;
        }
        Intent intent = QuizResultReviewPagerActivity.newIntent(getActivity(), (String) reviewCategorySelction.getSelectedItem(),
          (String) reviewLevelSelction.getSelectedItem());
        startActivity(intent);
        getActivity().finish();
      }
    });
  }

  public void setReviewCategorySelction() {
    existCategories.add("Choose a category from list");
    List<UserPassedLevel> passedLevels = repository.getUserPassedLevels(repository.getCurrentUser().getId());
    for (String category : categories) {
      for (UserPassedLevel passedLevel : passedLevels) {
        if (passedLevel.getCategory().equals(category)) {
          existCategories.add(category);
          break;
        }
      }
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, existCategories);
    reviewCategorySelction.setAdapter(adapter);
    reviewCategorySelction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        setReviewLevelSelction(existCategories.get(position));
        existLevels = null;
        existLevels = new ArrayList<>();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
      }

    });
  }


  public void setReviewLevelSelction(String s) {
    setViewsEnabled(reviewLevelSelction);
    existLevels.add("Choose a level from list");
    List<UserPassedLevel> passedLevels = repository.getUserPassedLevels(repository.getCurrentUser().getId());
    for (String level : levels) {
      for (UserPassedLevel passedLevel : passedLevels) {
        if (passedLevel.getCategory().equals(s) && passedLevel.getDifficulty().equals(level)) {
          existLevels.add(level);
          break;
        }
      }
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, existLevels);
    reviewLevelSelction.setAdapter(adapter);
  }


  public void setUpdateProfile() {
    editProfieTotalScore.setText(String.valueOf(repository.getCurrentUser().getTotalScore()));
    editProfileUsername.setText(repository.getCurrentUser().getName());
    editProfileApply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editProfileApply.setVisibility(View.GONE);
        profileEditBtn.setVisibility(View.VISIBLE);
        String newUsername = editProfileUsername.getText().toString();
        String newPassword = repository.getCurrentUser().getPassword();
        if (repository.getUser(newUsername, newPassword) == null || newUsername.equals(repository.getCurrentUser().getName())) {
          repository.getCurrentUser().setName(newUsername);
          repository.getCurrentUser().setPassword(newPassword);
          repository.updateUser(repository.getCurrentUser());
          Snackbar.make(getView(), R.string.update_user_sucessful, Snackbar.LENGTH_SHORT).show();

        } else {
          Snackbar.make(getView(), R.string.update_user_failed, Snackbar.LENGTH_SHORT).show();
        }

        setViewsDisabled(editProfileUsername, editProfileChangePasswordBtn);
      }
    });

    editProfileChangePasswordBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DialogChangePasswordFragment dialog = DialogChangePasswordFragment.newInstance();
        dialog.show(getActivity().getSupportFragmentManager(), "CHANGE_PASSWORD_FRAGMENT");
      }
    });
  }


  public void setAddQuestion() {
    addCategoryDialogBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DialogAddCategoryFragment dialog = DialogAddCategoryFragment.newInstance();
        dialog.show(getActivity().getSupportFragmentManager(), "you");
      }
    });
    addQuestionDialogBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DialogAddQuestionFragment dialog = DialogAddQuestionFragment.newInstance();
        dialog.show(getActivity().getSupportFragmentManager(), "hey");
      }
    });
  }
}
