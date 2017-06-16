package com.fastaoe.proficient.component.other.hook.plugin;

import android.content.Context;

/**
 * Created by jinjin on 17/6/16.
 * description:
 */

public class PluginManager {
    public static final void install(Context context, String apkPath) {
        // 解决类加载的问题
        try {
            FixDexManager fixDexManager =
                    new FixDexManager(context);
            // 把apk的class加载到ApplicationClassLoader
            fixDexManager.fixDex(apkPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
