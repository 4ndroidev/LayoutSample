package com.androidev.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.androidev.layout.sample.R;
import com.androidev.sample.boundlayout.BoundLayoutSample;
import com.androidev.sample.flowlayout.FlowLayoutSample;
import com.androidev.sample.ninegridlayout.NineGridLayoutSample;
import com.androidev.sample.refreshlayout.RefreshLayoutSample;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static SparseArray<Class> samples = new SparseArray<>();

    static {
        samples.put(R.id.flow_layout, FlowLayoutSample.class);
        samples.put(R.id.nine_grid_layout, NineGridLayoutSample.class);
        samples.put(R.id.bound_layout, BoundLayoutSample.class);
        samples.put(R.id.refresh_layout, RefreshLayoutSample.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0, size = samples.size(); i < size; i++) {
            findViewById(samples.keyAt(i)).setOnClickListener(this);
        }
    }

    private void toSample(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        toSample(samples.get(v.getId()));
    }
}
