package com.androidev.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.androidev.layout.sample.R;

public class FlowLayout extends ViewGroup {

    private final static int DEFAULT_VERTICAL_SPACE = 10;
    private final static int DEFAULT_HORIZONTAL_SPACE = 6;

    private ListAdapter mAdapter;
    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            startUpdate();
        }
    };
    private int mVerticalSpace;
    private int mHorizontalSpace;
    private int mRowHeight;
    private OnItemClickListener mOnItemClickListener;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
            mVerticalSpace = array.getDimensionPixelOffset(R.styleable.FlowLayout_verticalSpace, DEFAULT_VERTICAL_SPACE);
            mHorizontalSpace = array.getDimensionPixelOffset(R.styleable.FlowLayout_horizontalSpace, DEFAULT_HORIZONTAL_SPACE);
            array.recycle();
        }
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = getDefaultSize(0, widthMeasureSpec);
        int paddingHorizontal = getPaddingLeft() + getPaddingRight();
        int paddingVertical = getPaddingTop() + getPaddingBottom();
        int actualWidth = measureWidth - paddingHorizontal;
        int count = getChildCount();
        int freeWidth = actualWidth;
        int row = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            mRowHeight = Math.max(mRowHeight, child.getMeasuredHeight());
            int childWidth = child.getMeasuredWidth();
            int space = freeWidth == actualWidth ? 0 : mHorizontalSpace;
            if (childWidth + space > freeWidth) {
                freeWidth = actualWidth - childWidth;
                row++;
            } else if (childWidth + space == freeWidth) {
                freeWidth = actualWidth;
                row++;
            } else {
                freeWidth -= childWidth + space;
            }
        }
        if (freeWidth < actualWidth) row++;
        setMeasuredDimension(measureWidth, paddingVertical + row * mRowHeight + Math.max(0, row - 1) * mVerticalSpace);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int row = 0;
        int actualWidth = getMeasuredWidth() - paddingLeft - paddingRight;
        int freeWidth = actualWidth;
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int space = freeWidth == actualWidth ? 0 : mHorizontalSpace;
            int left, top;
            if (childWidth + space > freeWidth) {
                freeWidth = actualWidth - childWidth;
                row++;
                left = paddingLeft;
                top = paddingTop + row * mRowHeight + row * mVerticalSpace;
            } else if (childWidth + space == freeWidth) {
                left = actualWidth - freeWidth + space;
                top = paddingTop + row * mRowHeight + row * mVerticalSpace;
                freeWidth = actualWidth;
                row++;
            } else {
                left = actualWidth - freeWidth + space;
                top = paddingTop + row * mRowHeight + row * mVerticalSpace;
                freeWidth -= childWidth + space;
            }
            child.layout(left, top, left + childWidth, top + childHeight);
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
                    mOnItemClickListener.onItemClick(FlowLayout.this, v, position, mAdapter.getItemId(position));
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void startUpdate() {
        if (mAdapter == null) {
            removeAllViews();
            return;
        }
        int expectCount = mAdapter.getCount();
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
        void onItemClick(FlowLayout parent, View view, int position, long id);
    }

}
