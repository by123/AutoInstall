package com.scrat.mm2.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scrat.mm2.R
import com.xinstall.XInstall
import com.xinstall.listener.XInstallAdapter
import com.xinstall.model.XAppData
import kotlinx.android.synthetic.main.activity_install.*

class InstallActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, InstallActivity::class.java)
            context.startActivity(intent)
        }
    }

    var mData: XAppData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_install)

        initView()

        /**
         *  获取安装携带的参数
         */
        
        XInstall.getInstallParam(object : XInstallAdapter() {
            override fun onInstall(xAppData: XAppData) { // 打印数据便于调试
                mData = xAppData
                // 获取渠道数据
                val channelCode = xAppData.getChannelCode()
                val data = xAppData.extraData
                val timeSpan = xAppData.timeSpan
            }
        })
    }

    private fun initView() {
        back.setOnClickListener {
            finish()
        }
        btInstall.setOnClickListener {
            var str = ""
            if (mData != null) {
                val extraData = mData!!.extraData
                str =
                    "channelCode=${mData!!.channelCode}\nco=${extraData["co"]}\nuo=${extraData["uo"]}\ntimeSpan=${mData!!.timeSpan}"
            }
            val dialog =
                TipDialogSingleButton(this)
//            dialog.setTitle("安装参数示例").setMessage(
//                "1、应用是否已经上传了集成完的安装包\n" +
//                        "2、appkey是否正确配置\n" +
//                        "3、分享链接内是否配置了动态参数\n" +
//                        "4、安装的app与链接是否一致"
//            )
            dialog.setTitle("安装参数示例").setMessage(str)
                .setYesOnclickListener {
                    dialog.dismiss()
                }.show()
        }
    }
}
