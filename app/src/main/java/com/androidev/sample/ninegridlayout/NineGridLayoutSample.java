package com.androidev.sample.ninegridlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidev.adapter.BaseAdapter;
import com.androidev.layout.sample.R;
import com.androidev.sample.SampleFragment;
import com.androidev.widget.NineGridLayout;

import java.util.ArrayList;
import java.util.List;

public class NineGridLayoutSample extends SampleFragment {

    private List<Item> data = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.nine_grid_sample;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NineGridLayout nineGridLayout = (NineGridLayout) view.findViewById(R.id.nine);
        BaseAdapter<Item> adapter = new BaseAdapter<Item>(R.layout.nine_grid_item) {
            @Override
            public void onBindView(int position, View view, Item data) {
                ImageView image = (ImageView) view.findViewById(R.id.image);
                image.setImageResource(data.image);
                TextView desc = (TextView) view.findViewById(R.id.desc);
                desc.setText(data.desc);
            }
        };
        nineGridLayout.setAdapter(adapter);
        data.add(new Item(R.drawable.gundam_exia, "Exia"));
        data.add(new Item(R.drawable.gundam_00, "00 Gundam"));
        data.add(new Item(R.drawable.gundam_00_qan, "00 Qan"));
        data.add(new Item(R.drawable.gundam_unicorn, "Unicorn"));
        data.add(new Item(R.drawable.gundam_ntd, "Ntd"));
        data.add(new Item(R.drawable.gundam_banshee, "Banshee"));
        data.add(new Item(R.drawable.gundam_0, "0 Gundam"));
        data.add(new Item(R.drawable.gundam_freedom, "Freedom"));
        data.add(new Item(R.drawable.gundam_justin, "Justin"));

        adapter.setData(data);
    }

    private class Item {
        private int image;
        private String desc;

        private Item(int image, String desc) {
            this.image = image;
            this.desc = desc;
        }
    }
}
