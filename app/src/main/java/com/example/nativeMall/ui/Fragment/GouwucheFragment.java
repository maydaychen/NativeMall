package com.example.nativeMall.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nativeMall.Adapter.GouwucheAdapter;
import com.example.nativeMall.Bean.GouwucheBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.entity.ProductInfo;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/8/29 10:35
 * 邮箱：2091320109@qq.com
 */
public class GouwucheFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, GouwucheAdapter.CheckInterface, GouwucheAdapter.ModifyCountInterface {
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.rv_cart_list)
    RecyclerView mRvCartList;
    @BindView(R.id.all_chekbox)
    CheckBox mAllChekbox;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    private SubscriberOnNextListener<JSONObject> cartsOnNext;
    private SharedPreferences preferences;
    private static final String EXTRA_CONTENT = "content";
    private Gson gson = new Gson();
    private GouwucheAdapter mGouwucheAdapter;
    private List<ProductInfo> children = new ArrayList<>();
    private int totalCount = 0;
    private GouwucheBean gouwucheBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gouwuche, container, false);
        ButterKnife.bind(this, root);
        initData();
        initDetail();
        return root;
    }

    public static GouwucheFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        GouwucheFragment gouwucheFragment = new GouwucheFragment();
        gouwucheFragment.setArguments(arguments);
        return gouwucheFragment;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            initDetail();
            mSwipeContainer.setRefreshing(false);
        }, 3000);
        // 3秒后发送消息，停止刷新
    }

    private void initData() {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        mSwipeContainer.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeContainer.setDistanceToTriggerSync(300);
        mSwipeContainer.setProgressBackgroundColor(R.color.blue);
        mSwipeContainer.setSize(SwipeRefreshLayout.DEFAULT);

        cartsOnNext = jsonObject -> {
            gouwucheBean = gson.fromJson(jsonObject.toString(), GouwucheBean.class);
            Log.i("chenyi", "gouwuche initData: " + jsonObject.toString());

            for (int k = 0; k < gouwucheBean.getResult().getList().size(); k++) {
                double price = Double.parseDouble(gouwucheBean.getResult().getList().get(k).getMarketprice());
                ProductInfo productInfo = new ProductInfo(k + "", "商品", gouwucheBean.getResult().getList().get(k).getThumb(), gouwucheBean.getResult().getList().get(k).getTitle(),
                        price, Integer.valueOf(gouwucheBean.getResult().getList().get(k).getTotal()), "", gouwucheBean.getResult().getList().get(k).getGoodsid());
                children.add(productInfo);
            }

            mRvCartList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mGouwucheAdapter = new GouwucheAdapter(getActivity(), gouwucheBean.getResult().getList());
            mGouwucheAdapter.setCheckInterface(this);
            mGouwucheAdapter.setModifyCountInterface(this);
            mRvCartList.setAdapter(mGouwucheAdapter);
        };

        mAllChekbox.setOnCheckedChangeListener((compoundButton, b) -> doCheckAll(b));
    }

    private void initDetail() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().cartsList(
                new ProgressSubscriber(cartsOnNext, getActivity()),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    @Override
    public void checkChild(int childPosition, boolean isChecked) {
        // 判断改组下面的所有子元素是否是同一种状态
        children.get(childPosition).setChoosed(isChecked);
        boolean allChildSameState = true;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            mAllChekbox.setChecked(true);
        } else {
            mAllChekbox.setChecked(false);
        }
        mGouwucheAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int childPosition, View showCountView, boolean isChecked) {
        Log.i("chenyi", "doIncrease: ");
        ProductInfo product = children.get(childPosition);
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        gouwucheBean.getResult().getList().get(childPosition).setTotal(currentCount + "");
        mGouwucheAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int childPosition, View showCountView, boolean isChecked) {
        Log.i("chenyi", "doDecrease: ");
        ProductInfo product = children.get(childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        gouwucheBean.getResult().getList().get(childPosition).setTotal(currentCount + "");
        mGouwucheAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * <p/>
     * <p/>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        double totalPrice = 0.00;

        for (int j = 0; j < children.size(); j++) {
            ProductInfo product = children.get(j);
            if (product.isChoosed()) {
                totalCount++;
                totalPrice += product.getPrice() * product.getCount();
            }
        }

        mTvTotalPrice.setText("￥" + String.format("%.2f", totalPrice));
//        tv_go_to_pay.setText("去支付(" + totalCount + ")");
    }

    private void doCheckAll(Boolean b) {
        for (int j = 0; j < children.size(); j++) {
            children.get(j).setChoosed(b);
        }
        mGouwucheAdapter.notifyDataSetChanged();
        calculate();
    }

}
