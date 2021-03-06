package com.liheyu.baseframe.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liheyu.baseframe.greendao.gen.DaoMaster;
import com.liheyu.baseframe.greendao.gen.DaoSession;

/**
 * Created by Liheyu on 2017/3/17.
 * Email:liheyu999@163.com
 */

public class DBManager {

    public static final String DB_NAME = "greendaotest.db";

    private static DBManager instance;
    private Context context;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;

    public DBManager(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoSession = daoMaster.newSession();
    }

    public static  DBManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager(context);
                }
            }
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
