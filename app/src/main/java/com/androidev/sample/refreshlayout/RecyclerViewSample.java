package com.androidev.sample.refreshlayout;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidev.layout.sample.R;

public class RecyclerViewSample extends RefreshSample<RecyclerView> {

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_view_sample;
    }

    @Override
    protected void bindView(final RecyclerView contentView) {
        contentView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contentView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        contentView.setAdapter(new RecyclerView.Adapter<RecyclerViewSample.TextViewHolder>() {
            @Override
            public RecyclerViewSample.TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                return new RecyclerViewSample.TextViewHolder(inflater.inflate(R.layout.recycler_view_item, contentView, false));
            }

            @Override
            public void onBindViewHolder(RecyclerViewSample.TextViewHolder holder, int position) {
                holder.textView.setText("item " + (position + 1));
            }

            @Override
            public int getItemCount() {
                return 20;
            }
        });
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        private TextViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "click:" + (getAdapterPosition() + 1), Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(), "long click:" + (getAdapterPosition() + 1), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            textView = (TextView) itemView.findViewById(R.id.text);
        }

    }
}
