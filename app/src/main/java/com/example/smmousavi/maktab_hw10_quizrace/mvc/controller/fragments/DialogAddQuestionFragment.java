package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.UserPassedLevel;

import java.util.ArrayList;
import java.util.List;

public class DialogAddQuestionFragment extends DialogFragment {

    private Spinner addCategorySelction;
    private Spinner addSerieSelection;
    private Spinner addLevelSelection;
    private Repository repository;

    private EditText addQuestionTitleEdt;
    private EditText addQuestionAnswer1Edt;
    private EditText addQuestionAnswer2Edt;
    private EditText addQuestionAnswer3Edt;
    private EditText addQuestionAnswer4Edt;
    private RadioGroup addQuestionRadioGroup;
    private Button addQuestionAddBtn;
    private ImageButton closeBtn;
    private String addCategorySelected;
    private String addLevelSelected;
    private String addSerieSelected;
    private String currentCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_add_dialog, container, false);
        findViews(view);

        setCategorySpinner();

        setLevelSpinner();

        setAddQuestion();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    private void findViews(View view) {
        repository = Repository.getInstance(getActivity());
        addCategorySelction = view.findViewById(R.id.add_question_category_selection);
        addLevelSelection = view.findViewById(R.id.add_question_level_selection);
        addSerieSelection = view.findViewById(R.id.add_question_serie_selection);

        addQuestionTitleEdt = view.findViewById(R.id.add_question_text);
        addQuestionAnswer1Edt = view.findViewById(R.id.add_question_answer1);
        addQuestionAnswer2Edt = view.findViewById(R.id.add_question_answer2);
        addQuestionAnswer3Edt = view.findViewById(R.id.add_question_answer3);
        addQuestionAnswer4Edt = view.findViewById(R.id.add_question_answer4);
        addQuestionRadioGroup = view.findViewById(R.id.add_question_answers);
        addQuestionAddBtn = view.findViewById(R.id.add_question_add_btn);
        closeBtn=view.findViewById(R.id.add_question_close_btn);
    }// end of findViews()

    public static DialogAddQuestionFragment newInstance() {

        Bundle args = new Bundle();

        DialogAddQuestionFragment fragment = new DialogAddQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCategorySpinner() {
        List<Category> categoryList = repository.getCategoryList();
        List<String> categories = new ArrayList<>();
        List<String> series = new ArrayList<>();
        categories.add("Choose A Category");
        outer:
        for (Category category : categoryList) {
            for (String s : categories) {
                if (s.equals(category.getName())) {
                    continue outer;
                }
            }
            categories.add(category.getName());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        addCategorySelction.setAdapter(adapter);
        addCategorySelction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                addCategorySelected = (String) addCategorySelction.getSelectedItem();
                setAddSerieSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void setAddSerieSpinner() {
        List<String> series = new ArrayList<>();
        series.add("Choose A Serie");
        List<Category> categoryList = repository.getCategoryList();
        for (Category category : categoryList) {
            if (category.getName().equals(addCategorySelected)) {
                series.add("Serie No. " + String.valueOf(category.getSerie()));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, series);
        addSerieSelection.setAdapter(adapter);
        addSerieSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                addSerieSelected = (String) addSerieSelection.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public void setLevelSpinner() {
        String[] levels = {"Choose A Level", "easy", "moderate", "tough"};
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

    public void setAddQuestion() {
        addQuestionAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty()) {
                    if (checkCategory()) {
                        addQuestion();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("You can't add question because someone has passed this serie.");
                        builder.setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else
                    Snackbar.make(getView(), "You should fill out fields.", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void addQuestion() {
        EditText[] editTexts = {addQuestionAnswer1Edt, addQuestionAnswer2Edt, addQuestionAnswer3Edt, addQuestionAnswer4Edt};
        int radioButtonID = addQuestionRadioGroup.getCheckedRadioButtonId();
        View radioButton = addQuestionRadioGroup.findViewById(radioButtonID);
        int idx = addQuestionRadioGroup.indexOfChild(radioButton);
        Question question = new Question(addQuestionTitleEdt.getText().toString());
        question.setCategory(addCategorySelected + addSerieSelected);
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

    private boolean checkCategory() {
        List<UserPassedLevel> userPassedLevels = repository.getUserPassedLevelList();
        for (UserPassedLevel userPassedLevel : userPassedLevels) {
            if (userPassedLevel.getCategory().equals(addCategorySelected + addSerieSelected) && userPassedLevel.getDifficulty().equals(addLevelSelected)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty() {
        if (addCategorySelction.getSelectedItemPosition() == 0 || addLevelSelection.getSelectedItemPosition() == 0 || addSerieSelection.getSelectedItemPosition() == 0 ||
                addQuestionTitleEdt.getText().length() == 0 ||
                addQuestionAnswer1Edt.getText().length() == 0 || addQuestionAnswer2Edt.getText().length() == 0 || addQuestionAnswer3Edt.getText().length() == 0 ||
                addQuestionAnswer4Edt.getText().length() == 0)
            return true;
        else
            return false;
    }

    private void setCurrentCategory() {
        int count = 0;
        List<Category> categories = repository.getCategoryList();
        for (Category category : categories) {
            if (category.getName().equals(addCategorySelected)) {
                count++;
            }
        }
        currentCategory = addCategorySelected + "Serie No." + count;
        Toast.makeText(getContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
    }
}
