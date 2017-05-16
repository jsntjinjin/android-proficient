package com.fastaoe.proficient;

import com.fastaoe.baselibrary.base.BaseApplication;

/**
 * Created by jinjin on 17/5/16.
 */

public class MyBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initConfig();

    }

    private void initConfig() {
        sConfigManager.mDebugOn = Constants.DEBUG;

        refreshConfig();
    }
}
