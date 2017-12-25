package com.example.nativeMall.Adapter;

/**
 * 作者：JTR on 2016/9/20 10:45
 * 邮箱：2091320109@qq.com
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nativeMall.Bean.OrderBean;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.OrderDetailActivity;
import com.example.nativeMall.ui.Activity.PayActivity;
import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * 外层RecyclerView的Adapter
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.GridViewHolder> {
    //条目样式
    private final int DAIFUKUAN = 0;
    private final int DAIFAHUO = 1;
    private final int DAISHOUHUO = 2;
    private final int DAIPINGJIA = 3;
    private int screenWidth;//屏幕宽度
    private List<OrderBean.ResultBean> mData = new ArrayList<>();
    private Gson mGson = new Gson();
    private Context mContext;

    public void addAllData(List<OrderBean.ResultBean> dataList) {
        this.mData.addAll(dataList);
//        notifyDataSetChanged();
    }

    public void clearData() {
        this.mData.clear();
    }

    public MyOrderAdapter(Context context, List<OrderBean.ResultBean> list) {
        mContext = context;
        mData = list;
    }

//    public MyOrderAdapter(List<Map<String, Object>> mData, FragmentActivity fragmentActivity, Handler handler) {
//        this.mData = mData;
//        this.mFragmentActivity = fragmentActivity;
//        this.mHandler = handler;
//    }


    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DAISHOUHUO:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_daishouhuo, parent, false);
                return new GridViewHolder(view, parent, viewType);
            case DAIFUKUAN:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_daifukuan, parent, false);
                return new GridViewHolder(view1, parent, viewType);
            case DAIFAHUO:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_daifahuo, parent, false);
                return new GridViewHolder(view2, parent, viewType);
            case DAIPINGJIA:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_daipingjia, parent, false);
                return new GridViewHolder(view3, parent, viewType);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
//        basicParamInit();
        List<OrderBean.ResultBean.GoodsBean> list = new ArrayList();
        for (OrderBean.ResultBean.GoodsBean goodsBean : mData.get(position).getGoods()) {
            list.add(goodsBean);
        }
        holder.shopname.setText(mData.get(position).getOrdersn());
        holder.totalPrice.setText(String.format(mContext.getResources().getString(R.string.tv_order_total_price), (String) mData.get(position).getGoodsprice()));
        holder.totalNum.setText(String.format(mContext.getResources().getString(R.string.tv_order_num), mData.get(position).getGoods().size() + ""));
        holder.itemRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        GridAdapter gridAdapter = new GridAdapter(list);
        holder.itemRecyclerview.setAdapter(gridAdapter);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mData.get(position).getStatus()) {
            case DAIFUKUAN + "":
                return DAIFUKUAN;
            case DAIFAHUO + "":
                return DAIFAHUO;
            case DAISHOUHUO + "":
                return DAISHOUHUO;
            case DAIPINGJIA + "":
                return DAIPINGJIA;
            default:
                break;
        }
        return DAIFUKUAN;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView shopname;
        TextView totalPrice;
        TextView totalNum;
        private RecyclerView itemRecyclerview;
        private Button paynow, cancel;
        private Button confirm;
        private Button delete;
        private Button comment;
//        RelativeLayout mOrderDetail;

        private final int ONE_LINE_SHOW_NUMBER = 3;

        private ArrayList data;

        GridViewHolder(View mView, ViewGroup parent, int viewType) {
            super(mView);
            shopname = mView.findViewById(R.id.tv_order_shopname);
            totalPrice = mView.findViewById(R.id.tv_order_total_price);
            totalNum = mView.findViewById(R.id.tv_order_total_num);
//            mOrderDetail = itemView.findViewById(R.id.rl_order_detail);
            itemRecyclerview = mView.findViewById(R.id.rv_item_order_pic);
//            mOrderDetail.setOnClickListener(view -> {
//                Intent intent = new Intent(mFragmentActivity, OrderDetailActivity.class);
//                intent.putExtra("oid", (String) mData.get(getAdapterPosition()).getOrdersn());
//                mFragmentActivity.startActivity(intent);
//            });
            itemRecyclerview.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {//此处是点击按下时才执行跳转动作
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("oid", (String) mData.get(getAdapterPosition()-1).getOrdersn());
                    mContext.startActivity(intent);
                }
                return false;
            });
            if (viewType == DAIPINGJIA) {
                comment = itemView.findViewById(R.id.bt_daipingjia_comment);
                comment.setOnClickListener(view -> {

                });
            }
            if (viewType == DAIFUKUAN) {
                paynow = itemView.findViewById(R.id.bt_daifukuan_paynow);
                cancel = itemView.findViewById(R.id.bt_cancel_order);
                paynow.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra("price", mData.get(getAdapterPosition()-1).getGoodsprice());
                    intent.putExtra("order_num", mData.get(getAdapterPosition()-1).getOrdersn());
                    intent.putExtra("fromFlag", "1");
                    mContext.startActivity(intent);
                });
                cancel.setOnClickListener(view -> new AlertDialog.Builder(mContext).setTitle("请选择取消订单的理由").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"卖家缺货", "我不想买了", "见面同城交易", "信息填写错误，重新拍", "其他原因"}, 0, null
                ).setPositiveButton("确定", (dialog, i) -> {

                }).setNegativeButton("取消", (dialog, i) -> dialog.dismiss()).show());
            }
            if (viewType == DAISHOUHUO) {
                confirm = (Button) itemView.findViewById(R.id.bt_daishouhuo_confirm);
                confirm.setOnClickListener(view -> {

                });
            }


        }
    }

    /**
     * 计算屏幕的宽度
     */
//    private void basicParamInit() {
//        DisplayMetrics metric = new DisplayMetrics();
//        mFragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        screenWidth = metric.widthPixels;
//    }

    /**
     * 将dp转化为px
     */
    private int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mContext.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }

    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ItemViewHolder> {
        private List<OrderBean.ResultBean.GoodsBean> mArrayList = new ArrayList<>();

        GridAdapter(List<OrderBean.ResultBean.GoodsBean> mArrayList) {
            this.mArrayList = mArrayList;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_x2_imageview, parent, false);
            return new ItemViewHolder(view1, parent, viewType);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.imageviewItem.setImageUrl(mArrayList.get(position).getThumb());
            holder.item_title.setText(mArrayList.get(position).getTitle());
            holder.item_price.setText(mArrayList.get(position).getMarketprice());
            holder.item_num.setText(String.format(mContext.getResources().getString(R.string.tv_mall_num), mArrayList.get(position).getTotal()));
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        /**
         * 通用子条目hodler
         */
        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private SmartImageView imageviewItem;
            private TextView item_title;
            private TextView item_price;
            private TextView item_num;

            ItemViewHolder(View mView, ViewGroup parent, int viewType) {
                super(mView);
                imageviewItem = mView.findViewById(R.id.imageview_item);
                item_title = mView.findViewById(R.id.tv_item_title);
                item_price = mView.findViewById(R.id.tv_item_price);
                item_num = mView.findViewById(R.id.tv_item_num);
            }
        }
    }

}
