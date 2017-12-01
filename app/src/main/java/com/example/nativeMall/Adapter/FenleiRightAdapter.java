package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.CategoryRightBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class FenleiRightAdapter extends RecyclerView.Adapter<FenleiRightAdapter.ViewHolder> implements View.OnClickListener {
    private FenleiRightAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<CategoryRightBean.ResultBean> mData;
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public FenleiRightAdapter(Context context, List<CategoryRightBean.ResultBean> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public FenleiRightAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fenlei_grid, viewGroup, false);
        FenleiRightAdapter.ViewHolder vh = new FenleiRightAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(FenleiRightAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getName());
        viewHolder.logo.setImageUrl(mData.get(position).getThumb());

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

    public void setOnItemClickListener(FenleiRightAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SmartImageView logo;
        public TextView name;


        public ViewHolder(View view) {
            super(view);
            logo = view.findViewById(R.id.gridimage_fenlei);
            name = view.findViewById(R.id.gridtext_fenlei);
        }
    }
}
