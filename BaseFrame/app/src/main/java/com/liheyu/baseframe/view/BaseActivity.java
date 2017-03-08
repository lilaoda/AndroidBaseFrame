package com.liheyu.baseframe.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.liheyu.baseframe.R;
import com.liheyu.baseframe.utils.StatusBarUtil;
import com.liheyu.baseframe.widget.DialogLoading;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by liheyu on 2017/3/1.
 */

public class BaseActivity extends RxAppCompatActivity {

    protected DialogLoading mDialogLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    public void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    protected void showLoadingDialog() {
        if (mDialogLoading == null) {
            mDialogLoading = new DialogLoading(this);
        }
        mDialogLoading.show();
    }

    protected void dismissLoadingDialog() {
        if (mDialogLoading != null) {
            mDialogLoading.dismiss();
        }
    }

}
