package com.example.nativeMall.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.MyQuestionAct;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Du on 2016/10/14.
 */
public class FreeAnswerService extends Service {
    @Nullable
    private getMessageThread messageThread = null;

    private int notificationMsgId = 1;
    private int notificationNum = 1;  // 推送的消息次数，显示在右下角
    private NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    private Notification notification = null;
    private NotificationManager notificationManager = null;
    private static final String TAG = "FreeAnswerService";

    private String response = null;
    int i = 0;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Config.IS_LOG) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            messageThread = new getMessageThread();
            messageThread.start();
            Log.i(TAG, "onStartCommand: ");
        }


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {

        messageThread.exit = true;
        super.onDestroy();

    }

    class getMessageThread extends Thread {

        public boolean exit = false;
        @Override
        public void run() {

            try {
                while (!exit) {
                    Thread.sleep(2000);
                    //服务器获取信息,并推送

                     Log.i(TAG, "run: time" + (i++));
                    getQuestionRespond();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        private void getQuestionRespond() {


            AsyncHttpClient client = new SyncHttpClient();
            String URL = Config.GLOBAL_URL1 + "inquisitionInfo/qureyInquisition?userId=" + Config
                    .userBean.getData().getUid();
            // Log.i(TAG, "getQuestionListByAcyncClient: " + URL);

            client.get(URL, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String result = new String(bytes);
                    Gson gson = new Gson();
                    int number = 1;
                    CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                            .class);
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {

                        response = codeMsgMItemsBean.getITEMS().get(j).get
                                ("inquisitionResponse").toString();
                        if (!TextUtils.isEmpty(response)) {
                            number++;

                        }
                    }
                    if (number > notificationNum) {
                        simpleNotify();
                        notificationNum++;
                        notificationMsgId++;
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });

        }

    }

    private void simpleNotify() {


        builder.setTicker("您的问诊有回复啦！");
        builder.setContentTitle("寻医问药");
        builder.setContentText("");
        builder.setSubText("您的问诊有回复啦！");
        //设置通知数量：          builder.setContentInfo() 该方法同样效果
        builder.setNumber(notificationNum);
        //builder.setNumber(1);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.xywy_zxwz);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.xywy_zxwz));

        //响应的Activity;
        Intent intent = new Intent(this, MyQuestionAct.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notification = builder.build();
        notificationManager.notify(notificationMsgId, notification);
        //notificationManager.notify(1, notification);


    }


}
