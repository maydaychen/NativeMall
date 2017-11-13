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
 * Created by Du on 2016/9/14.
 */
public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public DateListAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_date_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {



        viewHolder.ivDoctImg.setImageUrl(Config.PIC_URL + mData.get(position).get("ivDoctImg"));
        viewHolder.tvDoctName.setText((String) mData.get(position).get("tvDoctName"));
        viewHolder.tvDempt.setText((String) mData.get(position).get("tvDempt"));
        viewHolder.tvDoctLevel.setText((String) mData.get(position).get("tvDoctLevel"));
        viewHolder.tvPrice.setText((String) mData.get(position).get("tvPrice"));
        viewHolder.tvHospName.setText((String) mData.get(position).get("tvHospName"));
        viewHolder.tvPatientName.setText((String) mData.get(position).get("tvPatientName"));
        viewHolder.tvIsCancel.setText((String) mData.get(position).get("tvIsCancel"));
        viewHolder.tvDateTime.setText((String) mData.get(position).get("tvDateTime"));

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

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SmartImageView ivDoctImg;
        public TextView tvDoctName;
        public TextView tvDempt;
        public TextView tvDoctLevel;
        public TextView tvPrice;
        public TextView tvHospName;
        public TextView tvPatientName;
        public TextView tvIsCancel;
        public TextView tvDateTime;


        public ViewHolder(View view) {
            super(view);
            ivDoctImg = (SmartImageView) view.findViewById(R.id.iv_doct_img);
            tvDoctName = (TextView) view.findViewById(R.id.tv_doct_name);
            tvDempt = (TextView) view.findViewById(R.id.tv_depmt);
            tvDoctLevel = (TextView) view.findViewById(R.id.tv_doct_level);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvHospName = (TextView) view.findViewById(R.id.tv_hos_name);
            tvPatientName = (TextView) view.findViewById(R.id.tv_patient_name);
            tvIsCancel = (TextView) view.findViewById(R.id.tv_is_cancle);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);

        }


    }
}
