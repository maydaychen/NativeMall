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
 * 作者：JTR on 2016/9/1 15:53
 * 邮箱：2091320109@qq.com
 */
public class GridDownGridAdapter extends RecyclerView.Adapter<GridDownGridAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private Context mContext = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public GridDownGridAdapter(Context context,List<Map<String, Object>> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_down_grid, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText((String) mData.get(position).get("name"));
        viewHolder.price.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price),(String) mData.get(position).get("shopprice")));
        viewHolder.shopname.setText((String) mData.get(position).get("sname"));
        viewHolder.logo.setImageUrl(Config.PIC_URL + mData.get(position).get("url"));

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
        public TextView shopname;

        public ViewHolder(View view) {
            super(view);
            logo = (SmartImageView) view.findViewById(R.id.iv_item_grid_down_grid_logo);
            name = (TextView) view.findViewById(R.id.tv_item_grid_down_grid_name);
            price = (TextView) view.findViewById(R.id.tv_item_grid_down_grid_price);
            shopname = (TextView) view.findViewById(R.id.tv_item_grid_down_grid_shop_name);
        }
    }
}