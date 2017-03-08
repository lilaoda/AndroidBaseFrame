package com.liheyu.baseframe.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class ActivityUtils {

    /**
     * add fragment toactivity
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
