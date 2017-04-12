package com.liheyu.baseframe.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Liheyu on 2017/3/6.
 * Email:liheyu999@163.com
 */

public abstract class HttpObserver<T> implements Observer<T> {

    private Activity actvity;
    private ProgressDialog progressDialog;
    private Disposable mDisposable;
    private boolean isUserCancel = false;

    public HttpObserver(Activity activity) {
        this.actvity = activity;
    }

    public HttpObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        showDialog();
    }

    @Override
    public void onNext(T value) {
        hideDialog();
        onSuccess(value);
    }

    @Override
    public void onError(final Throwable e) {
        hideDialog();

        Log.e("yes'", e.getClass().getName());
        String errorMsg;
        if (e instanceof IOException) {
            errorMsg = "网络错误";
        } else if (e instanceof HttpException) {
            errorMsg = "网络错误";
        } else if (e instanceof TimeoutException) {
            errorMsg = "连接超时，请稍候再试";
        } else if (e instanceof ApiException) {
            errorMsg = e.getMessage();
        } else {
            errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : "未知错误";
        }

        //如果用户主动取消 则不提示任何信息
        if (!isUserCancel) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
               // Toast.makeText(BaseApplication.getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            isUserCancel = false;
        }
        onFailure(e);
    }

    @Override
    public void onComplete() {
        hideDialog();
    }

    private void showDialog() {

        //如果未传activity就不显示对话框
        if (actvity == null) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(actvity);
            //取消这块 待测试
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //用户主动取消时调用
                    Log.e("cancel","cancel");
                    isUserCancel = true;
                    mDisposable.dispose();
                }
            });
        }
        progressDialog.show();
    }

    private void hideDialog() {
        if (actvity == null) {
            return;
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public abstract void onSuccess(T value);

    public abstract void onFailure(Throwable e);
}
