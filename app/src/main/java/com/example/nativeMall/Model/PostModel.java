package com.example.nativeMall.Model;

import android.content.Context;

import com.example.nativeMall.Listener.AddAddressListener;

/**
 * 作者：JTR on 2016/11/16 15:21
 * 邮箱：2091320109@qq.com
 */
public interface PostModel {
    void post(Context context, AddAddressListener addAddressListener, String url, String map);
}
