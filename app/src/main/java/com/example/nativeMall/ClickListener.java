package com.example.nativeMall;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.nativeMall.Adapter.FamousAdapter;
import com.example.nativeMall.ui.Activity.GoodsDetailActivity;

/**
 * 作者：JTR on 2016/9/5 09:12
 * 邮箱：2091320109@qq.com
 */
public class ClickListener implements FamousAdapter.OnRecyclerViewItemClickListener{
    Context mContext;
    String pid;
    public ClickListener(Context mContext,String pid) {
        this.mContext = mContext;
        this.pid = pid;
    }

    @Override
    public void onItemClick(View view, int data) {
        Intent intent = new Intent(mContext, GoodsDetailActivity.class);
        mContext.startActivity(intent);
    }
}
