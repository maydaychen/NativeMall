package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.CouponsBean;
import com.example.nativeMall.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/12/9.
 */

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private List<CouponsBean.ResultBean> mData = new ArrayList<>();


    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public void clearData() {
        this.mData.clear();
    }

    public CouponsAdapter(Context context, List<CouponsBean.ResultBean> dataList) {
        mContext = context;
        mData = dataList;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_money, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvMoneyName.setText(mData.get(position).getCouponname());
        holder.mTvMoneyDate.setText(String.format(mContext.getResources().getString(R.string.tv_order_useful_time), mData.get(position).getTimestr()));
        holder.mTvMoneyPrice.setText(String.format(mContext.getResources().getString(R.string.mall_daily_nowprice), mData.get(position).get_backmoney()));
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_money_name)
        TextView mTvMoneyName;
        @BindView(R.id.tv_money_date)
        TextView mTvMoneyDate;
        @BindView(R.id.tv_money_price)
        TextView mTvMoneyPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
