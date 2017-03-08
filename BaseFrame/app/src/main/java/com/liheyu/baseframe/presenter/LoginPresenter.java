package com.liheyu.baseframe.presenter;

import android.util.Log;

import com.liheyu.baseframe.contract.LoginContract;
import com.liheyu.baseframe.data.DataRepository;
import com.liheyu.baseframe.data.bean.User;
import com.liheyu.baseframe.http.HttpObserver;
import com.liheyu.baseframe.view.activity.LoginActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginActivity mView;
    private DataRepository mRepository;

    public LoginPresenter(LoginActivity view, DataRepository repository) {
        mView = view;
        mRepository = repository;
        view.setPresenter(this);
    }

    @Override
    public void login(String userName, String password) {
        mRepository.login(userName, password).delay(5, TimeUnit.SECONDS)
                .compose(mView.<User>getLifeTransformer())
                .subscribe(new HttpObserver<User>(mView) {
                    @Override
                    public void onSuccess(User value) {
                        Log.e("onsuccess", value.toString());
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("onFailure", e.getMessage());
                    }
                });
    }
}
