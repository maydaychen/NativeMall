package com.example.nativeMall.Model;

import android.content.Context;

import com.example.nativeMall.Listener.AddAddressListener;

/**
 * 作者：JTR on 2016/11/14 11:23
 * 邮箱：2091320109@qq.com
 */
public interface NewsModel {
    void addAddress(Context context, AddAddressListener addAddressListener, String url, String map);
}
