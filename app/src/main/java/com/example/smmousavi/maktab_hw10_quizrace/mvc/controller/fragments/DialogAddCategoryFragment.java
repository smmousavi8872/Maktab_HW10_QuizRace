package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.List;

public class DialogAddCategoryFragment extends DialogFragment {

    Button addCategoryBtn;
    EditText addCategoryText;
    TextView addCategorySerie;
    ImageButton closeBtn;
    Repository repository;

    int serieNo;

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
        addCategorySerie = view.findViewById(R.id.add_category_serie);
        closeBtn=view.findViewById(R.id.add_category_close_btn);

        defineSerie();

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag1", addCategoryText.getText().toString());
                addCategory();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    private void addCategory() {
        if(checkText()) {
            Category category = new Category(addCategoryText.getText().toString());
            Log.e("serieno", String.valueOf(serieNo));
            category.setSerie(serieNo);
            repository.addCategory(category);
            addCategoryText.setText("");
            Snackbar.make(getView(), "Your Entry Category has been added", Snackbar.LENGTH_SHORT).show();
        }
        else {
            Snackbar.make(getView(),"You should enter the category name",Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean checkText(){
        if(addCategoryText.getText().toString().length()!=0){
            return true;
        }
        else {
            return false;
        }
    }

    private void defineSerie() {
        addCategoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int count = 0;
                List<Category> categories = repository.getCategoryList();
                for (Category category : categories) {
                    if (category.getName().equals(editable.toString())) {
                        count++;
                    }
                }
                serieNo = count + 1;
                addCategorySerie.setText(String.valueOf(count + 1));
            }
        });
    }

    public static DialogAddCategoryFragment newInstance() {

        Bundle args = new Bundle();

        DialogAddCategoryFragment fragment = new DialogAddCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
