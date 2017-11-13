package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/9/29 15:43
 * 邮箱：2091320109@qq.com
 */
public class MyShoucangAdapter extends RecyclerView.Adapter<MyShoucangAdapter.ViewHolder> implements
        View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public MyShoucangAdapter(List<Map<String, Object>> mData, Context context) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_shoucang,
                viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.ivMedImg.setImageUrl(Config.PIC_URL + mData.get(position).get("url"));
        viewHolder.name.setText((String) mData.get(position).get("name"));
        viewHolder.price.setText("￥" + mData.get(position).get("price"));
        viewHolder.shopname.setText((String) mData.get(position).get("shopname"));

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
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
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

