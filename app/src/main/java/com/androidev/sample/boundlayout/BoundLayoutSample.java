package com.androidev.sample.boundlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidev.layout.sample.R;

public class BoundLayoutSample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());
        setContentView(R.layout.bound_sample);
    }
}
