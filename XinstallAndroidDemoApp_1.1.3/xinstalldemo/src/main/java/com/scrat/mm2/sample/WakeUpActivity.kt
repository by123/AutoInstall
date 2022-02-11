package com.scrat.mm2.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scrat.mm2.R
import com.xinstall.XInstall
import com.xinstall.listener.XWakeUpAdapter
import com.xinstall.model.XAppData
import kotlinx.android.synthetic.main.activity_channel.*

class WakeUpActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WakeUpActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wakeup)

        /**
         *  获取唤醒参数 
         */
        
        XInstall.getWakeUpParam(getIntent(), wakeUpAdapter)

        initView()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        /**
         *  获取唤醒参数
         */
        XInstall.getWakeUpParam(getIntent(), wakeUpAdapter)
    }

    private fun initView() {
        back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, SimpleMainActivity::class.java)
        startActivity(intent)
    }

    private var wakeUpAdapter: XWakeUpAdapter? = object : XWakeUpAdapter() {
        override fun onWakeUp(xAppData: XAppData) {
            // 获取渠道数据
            val channelCode = xAppData.getChannelCode()
            val extraData = xAppData.extraData
            val timeSpan = xAppData.timeSpan

            var str =
                "channelCode=${xAppData!!.channelCode}\nco=${extraData["co"]}\nuo=${extraData["uo"]}\ntimeSpan=${xAppData!!.timeSpan}"
            val dialog =
                TipDialogSingleButton(this@WakeUpActivity)
            dialog.setTitle("唤醒参数示例").setMessage(str)
                .setYesOnclickListener {
                    dialog.dismiss()
                }.show()
        }

    }
}
