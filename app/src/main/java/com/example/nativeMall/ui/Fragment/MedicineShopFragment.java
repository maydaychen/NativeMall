package com.example.nativeMall.ui.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Administrator on 2016/5/31 10:47
 * 邮箱：2091320109@qq.com
 */
public class MedicineShopFragment extends Fragment {
    @BindView(R.id.lv_shop_history)
    ListView lvShopHistory;
    @BindView(R.id.tv_medicine_shop_clear)
    TextView mTvMedicineShopClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medicine_shop, container, false);
        ButterKnife.bind(this, root);
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_textview, Config.supplier_history);
        lvShopHistory.setAdapter(adapter);
        lvShopHistory.setOnItemClickListener((adapterView, view, i, l) -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            SearchShopFragment searchShopFragment = new SearchShopFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", Config.supplier_history.get(i));
            SearchActivity.searchName.setText(Config.supplier_history.get(i));
            searchShopFragment.setArguments(bundle);
            ft.replace(R.id.frame, searchShopFragment);
            ft.commit();
        });
        if (Config.supplier_history.isEmpty()){
            mTvMedicineShopClear.setVisibility(View.GONE);
        }else {
            mTvMedicineShopClear.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定要删除所有搜索记录么");
                builder.setTitle("健康商城");

                builder.setPositiveButton("确认", (dialog, which) -> {
                    Config.supplier_history.clear();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    mTvMedicineShopClear.setVisibility(View.GONE);
                });
                builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            });
        }
        return root;
    }
}
