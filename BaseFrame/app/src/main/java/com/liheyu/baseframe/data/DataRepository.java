package com.liheyu.baseframe.data;

import com.liheyu.baseframe.data.bean.User;

import io.reactivex.Observable;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 *
 * 统一管理各种数据请求
 */

public class DataRepository {

    private static DataRepository INSTANCE = null;

    private RemoteRepository mRemoteRepository;

    private LocalRepository mLocalRepository;

    private DataRepository(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.mLocalRepository = localRepository;
        this.mRemoteRepository = remoteRepository;
    }

    public static DataRepository getInstance(RemoteRepository remoteRepository,
                                             LocalRepository localRepository) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(remoteRepository, localRepository);
        }
        return INSTANCE;
    }

    /*==================================================以下为远程数据操作=================================*/

    public Observable<User> login(String userNmae, String password) {
        return mRemoteRepository.login(userNmae, password);
    }
}
