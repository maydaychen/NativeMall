package com.example.nativeMall.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nativeMall.R;

import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/8/18 13:56
 * 邮箱：2091320109@qq.com
 */
public class MainFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";

    public static MainFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(arguments);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        return root;
    }
}
