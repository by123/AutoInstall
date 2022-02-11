package com.scrat.mm2;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.xinstall.XInstall;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            // 初始化
            XInstall.init(this);
            // 启用log
            XInstall.setDebug(true);
        }
    }

    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }
}
