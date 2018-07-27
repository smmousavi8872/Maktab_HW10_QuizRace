package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities.CategorySelectionActivity;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSignUpDialogFragment extends DialogFragment {

  private TextView userWarnTxt;
  private EditText usernameEdt;
  private EditText passwordEdt;
  private EditText repeatPasswordEdt;
  private CheckBox policyAgreementChk;
  private AlertDialog dialog;
  private String username;
  private String password;
  private String repatePassword;
  private boolean policyAgreemnetCheck;


  public static UserSignUpDialogFragment newInstance() {

    Bundle args = new Bundle();

    UserSignUpDialogFragment fragment = new UserSignUpDialogFragment();
    fragment.setArguments(args);
    return fragment;
  }// end of newInstance()


  public UserSignUpDialogFragment() {
    // Required empty public constructor

  }// end of UserSignUpDialogFragment()


  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    LayoutInflater inflater = LayoutInflater.from(getActivity());
    @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_user_sign_up, null, false);

    userWarnTxt = view.findViewById(R.id.txt_sign_up_user_warn);
    usernameEdt = view.findViewById(R.id.edt_sign_up_username);
    passwordEdt = view.findViewById(R.id.edt_sign_up_password);
    repeatPasswordEdt = view.findViewById(R.id.edt_sign_up_repeat_password);
    policyAgreementChk = view.findViewById(R.id.chk_policy_agreement);


    dialog = new AlertDialog.Builder(getActivity())
      .setView(view)
      .setPositiveButton("Let's Go", null)
      .setNegativeButton(android.R.string.cancel, null)
      .create();

    usernameEdt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        username = usernameEdt.getText().toString();
        validateSignUp(policyAgreemnetCheck);
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    passwordEdt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        password = passwordEdt.getText().toString();
        validateSignUp(policyAgreemnetCheck);

      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    repeatPasswordEdt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        repatePassword = repeatPasswordEdt.getText().toString();
        validateSignUp(policyAgreemnetCheck);

      }

      @Override
      public void afterTextChanged(Editable editable) {
      }
    });

    policyAgreementChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        policyAgreemnetCheck = isChecked;
        validateSignUp(policyAgreemnetCheck);

      }
    });
    return dialog;
  }// end of onCreateDialog()


  private void validateSignUp(boolean isChecked) {
    Button confirmSignUp = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    if (username != null && password != null && repatePassword != null) {
      if (!username.equals("") && !password.equals("") && !repatePassword.equals("")) {
        if (password.equals(repatePassword)) {
          if (isChecked) {
            confirmSignUp.setEnabled(true);
            userWarnTxt.setTextColor(Color.GREEN);
            userWarnTxt.setText(R.string.we_are_go_to_go);
            positiveButtonActions();

          } else
            invalidateSignUp(getString(R.string.agree_to_terms_of_policy));

        } else
          invalidateSignUp(getString(R.string.passwords_dont_match));

      } else
        invalidateSignUp(getString(R.string.fill_in_all_fields));

    } else
      invalidateSignUp(getString(R.string.fill_in_all_fields));

  }// end of validateSignUp()


  public void invalidateSignUp(String massage) {
    Button confirmSignUp = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    confirmSignUp.setEnabled(false);
    userWarnTxt.setTextColor(Color.RED);
    userWarnTxt.setText(massage);
  }// end of invalidateSignUp()

  public void positiveButtonActions() {
    dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        User user = new User(username, password);
        Repository.getInstance(getActivity()).addUser(user);
        Repository.getInstance(getActivity()).setCurrentUser(user);
        Intent intent = CategorySelectionActivity.newIntent(getActivity(), user.getId());
        startActivity(intent);

        getActivity().finish();
      }
    });
  }// end of positiveButtonActions()

}
