package com.liheyu.baseframe.presenter;

import android.util.Log;

import com.liheyu.baseframe.contract.LoginContract;
import com.liheyu.baseframe.data.DataRepository;
import com.liheyu.baseframe.data.bean.User;
import com.liheyu.baseframe.http.HttpObserver;
import com.liheyu.baseframe.view.activity.LoginActivity;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private DataRepository mRepository;

    public LoginPresenter(LoginContract.View mView, DataRepository mRepository) {
        this.mView = mView;
        this.mRepository = mRepository;
    }

    @Override
    public void login(String userName, String password) {
        mRepository.login(userName, password)
                .compose(mView.<User>getLifeTransformer())
                .subscribe(new HttpObserver<User>((LoginActivity) mView) {
                    @Override
                    public void onSuccess(User value) {
                        Log.e("onsuccess", value.toString());
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("onFailure", e.getMessage());
                        Log.e("1", android.os.Process.myTid() + "");
                       // Log.e("1", BaseApplication.mAPPTid + "");
                    }
                });
    }
}
