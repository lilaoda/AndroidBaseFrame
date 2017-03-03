package com.liheyu.baseframe.data;

import android.content.Context;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 */

public class LocalRepository implements DataSource {

    private static LocalRepository instance = null;

    private LocalRepository(Context context) {
        //用context操作数据库获取数据 不能直接提取为成员变量 会造成对context的引用而引起内存泄露
        //使用context....
    }

    public static LocalRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LocalRepository(context);
        }
        return instance;
    }
}
