package com.example.nativeMall.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.FenleiRightAdapter;
import com.example.nativeMall.Bean.CategoryBean;
import com.example.nativeMall.Bean.CategoryRightBean;
import com.example.nativeMall.Bean.FenleiBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.Utils;
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
 * Created by user on 2017/11/20.
 */

public class FenleiFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.fenleiList)
    ListView fenleiList;
    @BindView(R.id.fenlei_list_right)
    RecyclerView mFenleiListRight;

    private FenleiRightAdapter mFenleiRightAdapter;
    private List<String> left_list = new ArrayList<>();
    private Gson gson = new Gson();
    private FenleiBean fenleiBean;
    int currentItem = 0;
    private SubscriberOnNextListener<JSONObject> getLeftOnNext;
    private SubscriberOnNextListener<JSONObject> getRightOnNext;
    private SharedPreferences preferences;

    public static FenleiFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        FenleiFragment mainFragment = new FenleiFragment();
        mainFragment.setArguments(arguments);
        return mainFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fenlei, container, false);
        ButterKnife.bind(this, root);
        initData();
        initView();
        initCategory();
        return root;
    }

    public void initData() {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        getLeftOnNext = jsonObject -> {
            Log.i("chenyi", "get banner onNext: " + jsonObject.toString());
            if (jsonObject.getInt("statusCode") == 1) {
                CategoryBean indexBean = gson.fromJson(jsonObject.toString(), CategoryBean.class);
                for (CategoryBean.ResultBean resultBean : indexBean.getResult()) {
                    left_list.add(resultBean.getName());
                }
                final FenleiLeftAdapter fenleiLeftAdapter = new FenleiLeftAdapter(getContext(), R.layout.layout_fenlei, left_list);
                fenleiList.setAdapter(fenleiLeftAdapter);
                fenleiList.setSelection(0);
                fenleiList.setOnItemClickListener((adapterView, view, i, l) -> {
                    currentItem = i;
                    fenleiList.setAdapter(fenleiLeftAdapter);
                    initRightCategory(indexBean.getResult().get(i).getId());
                });
                initRightCategory(indexBean.getResult().get(0).getId());
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        getRightOnNext = jsonObject -> {
            Log.i("chenyi", "get right fenlei onNext: " + jsonObject.toString());
            if (jsonObject.getInt("statusCode") == 1) {
                CategoryRightBean indexBean = gson.fromJson(jsonObject.toString(), CategoryRightBean.class);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
                mFenleiListRight.setLayoutManager(layoutManager);
//                FenleiAdapter mFenleiAdapter = new FenleiAdapter(list, getActivity());
                mFenleiRightAdapter = new FenleiRightAdapter(getActivity(), indexBean.getResult());
                mFenleiListRight.setAdapter(mFenleiRightAdapter);
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void initView() {
    }

    private void initCategory() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().get_category(
                new ProgressSubscriber(getLeftOnNext, getActivity()),
                preferences.getString("access_token", ""), sign, time);
    }

    private void initRightCategory(String pid) {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "pid=" + pid + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().getRightCategory(
                new ProgressSubscriber(getRightOnNext, getActivity()),
                preferences.getString("access_token", ""), pid, sign, time);
    }

    class FenleiLeftAdapter extends BaseAdapter {

        private LayoutInflater flater;
        private int resource;
        private TextView text;
        private List mList;

        public FenleiLeftAdapter(Context context, int resource, List objects) {
            flater = LayoutInflater.from(context);
            this.mList = objects;
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = flater.inflate(resource, null);
            text = convertView.findViewById(R.id.fenlei_text);
            text.setHeight(Util.dip2px(40, getActivity()));
            text.setText((String) mList.get(position));
            if (position == currentItem) {
                convertView.setBackgroundResource(R.color.white);
            } else {
                convertView.setBackgroundResource(R.color.lightgray);
            }
            return convertView;
        }
    }
}
