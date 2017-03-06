package com.liheyu.baseframe.data;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 */

public class Injection {

    /**
     * context 使用applicationContext
     *
     * @param context
     * @return
     */
    public static DataRepository provideDataRepository(@NonNull Context context) {
        //checkNotNull(context);
        return DataRepository.getInstance(RemoteRepository.getInstance(), LocalRepository.getInstance(context));
    }

}
