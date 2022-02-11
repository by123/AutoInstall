package com.scrat.mm2.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scrat.mm2.R
import kotlinx.android.synthetic.main.activity_simple_main.*


class SimpleMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_main)

        initView()
    }


    private fun initView() {
        install.setOnClickListener {
            InstallActivity.start(this@SimpleMainActivity)
        }
        channel.setOnClickListener {
            ChannelActivity.start(this@SimpleMainActivity)
        }
        wakeup.setOnClickListener {
            WakeUpActivity.start(this@SimpleMainActivity)
        }
    }
}
