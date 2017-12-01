package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.ShoucangBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：JTR on 2016/9/29 15:43
 * 邮箱：2091320109@qq.com
 */
public class MyShoucangAdapter extends RecyclerView.Adapter<MyShoucangAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private List<ShoucangBean.ResultBean> mData = new ArrayList<>();

    public void addAllData(List<ShoucangBean.ResultBean> dataList) {
        this.mData.addAll(dataList);
        notifyDataSetChanged();
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public void clearData() {
        this.mData.clear();
    }

    public MyShoucangAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_shoucang, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivMedImg.setImageUrl(mData.get(position).getThumb());
        holder.name.setText(mData.get(position).getTitle());
        holder.price.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), mData.get(position).getMarketprice() + ""));
//        holder.mOrderPrice.setText(String.format(mContext.getResources().getString(R.string.order_price), mData.get(position).getFee() + ""));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SmartImageView ivMedImg;
        public TextView name;
        public TextView price;
        public TextView shopname;


        public ViewHolder(View view) {
            super(view);
            ivMedImg = (SmartImageView) view.findViewById(R.id.iv_shoucang_logo);
            name = (TextView) view.findViewById(R.id.tv_shoucang_name);
            price = (TextView) view.findViewById(R.id.tv_shoucang_price);
            shopname = (TextView) view.findViewById(R.id.tv_shoucang_shopname);
        }
    }
}

