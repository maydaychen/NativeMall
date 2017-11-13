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
 * Created by Du on 2016/9/28.
 */
public class InsuranProductAdapter extends RecyclerView.Adapter<InsuranProductAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public InsuranProductAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_insuran_product, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvProductName.setText(mData.get(position).get("tvProductName").toString());
        viewHolder.tvPrice.setText("¥ " + mData.get(position).get("tvPrice").toString());
        viewHolder.tvProductDes.setText(mData.get(position).get("tvProductDes").toString());
        viewHolder.tvBzService.setText("保障服务: " + mData.get(position).get("tvBzService").toString());
        viewHolder.tvCbAge.setText("承保年龄: " + mData.get(position).get("tvCbAge").toString());
        viewHolder.tvSaleSum.setText("销量: " + mData.get(position).get("tvSaleSum").toString());
        viewHolder.tvGoodRate.setText("好评率: " + mData.get(position).get("tvGoodRate").toString());

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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvProductName;
        public TextView tvPrice;
        public TextView tvProductDes;
        public TextView tvBzService;
        public TextView tvCbAge;
        public TextView tvSaleSum;
        public TextView tvGoodRate;

        public ViewHolder(View view) {
            super(view);
            tvProductName = (TextView) view.findViewById(R.id.tv_product_name);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvProductDes = (TextView) view.findViewById(R.id.tv_product_des);
            tvBzService = (TextView) view.findViewById(R.id.tv_bz_service);
            tvCbAge = (TextView) view.findViewById(R.id.tv_cb_age);
            tvSaleSum = (TextView) view.findViewById(R.id.tv_sale_sum);
            tvGoodRate = (TextView) view.findViewById(R.id.tv_good_rate);

        }


    }
}

