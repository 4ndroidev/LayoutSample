package com.androidev.sample.flowlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.androidev.adapter.BaseAdapter;
import com.androidev.layout.sample.R;
import com.androidev.sample.SampleFragment;
import com.androidev.widget.FlowLayout;

import java.util.Arrays;

public class FlowLayoutSample extends SampleFragment {

    @Override
    public int getLayoutId() {
        return R.layout.flow_sample;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.flow_layout);
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
