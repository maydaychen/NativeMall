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
 * 作者：JTR on 2016/10/18 09:30
 * 邮箱：2091320109@qq.com
 */
public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.ViewHolder> {
    private ConfirmOrderAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private Context mContext = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public ConfirmOrderAdapter(Context context, List<Map<String, Object>> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ConfirmOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_confirm_orderr, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shopname.setText(String.format(mContext.getResources().getString(R.string.tv_confirm_order_num), (String) mData.get(position).get("sname")));
        holder.price.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), (String) mData.get(position).get("shopprice")));
        holder.name.setText((String) mData.get(position).get("name"));
        holder.logo.setImageUrl(Config.PIC_URL + mData.get(position).get("url"));

        holder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SmartImageView logo;
        public TextView name;
        public TextView price;
        public TextView shopname;

        public ViewHolder(View view) {
            super(view);
            logo = (SmartImageView) view.findViewById(R.id.iv_item_grid_down_logo);
            name = (TextView) view.findViewById(R.id.tv_item_grid_down_name);
            price = (TextView) view.findViewById(R.id.tv_item_grid_down_price);
            shopname = (TextView) view.findViewById(R.id.tv_item_grid_down_shop_name);
        }
    }
}