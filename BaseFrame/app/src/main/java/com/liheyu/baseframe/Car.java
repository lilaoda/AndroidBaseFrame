package com.liheyu.baseframe;

import javax.inject.Inject;

/**
 * Created by Alan on 2017/2/27.
 */


public class Car {

    private String  carColor;

    @Inject
    public Car() {
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
}
