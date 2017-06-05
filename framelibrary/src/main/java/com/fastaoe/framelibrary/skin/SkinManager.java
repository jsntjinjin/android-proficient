package com.fastaoe.framelibrary.skin;

import android.app.Activity;
import android.content.Context;

import com.fastaoe.framelibrary.skin.attr.SkinView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jinjin on 2017/6/4.
 */

public class SkinManager {


    private static SkinManager mInstance;
    private Context mContext;
    private Map<Activity, List<SkinView>> mSkinViews = new HashMap<>();
    private SkinResource mSkinResource;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {
        // TODO: 2017/6/4 校验签名

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);

        Set<Activity> activities = mSkinViews.keySet();
        for (Activity activity : activities) {
            List<SkinView> skinViews = mSkinViews.get(activity);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }

        return 0;
    }

    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        return 0;
    }

    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    public void register(Activity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity, skinViews);
    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
