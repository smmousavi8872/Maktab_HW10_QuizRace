package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.QuizResultReviewPagerActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Answer;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.AnsweredQuestion;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */


public class QuizResultReviewFragment extends Fragment {

  public static final String ARGS_QUESTION_ID = "args_answered_question_id";
  public static final String ARGS_QUESTION_NUMBER = "args_question_number";

  private TextView mCategoryTxt;
  private TextView mDifficultyTxt;
  private TextView mQuestionNumberTxt;
  private TextView mScoreTxt;
  private TextView mQuestionViewTxt;
  private Button[] answerButtons;
  private AnsweredQuestion mAnsweredQuestion;
  private UUID mAnsweredQuestionId;
  private List<Answer> mAnswers;
  private UUID mUserAnswerId;
  private List<AnsweredQuestion> answeredQuestionList;
  private User currentUser;
  private UUID mCurrentQuestionID;
  private Question mCurrentQuestion;
  private String category;
  private String difficulty;
  private ProgressBar progressBarCircle;
  private TextView textViewTime;
  private CountDownTimer countDownTimer;


  public static QuizResultReviewFragment newInstance(UUID questionId, int questionNumber) {

    Bundle args = new Bundle();
    args.putSerializable(ARGS_QUESTION_ID, questionId);
    args.putInt(ARGS_QUESTION_NUMBER, questionNumber);

    QuizResultReviewFragment fragment = new QuizResultReviewFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()

  public QuizResultReviewFragment() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    currentUser = Repository.getInstance(getActivity()).getCurrentUser();
    mCurrentQuestionID = (UUID) getArguments().getSerializable(ARGS_QUESTION_ID);
    mCurrentQuestion = Repository.getInstance(getActivity()).getQuestion(mCurrentQuestionID);
    mAnswers = Repository.getInstance(getActivity()).getAnswersList(mCurrentQuestionID);
    category = mCurrentQuestion.getCategory();
    difficulty = mCurrentQuestion.getDifficulty();

    answeredQuestionList = Repository.getInstance(getActivity()).getAnsweredQuestionList(currentUser.getId(), category, difficulty);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_quiz_show, container, false);

    int questionNumber = getArguments().getInt(ARGS_QUESTION_NUMBER);

    initViews(view);

    answerButtons = new Button[]{
      view.findViewById(R.id.answer_one),
      view.findViewById(R.id.answer_two),
      view.findViewById(R.id.answer_three),
      view.findViewById(R.id.answer_four)};

    mCategoryTxt.setText(getString(R.string.quiz_show_specification_category, category));
    mDifficultyTxt.setText(getString(R.string.quiz_show_specification_level, difficulty));
    mScoreTxt.setText(getString(R.string.quiz_show_specification_score, QuizResultReviewPagerActivity.scoreSum));
    mQuestionNumberTxt.setText(getString(R.string.quiz_show_specification_question_number, questionNumber));
    mQuestionViewTxt.setText(mCurrentQuestion.getText());

    for (int i = 0; i < answerButtons.length; i++) {
      answerButtons[i].setText(mAnswers.get(i).getText());
      answerButtons[i].setTag(R.string.is_true_answer, mAnswers.get(i).isTrue() + "");
      answerButtons[i].setTag(R.string.answer_uuid, mAnswers.get(i).getId().toString());
    }

    return view;
  }//end of onCreateView()


  private void initViews(View view) {
    progressBarCircle = view.findViewById(R.id.progress_bar_circle);
    textViewTime = view.findViewById(R.id.txt_time_counter);
    mCategoryTxt = view.findViewById(R.id.txt_category);
    mDifficultyTxt = view.findViewById(R.id.txt_difficulty);
    mScoreTxt = view.findViewById(R.id.txt_score);
    mQuestionNumberTxt = view.findViewById(R.id.txt_question_number);
    mQuestionViewTxt = view.findViewById(R.id.txt_question);
  }


  private void identifyUserChoice() {
    for (Button button : answerButtons) {
      boolean isTrueAnswer = Boolean.parseBoolean(button.getTag(R.string.is_true_answer).toString());
      UUID buttonId = UUID.fromString(button.getTag(R.string.answer_uuid).toString());
      if (isTrueAnswer)
        setCorrectButtonView(button);

      if (buttonId.equals(mUserAnswerId))
        setUserChoiceButtonView(button);
    }
  }

  private void setCorrectButtonView(Button button) {

  }


  private void setUserChoiceButtonView(Button button) {

  }


}