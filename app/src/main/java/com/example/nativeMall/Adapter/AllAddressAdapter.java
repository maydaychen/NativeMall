package com.example.nativeMall.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.Bean.AddressBean;
import com.example.nativeMall.R;

import java.util.List;

/**
 * 作者：JTR on 2016/9/23 15:43
 * 邮箱：2091320109@qq.com
 */
public class AllAddressAdapter extends RecyclerView.Adapter<AllAddressAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<AddressBean.ResultBean.ListBean> mData;
    private EditInterface mEditInterface;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public AllAddressAdapter(List<AddressBean.ResultBean.ListBean> mData) {
        this.mData = mData;
    }

    public void setCheckInterface(EditInterface editInterface) {
        this.mEditInterface = editInterface;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_address, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getRealname());
        viewHolder.mobile.setText(mData.get(position).getMobile());
        viewHolder.address.setText(mData.get(position).getProvince() + mData.get(position).getCity() +
                mData.get(position).getArea() + mData.get(position).getAddress());
        if (!mData.get(position).getIsdefault().equals("1")) {
            viewHolder.IS_DEFAULT.setVisibility(View.INVISIBLE);
        }
        viewHolder.change.setOnClickListener(view -> mEditInterface.changeAddress(position));
        viewHolder.delete.setOnClickListener(view -> mEditInterface.deleteAddress(position));
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

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView mobile;
        public TextView address;
        public TextView IS_DEFAULT;
        public TextView change;
        public TextView delete;


        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_address_name);
            mobile = view.findViewById(R.id.tv_address_mobile);
            address = view.findViewById(R.id.tv_address_detail);
            IS_DEFAULT = view.findViewById(R.id.tv_address_default);
            change = view.findViewById(R.id.tv_address_change);
            delete = view.findViewById(R.id.tv_address_delete);
        }
    }

    public interface EditInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param positon 组元素位置
         */
        void changeAddress(int positon);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param positon 组元素位置
         */
        void deleteAddress(int positon);
    }

}