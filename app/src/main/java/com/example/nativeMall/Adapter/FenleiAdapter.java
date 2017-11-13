package com.example.nativeMall.Adapter;

import android.app.Activity;
import android.content.Intent;
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

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.View.BaseHolder;
import com.example.nativeMall.ui.Activity.GridDownActivity;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/9/12 17:06
 * 邮箱：2091320109@qq.com
 */
public class FenleiAdapter extends RecyclerView.Adapter<BaseHolder> {
    //条目样式
    public int screenWidth;//屏幕宽度
    private List<Map<String, Object>> mData;

    public Activity mFragmentActivity;

    public FenleiAdapter(List<Map<String, Object>> mData, FragmentActivity fragmentActivity) {
        this.mData = mData;
        this.mFragmentActivity = fragmentActivity;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GridViewHolder(R.layout.item_fenlei_right, parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        basicParamInit();
        List<Map<String, Object>> inside_list;
        inside_list = (ArrayList) (mData.get(position).get("inside_list"));
        holder.refreshData(inside_list, position);
        ((GridViewHolder) holder).fenleiName.setText((String) mData.get(position).get("name"));
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
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.rv_fenlei_right_content);
            fenleiName = (TextView) itemView.findViewById(R.id.tv_fenlei_right_name);
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
//            int lineNumber = data.size() % ONE_LINE_SHOW_NUMBER == 0 ? data.size() / ONE_LINE_SHOW_NUMBER : data.size() / ONE_LINE_SHOW_NUMBER + 1;
            //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
            //因为每行显示3个条目，为了保持正方形，那么高度应该是也是宽度/3
            //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
            layoutParams.height = lineNumber * (screenWidth / 3) + (lineNumber - 1) * dip2px(10) + dip2px(10);
            layoutParams.width = screenWidth;

            item_recyclerview.setLayoutParams(layoutParams);
            item_recyclerview.setAdapter(new GridAdapter(data));
        }

        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {
            private List<Map<String, Object>> mArrayList = new ArrayList<>();

            public GridAdapter(List<Map<String, Object>> mArrayList) {
                this.mArrayList = mArrayList;
            }

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_fenlei_right_content, parent, viewType);
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

            private SmartImageView imageview_item;
            private TextView name;

            public ItemViewHolder(int viewId, ViewGroup parent, int viewType) {
                super(viewId, parent, viewType);
                imageview_item = (SmartImageView) itemView.findViewById(R.id.fenlei_right_content_logo);
                name = (TextView) itemView.findViewById(R.id.fenlei_right_content_name);
                ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
                int lineNumber = data.size() % ONE_LINE_SHOW_NUMBER == 0 ? data.size() / ONE_LINE_SHOW_NUMBER : data.size() / ONE_LINE_SHOW_NUMBER + 1;
                layoutParams.width = screenWidth / 2 + dip2px(100);
                layoutParams.height = layoutParams.width / 3 * lineNumber;
                parent.setLayoutParams(layoutParams);
            }

            @Override
            public void refreshData(final List<Map<String, Object>> data, final int position) {
                super.refreshData(data, position);
                imageview_item.setImageUrl(Config.PIC_URL + data.get(position).get("inside_path"));
                name.setText((String) data.get(position).get("inside_name"));
                imageview_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mFragmentActivity, GridDownActivity.class);
                        intent.putExtra("cateid", (String) data.get(position).get("inside_cateid"));
                        intent.putExtra("title", (String) data.get(position).get("title"));
                        mFragmentActivity.startActivity(intent);
                    }
                });
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