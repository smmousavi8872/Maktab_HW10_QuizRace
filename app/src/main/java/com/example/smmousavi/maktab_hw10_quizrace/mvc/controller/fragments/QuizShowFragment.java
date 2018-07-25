package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizShowFragment extends Fragment {


  public static final String ARGS_QUESTION_CATEGORY = "args_question_category";
  public static final String ARGS_QUESTION_LEVEL = "args_question_level";

  private TextView mCategoryTxt;
  private TextView mDifficultyTxt;
  private TextView mQuestionNumberTxt;
  private TextView mScoreTxt;
  private TextView mQuestionTextViewTxt;
  private Button[] answerButtons;
  private List<Question> mQuestionList;
  private List<Answer> mAnswers;
  private String mQuestionCategory;
  private String mQuestionDifficulty;
  private UUID mCurrentQuestionId;
  private int mCurrentQuesionNumber;
  private Question mCurrentQuestion;
  private User mCurrentUser;


  //Count donw class fields
  private enum TimerStatus {
    STARTED,
    STOPPED
  }

  private static final long TIME_COUNT_MILLIES = 15000;

  private TimerStatus timerStatus = TimerStatus.STOPPED;
  private TimerZero mCallback;
  private ProgressBar progressBarCircle;
  private TextView textViewTime;
  private CountDownTimer countDownTimer;


  public static QuizShowFragment newInstance(String category, String difficulty) {

    Bundle args = new Bundle();
    args.putString(ARGS_QUESTION_CATEGORY, category);
    args.putString(ARGS_QUESTION_LEVEL, difficulty);
    Log.i("TEST1", "QuizShowFragment newInstance() Category: " + category + "Difficulty: " + difficulty);

    QuizShowFragment fragment = new QuizShowFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()


  public QuizShowFragment() {
    // Required empty public constructor
  }


  @Override
  public void onStart() {
    super.onStart();
    startOrStop();
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mQuestionCategory = getArguments().getString(ARGS_QUESTION_CATEGORY);
    mQuestionDifficulty = getArguments().getString(ARGS_QUESTION_LEVEL);
    mQuestionList = Repository.getInstance(getActivity()).getQuestionsList(mQuestionCategory, mQuestionDifficulty);
    Log.i("TEST1", "QuizShowFragment onCreate() Category: " + mQuestionCategory + " Difficulty: " + mQuestionDifficulty + " QuestionList size is: " + mQuestionList.size());
    mCurrentUser = Repository.getInstance(getActivity()).getCurrentUser();
  }// end of oncreate()


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_quiz_show, container, false);

    findViews(view);

    answerButtons = new Button[]{
      view.findViewById(R.id.answer_one),
      view.findViewById(R.id.answer_two),
      view.findViewById(R.id.answer_three),
      view.findViewById(R.id.answer_four)};

    mCategoryTxt.setText(getString(R.string.quiz_show_specification_category, mQuestionCategory));
    mDifficultyTxt.setText(getString(R.string.quiz_show_specification_level, mQuestionDifficulty));

    updateAfterUserAnswered();

    return view;
  }//end of onCreateView()


  private void findViews(View view) {
    mCategoryTxt = view.findViewById(R.id.txt_category);
    mDifficultyTxt = view.findViewById(R.id.txt_difficulty);
    mQuestionNumberTxt = view.findViewById(R.id.txt_question_number);
    mScoreTxt = view.findViewById(R.id.txt_score);
    mQuestionTextViewTxt = view.findViewById(R.id.txt_question);
    progressBarCircle = view.findViewById(R.id.progress_bar_circle);
    textViewTime = view.findViewById(R.id.txt_time_counter);
  }// end of findViews()


  private void updateAfterUserAnswered() {
    mCurrentQuestion = mQuestionList.get(mCurrentQuesionNumber);
    mCurrentQuestionId = mCurrentQuestion.getId();
    mAnswers = Repository.getInstance(getActivity()).getAnswersList(mCurrentQuestionId);
    // this must be modified
    mScoreTxt.setText(getString(R.string.quiz_show_specification_score, QuizResultReviewPagerActivity.scoreSum));
    mQuestionTextViewTxt.setText(mCurrentQuestion.getText());
    for (int i = 0; i < answerButtons.length; i++) {
      answerButtons[i].setText(mAnswers.get(i).getText());
      answerButtons[i].setTag("" + mAnswers.get(i).isTrue());
    }
  }// end of updateAfterUserAnswered()


  private void setOnAnswerButtonsClickListener() {

  }// end of setOnAnswerButtonsClickListener()

  /*
   *
   * Count Down Timer Methods
   *
   */


  private void resetTimer() {
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

        if (++mCurrentQuesionNumber < mQuestionList.size()) {
          resetTimer();
          updateAfterUserAnswered();

        } else {
          finishQuiz();

        }
      }

    }.start();
    countDownTimer.start();
  }


  private void finishQuiz() {
    //temperory actioon, must be modified
    Snackbar.make(getView(), "Quiz Finished", Snackbar.LENGTH_SHORT).show();
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
