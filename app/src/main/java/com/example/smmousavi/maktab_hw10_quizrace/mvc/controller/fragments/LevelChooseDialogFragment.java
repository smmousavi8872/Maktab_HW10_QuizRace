package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizResultReviewPagerActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizShowActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.UserPassedLevel;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class LevelChooseDialogFragment extends DialogFragment {

    public static final String ARGS_CATEGORY = "args_category";
    private Button[] optionButtons;
    private AlertDialog dialog;
    private List<UserPassedLevel> userPassedLevels;
    private String category;
    private ImageView doneLevelEasyImg;
    private ImageView doneLevelModerateImg;
    private ImageView doneLevelToughImg;


    public LevelChooseDialogFragment() {
        // Required empty public constructor
    }

    public static LevelChooseDialogFragment newInstance(String category) {

        Bundle args = new Bundle();
        args.putString(ARGS_CATEGORY, category);

        LevelChooseDialogFragment fragment = new LevelChooseDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_level_choose_dialog, null, false);

        Button easyLevelBtn = view.findViewById(R.id.btn_easy_level);
        Button moderateLevelBtn = view.findViewById(R.id.btn_moderate_level);
        Button toughLevelBtn = view.findViewById(R.id.btn_tough_level);

        doneLevelEasyImg = view.findViewById(R.id.img_done_level_easy);
        doneLevelModerateImg = view.findViewById(R.id.img_done_level_moderate);
        doneLevelToughImg = view.findViewById(R.id.img_done_level_tough);

        easyLevelBtn.setTag(R.string.difficulty_option, "easy");
        moderateLevelBtn.setTag(R.string.difficulty_option, "moderate");
        toughLevelBtn.setTag(R.string.difficulty_option, "tough");

        optionButtons = new Button[]{easyLevelBtn, moderateLevelBtn, toughLevelBtn};
        category = getArguments().getString(ARGS_CATEGORY);

        UUID userId = Repository.getInstance(getActivity()).getCurrentUser().getId();
        userPassedLevels = Repository.getInstance(getActivity()).getUserPassedLevels(userId);

        tickPassedLevels();

        setOnDifficultyButtonsClickListener();

        dialog = new AlertDialog.Builder(getActivity())
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view)
                .create();

        return dialog;
    }


    public void setOnDifficultyButtonsClickListener() {
        for (final Button button : optionButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    String difficulty = button.getTag(R.string.difficulty_option).toString();
                    if(checkCategoryLevel(difficulty)) {
                        if (!isPassed(button)) {
                            Intent intent = QuizShowActivity.newIntent(getActivity(), category, difficulty);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Intent intent = QuizResultReviewPagerActivity.newIntent(getActivity(), category, difficulty);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                    else {
                        dismiss();
                        Snackbar.make(getActivity().findViewById(android.R.id.content),"We don't have this question",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }// end of setOnDifficultyButtonClickListener()


    private boolean isPassed(Button button) {
        String buttonDifficulty = button.getTag(R.string.difficulty_option).toString();

        for (UserPassedLevel level : userPassedLevels) {
            String levelCategory = level.getCategory();
            String levelDifficulty = level.getDifficulty();
            if (category.equals(levelCategory) && buttonDifficulty.equals(levelDifficulty)) {
                return true;
            }
        }
        return false;
    }// end of isPassed()


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void tickPassedLevels() {
        for (Button button : optionButtons) {
            for (UserPassedLevel level : userPassedLevels) {
                String passedCategory = level.getCategory();
                String passedDifficulty = level.getDifficulty();
                String buttonDifficulty = button.getTag().toString();
                if (category.equals(passedCategory) && buttonDifficulty.equals(passedDifficulty)) {
                    button.setBackground(getActivity().getDrawable(R.drawable.passed_level_button_style));
                    setDifficultyLevelDone(buttonDifficulty);
                }
            }
        }
    }


    public void setDifficultyLevelDone(String difficulty) {

        switch (difficulty) {
            case "easy":
                doneLevelEasyImg.setVisibility(View.VISIBLE);
                break;
            case "moderate":
                doneLevelModerateImg.setVisibility(View.VISIBLE);
                break;
            case "tough":
                doneLevelToughImg.setVisibility(View.VISIBLE);
        }

    }

    public boolean checkCategoryLevel(String difficulty) {
        Repository repository = Repository.getInstance(getContext());
        List<Question> questions = repository.getQuestionsList(category, difficulty);
        if (questions.size()==0)
            return false;
        else
            return true;
    }
}
