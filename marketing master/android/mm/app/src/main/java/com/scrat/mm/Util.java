package com.scrat.mm;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;


public class Util {

    public static int getScreenWidth() {
        Context context = MyApplication.shareApplication().getApplicationContext();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        Context context = MyApplication.shareApplication().getApplicationContext();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取剪切板内容
     *
     * @return
     */
    public static String paste() {
        ClipboardManager manager = (ClipboardManager) MyApplication.shareApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }


    public static String pasteHtml(){
        ClipboardManager mClipboardManager = (ClipboardManager) MyApplication.shareApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        if (mClipboardManager == null) {
            return "";
        }

        ClipData clipData = null;
        try {
            clipData = mClipboardManager.getPrimaryClip();
        } catch (Throwable e) {
            // do nothing
            LogUtil.d("ClipboardManager getPrimaryClip error!");
        }

        if (clipData == null) {
            LogUtil.d("clip data is empty, return null.");
            return "";
        }
        int count = clipData.getItemCount();
        for(int i = 0 ; i < count ; i++ ) {
            ClipData.Item item = clipData.getItemAt(i);
            if (item == null || item.getText() == null) {
                continue;
            }
            String content = item.getText().toString();
            if (Build.VERSION.SDK_INT >= 16 && !TextUtils.isEmpty(item.getHtmlText())) {
                content = item.getHtmlText();
            }
            return content;
        }
        return "";
    }

    /**
     * 清空剪切板
     */
    public static void clear() {
        ClipboardManager manager = (ClipboardManager) MyApplication.shareApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setPrimaryClip(ClipData.newPlainText("", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
