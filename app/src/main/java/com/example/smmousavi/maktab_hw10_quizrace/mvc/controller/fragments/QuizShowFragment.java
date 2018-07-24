package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizShowPagerActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizShowFragment extends Fragment {

  public static final String ARGS_QUESTION_ID = "args_question_id";

  private TextView mCategoryTxt;
  private TextView mDifficultyTxt;
  private TextView mQuestionNumberTxt;
  private TextView mScoreTxt;
  private TextView mQuestionViewTxt;
  private Button[] answerButtons;
  private Question mCurrentQuestion;
  private UUID mCurrentQuestionId;
  private List<Answer> mAnswers;
  private View view;


  public static QuizShowFragment newInstance(UUID questionId) {

    Bundle args = new Bundle();
    args.putSerializable(ARGS_QUESTION_ID, questionId);

    QuizShowFragment fragment = new QuizShowFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()

  public QuizShowFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();
    updateUI();

  } // end of onResume()


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mCurrentQuestionId = (UUID) getArguments().getSerializable(ARGS_QUESTION_ID);
    mCurrentQuestion = Repository.getInstance(getActivity()).getQuestion(mCurrentQuestionId);
    mAnswers = Repository.getInstance(getActivity()).getAnswersList(mCurrentQuestionId);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_quiz_show, container, false);

    String category = mCurrentQuestion.getCategory();
    String difficulty = mCurrentQuestion.getLevel();

    mCategoryTxt = view.findViewById(R.id.txt_category);
    mDifficultyTxt = view.findViewById(R.id.txt_difficulty);
    mQuestionNumberTxt = view.findViewById(R.id.txt_question_number);
    mQuestionViewTxt = view.findViewById(R.id.txt_question);

    answerButtons = new Button[]{
      view.findViewById(R.id.answer_one),
      view.findViewById(R.id.answer_two),
      view.findViewById(R.id.answer_three),
      view.findViewById(R.id.answer_four)};

    mCategoryTxt.setText(getString(R.string.quiz_show_specification_category, category));
    mDifficultyTxt.setText(getString(R.string.quiz_show_specification_level, difficulty));
    mScoreTxt = view.findViewById(R.id.txt_score);
    mScoreTxt.setText(getString(R.string.quiz_show_specification_score, QuizShowPagerActivity.scoreSum));

    mQuestionViewTxt.setText(mCurrentQuestion.getText());

    for (int i = 0; i < answerButtons.length; i++) {
      answerButtons[i].setText(mAnswers.get(i).getText());
      answerButtons[i].setTag("" + mAnswers.get(i).isTrue());
    }

    setOnAnswerButtonsClickListener(answerButtons);

    return view;
  }//end of onCreateView()


  private void updateUI() {
  }


  public void setOnAnswerButtonsClickListener(Button[] buttons) {
    for (final Button button : buttons) {
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          boolean isTrueAnswer = Boolean.parseBoolean(button.getTag().toString());
          Log.i("TAG3", isTrueAnswer + "");
          calcScore(isTrueAnswer);
          mScoreTxt.setText(getString(R.string.quiz_show_specification_score, QuizShowPagerActivity.scoreSum));
        }
      });
    }
  }


  public void calcScore(boolean isTrueAnswer) {
    if (isTrueAnswer)
      QuizShowPagerActivity.scoreSum += 30;

    else
      QuizShowPagerActivity.scoreSum -= 10;
  }

}
