package com.liheyu.baseframe;

import android.util.Log;

import javax.inject.Inject;

import dagger.Module;

/**
 * Created by Alan on 2017/2/27.
 */
@Module
public class Engine {

    @Inject
    public Engine() {
    }

    public void start(){
        Log.e("Engine","engine start run....");
    }
}
