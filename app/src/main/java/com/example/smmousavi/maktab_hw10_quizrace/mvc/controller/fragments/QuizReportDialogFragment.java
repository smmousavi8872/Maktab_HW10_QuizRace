package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.CategorySelectionActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizResultReviewPagerActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.UserPassedLevel;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizReportDialogFragment extends DialogFragment {

  public static final String ARGS_CORRECT_ANSWERS = "args_correct_answers";
  public static final String ARGS_INCORRECT_ANSWERS = "args_incorrect_answers";
  public static final String ARGS_CATEGORY = "args_category";
  public static final String ARGS_DIFFICULTY = "args_difficulty";

  private TextView correctAnswersTxt;
  private TextView incorrectAnswersTxt;
  private TextView totalPositivePointsTxt;
  private TextView totalNegetivePointsTxt;
  private TextView totalScoreTxt;
  private TextView okReportBtn;
  private TextView shareReportBtn;
  private TextView reviewAnswersBtn;
  private String questionCategory;
  private String questionDifficulty;
  private UserPassedLevel userPassedLevel;

  public static QuizReportDialogFragment newInstance(int correctAnswers, int incorrectAnswers, String category, String difficulty) {
    Bundle args = new Bundle();
    args.putInt(ARGS_CORRECT_ANSWERS, correctAnswers);
    args.putInt(ARGS_INCORRECT_ANSWERS, incorrectAnswers);
    args.putString(ARGS_CATEGORY, category);
    args.putString(ARGS_DIFFICULTY, difficulty);

    QuizReportDialogFragment fragment = new QuizReportDialogFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()


  public QuizReportDialogFragment() {
    // Required empty public constructor
  }


  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    LayoutInflater inflater = LayoutInflater.from(getActivity());
    View view = inflater.inflate(R.layout.fragment_quiz_report_dialog, null, false);

    AlertDialog dialog = new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();

    getViews(view);

    final User currentUser = Repository.getInstance(getActivity()).getCurrentUser();
    int correctAnswers = getArguments().getInt(ARGS_CORRECT_ANSWERS);
    int incorrectAnswers = getArguments().getInt(ARGS_INCORRECT_ANSWERS);
    questionCategory = getArguments().getString(ARGS_CATEGORY);
    questionDifficulty = getArguments().getString(ARGS_DIFFICULTY);
    int totalPositivePoints = correctAnswers * 30;
    int totalNegetivePoints = incorrectAnswers * -15;
    int totalScore = totalPositivePoints + totalNegetivePoints;

    correctAnswersTxt.setText(getString(R.string.report_correct_answers, correctAnswers));
    incorrectAnswersTxt.setText(getString(R.string.report_incorrect_answers, incorrectAnswers));
    totalPositivePointsTxt.setText(getString(R.string.report_total_positive_points, totalPositivePoints));
    totalNegetivePointsTxt.setText(getString(R.string.report_total_negetive_points, totalNegetivePoints));
    totalScoreTxt.setText(getString(R.string.report_total_score, totalScore));

    userPassedLevel = new UserPassedLevel();
    userPassedLevel.setUserId(currentUser.getId());
    userPassedLevel.setCategory(questionCategory);
    userPassedLevel.setDifficulty(questionDifficulty);
    Repository.getInstance(getActivity()).addUserPassedLevel(userPassedLevel);

    okReportBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = CategorySelectionActivity.newIntent(getActivity(), currentUser.getId());
        startActivity(intent);
        getActivity().finish();
      }
    });

    reviewAnswersBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = QuizResultReviewPagerActivity.newIntent(getActivity(), questionCategory, questionDifficulty);
        startActivity(intent);
        getActivity().finish();

      }
    });

    shareReportBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    return dialog;
  }


  private void getViews(View view) {
    correctAnswersTxt = view.findViewById(R.id.txt_correct_answers);
    incorrectAnswersTxt = view.findViewById(R.id.txt_incorrect_answers);
    totalPositivePointsTxt = view.findViewById(R.id.txt_total_positive_points);
    totalNegetivePointsTxt = view.findViewById(R.id.txt_total_negetive_points);
    totalScoreTxt = view.findViewById(R.id.txt_total_score);
    okReportBtn = view.findViewById(R.id.btn_report_ok);
    reviewAnswersBtn = view.findViewById(R.id.btn_report_review);
    shareReportBtn = view.findViewById(R.id.btn_report_share);
  } // end of getViews()
}
