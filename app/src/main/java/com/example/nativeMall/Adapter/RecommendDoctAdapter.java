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
 * Created by Du on 2016/8/22.
 */
public class RecommendDoctAdapter extends RecyclerView.Adapter<RecommendDoctAdapter.ViewHolder>
        implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    // private Bitmap bitmap = null;
    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public RecommendDoctAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend,
                viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        //设置医生名字
        if (mData.get(position).get("tvDoctName").toString().length() > 5) {
            String name = mData.get(position).get("tvDoctName").toString().substring(0, 5);
            viewHolder.tvDoctName.setText(name);
        } else {
            viewHolder.tvDoctName.setText((String) mData.get(position).get("tvDoctName"));
        }
        //设置医生所在部门
        viewHolder.tvDepmt.setText((String) mData.get(position).get("tvDepmt"));
        //判断image URL 是否为空,并设置图像
        if (!TextUtils.isEmpty(mData.get(position).get("ivDoctImg").toString())) {
            String URL = Config.PIC_URL + mData.get(position).get("ivDoctImg");
            Util.GetCircleImg(URL,viewHolder.ivDoctImg);
        }
        //viewHolder.ivDoctImg.setImageUrl(Config.PIC_URL + mData.get(position).get("ivDoctImg"));
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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDepmt;
        public TextView tvDoctName;
        public SmartImageView ivDoctImg;

        public ViewHolder(View view) {
            super(view);
            tvDepmt = (TextView) view.findViewById(R.id.tv_recommend_depmt);
            tvDoctName = (TextView) view.findViewById(R.id.tv_recommend_name);
            ivDoctImg = (SmartImageView) view.findViewById(R.id.iv_recommend_doct);
        }
    }


}
