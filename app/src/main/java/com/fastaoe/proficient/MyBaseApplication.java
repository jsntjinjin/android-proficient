package com.fastaoe.proficient;

import com.fastaoe.baselibrary.base.BaseApplication;
import com.fastaoe.framelibrary.skin.SkinManager;
import com.fastaoe.proficient.component.other.hook.HookStartActivityUtil;
import com.fastaoe.proficient.component.other.hook.ProxyActivity;

import org.xutils.x;

/**
 * Created by jinjin on 17/5/16.
 */

public class MyBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        HookStartActivityUtil hookStartActivityUtil
                = new HookStartActivityUtil(this, ProxyActivity.class);
        try {
            hookStartActivityUtil.hookStartActivity();
            hookStartActivityUtil.hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SkinManager.getInstance().init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        initConfig();

    }

    private void initConfig() {
        sConfigManager.mDebugOn = Constants.DEBUG;

        refreshConfig();
    }
}
