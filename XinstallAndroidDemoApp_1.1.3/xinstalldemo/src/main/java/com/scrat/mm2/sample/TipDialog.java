package com.scrat.mm2.sample;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.scrat.mm2.R;


public class TipDialog extends Dialog {
    private Button yes;//确定按钮
    private Button calcel;//取消按钮
    private View btDivider;//按钮间的分割线
    private TextView messageTv;//消息提示文本
    private TextView titleTv;
    private String messageStr;//从外界设置的消息文本
    private String titleStr;
    //确定文本和取消文本的显示内容
    private String yesStr;
    private String calcelStr;
    private int calcelBtVisible;
    private int btTextColor = Color.parseColor("#3296FA");

    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器


    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public TipDialog setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
        return this;
    }

    public TipDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
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
                WindowManager.LayoutParams lp = TipDialog.this.getWindow().getAttributes();
                lp.dimAmount = 0.5f;
                TipDialog.this.getWindow().setAttributes(lp);
                TipDialog.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp = TipDialog.this.getWindow().getAttributes();
                lp.dimAmount = 0.0f;
                TipDialog.this.getWindow().setAttributes(lp);
                TipDialog.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
        calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
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
        if (calcelStr != null) {
            calcel.setText(calcelStr);
        }
        calcel.setVisibility(calcelBtVisible);
        btDivider.setVisibility(calcelBtVisible);
        
        yes.setTextColor(btTextColor);
        calcel.setTextColor(btTextColor);
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        calcel = (Button) findViewById(R.id.calcel);
        btDivider = (View) findViewById(R.id.btDivider);
        messageTv = (TextView) findViewById(R.id.message);
        titleTv = (TextView) findViewById(R.id.title);
    }


    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public TipDialog setMessage(String message) {
        messageStr = message;
        return this;
    }
    
    public TipDialog setTitle(String title) {
        titleStr = title;
        return this;
    }

    public TipDialog setYesString(String str) {
        yesStr = str;
        return this;
    }
    
    public TipDialog setNoString(String str) {
        calcelStr = str;
        return this;
    }
    
    public TipDialog setBtTextColor(int btTextColor){
        this.btTextColor = btTextColor;
        return this;
    }

    /**
     * View.GONE or View.VISIBLE
     * @param calcelBtVisible
     * @return
     */
    public TipDialog setCalcelBtVisible(int calcelBtVisible){
        if (calcelBtVisible == View.INVISIBLE) {
            calcelBtVisible = View.GONE;
        }
        this.calcelBtVisible = calcelBtVisible;
        return this;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }
}
