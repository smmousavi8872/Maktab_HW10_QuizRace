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
public class UserCategoryFragment extends Fragment {


  public UserCategoryFragment() {
    // Required empty public constructor
  }

  public static UserCategoryFragment newInstance() {

    Bundle args = new Bundle();

    UserCategoryFragment fragment = new UserCategoryFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_user_category, container, false);
  }

}
