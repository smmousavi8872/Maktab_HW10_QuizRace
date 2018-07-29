package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExitDialogFragment extends DialogFragment {


  public ExitDialogFragment() {
    // Required empty public constructor
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    AlertDialog dialog = new AlertDialog.Builder(getActivity())
      .setTitle("Are you sure you want to Exit? The score of this quiz would be lost")
      .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
      })
      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
      })
      .create();

    return dialog;


  }
}
