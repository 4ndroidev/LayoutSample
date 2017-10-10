package com.androidev.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    private List<T> mData;
    private int mLayoutId;

    public BaseAdapter(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(mLayoutId, parent, false);
        }
        onBindView(position, convertView, mData.get(position));
        return convertView;
    }

    public abstract void onBindView(int position, View view, T data);

}
