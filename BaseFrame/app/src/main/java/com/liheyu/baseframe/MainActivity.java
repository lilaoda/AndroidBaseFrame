package com.liheyu.baseframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends AppCompatActivity {
    @Named("car1")
    @Inject
    Car car1;

    @Named("car2")
    @Inject
    Car car2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DaggerCarComponent.builder().build().inject(this);
        DaggerAppComponent.create().inject(this);
    }

    public void onClick(View view) {
        Log.e("bbb", "aaaaaa");
        Log.e("car1", car1.getCarColor());
        Log.e("car2", car2.getCarColor());

    }
}
