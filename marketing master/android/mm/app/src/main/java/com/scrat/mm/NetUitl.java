package com.scrat.mm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import androidx.annotation.RequiresApi;

class NetUitl {

    public class NetState {
        public static final String NET_NO_CONNECT = "no connect";
        public static final String NET_WIFI = "wifi";
        public static final String NET_2G = "2G";
        public static final String NET_3G = "3G";
        public static final String NET_4G = "4G";
        public static final String NET_5G = "5G";
        public static final String NET_UNKNOWN = "unknown";
        /**
         * 没有READ_PHONE_STATE 权限,不过是可以知道当前是 移动网络,不是wifi
         */
        public static final String NOT_PERMISSION_READ_PHONE_STATE_ONLY_GPRS = "no permission read phone state only gprs";
        public static final String NOT_PERMISSION_ACCESS_NETWORK_STATE = "no premission access network state";
    }


    /**
     * 判断当前网络连接
     * android N 以上用 NetworkCapabilities 和 TelephonyManager.getDataNetworkType()
     *
     *  {ACCESS_NETWORK_STATE (必须) , READ_PHONE_STATE (可无,有的话可以判断是 3g/4g)}
     * @return 状态码 @NetStateInt
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getNetType( Network network
            , NetworkCapabilities netCapabilities) {
        Context context = MyApplication.shareApplication().getApplicationContext();
        String stateCode = NetState.NET_UNKNOWN;

        final String tag_ = "getNetType";
        if (!PermissionUtil.hasAccessNetworkState()) {
            Log.d(tag_,"checkPermission  ACCESS_NETWORK_STATE");
            return NetState.NOT_PERMISSION_ACCESS_NETWORK_STATE;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Network ni = network;
            if (ni == null) {
                Log.d(tag_,"Network is null");
                return NetState.NET_NO_CONNECT;
            }
            NetworkCapabilities capabilities = netCapabilities;

            if (capabilities == null) {
                Log.d(tag_,"NetworkCapabilities is null");
                return NetState.NET_NO_CONNECT;
            }

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                stateCode = NetState.NET_WIFI;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                if (!PermissionUtil.hasReadPhoneStatePermission()) {
                    return NetState.NOT_PERMISSION_READ_PHONE_STATE_ONLY_GPRS;
                }
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                @SuppressLint("MissingPermission")
                int type = tm.getDataNetworkType();
                if (type == TelephonyManager.NETWORK_TYPE_UNKNOWN){
                    return NetState.NET_UNKNOWN;
                }
                Log.d(tag_,"getNetworkType type  =="+type);
                switch (type) {

                    case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        stateCode = NetState.NET_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        stateCode = NetState.NET_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        stateCode = NetState.NET_4G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_NR:
                        stateCode = NetState.NET_5G;
                        break;
                    default:
                        Log.d(tag_,"getDataNetworkType type err ");
                        stateCode = NetState.NET_UNKNOWN;
                        break;
                }
            }else {
                Log.d(tag_,"NetworkCapabilities type err");
                stateCode = NetState.NET_NO_CONNECT;
            }


        } else {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null && ni.isConnectedOrConnecting()) {
                switch (ni.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        stateCode = NetState.NET_WIFI;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        switch (ni.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                            case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                            case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                stateCode = NetState.NET_2G;
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                stateCode = NetState.NET_3G;
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                stateCode = NetState.NET_4G;
                                break;
                            case TelephonyManager.NETWORK_TYPE_NR:
                                stateCode = NetState.NET_5G;
                                break;
                            default:
                                stateCode = NetState.NET_UNKNOWN;
                        }
                        break;
                    default:
                        stateCode = NetState.NET_UNKNOWN;
                }

            }
        }
        return stateCode;
    }

    public static String getIP() {
        Context context = MyApplication.shareApplication().getApplicationContext();
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G/5G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            return "无网络连接";
        }
        return null;
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
