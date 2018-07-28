package com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.smmousavi.maktab_hw10_quizrace.R;
import com.example.smmousavi.maktab_hw10_quizrace.mvc.controller.fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Fragment fragment = ProfileFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.profile_container, fragment).commit();
    }
}
