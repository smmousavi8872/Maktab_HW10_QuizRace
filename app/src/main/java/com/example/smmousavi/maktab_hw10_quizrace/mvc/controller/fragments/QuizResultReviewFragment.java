package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
  private ImageView mQuestionLogoImg;
  private Button[] answerButtons;
  private Button mSkipBtn;
  private List<Answer> mAnswers;
  private User currentUser;
  private UUID mCurrentQuestionID;
  private Question mCurrentQuestion;
  private String category;
  private String difficulty;
  private List<AnsweredQuestion> answeredQuestionList;
  private List<Question> questions;
  private AnsweredQuestion mAnsweredQuestion;
  private UUID mAnsweredQuestionId;
  private UUID mUserAnswerId;
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
    questions = Repository.getInstance(getActivity()).getQuestionsList(category, difficulty);


  }// end of onCreate()

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void showCorrectAnswers() {
    for (Question q : questions) {
      for (Button button : answerButtons) {
        boolean isTureAnswer = Boolean.parseBoolean(button.getTag(R.string.is_true_answer).toString());
        if (isTureAnswer) {
          button.setBackground(getActivity().getDrawable(R.drawable.correct_answer_button_style));
        }
      }
    }

  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void showUserAnswer() {
    for (AnsweredQuestion AQ : answeredQuestionList) {
      for (Button button : answerButtons) {
        UUID answerButtonUUID = UUID.fromString(button.getTag(R.string.answer_uuid).toString());
        if (AQ.getAnswerId().equals(answerButtonUUID)) {
          button.setBackgroundColor(Color.GRAY);
          boolean isTureAnswer = Boolean.parseBoolean(button.getTag(R.string.is_true_answer).toString());
          if (isTureAnswer) {
            mQuestionLogoImg.setBackground(getActivity().getDrawable(R.drawable.correct_answer));


          } else {
            mQuestionLogoImg.setBackground(getActivity().getDrawable(R.drawable.wrong_answer));
            button.setBackground(getActivity().getDrawable(R.drawable.wrong_answer_style));

          }
        }
      }
    }
  }


  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    mSkipBtn.setVisibility(View.GONE);
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

    showUserAnswer();
    showCorrectAnswers();

    return view;
  }//end of onCreateView()


  private void initViews(View view) {
    mQuestionLogoImg = view.findViewById(R.id.img_question_logo);
    progressBarCircle = view.findViewById(R.id.progress_bar_circle);
    textViewTime = view.findViewById(R.id.txt_time_counter);
    mCategoryTxt = view.findViewById(R.id.txt_category);
    mDifficultyTxt = view.findViewById(R.id.txt_difficulty);
    mScoreTxt = view.findViewById(R.id.txt_score);
    mQuestionNumberTxt = view.findViewById(R.id.txt_question_number);
    mQuestionViewTxt = view.findViewById(R.id.txt_question);
    mSkipBtn = view.findViewById(R.id.btn_skip_question);
  }


}
