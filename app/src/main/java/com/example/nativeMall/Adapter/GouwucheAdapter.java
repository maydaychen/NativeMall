package com.example.nativeMall.Adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.R;
import com.example.nativeMall.View.BaseHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/10/25 10:13
 * 邮箱：2091320109@qq.com
 */
public class GouwucheAdapter extends RecyclerView.Adapter<BaseHolder> {
    //条目样式
    public int screenWidth;//屏幕宽度
    private List<Map<String, Object>> mData;
    private int selectPosition ;
    public Activity mFragmentActivity;

    public GouwucheAdapter(List<Map<String, Object>> mData, FragmentActivity fragmentActivity) {
        this.mData = mData;
        this.mFragmentActivity = fragmentActivity;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GouwucheAdapter.GridViewHolder(R.layout.item_gouwuche_outside, parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        basicParamInit();
        List<Map<String, Object>> inside_list = new ArrayList<>();
        inside_list = (ArrayList) (mData.get(position).get("inside_info"));
        holder.refreshData(inside_list, position);
        ((GouwucheAdapter.GridViewHolder) holder).fenleiName.setText((String) mData.get(position).get("name"));
    }

    @Override
    /**
     * 当Item出现时调用此方法
     */
    public void onViewAttachedToWindow(BaseHolder holder) {
        Log.i("mengyuan", "onViewAttachedToWindow:" + holder.getClass().toString());
    }

    @Override
    /**
     * 当Item被回收时调用此方法
     */
    public void onViewDetachedFromWindow(BaseHolder holder) {
        Log.i("mengyuan", "onViewDetachedFromWindow:" + holder.getClass().toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 11;
    }

    public class GridViewHolder extends BaseHolder<List<Map<String, Object>>> {
        public TextView fenleiName;
        private RecyclerView item_recyclerview;
        private final int ONE_LINE_SHOW_NUMBER = 3;

        private List<Map<String, Object>> data;

        public GridViewHolder(final int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            item_recyclerview = (RecyclerView) itemView.findViewById(com.example.nativeMall.R.id.rv_gouwuche_inside);
            fenleiName = (TextView) itemView.findViewById(com.example.nativeMall.R.id.tv_gouwuche_outside);
        }

        @Override
        public void refreshData(List<Map<String, Object>> data, int position) {
            super.refreshData(data, position);
            this.data = data;
            //每行显示3个，水平显示
            item_recyclerview.setLayoutManager(new GridLayoutManager(mFragmentActivity, ONE_LINE_SHOW_NUMBER, LinearLayoutManager.VERTICAL, false));

            ViewGroup.LayoutParams layoutParams = item_recyclerview.getLayoutParams();
            //计算行数
            int lineNumber = data.size() % ONE_LINE_SHOW_NUMBER == 0 ? data.size() / ONE_LINE_SHOW_NUMBER : data.size() / ONE_LINE_SHOW_NUMBER + 1;

            layoutParams.height = dip2px(20) * (lineNumber + 1);
            layoutParams.width = screenWidth;

            item_recyclerview.setLayoutParams(layoutParams);
            item_recyclerview.setAdapter(new GouwucheAdapter.GridViewHolder.GridAdapter(data));
        }

        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {
            private List<Map<String, Object>> mArrayList = new ArrayList<>();

            public GridAdapter(List<Map<String, Object>> mArrayList) {
                this.mArrayList = mArrayList;
            }

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_gouwuche_text, parent, viewType);
            }

            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(data, position);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }


        /**
         * 通用子条目hodler
         */
        private class ItemViewHolder extends BaseHolder<List<Map<String, Object>>> {
            private TextView name;

            public ItemViewHolder(int viewId, ViewGroup parent, int viewType) {
                super(viewId, parent, viewType);
                name = (TextView) itemView.findViewById(com.example.nativeMall.R.id.tv_gouwuche_inside_name);
                ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
                int lineNumber = data.size() % ONE_LINE_SHOW_NUMBER == 0 ? data.size() / ONE_LINE_SHOW_NUMBER : data.size() / ONE_LINE_SHOW_NUMBER + 1;
                layoutParams.width = screenWidth;
                layoutParams.height = dip2px(40) * lineNumber;
                parent.setLayoutParams(layoutParams);
            }

            @Override
            public void refreshData(final List<Map<String, Object>> data, final int position) {
                super.refreshData(data, position);
                name.setText((String) data.get(position).get("inside_name"));
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(mFragmentActivity, ""+position, Toast.LENGTH_SHORT).show();
                        Toast.makeText(mFragmentActivity, ""+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        selectPosition = getAdapterPosition();
                        notifyDataSetChanged();
//                        view.setBackgroundColor(mFragmentActivity.getResources().getColor(R.color.red));
//                        int position = 0;
//                        position = getAdapterPosition();

//                        view.setBackgroundColor(mFragmentActivity.getResources().getColor(R.color.red));
//                        notifyDataSetChanged();
                    }
                });
//
//                if(position==selectPosition)
//                {
//                    //change color like
//                    name.setBackgroundColor(mFragmentActivity.getResources().getColor(R.color.red));
//                }else {
//                    name.setBackgroundColor(mFragmentActivity.getResources().getColor(R.color.background));
//                }
            }
        }
    }

    /**
     * 计算屏幕的宽度
     */
    private void basicParamInit() {
        DisplayMetrics metric = new DisplayMetrics();
        mFragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
    }

    /**
     * 将dp转化为px
     */
    private int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mFragmentActivity.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }
}
