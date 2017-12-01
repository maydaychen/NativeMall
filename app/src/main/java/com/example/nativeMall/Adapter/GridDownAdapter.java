package com.example.nativeMall.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.GridDownBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：JTR on 2016/9/1 14:54
 * 邮箱：2091320109@qq.com
 */
public class GridDownAdapter extends RecyclerView.Adapter<GridDownAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<GridDownBean.ResultBean> mData = new ArrayList<>();
    private Context mContext = null;

    public void addAllData(List<GridDownBean.ResultBean> dataList) {
        this.mData.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mData.clear();
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public GridDownAdapter(Context context) {
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_down, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setIsRecyclable(false);
        viewHolder.name.setText(mData.get(position).getTitle());
        viewHolder.price.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), (String) mData.get(position).getMarketprice()));
        viewHolder.sold.setText(String.format(mContext.getResources().getString(R.string.sold), (String) mData.get(position).getSales()));
        viewHolder.keep.setText(String.format(mContext.getResources().getString(R.string.goods_detail_inventory), (String) mData.get(position).getTotal()));
        viewHolder.old_price.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), (String) mData.get(position).getProductprice()));
        viewHolder.old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.logo.setImageUrl(mData.get(position).getThumb());
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public SmartImageView logo;
        public TextView name;
        public TextView price;
        public TextView old_price;
        public TextView sold;
        public TextView keep;

        public ViewHolder(View view) {
            super(view);
            logo = view.findViewById(R.id.iv_item_grid_down_logo);
            name = view.findViewById(R.id.tv_item_grid_down_name);
            price = view.findViewById(R.id.tv_item_grid_down_price);
            old_price = view.findViewById(R.id.tv_item_grid_down_old_price);
            sold = view.findViewById(R.id.tv_item_grid_down_sold_num);
            keep = view.findViewById(R.id.tv_item_grid_down_keep_num);
        }
    }
}