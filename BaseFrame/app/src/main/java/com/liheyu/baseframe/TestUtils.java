package com.liheyu.baseframe;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alan on 2017/2/27.
 */

@Module
public class TestUtils {

    @Named("car1")
    @Provides
    public Car sayHello() {
        Car car = new Car();
        car.setCarColor("绿色的车");
        return car;
    }

    @Named("car2")
    @Provides
    public Car sayWorld() {
        Car car = new Car();
        car.setCarColor("红色的车");
        return car;
    }
}
