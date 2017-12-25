package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/10/9 11:04
 * 邮箱：2091320109@qq.com
 */
public class ConfirmPicAdapter extends RecyclerView.Adapter<ConfirmPicAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<PreOrderBean.ResultBean.OrderGoodsBean> mData;
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public ConfirmPicAdapter(List<PreOrderBean.ResultBean.OrderGoodsBean> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_confirm_img, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mIvMedPic.setImageUrl(mData.get(position).getThumb());
        viewHolder.mTvContirmPrice.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), mData.get(position).getMarketprice()));
        viewHolder.mTvContirmTitle.setText(mData.get(position).getTitle());
        viewHolder.mTvOption.setText("规格：" + mData.get(position).getOptiontitle());
        viewHolder.mTvContirmNum.setText("X" + mData.get(position).getTotal());
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_med_pic)
        SmartImageView mIvMedPic;
        @BindView(R.id.tv_contirm_price)
        TextView mTvContirmPrice;
        @BindView(R.id.tv_contirm_title)
        TextView mTvContirmTitle;
        @BindView(R.id.tv_contirm_num)
        TextView mTvContirmNum;
        @BindView(R.id.tv_option)
        TextView mTvOption;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}