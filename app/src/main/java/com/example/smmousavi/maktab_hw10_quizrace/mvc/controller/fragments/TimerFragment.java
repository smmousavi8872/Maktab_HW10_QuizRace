package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


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
public class TimerFragment extends Fragment {

    TimerZero mCallback;
    private long timeCountInMilliSeconds = 60000;
    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;
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
        startStop();
    }

    private void initViews(View view) {
        progressBarCircle = (ProgressBar) view.findViewById(R.id.progressBarCircle);
        textViewTime = (TextView) view.findViewById(R.id.textViewTime);
    }

    //method to reset count down timer
    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    //method to start and stop count down timer
    private void startStop() {
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

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText(sTimeFormatter(millisUntilFinished));
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                textViewTime.setText(sTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
                //Send message to change question
                send();
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
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
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
