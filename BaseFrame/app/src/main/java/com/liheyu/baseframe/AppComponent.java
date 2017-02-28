package com.liheyu.baseframe;

import dagger.Component;

/**
 * Created by Alan on 2017/2/27.
 */

@Component(modules = {TestUtils.class,Engine.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
