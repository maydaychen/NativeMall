package com.example.nativeMall.View;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/5/24/下午4:28
 * DESC:
 */
public class BaseHolder<T> extends RecyclerView.ViewHolder {


    public BaseHolder(int viewId, ViewGroup parent, int viewType) {
        super(parent);
    }

    public void refreshData(T data, int position) {

    }
}
