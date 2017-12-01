package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.AttrbuteBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by user on 2017/11/16.
 */

public class HotGoodsAdapter extends RecyclerView.Adapter<HotGoodsAdapter.ViewHolder> implements View.OnClickListener {
    private AllAddressAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<AttrbuteBean.ResultBean> mData;
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public HotGoodsAdapter(Context context, List<AttrbuteBean.ResultBean> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public HotGoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hot_goods, viewGroup, false);
        HotGoodsAdapter.ViewHolder vh = new HotGoodsAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(HotGoodsAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), mData.get(position).getMarketprice()));
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

    public void setOnItemClickListener(AllAddressAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public SmartImageView logo;


        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_hot_goods_name);
            logo = view.findViewById(R.id.iv_hot_goods_logo);

        }
    }
}