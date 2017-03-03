package com.liheyu.baseframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lilaoda on 2016/10/24.
 */
public class SPUtils {

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("wqdconfig", Context.MODE_PRIVATE);
    }

    public static boolean putString(Context context, String key, String value) {
        boolean commit = getSP(context).edit().putString(key, value).commit();
        return commit;
    }

    public static String getString(Context context, String key) {
        return getSP(context).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return getSP(context).getInt(key, 0);
    }

    public static boolean putInt(Context context, String key, int value) {
        return getSP(context).edit().putInt(key, value).commit();
    }


    public static boolean getBoolean(Context context, String key) {
        return getSP(context).getBoolean(key, false);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        return getSP(context).edit().putBoolean(key, value).commit();
    }


}
