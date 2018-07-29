package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {


  private Spinner reviewCategorySelction;
  private Spinner reviewLevelSelction;
  private Spinner addCategorySelction;
  private Spinner addLevelSelection;
  private Repository repository;

  private EditText editProfileUsername;
  private TextInputEditText editProfilePassword;
  private Button editProfileApply;
  private TextView editProfieTotalScore;

  private EditText addQuestionTitleEdt;
  private EditText addQuestionAnswer1Edt;
  private EditText addQuestionAnswer2Edt;
  private EditText addQuestionAnswer3Edt;
  private EditText addQuestionAnswer4Edt;
  private RadioGroup addQuestionRadioGroup;
  private Button addQuestionAddBtn;

  private List<String> existCategories = new ArrayList<>();
  private List<String> existLevels = new ArrayList<>();
  private String[] categories;
  private String[] levels;
  private String addCategorySelected;
  private String addLevelSelected;
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

    setCategorySpinner();

    setLevelSpinner();

    setReviewCategorySelction();

    setUpdateProfile();

    setAddQuestion();

    setViewsDisabled(editProfileUsername, editProfilePassword);

    profileEditBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        profileEditBtn.setVisibility(View.GONE);
        editProfileApply.setVisibility(View.VISIBLE);
        setViewsEnabled(editProfileUsername, editProfilePassword);

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
    addCategorySelction = view.findViewById(R.id.add_question_category_selection);
    addLevelSelection = view.findViewById(R.id.add_question_level_selection);
    reviewCategorySelction = view.findViewById(R.id.review_category_selection);
    reviewLevelSelction = view.findViewById(R.id.review_level_selection);
    editProfileUsername = view.findViewById(R.id.edit_profile_username);
    editProfilePassword = view.findViewById(R.id.edit_profile_pass);
    editProfileApply = view.findViewById(R.id.edit_profile_apply_btn);
    editProfileApply.setVisibility(View.GONE);
    profileEditBtn = view.findViewById(R.id.edt_profile_edit);

    addQuestionTitleEdt = view.findViewById(R.id.add_question_text);
    addQuestionAnswer1Edt = view.findViewById(R.id.add_question_answer1);
    addQuestionAnswer2Edt = view.findViewById(R.id.add_question_answer2);
    addQuestionAnswer3Edt = view.findViewById(R.id.add_question_answer3);
    addQuestionAnswer4Edt = view.findViewById(R.id.add_question_answer4);
    addQuestionRadioGroup = view.findViewById(R.id.add_question_answers);
    addQuestionAddBtn = view.findViewById(R.id.add_question_add_btn);
    editProfieTotalScore = view.findViewById(R.id.edit_profile_total_score);
  }// end of findViews()


  public void setCategorySpinner() {
    String[] categories = {"Choose Your Category", "science", "sport", "technology"};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
    addCategorySelction.setAdapter(adapter);
    addCategorySelction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        addCategorySelected = (String) addCategorySelction.getSelectedItem();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
      }

    });
  }


  public void setLevelSpinner() {
    String[] levels = {"Choose Your Level", "easy", "moderate", "tough"};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, levels);
    addLevelSelection.setAdapter(adapter);
    addLevelSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        addLevelSelected = (String) addLevelSelection.getSelectedItem();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
      }

    });
  }


  public void setReviewCategorySelction() {

    for (String category : categories) {
      for (String level : levels) {
        if (repository.getAnsweredQuestionList(repository.getCurrentUser().getId(), category, level).size() > 0) {
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
    for (String level : levels) {
      if (repository.getAnsweredQuestionList(repository.getCurrentUser().getId(), s, level).size() > 0) {
        existLevels.add(level);
      }
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, existLevels);
    reviewLevelSelction.setAdapter(adapter);
  }


  public void setUpdateProfile() {
    editProfieTotalScore.setText(String.valueOf(repository.getCurrentUser().getTotalScore()));
    editProfileUsername.setText(repository.getCurrentUser().getName());
    editProfilePassword.setText(repository.getCurrentUser().getPassword());
    editProfileApply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editProfileApply.setVisibility(View.GONE);
        profileEditBtn.setVisibility(View.VISIBLE);
        String newUsername = editProfileUsername.getText().toString();
        String newPassword = editProfilePassword.getText().toString();
        if (repository.getUser(newUsername, newPassword) == null) {
          repository.getCurrentUser().setName(newUsername);
          repository.getCurrentUser().setPassword(newPassword);
          repository.updateUser(repository.getCurrentUser());
          Snackbar.make(getView(), R.string.update_user_sucessful, Snackbar.LENGTH_SHORT).show();

        } else
          Snackbar.make(getView(), R.string.update_user_failed, Snackbar.LENGTH_SHORT).show();

        setViewsDisabled(editProfileUsername, editProfilePassword);
      }
    });
  }


  public void setAddQuestion() {
    addQuestionAddBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!isEmpty()) {
          EditText[] editTexts = {addQuestionAnswer1Edt, addQuestionAnswer2Edt, addQuestionAnswer3Edt, addQuestionAnswer4Edt};
          int radioButtonID = addQuestionRadioGroup.getCheckedRadioButtonId();
          View radioButton = addQuestionRadioGroup.findViewById(radioButtonID);
          int idx = addQuestionRadioGroup.indexOfChild(radioButton);
          Question question = new Question(addQuestionTitleEdt.getText().toString());
          question.setCategory(addCategorySelected);
          question.setDifficulty(addLevelSelected);
          repository.addQuestion(question);
          for (int i = 0; i < 4; i++) {

            Answer answer = new Answer(editTexts[i].getText().toString(), false);
            answer.setRelatedQuestionId(question.getId());
            if (i == idx)
              answer.setTrue(true);
            repository.addAnswer(answer);
          }
          Snackbar.make(getView(), "Question added.", Snackbar.LENGTH_SHORT).show();
        } else
          Snackbar.make(getView(), "You should fill out fields.", Snackbar.LENGTH_SHORT).show();
      }
    });

  }

  private boolean isEmpty() {
    if (addCategorySelction.getSelectedItemPosition() == 0 || addLevelSelection.getSelectedItemPosition() == 0 || addQuestionTitleEdt.getText().length() == 0 ||
      addQuestionAnswer1Edt.getText().length() == 0 || addQuestionAnswer2Edt.getText().length() == 0 || addQuestionAnswer3Edt.getText().length() == 0 ||
      addQuestionAnswer4Edt.getText().length() == 0)
      return true;
    else
      return false;
  }
}
