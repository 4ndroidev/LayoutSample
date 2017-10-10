package com.androidev.sample.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.androidev.adapter.BaseAdapter;
import com.androidev.layout.sample.R;
import com.androidev.widget.FlowLayout;

import java.util.Arrays;

public class FlowLayoutSample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());
        setContentView(R.layout.flow_sample);
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        BaseAdapter<String> adapter = new BaseAdapter<String>(R.layout.flow_item) {
            @Override
            public void onBindView(int position, View view, String data) {
                ((TextView) view.findViewById(R.id.tag)).setText(data);
            }
        };
        flowLayout.setAdapter(adapter);
        adapter.setData(Arrays.asList("google", "xiaomi", "huawei", "oppo", "vivo", "one plus", "samsung", "htc", "moto"));
    }
}
