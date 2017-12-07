package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nativeMall.Bean.GouwucheBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/10/25 10:13
 * 邮箱：2091320109@qq.com
 */
public class GouwucheAdapter extends RecyclerView.Adapter<GouwucheAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<GouwucheBean.ResultBean.ListBean> mData;
    private Context mContext;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public GouwucheAdapter(Context context, List<GouwucheBean.ResultBean.ListBean> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gouwuche, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTvIntro.setText(mData.get(position).getTitle());
        viewHolder.mIvMedPic.setImageUrl(mData.get(position).getThumb());
        viewHolder.mTvGuige.setText(mData.get(position).getOptiontitle());
        viewHolder.mTvNum.setText(mData.get(position).getTotal());
        viewHolder.mTvPrice.setText(String.format(mContext.getResources().getString(R.string.tv_mall_price), mData.get(position).getMarketprice()));
        viewHolder.mTvReduce.setOnClickListener(view -> {
            // 暴露增加接口
            modifyCountInterface.doDecrease(position,  viewHolder.mTvNum, viewHolder.mCheckBox.isChecked());
//            if (Integer.valueOf(mData.get(position).getTotal()) > 1) {
//                viewHolder.mTvNum.setText(Integer.valueOf(mData.get(position).getTotal()) - 1 + "");
//                mData.get(position).setTotal(Integer.valueOf(mData.get(position).getTotal()) - 1 + "");
//            }
        });
        viewHolder.mTvAdd.setOnClickListener(view -> {
            // 暴露增加接口
            modifyCountInterface.doIncrease(position,  viewHolder.mTvNum, viewHolder.mCheckBox.isChecked());
//            viewHolder.mTvNum.setText(Integer.valueOf(mData.get(position).getTotal()) + 1 + "");
//            mData.get(position).setTotal(Integer.valueOf(mData.get(position).getTotal()) + 1 + "");
        });
        viewHolder.mCheckBox.setOnClickListener(v -> {
            // 暴露子选接口
            viewHolder.mCheckBox.setChecked(((CheckBox) v).isChecked());
            checkInterface.checkChild(position, ((CheckBox) v).isChecked());
        });
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
        @BindView(R.id.check_box)
        CheckBox mCheckBox;
        @BindView(R.id.iv_med_pic)
        SmartImageView mIvMedPic;
        @BindView(R.id.tv_intro)
        TextView mTvIntro;
        @BindView(R.id.tv_guige)
        TextView mTvGuige;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_reduce)
        TextView mTvReduce;
        @BindView(R.id.tv_num)
        TextView mTvNum;
        @BindView(R.id.tv_add)
        TextView mTvAdd;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 子选框状态改变时触发的事件
         *
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int childPosition, View showCountView, boolean isChecked);

    }

}