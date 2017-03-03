package com.liheyu.baseframe.view.activity;

import com.liheyu.baseframe.data.DataRepository;
import com.liheyu.baseframe.data.DataSource;
import com.liheyu.baseframe.data.bean.User;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private DataRepository mRepository;

    LoginPresenter(LoginContract.View view, DataRepository repository) {
        mView = view;
        mRepository = repository;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String userName, String password) {
        mRepository.login(userName, password, new DataSource.LoginCallback() {
            @Override
            public void loginSuccess(User user) {
                mView.loginSuccess(user);
            }

            @Override
            public void loginFailed(String s) {
                mView.loginFailure(s);
            }
        });
    }
}
