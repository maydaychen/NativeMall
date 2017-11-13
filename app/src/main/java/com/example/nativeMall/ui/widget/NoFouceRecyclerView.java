package com.example.nativeMall.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * 作者：JTR on 2016/10/12 14:09
 * 邮箱：2091320109@qq.com
 */
public class NoFouceRecyclerView extends RecyclerView{
    public NoFouceRecyclerView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
