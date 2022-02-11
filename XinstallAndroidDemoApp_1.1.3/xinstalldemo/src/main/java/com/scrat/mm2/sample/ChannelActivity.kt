package com.scrat.mm2.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scrat.mm2.R
import com.xinstall.XInstall
import kotlinx.android.synthetic.main.activity_channel.*

class ChannelActivity : AppCompatActivity() {
    
    companion object{
        fun start(context: Context){
            val intent = Intent(context, ChannelActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)
        
        initView()
    }

    private fun initView() {
        back.setOnClickListener { 
            finish()
        }

        btRegist.setOnClickListener {
            /**
             * 注册上报
             */
            XInstall.reportRegister()
            val dialog =
                TipDialogSingleButton(this)
//            "1、应用是否已经上传了集成完的安装包\n" +
//                    "2、appkey是 否正确配置"
            dialog.setTitle("注册量统计上报完成").setMessage("注册量上报成功")
                .setYesOnclickListener {
                    dialog.dismiss()
                }.show()
        }
        btEffect.setOnClickListener {
            /**
             * 事件上报
             */
            XInstall.reportPoint("test", 1)
            val dialog =
                TipDialogSingleButton(this)
//            "1、应用是否已经上传了集成完的安装包\n" +
//                    "2、appkey是 否正确配置"
            dialog.setTitle("事件统计上报完成").setMessage("事件统计上报成功")
                .setYesOnclickListener {
                    dialog.dismiss()
                }.show()
        }
    }
}
