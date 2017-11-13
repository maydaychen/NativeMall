package com.example.nativeMall.Adapter;

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
 * Created by Du on 2016/8/22.
 */
public class HosAdapter extends RecyclerView.Adapter<HosAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public HosAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hos_detail, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.ivHosImg.setImageUrl(Config.PIC_URL + mData.get(position).get("ivHosImg"));
        viewHolder.tvHosName.setText((String) mData.get(position).get("tvHosName"));
        viewHolder.tvNum.setText((String) mData.get(position).get("tvNum"));
        viewHolder.tvAddress.setText((String) mData.get(position).get("tvAddress"));
        viewHolder.tvHosLevel.setText((String) mData.get(position).get("tvHosLevel"));

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
            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNum;
        public TextView tvHosName;
        public TextView tvAddress;
        public TextView tvHosLevel;
        public SmartImageView ivHosImg;

        public ViewHolder(View view) {
            super(view);
            ivHosImg = (SmartImageView) view.findViewById(R.id.iv_hos_img);
            tvHosName = (TextView) view.findViewById(R.id.tv_hos_name);
            tvNum = (TextView) view.findViewById(R.id.tv_doct_num);
            tvAddress = (TextView)view.findViewById(R.id.tv_depmt);
            tvHosLevel = (TextView)view.findViewById(R.id.tv_hos_level);
        }
    }
}

