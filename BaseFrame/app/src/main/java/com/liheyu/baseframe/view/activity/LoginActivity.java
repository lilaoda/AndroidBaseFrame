package com.liheyu.baseframe.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.liheyu.baseframe.R;
import com.liheyu.baseframe.contract.LoginContract;
import com.liheyu.baseframe.data.Injection;
import com.liheyu.baseframe.data.bean.User;
import com.liheyu.baseframe.presenter.LoginPresenter;
import com.liheyu.baseframe.utils.UiUtils;
import com.liheyu.baseframe.view.BaseActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_password)
    EditText editPassword;

    LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, Injection.provideDataRepository(getApplicationContext()));
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        mPresenter.login(UiUtils.getString(editName), UiUtils.getString(editPassword));
    }


    @Override
    public void loginSuccess(User user) {

    }

    @Override
    public void loginFailure(String string) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public <R>LifecycleTransformer<R> getLifeTransformer() {
        return bindToLifecycle();
    }
}
