package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nativeMall.R;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.NetUtils;

import butterknife.ButterKnife;

public class OnlineAskDoctAct extends AppCompatActivity {

//    @BindView(R.id.custom_title)
//    CustomTitle customTitle;

    private String DoctName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_ask_doct);
        ButterKnife.bind(this);
        getSupportActionBar().hide();


        InitData();

        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        EaseChatFragment chatFragment = new EaseChatFragment();
        Bundle args = new Bundle();

        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, DoctName);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_chat,chatFragment).commit();


//        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
//            @Override
//            public void leftClick(Boolean click) {
//                finish();
//            }
//
//            @Override
//            public void rightClick(Boolean click) {
//
//            }
//        });
    }

    private void InitData() {
        Intent intent1 = getIntent();
        DoctName = intent1.getStringExtra("DoctName");
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(OnlineAskDoctAct.this)){

                        }
                        //连接不到聊天服务器
                        else{

                        }
                        //当前网络不可用，请检查网络设置
                    }
                }
            });
        }
    }
}
