package com.scrat.mm;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.leon.channel.helper.ChannelReaderUtil;
import com.leon.channel.reader.IdValueReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.RequiresApi;


public class MainActivity extends Activity {

    private TextView mParamTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String data1 = ChannelReaderUtil.getStringValueById(this,600);
        String data2 = ChannelReaderUtil.getStringValueById(this,601);
        String data3 = ChannelReaderUtil.getStringValueById(this,602);
        mParamTxt = findViewById(R.id.txt_param);
        mParamTxt.setText(
                "应用:"+data1 +
                        "\n分享ID:"+data2 +
                        "\n参数:"+data3 +
                        "\n屏幕宽："+ Util.getScreenWidth() +
                        "\n屏幕高: "+ Util.getScreenHeight() +
                        "\nIP：" + NetUitl.getIP() );
        registerNetworkCallback(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mParamTxt.setText(mParamTxt.getText().toString() +
                        "\n粘贴板内容：" + Util.pasteHtml());
            }
        });
    }

    private void updateState(boolean hasConnect){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mParamTxt.setText(mParamTxt.getText().toString() +
                        "\n网络环境:无网络");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateState(boolean hasConnect, final Network network, final NetworkCapabilities networkCapabilities){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mParamTxt.setText(mParamTxt.getText().toString() +
                        "\n网络环境:" + NetUitl.getNetType(network,networkCapabilities));
            }
        });
    }

    /**
     * Android10监听网络变化广播
     * updateState 更新view用的函数,注意里面要在主线程使用
     */
    ConnectivityManager.NetworkCallback mNetworkCallback = null;
    // 注册回调
    private void registerNetworkCallback(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            final ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mNetworkCallback == null)
                mNetworkCallback = new ConnectivityManager.NetworkCallback() {
                    // 可用网络接入
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                        Log.d("TAG","ConnectivityManager onCapabilitiesChanged ");
                        // 一般在此处获取网络类型然后判断网络类型，就知道时哪个网络可以用connected
                        if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){//WIFI
                            updateState(true,network,networkCapabilities);
                        }else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){//移动数据
                            updateState(true,network,networkCapabilities);
                        }else {
                            updateState(false);
                        }
                    }

                    // 网络断开
                    public void onLost(Network network) {
                        Log.d("TAG","ConnectivityManager onLost ");
                        // 如果通过ConnectivityManager#getActiveNetwork()返回null，表示当前已经没有其他可用网络了。
                        updateState(false);
                    }
                };

            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
            if (mNetworkCallback!=null)
                cm.registerNetworkCallback(builder.build(), mNetworkCallback);
        }

    }

    // 注销回调
    private void unregisterNetworkCallback(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (mNetworkCallback != null) {
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                cm.unregisterNetworkCallback(mNetworkCallback);
            }
        }
    }
}