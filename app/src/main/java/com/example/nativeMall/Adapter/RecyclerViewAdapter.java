package com.example.nativeMall.Adapter;

/**
 * 作者：JTR on 2016/9/20 10:45
 * 邮箱：2091320109@qq.com
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.View.BaseHolder;
import com.example.nativeMall.ui.Activity.OrderDetailActivity;
import com.example.nativeMall.ui.Activity.PayActivity;
import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 外层RecyclerView的Adapter
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {
    //条目样式
    private final int DAIFUKUAN = 30;
    private final int DAIFAHUO = 70;
    private final int DAISHOUHUO = 110;
    private final int DAIPINGJIA = 140;
    private final int YIWANCHENG = 150;
    private final int YIQUXIAO = 200;
    private int screenWidth;//屏幕宽度
    private List<Map<String, Object>> mData;
    private Handler mHandler;
    private Gson mGson = new Gson();

    private FragmentActivity mFragmentActivity;

    public RecyclerViewAdapter(List<Map<String, Object>> mData, FragmentActivity fragmentActivity, Handler handler) {
        this.mData = mData;
        this.mFragmentActivity = fragmentActivity;
        this.mHandler = handler;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DAISHOUHUO:
                return new GridViewHolder(R.layout.item_order_daishouhuo, parent, viewType);
            case DAIFUKUAN:
                return new GridViewHolder(R.layout.item_order_daifukuan, parent, viewType);
            case DAIFAHUO:
                return new GridViewHolder(R.layout.item_order_daifahuo, parent, viewType);
            case DAIPINGJIA:
                return new GridViewHolder(R.layout.item_order_daipingjia, parent, viewType);
            case YIWANCHENG:
                return new GridViewHolder(R.layout.item_order_yiwancheng, parent, viewType);
            case YIQUXIAO:
                return new GridViewHolder(R.layout.item_order_yiquxiao, parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        basicParamInit();
        ArrayList<String> imgURL;
        imgURL = (ArrayList) (mData.get(position).get("img"));
        if (holder instanceof GridViewHolder) {
            holder.refreshData(imgURL, position);
            ((GridViewHolder) holder).shopname.setText((String) mData.get(position).get("storename"));
            ((GridViewHolder) holder).totalPrice.setText(String.format(mFragmentActivity.getResources().getString(R.string.tv_order_total_price), (String) mData.get(position).get("payfee")));
            ((GridViewHolder) holder).totalNum.setText(String.format(mFragmentActivity.getResources().getString(R.string.tv_order_num), mData.get(position).get("num")));
        }
    }

    @Override
    /**
     * 当Item出现时调用此方法
     */
    public void onViewAttachedToWindow(BaseHolder holder) {
        Log.i("mengyuan", "onViewAttachedToWindow:" + holder.getClass().toString());
    }

    @Override
    /**
     * 当Item被回收时调用此方法
     */
    public void onViewDetachedFromWindow(BaseHolder holder) {
        Log.i("mengyuan", "onViewDetachedFromWindow:" + holder.getClass().toString());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mData.get(position).get("orderstate").toString()) {
            case DAIFUKUAN + "":
                return DAIFUKUAN;
            case DAIFAHUO + "":
                return DAIFAHUO;
            case DAISHOUHUO + "":
                return DAISHOUHUO;
            case DAIPINGJIA + "":
                return DAIPINGJIA;
            case YIQUXIAO + "":
                return YIQUXIAO;
        }
        return YIWANCHENG;
    }

    private class GridViewHolder extends BaseHolder<ArrayList> {
        TextView shopname;
        TextView totalPrice;
        TextView totalNum;
        private RecyclerView item_recyclerview;
        private Button paynow, cancel;
        private Button confirm;
        private Button delete;
        private Button comment;
        RelativeLayout mOrderDetail;

        private final int ONE_LINE_SHOW_NUMBER = 3;

        private ArrayList data;

        GridViewHolder(final int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            shopname = (TextView) itemView.findViewById(R.id.tv_order_shopname);
            totalPrice = (TextView) itemView.findViewById(R.id.tv_order_total_price);
            totalNum = (TextView) itemView.findViewById(R.id.tv_order_total_num);
            mOrderDetail = (RelativeLayout) itemView.findViewById(R.id.rl_order_detail);
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.rv_item_order_pic);
            mOrderDetail.setOnClickListener(view -> {
                Intent intent = new Intent(mFragmentActivity, OrderDetailActivity.class);
                intent.putExtra("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                mFragmentActivity.startActivity(intent);
            });
            item_recyclerview.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {//此处是点击按下时才执行跳转动作
                    Intent intent = new Intent(mFragmentActivity, OrderDetailActivity.class);
                    intent.putExtra("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                    mFragmentActivity.startActivity(intent);
                }
                return false;
            });
            if (viewType == DAIPINGJIA) {
                comment = (Button) itemView.findViewById(R.id.bt_daipingjia_comment);
                comment.setOnClickListener(view -> {
                    Map<String, String> param = new HashMap<>();
                    param.put("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                    Http.getInstance().init(mFragmentActivity, mHandler, mGson.toJson(param), "order/orderDtl", 1).sendMsg();
                });
            }
            if (viewType == DAIFUKUAN) {
                paynow = (Button) itemView.findViewById(R.id.bt_daifukuan_paynow);
                cancel = (Button) itemView.findViewById(R.id.bt_cancel_order);
                paynow.setOnClickListener(view -> {
                    Intent intent = new Intent(mFragmentActivity, PayActivity.class);
                    intent.putExtra("sname", (String) mData.get(getAdapterPosition()).get("sname"));
                    intent.putExtra("price", (String) mData.get(getAdapterPosition()).get("payfee"));
                    intent.putExtra("orderid", (String) mData.get(getAdapterPosition()).get("oid"));
                    intent.putExtra("fromFlag", "1");
                    mFragmentActivity.startActivity(intent);
                });
                cancel.setOnClickListener(view -> new AlertDialog.Builder(mFragmentActivity).setTitle("请选择取消订单的理由").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"卖家缺货", "我不想买了", "见面同城交易", "信息填写错误，重新拍", "其他原因"}, 0, null
                ).setPositiveButton("确定", (dialog, i) -> {
                    Map<String, String> comment1 = new HashMap<>();
                    comment1.put("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                    comment1.put("statue", "200");
                    Http.getInstance().init(mFragmentActivity, mHandler, mGson.toJson(comment1), "order/handleOrder", 0).sendMsg();
                }).setNegativeButton("取消", (dialog, i) -> dialog.dismiss()).show());
            }
            if (viewType == YIQUXIAO) {
                delete = (Button) itemView.findViewById(R.id.bt_yiquxiao_shanchu);
                delete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mFragmentActivity);
                    builder.setMessage("确定要删除订单么？");
                    builder.setTitle("健康商城");

                    builder.setPositiveButton("确认", (dialog, which) -> {
                        Map<String, String> comment12 = new HashMap<>();
                        comment12.put("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                        comment12.put("statue", "210");
                        Http.getInstance().init(mFragmentActivity, mHandler, mGson.toJson(comment12), "order/handleOrder", 0).sendMsg();
                    });

                    builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

                    builder.create().show();
                });
            }
            if (viewType == YIWANCHENG) {
                delete = (Button) itemView.findViewById(R.id.bt_yiquxiao_shanchu);
                delete.setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mFragmentActivity);
                    builder.setMessage("确定要删除订单么？");
                    builder.setTitle("健康商城");

                    builder.setPositiveButton("确认", (dialog, which) -> {
                        Map<String, String> comment13 = new HashMap<>();
                        comment13.put("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                        comment13.put("statue", "210");
                        Http.getInstance().init(mFragmentActivity, mHandler, mGson.toJson(comment13), "order/handleOrder", 0).sendMsg();
                    });

                    builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

                    builder.create().show();
                });
            }
            if (viewType == DAISHOUHUO) {
                confirm = (Button) itemView.findViewById(R.id.bt_daishouhuo_confirm);
                confirm.setOnClickListener(view -> {
                    Map<String, String> comment14 = new HashMap<>();
                    comment14.put("oid", (String) mData.get(getAdapterPosition()).get("oid"));
                    comment14.put("statue", "140");
                    Http.getInstance().init(mFragmentActivity, mHandler, mGson.toJson(comment14), "order/handleOrder", 0).sendMsg();
                });
            }


        }

        @Override
        public void refreshData(ArrayList data, int position) {
            super.refreshData(data, position);
            this.data = data;
            //每行显示3个，水平显示
            item_recyclerview.setLayoutManager(new GridLayoutManager(mFragmentActivity, ONE_LINE_SHOW_NUMBER, LinearLayoutManager.VERTICAL, false));

            ViewGroup.LayoutParams layoutParams = item_recyclerview.getLayoutParams();
            //计算行数
            int lineNumber = data.size() / 4 + 1;
//            int lineNumber = data.size() % ONE_LINE_SHOW_NUMBER == 0 ? data.size() / ONE_LINE_SHOW_NUMBER : data.size() / ONE_LINE_SHOW_NUMBER + 1;
            //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
            //因为每行显示3个条目，为了保持正方形，那么高度应该是也是宽度/3
            //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
            layoutParams.width = screenWidth - dip2px(60);
            layoutParams.height = lineNumber * (layoutParams.width / 3) - dip2px(10);

            item_recyclerview.setLayoutParams(layoutParams);
            item_recyclerview.setAdapter(new GridAdapter(data));
        }

        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {
            private ArrayList mArrayList = new ArrayList<>();

            GridAdapter(ArrayList mArrayList) {
                this.mArrayList = mArrayList;
            }

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_x2_imageview, parent, viewType);
            }

            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(data, position);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }

        /**
         * 通用子条目hodler
         */
        private class ItemViewHolder extends BaseHolder<ArrayList> {

            private SmartImageView imageview_item;

            ItemViewHolder(int viewId, ViewGroup parent, int viewType) {
                super(viewId, parent, viewType);
                imageview_item = (SmartImageView) itemView.findViewById(R.id.imageview_item);
                ViewGroup.LayoutParams layoutParams = imageview_item.getLayoutParams();
                layoutParams.width = layoutParams.height = dip2px(70);
                imageview_item.setLayoutParams(layoutParams);
            }

            @Override
            public void refreshData(ArrayList data, int position) {
                super.refreshData(data, position);
                imageview_item.setImageUrl(Config.PIC_URL + data.get(position));
            }
        }
    }

    /**
     * 计算屏幕的宽度
     */
    private void basicParamInit() {
        DisplayMetrics metric = new DisplayMetrics();
        mFragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
    }

    /**
     * 将dp转化为px
     */
    private int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mFragmentActivity.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }

}
