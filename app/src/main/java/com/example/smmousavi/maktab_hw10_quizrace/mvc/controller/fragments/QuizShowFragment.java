package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
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
  private Button[] mAnswerButtons;
  private List<Question> mQuestionList;
  private List<Answer> mAnswers;
  private String mQuestionCategory;
  private String mQuestionDifficulty;
  private UUID mCurrentQuestionId;
  private Question mCurrentQuestion;
  private int mCurrentQuesionNumber;
  private long mCurrentScore;
  private User mCurrentUser;


  //Count donw class fields
  private enum TimerStatus {
    STARTED,
    STOPPED
  }

  private static final long TIME_COUNT_MILLIES = 15000;

  private TimerStatus timerStatus = TimerStatus.STOPPED;
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
    setRetainInstance(true);
    mQuestionCategory = getArguments().getString(ARGS_QUESTION_CATEGORY);
    mQuestionDifficulty = getArguments().getString(ARGS_QUESTION_LEVEL);
    mQuestionList = Repository.getInstance(getActivity()).getQuestionsList(mQuestionCategory, mQuestionDifficulty);
    Log.i("TEST1", "QuizShowFragment onCreate() Category: " + mQuestionCategory + " Difficulty: " + mQuestionDifficulty + " QuestionList size is: " + mQuestionList.size());
    mCurrentUser = Repository.getInstance(getActivity()).getCurrentUser();
  }// end of oncreate()


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_quiz_show, container, false);

    findViews(view);

    mAnswerButtons = new Button[]{
      view.findViewById(R.id.answer_one),
      view.findViewById(R.id.answer_two),
      view.findViewById(R.id.answer_three),
      view.findViewById(R.id.answer_four)};

    setOnAnswerButtonClickListener();

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
    mScoreTxt.setText(getString(R.string.quiz_show_specification_score, mCurrentScore));
    mQuestionTextViewTxt.setText(mCurrentQuestion.getText());
    mQuestionNumberTxt.setText(getString(R.string.quiz_show_specification_question_number, mCurrentQuesionNumber + 1));

    for (int i = 0; i < mAnswerButtons.length; i++) {
      mAnswerButtons[i].setText(mAnswers.get(i).getText());
      mAnswerButtons[i].setTag("" + mAnswers.get(i).isTrue());
    }
  }// end of updateAfterUserAnswered()


  private void setOnAnswerButtonClickListener() {
    for (final Button button : mAnswerButtons) {
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          boolean isTrueAnswer = Boolean.parseBoolean(button.getTag().toString());
          if (isTrueAnswer) {
            mCurrentScore += 30;
            updateAfterUserAnswered();

            deactivateButtons();

            stopCountDownTimer();

            rightAnswerAction();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                //Do something after 1000ms
                countDownTimer.onFinish();
              }
            }, 1000);

          } else {
            mCurrentScore -= 10;
            updateAfterUserAnswered();

            deactivateButtons();

            stopCountDownTimer();

            wrongAnswerAction();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                countDownTimer.onFinish();
              }
            }, 1000);
          }
        }
      });
    }
  }// end of setOnAnswerButtonsClickListener()


  private void wrongAnswerAction() {
    Snackbar.make(getView(), "You Choosed Wrong Answer", Snackbar.LENGTH_SHORT).show();

  }// end of wrongAnswerAction()


  private void rightAnswerAction() {
    Snackbar.make(getView(), "You Choosed Right Answer", Snackbar.LENGTH_SHORT).show();

  }// end of rightAnswerAction()


  private void finishQuiz() {
    //temperory actioon, must be modified
    Snackbar.make(getView(), "Quiz Finished", Snackbar.LENGTH_SHORT).show();
    deactivateButtons();
    stopCountDownTimer();
    long totalScore = mCurrentUser.getTotalScore();
    totalScore += mCurrentScore;
    mCurrentUser.setTotalScore(totalScore);
    Repository.getInstance(getActivity()).updateUser(mCurrentUser);
  }// end of finishQuiz();

  private void deactivateButtons() {
    for (Button button : mAnswerButtons) {
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
      });
    }
  }

  /*
   *
   * Count Down Timer Impelemenation
   *
   */

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
        timerStatus = TimerStatus.STOPPED;

        if (++mCurrentQuesionNumber < mQuestionList.size()) {
          resetTimer();
          updateAfterUserAnswered();
          setOnAnswerButtonClickListener();

        } else
          finishQuiz();
      }

    }.start();
  }


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


}
