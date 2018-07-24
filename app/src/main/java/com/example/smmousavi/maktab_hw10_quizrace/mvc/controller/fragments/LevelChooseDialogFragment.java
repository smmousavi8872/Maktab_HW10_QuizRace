package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizShowPagerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LevelChooseDialogFragment extends DialogFragment {

  public static final String ARGS_CATEGORY = "args_category";
  private Button[] difficultyButtons;
  private AlertDialog dialog;


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


  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    LayoutInflater inflater = LayoutInflater.from(getActivity());
    View view = inflater.inflate(R.layout.fragment_level_choose_dialog, null, false);

    Button easyLevelBtn = view.findViewById(R.id.btn_easy_level);
    Button moderateLevelBtn = view.findViewById(R.id.btn_moderate_level);
    Button toughLevelBtn = view.findViewById(R.id.btn_tough_level);

    difficultyButtons = new Button[]{easyLevelBtn, moderateLevelBtn, toughLevelBtn};
    setOnDifficultyButtonsClickListener(difficultyButtons);

    dialog = new AlertDialog.Builder(getActivity())
      .setTitle("Choose Difficulty")
      .setNegativeButton(android.R.string.cancel, null)
      .setView(view)
      .create();

    return dialog;
  }


  public void setOnDifficultyButtonsClickListener(Button[] buttons) {
    for (final Button button : difficultyButtons) {
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          String category = getArguments().getString(ARGS_CATEGORY);
          String difficulty = button.getTag().toString();
          Intent intent = QuizShowPagerActivity.newIntent(getActivity(), category, difficulty);
          startActivity(intent);
          dialog.dismiss();

        }
      });
    }
  }
}
