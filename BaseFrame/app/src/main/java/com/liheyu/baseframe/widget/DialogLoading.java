package com.liheyu.baseframe.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.liheyu.baseframe.R;

/**
 * Created by lilaoda on 2016/10/8.
 * <p>
 * 正在加载的对话框
 */
public class DialogLoading extends AlertDialog {


    public DialogLoading(Context context) {
        super(context, R.style.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.7F;
//        params.gravity = Gravity.CENTER;
//        WindowManager windowManager = window.getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        params.width = display.getWidth();
//        params.height = display.getHeight();
        window.setAttributes(params);
    }
}
