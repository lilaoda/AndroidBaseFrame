package com.liheyu.baseframe.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liheyu.baseframe.R;
import com.liheyu.baseframe.contract.LoginContract;
import com.liheyu.baseframe.data.Injection;
import com.liheyu.baseframe.data.bean.User;
import com.liheyu.baseframe.greendao.DBManager;
import com.liheyu.baseframe.http.ApiService;
import com.liheyu.baseframe.http.HttpManager;
import com.liheyu.baseframe.presenter.LoginPresenter;
import com.liheyu.baseframe.view.BaseActivity;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_password)
    EditText editPassword;

    LoginPresenter mPresenter;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, Injection.provideDataRepository(getApplicationContext()));
       // text.setText("此版本为BUG版");
        text.setText("此版本为BUG修复版");
//        text.setText(getResources().getString(R.string.app_name2));
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch");
     //   Toast.makeText(getApplicationContext(), "出现了BUG", Toast.LENGTH_SHORT).show();
      Toast.makeText(getApplicationContext(), "bug已解决", Toast.LENGTH_SHORT).show();
        //   beginDownload();
//        if (UiUtils.getString(editName).equals("abc")) {
//            Log.e(this.getClass().getName(), "打补丁成功了....");
//            Toast.makeText(this,"打补丁成功了....",Toast.LENGTH_SHORT).show();
//        }
        //  mPresenter.login(UiUtils.getString(editName), UiUtils.getString(editPassword));
        // inserUser();
    }

    private void beginDownload() {
        Observable<ResponseBody> observable = HttpManager.getInstance().getRetrofit().create(ApiService.class).downloadPatch();
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("onSubscribe", "begin.....");
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        Log.e("onNext", "onNext.....");
                        Log.e("onNext", Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "andfixtext222.apatch");
                        streamToFile(value.byteStream(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "andfixtext222.apatch");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "e.....");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "onComplete.....");
                    }
                });
    }

    private void inserUser() {
        final User user = new User();
        user.setPassword("abc");
        user.setUserName("name");

        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {
                long insert = DBManager.getInstance(getApplicationContext()).getDaoSession().getUserDao().insert(user);
                Log.e("insert", "insert" + insert);
                if (insert != 1) {
                    e.onError(new Throwable("数据操作失败"));
                } else {
                    e.onNext(user);
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Log.e("accept", "插入成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("throwable", throwable.getMessage());
                    }
                });

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
    public <R> LifecycleTransformer<R> getLifeTransformer() {
        return bindToLifecycle();
    }

    public void streamToFile(InputStream in, String filePath) {
        if (in == null) {
            return;
        }
        BufferedInputStream bio = new BufferedInputStream(in);
        BufferedOutputStream bos = null;
        File file = new File(filePath);
        try {
            while (!file.exists()) {
                file.createNewFile();
            }

            bos = new BufferedOutputStream(new FileOutputStream(file));
            int len;
            byte[] buffer = new byte[2048];
            while ((len = bio.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                bio.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
