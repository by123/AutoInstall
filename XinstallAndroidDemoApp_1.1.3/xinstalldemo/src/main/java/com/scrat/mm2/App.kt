package com.scrat.mm2

import androidx.multidex.MultiDexApplication
import com.xinstall.XInstall

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        /**
         * 初始化
         */
        if (XInstall.isMainProcess(this)) {
            XInstall.setDebug(true)
            XInstall.init(this)
        }
    }

    
}