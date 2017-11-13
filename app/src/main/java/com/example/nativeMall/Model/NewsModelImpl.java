package com.example.nativeMall.Model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.nativeMall.Http;
import com.example.nativeMall.Listener.AddAddressListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：JTR on 2016/11/14 11:23
 * 邮箱：2091320109@qq.com
 */
public class NewsModelImpl implements NewsModel,PostModel {
    @Override
    public void addAddress(Context context, final AddAddressListener addAddressListener, String url, String map) {

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.obj.toString();
                switch (msg.what) {
                    case 0:
                        JSONObject json;
                        try {
                            json = new JSONObject(data);
                            if (json.getString("success").equals("T")) {
                                addAddressListener.onSuccess(data);
                            } else {
                                addAddressListener.onFaile(json.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }
        };

        Http.getInstance().init(context.getApplicationContext(), handler, map, url, 0).sendMsg();
    }

    @Override
    public void post(Context context, final AddAddressListener addAddressListener, String url, String map) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.obj.toString();
                switch (msg.what) {
                    case 0:
                        JSONObject json;
                        try {
                            json = new JSONObject(data);
                            if (json.getString("success").equals("T")) {
                                addAddressListener.onSuccess(data);
                            } else {
                                addAddressListener.onFaile(json.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }
        };

        Http.getInstance().init(context.getApplicationContext(), handler, map, url, 0).sendMsg();
    }
}
