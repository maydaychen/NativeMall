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
public class MedicineKindFragment extends Fragment {
    @BindView(R.id.lv_history)
    ListView lvHistory;
    @BindView(R.id.tv_medicine_kind_clear)
    TextView mTvMedicineKindClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_medicine, container, false);
        ButterKnife.bind(this, root);
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_textview, Config.shistory);
        lvHistory.setAdapter(adapter);
        lvHistory.setOnItemClickListener((adapterView, view, i, l) -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            SearchMedFragment searchMedFragment = new SearchMedFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", Config.shistory.get(i));
            SearchActivity.searchName.setText(Config.shistory.get(i));
            searchMedFragment.setArguments(bundle);
            ft.replace(R.id.frame, searchMedFragment);
            ft.commit();
        });
        if (Config.shistory.isEmpty()){
            mTvMedicineKindClear.setVisibility(View.GONE);
        }else {
            mTvMedicineKindClear.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定要删除所有搜索记录么");
                builder.setTitle("健康商城");

                builder.setPositiveButton("确认", (dialog, which) -> {
                    Config.shistory.clear();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    mTvMedicineKindClear.setVisibility(View.GONE);
                });

                builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

                builder.create().show();
            });
        }
        return root;
    }
}
