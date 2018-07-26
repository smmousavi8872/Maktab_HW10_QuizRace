package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Category;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.Repository;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.model.User;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategorySelectionFragment extends Fragment {

  public static final String ARGS_USER_ID = "args_user_id";
  public static final String TAG_DIALOG_CATEGORY = "tag_dialog_category";

  private User currentUser;
  private TextView usernameTxt;
  private TextView totalScoreTxt;
  private TextView topScoreTxt;
  private Button scienceCategoryBtn;
  private Button sportCategoryBtn;
  private Button technologyCategoryBtn;
  private Button generalCategoryBtn;
  Button[] categoryButtons;
  private UUID userId;

  public CategorySelectionFragment() {
    // Required empty public constructor
  }


  public static CategorySelectionFragment newInstance(UUID userId) {

    Bundle args = new Bundle();
    args.putSerializable(ARGS_USER_ID, userId);

    CategorySelectionFragment fragment = new CategorySelectionFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public void onResume() {
    super.onResume();
    updateScore();
  }

  private void updateScore() {
    currentUser = Repository.getInstance(getActivity()).getUser(userId);
    totalScoreTxt.setText(getString(R.string.total_score_title, currentUser.getTotalScore()));
    topScoreTxt.setText(getString(R.string.top_score_title, currentUser.getTotalScore()));
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    userId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_category_selection, container, false);
    usernameTxt = view.findViewById(R.id.category_selection_user_welcome);
    totalScoreTxt = view.findViewById(R.id.txt_category_selection_total_score);
    topScoreTxt = view.findViewById(R.id.txt_category_selection_top_score);
    categoryButtons = new Button[]{
      scienceCategoryBtn = view.findViewById(R.id.btn_sience_category),
      sportCategoryBtn = view.findViewById(R.id.btn_sports_category),
      technologyCategoryBtn = view.findViewById(R.id.btn_technology_category),
      generalCategoryBtn = view.findViewById(R.id.btn_general_category)

    };
    updateScore();

    usernameTxt.setText(getString(R.string.hi_user_title, currentUser.getName()));


    setOnCategoryButtonsListener(categoryButtons);

    return view;
  }

  public Button createCategoryButton(Category category) {
    return null;
  }


  public void setOnCategoryButtonsListener(Button[] buttons) {

    for (final Button button : buttons) {
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          String category = button.getTag().toString();
          FragmentManager fm = getFragmentManager();
          LevelChooseDialogFragment dialog = LevelChooseDialogFragment.newInstance(category);
          dialog.show(fm, TAG_DIALOG_CATEGORY);

        }
      });
    }

  }

}
