package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.List;

public class DialogAddCategoryFragment extends DialogFragment {

    Button addCategoryBtn;
    EditText addCategoryText;
    Repository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = Repository.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_add_dialog, container, false);
        addCategoryBtn = view.findViewById(R.id.add_category_btn);
        addCategoryText = view.findViewById(R.id.add_category_text);

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag1", addCategoryText.getText().toString());
                addCategory();
            }
        });

        return view;
    }

    private void addCategory() {
        if (checkCategory()) {
            Category category = new Category(addCategoryText.getText().toString());
            repository.addCategory(category);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You can't");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean checkCategory() {
        List<Category> categoryList = repository.getCategoryList();
        for (Category category : categoryList) {
            if (category.getName().equals(addCategoryText.getText().toString())) {
                return false;
            }
        }

        return true;
    }

    public static DialogAddCategoryFragment newInstance() {

        Bundle args = new Bundle();

        DialogAddCategoryFragment fragment = new DialogAddCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
