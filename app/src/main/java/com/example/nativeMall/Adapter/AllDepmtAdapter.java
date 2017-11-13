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
 * Created by Du on 2016/8/23.
 */
public class AllDepmtAdapter extends RecyclerView.Adapter<AllDepmtAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public AllDepmtAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_all_depmt, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvDepmtName.setText((String) mData.get(position).get("tvPatientName"));
//        viewHolder.ivDepmtImg.setImageUrl(Config.PIC_URL1 + mData.get(position).get("img"));
        // viewHolder.ivDepmtImg.setBackgroundResource(R.drawable.choose_doc);
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

            mOnItemClickListener.onItemClick(v, (int)(v.getTag()));
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDepmtName;
//        public SmartImageView ivDepmtImg;

        public ViewHolder(View view) {
            super(view);
            tvDepmtName = (TextView) view.findViewById(R.id.tv_depmt_name);

        }
    }
}


