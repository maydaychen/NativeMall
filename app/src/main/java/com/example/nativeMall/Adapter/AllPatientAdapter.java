package com.example.nativeMall.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.R;

import java.util.List;

/**
 * Created by Du on 2016/9/11.
 */
public class AllPatientAdapter extends RecyclerView.Adapter<AllPatientAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List mData;


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public AllPatientAdapter(List mData) {
        this.mData = mData;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_all_patient, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvPatientName.setText((String) mData.get(position));

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
        public TextView tvPatientName;


        public ViewHolder(View view) {
            super(view);
            tvPatientName = (TextView) view.findViewById(R.id.tv_patient_name);

        }
    }
}



