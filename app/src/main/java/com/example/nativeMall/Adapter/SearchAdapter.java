package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.SearchBean;
import com.example.nativeMall.R;

import java.util.List;

/**
 * Created by user on 2017/11/27.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener {
    private SearchAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<SearchBean.ResultBean> mData;
    private Context mContext = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public SearchAdapter(Context context, List<SearchBean.ResultBean> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getTitle());
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

    public void setOnItemClickListener(SearchAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_search_goods);
        }
    }
}
