package com.example.nativeMall.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.R;

import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/9/23 15:43
 * 邮箱：2091320109@qq.com
 */
public class AllAddressListAdapter extends RecyclerView.Adapter<AllAddressListAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public AllAddressListAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_address_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText((String) mData.get(position).get("name"));
        viewHolder.mobile.setText((String) mData.get(position).get("mobile"));
        viewHolder.address.setText((String) mData.get(position).get("address"));
        if ( !mData.get(position).get("default").equals("11")){
            viewHolder.IS_DEFAULT.setVisibility(View.INVISIBLE);
        }
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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView mobile;
        public TextView address;
        public TextView IS_DEFAULT;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_address_name);
            mobile = (TextView) view.findViewById(R.id.tv_address_mobile);
            address = (TextView) view.findViewById(R.id.tv_address_detail);
            IS_DEFAULT = (TextView) view.findViewById(R.id.tv_address_default);
        }
    }
}