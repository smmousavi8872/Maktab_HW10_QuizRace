package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.CategorySelectionActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginFragment extends Fragment {

  public static final String DIAL0G_SIGN_UP_TAG = "dialog_sign_up_tag";


  TextView userNameEdt;
  TextView userPasswordEdt;
  Button userLoginBtn;
  Button userSignUpBtn;


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

    userNameEdt = view.findViewById(R.id.edt_sign_up_username);
    userPasswordEdt = view.findViewById(R.id.edt_sign_up_password);
    userLoginBtn = view.findViewById(R.id.login_button);
    userSignUpBtn = view.findViewById(R.id.btn_login_sign_up);

    userLoginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String username = userNameEdt.getText().toString();
        String password = userPasswordEdt.getText().toString();
        if (!username.equals("") && !password.equals("")) {
          User user = Repository.getInstance(getActivity()).getUser(username, password);
          if (user != null) {
            Repository.getInstance(getActivity()).setCurrentUser(user);
            Intent intent = CategorySelectionActivity.newIntent(getActivity(), user.getId());
            startActivity(intent);

          } else
            Snackbar.make(getView(), "Incorrect Username or Password", Snackbar.LENGTH_SHORT).show();

        } else
          Snackbar.make(getView(), "Enter Username and Password", Snackbar.LENGTH_SHORT).show();
      }
    });

    userSignUpBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        UserSignUpDialogFragment dialog = UserSignUpDialogFragment.newInstance();
        FragmentManager fm = getFragmentManager();

        dialog.show(fm, DIAL0G_SIGN_UP_TAG);
      }
    });

    return view;


  }

}
