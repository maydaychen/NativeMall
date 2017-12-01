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
 * 作者：JTR on 2016/8/29 16:30
 * 邮箱：2091320109@qq.com
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<AttrbuteBean.ResultBean> mData;
    private Context mContext = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public RecommendAdapter(Context context, List<AttrbuteBean.ResultBean> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_goods, viewGroup, false);
        RecommendAdapter.ViewHolder vh = new RecommendAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(RecommendAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), mData.get(position).getMarketprice()));
        viewHolder.sold.setText(String.format(mContext.getResources().getString(R.string.sold), mData.get(position).getSales()));
        viewHolder.total.setText(String.format(mContext.getResources().getString(R.string.goods_detail_inventory), mData.get(position).getTotal()));
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

    public void setOnItemClickListener(RecommendAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView sold;
        public TextView total;
        public SmartImageView logo;


        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_hot_goods_name);
            sold = view.findViewById(R.id.tv_recommend_sold);
            total = view.findViewById(R.id.tv_recommend_total);
            logo = view.findViewById(R.id.iv_hot_goods_logo);

        }
    }
}
