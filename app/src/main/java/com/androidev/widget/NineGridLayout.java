package com.androidev.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.androidev.layout.sample.R;

public class NineGridLayout extends ViewGroup {

    private final static int DEFAULT_DIVIDER_WIDTH = 6;

    private int mDividerWidth;
    private ListAdapter mAdapter;
    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            startUpdate();
        }
    };
    private OnItemClickListener mOnItemClickListener;

    public NineGridLayout(Context context) {
        this(context, null);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout);
            mDividerWidth = array.getDimensionPixelOffset(R.styleable.NineGridLayout_dividerWidth, DEFAULT_DIVIDER_WIDTH);
            array.recycle();
        }
    }

    /**
     * 根据父组件大小，决定子组件大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int itemSize = (int) ((width - getPaddingLeft() - getPaddingRight() - 2 * mDividerWidth) / 3.0f);
        if (itemSize < 0)
            throw new IllegalStateException("measureWith must more than the sum of padding and dividerWidth!");
        int height = getPaddingTop() + getPaddingBottom();
        int count = getChildCount();
        if (count == 1) {
            height += 2 * itemSize + mDividerWidth;
        } else if (count <= 3) {
            height += itemSize;
        } else if (count <= 6) {
            height += 2 * itemSize + mDividerWidth;
        } else {
            height += 3 * itemSize + 2 * mDividerWidth;
        }
        setMeasuredDimension(width, height);
        if (count == 1) {
            int singleSize = 2 * itemSize + mDividerWidth;
            int childWithMeasureSpec = MeasureSpec.makeMeasureSpec(singleSize, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(singleSize, MeasureSpec.EXACTLY);
            getChildAt(0).measure(childWithMeasureSpec, childHeightMeasureSpec);
        } else {
            int childWithMeasureSpec = MeasureSpec.makeMeasureSpec(itemSize, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(itemSize, MeasureSpec.EXACTLY);
            for (int i = 0; i < count; i++) {
                getChildAt(i).measure(childWithMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int columns;
        if (count <= 3) {
            columns = count;
        } else if (count == 4) {
            columns = 2;
        } else {
            columns = 3;
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int itemSize = (int) ((getMeasuredWidth() - paddingLeft - paddingRight - 2 * mDividerWidth) / 3.0f);
        if (count == 1) {
            int singleSize = 2 * itemSize + mDividerWidth;
            getChildAt(0).layout(paddingLeft, paddingTop, paddingLeft + singleSize, paddingTop + singleSize);
        } else {
            for (int i = 0; i < count; i++) {
                int row = i / columns;
                int column = i % columns;
                int left = paddingLeft + column * (itemSize + mDividerWidth);
                int top = paddingTop + row * (itemSize + mDividerWidth);
                getChildAt(i).layout(left, top, left + itemSize, top + itemSize);
            }
        }
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        child.setOnClickListener(null);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = indexOfChild(v);
                    mOnItemClickListener.onItemClick(NineGridLayout.this, v, position, mAdapter.getItemId(position));
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mObserver);
        }
        startUpdate();
    }

    private void startUpdate() {
        if (mAdapter == null) {
            removeAllViews();
            return;
        }
        int expectCount = mAdapter.getCount();
        if (expectCount > 9) {
            throw new IllegalStateException("NineGridView can host at most 9 children!");
        }
        int childCount = getChildCount();
        if (childCount > expectCount) {
            for (int i = childCount - 1; i >= expectCount; i--) {
                removeViewAt(i);
            }
            for (int i = 0; i < expectCount; i++) {
                mAdapter.getView(i, getChildAt(i), this);
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                mAdapter.getView(i, getChildAt(i), this);
            }
            for (int i = childCount; i < expectCount; i++) {
                addView(mAdapter.getView(i, null, this));
            }
        }
        requestLayout();
    }

    public interface OnItemClickListener {
        void onItemClick(NineGridLayout parent, View view, int position, long id);
    }

}
