package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.nativeMall.Adapter.ConfirmOrderAdapter;
import com.example.nativeMall.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrderActivity extends InitActivity {

    @BindView(R.id.rv_confirm_order)
    RecyclerView mRvConfirmOrder;
    private List<Map<String, Object>> data_list = new ArrayList<>();

    @OnClick(R.id.iv_comfirm_back)
    public void onClick() {
        finish();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        mRvConfirmOrder.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        ConfirmOrderAdapter gridDownAdapter = new ConfirmOrderAdapter(this, data_list);
        mRvConfirmOrder.setAdapter(gridDownAdapter);
    }

    @Override
    public void initData() {
//        PreOrderBean preOrderBean = (PreOrderBean) getIntent().getSerializableExtra("preorder");
//        for (int i = 0; i< preOrderBean.getData().getProducts().size(); i++){
//            Map<String, Object> map = new HashMap<>();
//            map.put("name", preOrderBean.getData().getProducts().get(i).getProduct().getName());
//            map.put("shopprice", preOrderBean.getData().getProducts().get(i).getProduct().getShopprice());
//            map.put("sname", preOrderBean.getData().getProducts().get(i).getBuycount());
//            map.put("url", preOrderBean.getData().getProducts().get(i).getProduct().getShowimg());
//            data_list.add(map);
//        }
    }
}
