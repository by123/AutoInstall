package com.scrat.mm2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class TipDialogSingleButton extends Dialog {
    private Button yes;//确定按钮
    private TextView messageTv;//消息提示文本
    private TextView titleTv;
    private String messageStr;//从外界设置的消息文本
    private String titleStr;
    //确定文本和取消文本的显示内容
    private String yesStr;
    private int btTextColor = Color.parseColor("#3296FA");

    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器


    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public TipDialogSingleButton setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
        return this;
    }

    public TipDialogSingleButton(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_button_dialog_tip);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                WindowManager.LayoutParams lp = TipDialogSingleButton.this.getWindow().getAttributes();
                lp.dimAmount = 0.5f;
                TipDialogSingleButton.this.getWindow().setAttributes(lp);
                TipDialogSingleButton.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp = TipDialogSingleButton.this.getWindow().getAttributes();
                lp.dimAmount = 0.0f;
                TipDialogSingleButton.this.getWindow().setAttributes(lp);
                TipDialogSingleButton.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了message
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        yes.setTextColor(btTextColor);
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        messageTv = (TextView) findViewById(R.id.message);
        titleTv = (TextView) findViewById(R.id.title);
    }


    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public TipDialogSingleButton setMessage(String message) {
        messageStr = message;
        return this;
    }
    
    public TipDialogSingleButton setTitle(String title) {
        titleStr = title;
        return this;
    }

    public TipDialogSingleButton setYesString(String str) {
        yesStr = str;
        return this;
    }
    
    public TipDialogSingleButton setBtTextColor(int btTextColor){
        this.btTextColor = btTextColor;
        return this;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }
}
