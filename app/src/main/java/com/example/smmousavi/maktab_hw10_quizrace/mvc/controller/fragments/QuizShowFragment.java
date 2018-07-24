package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
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

    private TextView mQuestionViewTxt;
    private Button mAnswer1Btn;
    private Button mAnswer2Btn;
    private Button mAnswer3Btn;
    private Button mAnswer4Btn;
    private Question mCurrentQuestion;
    private UUID mCurrentQuestionId;
    private List<Answer> mAnswers;


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
    } // end of onResume()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG1", "onCreate called");
        mCurrentQuestionId = (UUID) getArguments().getSerializable(ARGS_QUESTION_ID);
        mCurrentQuestion = Repository.getInstance(getActivity()).getQuestion(mCurrentQuestionId);
        mAnswers = Repository.getInstance(getActivity()).getAnswersList(mCurrentQuestionId);
        Log.i("TAG1", mCurrentQuestionId.toString());
        Log.i("TAG1", mAnswers.size() + "");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_show, container, false);
        mQuestionViewTxt = view.findViewById(R.id.txt_question);
        mAnswer1Btn = view.findViewById(R.id.answer_one);
        mAnswer2Btn = view.findViewById(R.id.answer_two);
        mAnswer3Btn = view.findViewById(R.id.answer_three);
        mAnswer4Btn = view.findViewById(R.id.answer_four);

        mQuestionViewTxt.setText(mCurrentQuestion.getText());
        Answer answer1 = mAnswers.get(0);
        Answer answer2 = mAnswers.get(1);
        Answer answer3 = mAnswers.get(2);
        Answer answer4 = mAnswers.get(3);

        mAnswer1Btn.setText(answer1.getText());
        mAnswer2Btn.setText(answer2.getText());
        mAnswer3Btn.setText(answer3.getText());
        mAnswer4Btn.setText(answer4.getText());


        return view;
    }

}
