package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smmousavi.maktab_hw10_quizrace.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginFragment extends Fragment {


  public UserLoginFragment() {
    // Required empty public constructor
  }


  public static UserLoginFragment newInstance() {

    Bundle args = new Bundle();

    UserLoginFragment fragment = new UserLoginFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_user_login, container, false);


    return view;


  }

}
