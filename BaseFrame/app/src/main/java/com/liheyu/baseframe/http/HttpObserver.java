package com.liheyu.baseframe.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.net.SocketException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liheyu on 2017/3/6.
 * Email:liheyu999@163.com
 */

public abstract class HttpObserver<T> implements Observer<T> {

    private Context context;
    private ProgressDialog progressDialog;

    public HttpObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        showDialog();
    }

    @Override
    public void onNext(T value) {
        hideDialog();
        onSuccess(value);
    }

    @Override
    public void onError(Throwable e) {
        hideDialog();
        if (e instanceof AppException) {

            //TODO 此处会出错，没有loop
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketException) {
            Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
        }
        onFailure(e);
    }

    @Override
    public void onComplete() {
        hideDialog();
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public abstract void onSuccess(T value);

    public abstract void onFailure(Throwable e);
}
