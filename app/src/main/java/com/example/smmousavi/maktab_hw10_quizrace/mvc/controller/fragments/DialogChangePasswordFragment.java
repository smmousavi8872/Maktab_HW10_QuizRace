package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

public class DialogChangePasswordFragment extends DialogFragment {

    Repository repository;

    Button applyPasswordBtn;
    TextInputLayout currentPasswordLayout;
    TextInputLayout repeatedPasswordLayout;
    TextInputEditText currentPassword;
    TextInputEditText newPassword;
    TextInputEditText repeatedPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = Repository.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password_dialog, container, false);
        applyPasswordBtn = view.findViewById(R.id.edit_profile_pass_apply);
        currentPassword = view.findViewById(R.id.edit_profile_current_pass);
        newPassword = view.findViewById(R.id.edit_profile_pass);
        repeatedPassword = view.findViewById(R.id.edit_profile_pass_repeat);
        currentPasswordLayout = view.findViewById(R.id.edit_profile_current_pass_layout);
        repeatedPasswordLayout = view.findViewById(R.id.edit_profile_pass_repeat_layout);
        applyPasswordBtn.setEnabled(false);
        checkNewPassword();

        applyPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
        return view;
    }

    private void changePassword() {
        if (checkValidation()) {
            User user = repository.getCurrentUser();
            user.setPassword(newPassword.getText().toString());
            repository.updateUser(user);
            dismiss();
        }
    }

    private boolean checkValidation() {
        if (!currentPassword.getText().toString().equals(repository.getCurrentUser().getPassword())) {
            currentPasswordLayout.setError("Invalid current password");
            return false;
        } else
            return true;
    }

    private void checkNewPassword() {
        repeatedPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!newPassword.getText().toString().equals(charSequence)) {
                    repeatedPasswordLayout.setError("This is not same!");
                    applyPasswordBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newPassword.getText().toString().equals(editable.toString())) {
                    repeatedPasswordLayout.setError("");
                    applyPasswordBtn.setEnabled(true);
                }
            }
        });
    }

    public static DialogChangePasswordFragment newInstance() {
        Bundle args = new Bundle();

        DialogChangePasswordFragment fragment = new DialogChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
