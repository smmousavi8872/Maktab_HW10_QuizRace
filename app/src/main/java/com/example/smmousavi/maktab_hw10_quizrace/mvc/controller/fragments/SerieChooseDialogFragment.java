package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SerieChooseDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private SerieAdapter adapter;
    private List<String> series;
    private Repository repository;

    private String currentCategory;

    public SerieChooseDialogFragment() {
        // Required empty public constructor
    }

    public static SerieChooseDialogFragment newInstance(String categoryName) {

        Bundle args = new Bundle();
        args.putString("CATEGORY_NAME", categoryName);
        SerieChooseDialogFragment fragment = new SerieChooseDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serie_choose_dialog, container, false);
        repository = Repository.getInstance(getActivity());
        recyclerView = view.findViewById(R.id.serie_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        currentCategory = getArguments().getString("CATEGORY_NAME");
        setSeries(currentCategory);
        recyclerView.setAdapter(new SerieAdapter(series));
        return view;
    }

    private void setSeries(String categoryName) {
        series = new ArrayList<>();
        List<Category> categoryList = repository.getCategoryList();
        for (Category category : categoryList) {
            if (category.getName().equals(categoryName)) {
                series.add("Serie No. " + String.valueOf(category.getSerie()));
            }
        }

    }

    private class SerieHolder extends RecyclerView.ViewHolder {

        private TextView serieText;

        public SerieHolder(@NonNull View itemView) {
            super(itemView);
            serieText = itemView.findViewById(R.id.level_edit_text);
            serieText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("CATEGORY_NAME", currentCategory);
                    intent.putExtra("SERIE_NAME", serieText.getText());
                    getTargetFragment().onActivityResult(1, Activity.RESULT_OK, intent);
                    dismiss();
                }
            });
        }

        public void bindSerie(String s) {
            serieText.setText(s);
        }
    }

    private class SerieAdapter extends RecyclerView.Adapter<SerieHolder> {

        List<String> serieList;

        public SerieAdapter(List<String> serieList) {
            this.serieList = serieList;

        }

        @NonNull
        @Override
        public SerieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.style_serie_selector, null);
            return new SerieHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SerieHolder serieHolder, int i) {
            String serie = serieList.get(i);
            serieHolder.bindSerie(serie);
        }

        @Override
        public int getItemCount() {
            return serieList.size();
        }
    }
}
