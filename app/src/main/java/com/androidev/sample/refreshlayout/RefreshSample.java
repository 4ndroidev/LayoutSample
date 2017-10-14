package com.androidev.sample.refreshlayout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.androidev.layout.sample.R;
import com.androidev.refreshlayout.RefreshLayout;
import com.androidev.sample.SampleFragment;

public abstract class RefreshSample<T> extends SampleFragment {

    @SuppressWarnings(value = "unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        bindView((T) view.findViewById(R.id.content_view));
    }

    protected abstract void bindView(final T contentView);
}
