package com.scrat.mm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;


class PermissionUtil {


    public static boolean hasReadPhoneStatePermission() {
        Context context = MyApplication.shareApplication().getApplicationContext();
        return context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasAccessNetworkState() {
        Context context = MyApplication.shareApplication().getApplicationContext();
        return ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasReadSMS(){
        Context context = MyApplication.shareApplication().getApplicationContext();
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasReadPhoneNumbers(){
        Context context = MyApplication.shareApplication().getApplicationContext();
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasReadPhoneState(){
        Context context = MyApplication.shareApplication().getApplicationContext();
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }


}
