package com.scrat.mm2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xinstall.XInstall;
import com.xinstall.listener.XWakeUpAdapter;
import com.xinstall.model.XAppData;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取唤醒参数
        XInstall.getWakeUpParam(this, getIntent(), wakeUpAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
        XInstall.getWakeUpParam(this, intent, wakeUpAdapter);
    }

    XWakeUpAdapter wakeUpAdapter = new XWakeUpAdapter() {
        @Override
        public void onWakeUp(XAppData XAppData) {
            //获取渠道数据
            String channelCode = XAppData.getChannelCode();

            //获取数据
            Map<String, String> data = XAppData.getExtraData();
            //通过链接后面携带的参数或者通过WebSdk初始化传入的data值。
            String uo = data.get("uo");
            //WebSdk初始，在buttonId里面定义的按钮点击携带数据
            String co = data.get("co");

            //获取时间戳
            String timeSpan = XAppData.getTimeSpan();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wakeUpAdapter = null;
    }
}