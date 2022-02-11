package com.scrat.mm2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xinstall.XInstall;
import com.xinstall.listener.XWakeUpAdapter;
import com.xinstall.model.XAppData;

import java.util.Map;

public class WakeUpActivity extends AppCompatActivity {

    private View backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);
        backBtn = findViewById(R.id.back);
        /**
         *  获取唤醒参数
         */
        XInstall.getWakeUpParam(this,getIntent(), wakeUpAdapter);
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         *  获取唤醒参数
         */
        XInstall.getWakeUpParam(this,getIntent(), wakeUpAdapter);
    }

    private void initView() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    XWakeUpAdapter wakeUpAdapter = new XWakeUpAdapter() {
        @Override
        public void onWakeUp(XAppData xAppData) {
            // 获取渠道数据
            String channelCode = xAppData.getChannelCode();
            Map<String, String> extraData = xAppData.getExtraData();
            String timeSpan = xAppData.getTimeSpan();

            String str = "channelCode = "+ channelCode +
                         "\nco = " + extraData.get("co") +
                         "\nuo = " + extraData.get("uo") +
                         "\ntimeSpan = " + timeSpan;
            TipDialogSingleButton dialog = new
                    TipDialogSingleButton(WakeUpActivity.this);
            dialog.setTitle("唤醒参数示例").setMessage(str).setYesOnclickListener(new TipDialogSingleButton.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                }
            }).show();
        }

    };
}
