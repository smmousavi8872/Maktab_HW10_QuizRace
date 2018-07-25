package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;

/**
 * A simple {@link Fragment} subclass.
 */

enum TimerStatus {
  STARTED,
  STOPPED
}

public class TimerFragment extends Fragment {

  private static final long TIME_COUNT_MILLIES = 15000;

  private TimerStatus timerStatus = TimerStatus.STOPPED;
  private TimerZero mCallback;
  private ProgressBar progressBarCircle;
  private TextView textViewTime;
  private CountDownTimer countDownTimer;

  public TimerFragment() {
    // Required empty public constructor
  }

  public static TimerFragment newInstance() {

    Bundle args = new Bundle();

    TimerFragment fragment = new TimerFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_timer, container, false);
    initViews(view);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    startOrStop();
  }

  private void initViews(View view) {
    progressBarCircle = view.findViewById(R.id.progress_bar_circle);
    textViewTime = view.findViewById(R.id.txt_time_counter);
  }

  //method to reset count down timer
  public void reset() {
    stopCountDownTimer();
    startCountDownTimer();
  }

  //method to start and stop count down timer
  private void startOrStop() {
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
        send();
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
    String s = String.valueOf(milliSeconds / 1000);
    return s;
  }

  //Callback
  public interface TimerZero {
    public void changeQuestion();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mCallback = (TimerZero) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
              + " must implement TextClicked");
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

  @Override
  public void onDestroy() {
    super.onDestroy();
    countDownTimer.cancel();
  }
}
