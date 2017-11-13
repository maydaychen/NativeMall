package com.example.nativeMall.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Map;

/**
 * Created by Du on 2016/8/23.
 */
public class DoctListAdapter extends RecyclerView.Adapter<DoctListAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public DoctListAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_date_doct, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //viewHolder.ivDoctImg.setImageUrl(Config.PIC_URL + mData.get(position).get("ivDoctImg"));

        if (!TextUtils.isEmpty(mData.get(position).get("ivDoctImg").toString())) {
            String URL = Config.PIC_URL + mData.get(position).get("ivDoctImg");
            Util.GetCircleImg(URL,viewHolder.ivDoctImg);
        }
        viewHolder.tvDoctName.setText((String) mData.get(position).get("tvDoctName"));
        viewHolder.tvHosName.setText((String) mData.get(position).get("tvHosName"));
//        viewHolder.tvHosLevel.setText((String) mData.get(position).get("tvHosLevel"));
        viewHolder.tvDoctField.setText((String) mData.get(position).get("tvDoctField"));

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

        public SmartImageView ivDoctImg;
        public TextView tvDoctName;
        public TextView tvHosName;
        public TextView tvHosLevel;
        public TextView tvDoctField;


        public ViewHolder(View view) {
            super(view);
            ivDoctImg = (SmartImageView) view.findViewById(R.id.iv_doct);
            tvDoctName = (TextView) view.findViewById(R.id.tv_doct_name);
            tvHosName = (TextView) view.findViewById(R.id.tv_hos_name);
            tvHosLevel = (TextView)view.findViewById(R.id.tv_hos_level);
            tvDoctField = (TextView)view.findViewById(R.id.tv_field_detail);
        }
    }
}


