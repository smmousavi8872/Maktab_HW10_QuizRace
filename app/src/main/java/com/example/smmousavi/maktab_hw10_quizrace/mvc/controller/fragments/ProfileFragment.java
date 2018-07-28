package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private Spinner reviewCategorySelction;
    private Spinner reviewLevelSelction;
    private Spinner addCategorySelction;
    private Spinner addLevelSelection;
    private Repository repository;

    private EditText editProfileUsername;
    private TextInputEditText editProfilePassword;
    private Button editProfileApply;
    private TextView editProfieTotalScore;

    private EditText addQuestionText;
    private EditText addQuestionAnswer1;
    private EditText addQuestionAnswer2;
    private EditText addQuestionAnswer3;
    private EditText addQuestionAnswer4;
    private RadioGroup addQuestionRadioGroup;
    private Button addQuestionAddBtn;

    List<String> existCategories = new ArrayList<>();
    List<String> existLevels = new ArrayList<>();
    String[] categories = {"science", "sport", "technology"};
    String[] levels = {"easy", "moderate", "tough"};
    String addCategorySelected;
    String addLevelSelected;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        repository = Repository.getInstance(getActivity());
        addCategorySelction = view.findViewById(R.id.add_question_category_selection);
        addLevelSelection = view.findViewById(R.id.add_question_level_selection);
        reviewCategorySelction = view.findViewById(R.id.review_category_selection);
        reviewLevelSelction = view.findViewById(R.id.review_level_selection);
        editProfileUsername = view.findViewById(R.id.edit_profile_username);
        editProfilePassword = view.findViewById(R.id.edit_profile_pass);
        editProfileApply = view.findViewById(R.id.edit_profile_apply_btn);

        addQuestionText = view.findViewById(R.id.add_question_text);
        addQuestionAnswer1 = view.findViewById(R.id.add_question_answer1);
        addQuestionAnswer2 = view.findViewById(R.id.add_question_answer2);
        addQuestionAnswer3 = view.findViewById(R.id.add_question_answer3);
        addQuestionAnswer4 = view.findViewById(R.id.add_question_answer4);
        addQuestionRadioGroup = view.findViewById(R.id.add_question_answers);
        addQuestionAddBtn = view.findViewById(R.id.add_question_add_btn);
        editProfieTotalScore=view.findViewById(R.id.edit_profile_total_score);

        setCategorySpinner();
        setLevelSpinner();
        setReviewCategorySelction();
        setUpdateProfile();
        setAddQuestion();

        return view;
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
                String newUsername = editProfileUsername.getText().toString();
                String newPassword = editProfilePassword.getText().toString();
                if (repository.getUser(newUsername, newPassword) == null) {
                    repository.getCurrentUser().setName(newUsername);
                    repository.getCurrentUser().setPassword(newPassword);
                    repository.updateUser(repository.getCurrentUser());
                    Snackbar.make(getView(), R.string.update_user_sucessful, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getView(), R.string.update_user_failed, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setAddQuestion() {
        addQuestionAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkEmptiness()) {
                    EditText[] editTexts = {addQuestionAnswer1, addQuestionAnswer2, addQuestionAnswer3, addQuestionAnswer4};
                    int radioButtonID = addQuestionRadioGroup.getCheckedRadioButtonId();
                    View radioButton = addQuestionRadioGroup.findViewById(radioButtonID);
                    int idx = addQuestionRadioGroup.indexOfChild(radioButton);
                    Question question = new Question(addQuestionText.getText().toString());
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
                }
                else
                    Snackbar.make(getView(),"You should fill out fields.",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkEmptiness(){
        if(addCategorySelction.getSelectedItemPosition()==0||addLevelSelection.getSelectedItemPosition()==0||addQuestionText.getText().length()==0||
                addQuestionAnswer1.getText().length()==0||addQuestionAnswer2.getText().length()==0||addQuestionAnswer3.getText().length()==0||
                addQuestionAnswer4.getText().length()==0)
            return true;
        else
            return false;
    }
}
