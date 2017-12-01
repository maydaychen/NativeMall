package com.example.nativeMall.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by user on 2017/11/16.
 */

public class GrideViewScroll extends GridView {

    public GrideViewScroll(Context context) {
        super(context);
    }

    public GrideViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrideViewScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
