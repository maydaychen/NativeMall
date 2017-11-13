package com.example.nativeMall.Adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
import com.example.nativeMall.ui.Activity.GoodsDetailActivity;
import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/9/26 15:19
 * 邮箱：2091320109@qq.com
 */
public class CategoryAdapter extends RecyclerView.Adapter<BaseHolder> {
    public int screenWidth;//屏幕宽度
    private int HORIZONTAL_VIEW_X = 0;//水平RecyclerView滑动的距离
    private List<Map<String, Object>> mData;
    private List<Map<String, Object>> inside;
    public FragmentActivity mFragmentActivity;

    public CategoryAdapter(List<Map<String, Object>> mData, FragmentActivity fragmentActivity) {
        this.mData = mData;
        this.mFragmentActivity = fragmentActivity;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HorizontalViewHolder(R.layout.item_category_out, parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        basicParamInit();
        for (int i = 0; i < mData.size(); i++) {
            inside = (List<Map<String, Object>>) (mData.get(i).get("inside"));
        }
        if (holder instanceof HorizontalViewHolder) {
            holder.refreshData(inside, position);
            ((HorizontalViewHolder) holder).logo.setImageUrl(Config.PIC_URL + mData.get(position).get("pic"));
        }
        inside = null;
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

    /**
     * 嵌套的水平RecyclerView
     * 当条目被回收时，下次加载会重新回到之前的x轴
     */
    private class HorizontalViewHolder extends BaseHolder<List<Map<String, Object>>> {
        private RecyclerView hor_recyclerview;
        private SmartImageView logo;

        private List<Map<String, Object>> mdata;

        private int scrollX;//纪录X移动的距离

        private boolean isLoadLastState = false;//是否加载了之前的状态


        public HorizontalViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            hor_recyclerview = (RecyclerView) itemView.findViewById(R.id.rv_mall_half);
            logo = (SmartImageView) itemView.findViewById(R.id.iv_category_logo);
            //为了保存移动距离，所以添加滑动监听
            hor_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    //每次条目重新加载时，都会滑动到上次的距离
                    if (!isLoadLastState) {
                        isLoadLastState = true;
                        hor_recyclerview.scrollBy(HORIZONTAL_VIEW_X, 0);
                    }
                    //dx为每一次移动的距离，所以我们需要做累加操作
                    scrollX += dx;
                }
            });
        }

        @Override
        public void refreshData(List<Map<String, Object>> data, int position) {
            this.mdata = data;
            ViewGroup.LayoutParams layoutParams = hor_recyclerview.getLayoutParams();
            //高度等于＝条目的高度＋ 10dp的间距 ＋ 10dp（为了让条目居中）
            layoutParams.height = screenWidth / 3;
            layoutParams.width = screenWidth;
            hor_recyclerview.setLayoutParams(layoutParams);
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(mFragmentActivity, LinearLayoutManager.HORIZONTAL, false));
            hor_recyclerview.setAdapter(new HorizontalAdapter());

        }

        /**
         * 在条目回收时调用，保存X轴滑动的距离
         */
        public void saveStateWhenDestory() {
            HORIZONTAL_VIEW_X = scrollX;
            isLoadLastState = false;
            scrollX = 0;
        }


        private class HorizontalAdapter extends RecyclerView.Adapter<BaseHolder> {

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_mall_three_rows, parent, viewType);
            }

            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData((Map) mdata.get(position), position);
            }

            @Override
            public int getItemCount() {
                return mdata.size();
            }
        }
    }

    /**
     * 通用子条目hodler
     */
    private class ItemViewHolder extends BaseHolder<Map<String, Object>> {

        private SmartImageView imageview_item;
        private TextView price;
        public TextView name;

        public ItemViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            imageview_item = (SmartImageView) itemView.findViewById(R.id.gridimage);
            price = (TextView) itemView.findViewById(R.id.tv_three_row_price);
            name = (TextView) itemView.findViewById(R.id.gridtext);
            ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
            layoutParams.width = screenWidth;
            layoutParams.height = screenWidth / 3 + dip2px(20);
            parent.setLayoutParams(layoutParams);
        }

        @Override
        public void refreshData(final Map<String, Object> data, int position) {
            super.refreshData(data, position);
            name.setText((String) data.get("name"));
            price.setText(String.format(mFragmentActivity.getResources().getString(R.string.tv_mall_price), (String) data.get("price")));
            imageview_item.setImageUrl(Config.PIC_URL + data.get("url"));

            imageview_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mFragmentActivity, GoodsDetailActivity.class);
                    intent.putExtra("pid", "" + data.get("pid"));
                    mFragmentActivity.startActivity(intent);
                }
            });
        }
    }
}