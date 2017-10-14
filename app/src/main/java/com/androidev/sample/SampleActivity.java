package com.androidev.sample;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.androidev.layout.sample.R;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportFragmentManager().beginTransaction().replace(R.id.root_view, instantiateSampleFragment()).commit();
    }

    private Fragment instantiateSampleFragment() {
        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra("fragment");
        Bundle arguments = intent.getBundleExtra("arguments");
        try {
            Class<?> clazz = Class.forName(fragmentName);
            Fragment fragment = (Fragment) clazz.newInstance();
            fragment.setArguments(arguments);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
