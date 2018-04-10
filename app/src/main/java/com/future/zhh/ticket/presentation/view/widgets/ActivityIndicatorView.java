package com.future.zhh.ticket.presentation.view.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.future.zhh.ticket.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ActivityIndicatorView {

    private static Dialog mDialog;
    public Context context;
    private String message;

    public ActivityIndicatorView(Context context) {
        this.context = context;
        message = context.getResources().getString(R.string.loading);
    }

    public ActivityIndicatorView(Context context, String message){
        this.context = context;
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void show() {
        if (mDialog != null && mDialog.isShowing()) return;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, R.style.full_screen_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_indicator_view, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.id_text);
        tvMessage.setText(message);
        mBuilder.setView(view);
        mDialog = mBuilder.create();
        mDialog.setCancelable(false);
        Window win = mDialog.getWindow();
        //获取对话框当前的参数值
        WindowManager.LayoutParams params = win.getAttributes();
        params.alpha = 0.5f;
        params.dimAmount = 0.1f;
        params.gravity = Gravity.CENTER;
        win.setAttributes(params);
        win.setBackgroundDrawableResource(R.mipmap.alpha);
        if (!((Activity) context).isFinishing())
            mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
