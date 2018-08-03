package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCategoryFragment extends Fragment {


    private RecyclerView recyclerView;
    private Repository repository;
    private CategoryAdapter adapter;
    private List<Category> categories;

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
        View view = inflater.inflate(R.layout.fragment_user_category, container, false);
        recyclerView = view.findViewById(R.id.my_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        repository = Repository.getInstance(getActivity());
        categories = new ArrayList<>();
        List<Category> categoryList = repository.getCategoryList();
        outer:
        for (Category category : categoryList) {
            for (Category category1 : categories) {
                if (category1.getName().equals(category.getName()))
                    continue outer;
            }
            categories.add(category);
        }
        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        private Button btn;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn_sience_category);
        }

        public void bindCategory(Category category) {
            final String categoryName = category.getName();
            btn.setText(category.getName());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SerieChooseDialogFragment dialog = SerieChooseDialogFragment.newInstance(categoryName);
                    dialog.setTargetFragment(UserCategoryFragment.this, 1);
                    dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                }
            });
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.user_category_card_view, null);
            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {
            Category category = categories.get(i);
            categoryHolder.bindCategory(category);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String serieName = data.getStringExtra("SERIE_NAME");
            String categoryName = data.getStringExtra("CATEGORY_NAME");
            LevelChooseDialogFragment dialog = LevelChooseDialogFragment.newInstance(categoryName + " " + serieName);
            Log.e("leveltag", categoryName + " " + serieName);
            dialog.show(getActivity().getSupportFragmentManager(), "leveltag");
        }
    }
}
