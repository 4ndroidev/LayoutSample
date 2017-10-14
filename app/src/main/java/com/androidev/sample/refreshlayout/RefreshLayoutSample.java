package com.androidev.sample.refreshlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;

import com.androidev.layout.sample.R;
import com.androidev.sample.SampleActivity;
import com.androidev.sample.SampleFragment;

public class RefreshLayoutSample extends SampleFragment implements View.OnClickListener {

    private static SparseArray<Class> samples = new SparseArray<>();

    static {
        samples.put(R.id.recycler_view_sample, RecyclerViewSample.class);
        samples.put(R.id.list_view_sample, ListViewSample.class);
        samples.put(R.id.grid_view_sample, GridViewSample.class);
        samples.put(R.id.nested_scroll_view_sample, NestedScrollViewSample.class);
        samples.put(R.id.scroll_view_sample, ScrollViewSample.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.refresh_sample;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0, size = samples.size(); i < size; i++) {
            view.findViewById(samples.keyAt(i)).setOnClickListener(this);
        }
    }

    private void toSample(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), SampleActivity.class);
        intent.putExtra("fragment", clazz.getName());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        toSample(samples.get(v.getId()));
    }
}
