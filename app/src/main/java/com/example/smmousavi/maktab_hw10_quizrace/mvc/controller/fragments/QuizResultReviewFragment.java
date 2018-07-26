package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Question;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */


public class QuizResultReviewFragment extends Fragment {

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
  private static final long TIME_COUNT_MILLIES = 15000;

  private TimerStatus timerStatus = TimerStatus.STOPPED;
  private TimerZero mCallback;
  private ProgressBar progressBarCircle;
  private TextView textViewTime;
  private CountDownTimer countDownTimer;


  public static QuizResultReviewFragment newInstance(UUID questionId) {

    Bundle args = new Bundle();
    args.putSerializable(ARGS_QUESTION_ID, questionId);

    QuizResultReviewFragment fragment = new QuizResultReviewFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()

  public QuizResultReviewFragment() {
    // Required empty public constructor
  }

  @Override
  public void onResume() {
    super.onResume();

  } // end of onResume()

  @Override
  public void onStart() {
    super.onStart();
    startOrStop();
  }

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
    initViews(view);

    answerButtons = new Button[]{
      view.findViewById(R.id.answer_one),
      view.findViewById(R.id.answer_two),
      view.findViewById(R.id.answer_three),
      view.findViewById(R.id.answer_four)};

    mCategoryTxt.setText(getString(R.string.quiz_show_specification_category, category));
    mDifficultyTxt.setText(getString(R.string.quiz_show_specification_level, difficulty));
    mScoreTxt = view.findViewById(R.id.txt_score);
    mScoreTxt.setText(getString(R.string.quiz_show_specification_score, QuizResultReviewPagerActivity.scoreSum));

    mQuestionViewTxt.setText(mCurrentQuestion.getText());

    for (int i = 0; i < answerButtons.length; i++) {
      answerButtons[i].setText(mAnswers.get(i).getText());
      answerButtons[i].setTag("" + mAnswers.get(i).isTrue());
    }

    setOnAnswerButtonsClickListener(answerButtons);

    return view;
  }//end of onCreateView()


  public void setOnAnswerButtonsClickListener(Button[] buttons) {
    for (final Button button : buttons) {
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          boolean isTrueAnswer = Boolean.parseBoolean(button.getTag().toString());
          calcScore(isTrueAnswer);
          mScoreTxt.setText(getString(R.string.quiz_show_specification_score, QuizResultReviewPagerActivity.scoreSum));
        }
      });
    }
  }


  public void calcScore(boolean isTrueAnswer) {
    if (isTrueAnswer)
      QuizResultReviewPagerActivity.scoreSum += 30;

    else
      QuizResultReviewPagerActivity.scoreSum -= 10;
  }

  /*
   *
   * Count Down Timer Methods
   *
   */

  private void initViews(View view) {
    progressBarCircle = view.findViewById(R.id.progress_bar_circle);
    textViewTime = view.findViewById(R.id.txt_time_counter);
  }

  private void reset() {
    stopCountDownTimer();
    startCountDownTimer();
  }

  //method to start and stop count down timer
  public void startOrStop() {
    if (timerStatus == TimerStatus.STOPPED) {
      setProgressBarValues();
      timerStatus = TimerStatus.STARTED;
      startCountDownTimer();

    } else {
      timerStatus = TimerStatus.STOPPED;
      stopCountDownTimer();
    }
  }

  //method to start count down timer
  private void startCountDownTimer() {

    countDownTimer = new CountDownTimer(TIME_COUNT_MILLIES, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        textViewTime.setText(sTimeFormatter(millisUntilFinished));
        progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
      }

      @Override
      public void onFinish() {
        textViewTime.setText(sTimeFormatter(TIME_COUNT_MILLIES));
        setProgressBarValues();
        //Send message to change question
        timerStatus = TimerStatus.STOPPED;
      }

    }.start();
    countDownTimer.start();
  }

  //method to stop count down timer
  private void stopCountDownTimer() {
    countDownTimer.cancel();
  }


  //method to set circular progress bar values
  private void setProgressBarValues() {
    progressBarCircle.setMax((int) TIME_COUNT_MILLIES / 1000);
    progressBarCircle.setProgress((int) TIME_COUNT_MILLIES / 1000);
  }

  private String sTimeFormatter(long milliSeconds) {
    return String.valueOf(milliSeconds / 1000);
  }


  //Callback
  public interface TimerZero {
    public void changeQuestion();
  }

  @Override
  public void onAttachFragment(Fragment childFragment) {
    super.onAttachFragment(childFragment);

    try {
      mCallback = (TimerZero) childFragment;
    } catch (ClassCastException e) {
      throw new ClassCastException(childFragment.toString() + " must implement TimerZero");
    }
  }

  public void send() {
    mCallback.changeQuestion();
  }

  @Override
  public void onDetach() {
    mCallback = null;
    super.onDetach();
  }

}
