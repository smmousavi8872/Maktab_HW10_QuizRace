package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.smmousavi.maktab_hw10_quizrace.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSignUpFragment extends DialogFragment {

  private EditText usernameEdt;
  private EditText passwordEdt;
  private EditText repeatPasswordEdt;
  private CheckBox policyAgreementChk;


  public static UserSignUpFragment newInstance() {

    Bundle args = new Bundle();

    UserSignUpFragment fragment = new UserSignUpFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()


  public UserSignUpFragment() {
    // Required empty public constructor

  }// end of UserSignUpFragment()


  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    LayoutInflater inflater = LayoutInflater.from(getActivity());
    View view = inflater.inflate(R.layout.fragment_user_sign_up, null, false);
    usernameEdt = view.findViewById(R.id.edt_sign_up_username);
    passwordEdt = view.findViewById(R.id.edt_sign_up_password);
    repeatPasswordEdt = view.findViewById(R.id.edt_sign_up_repeat_password);
    policyAgreementChk = view.findViewById(R.id.chk_policy_agreement);


    return new AlertDialog.Builder(getActivity())
      .setTitle("Sign Up")
      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {



        }
      })
      .setNegativeButton(android.R.string.cancel, null)
      .setView(view)
      .create();
  }// end of onCreateDialog()


}
