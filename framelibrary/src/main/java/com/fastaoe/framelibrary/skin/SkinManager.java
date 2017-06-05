package com.fastaoe.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.fastaoe.framelibrary.skin.attr.SkinView;
import com.fastaoe.framelibrary.skin.callback.ISkinChangeListener;
import com.fastaoe.framelibrary.skin.config.SkinConfig;
import com.fastaoe.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.id.list;

/**
 * Created by jinjin on 2017/6/4.
 */

public class SkinManager {


    private static SkinManager mInstance;
    private Context mContext;
    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new HashMap<>();
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

        // 判断是否有皮肤
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        File file = new File(currentSkinPath);
        if (!file.exists()) {
            SkinPreUtils.getInstance(mContext).clearSkinInfo();
            return;
        }

        // 判断是否是apk包
        String currentSkinPackageName = mContext.getPackageManager()
                .getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(currentSkinPackageName)) {
            SkinPreUtils.getInstance(mContext).clearSkinInfo();
            return;
        }

        // TODO: 17/6/5  校验签名

        mSkinResource = new SkinResource(mContext, currentSkinPath);
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {

        // 判断文件是否存在
        File file = new File(skinPath);
        if (!file.exists()) {
            return SkinConfig.SKIN_STATE_FILE_NOEXSIST;
        }

        // 判断是否是apk包
        String currentSkinPackageName = mContext.getPackageManager()
                .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(currentSkinPackageName)) {
            return SkinConfig.SKIN_STATE_FILE_ERROR;
        }

        // 判断现在加载的皮肤是否已经加载过
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (skinPath.equals(currentSkinPath)) {
            return SkinConfig.SKIN_STATE_NO_CHANGE;
        }

        // TODO: 2017/6/4 校验签名

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);

        changeSkin();

        // 保存皮肤状态
        saveSkinState(skinPath);

        return SkinConfig.SKIN_STATE_SUCCESS;
    }

    private void changeSkin() {
        Set<ISkinChangeListener> skinChangeListeners = mSkinViews.keySet();
        for (ISkinChangeListener skinChangeListener : skinChangeListeners) {
            List<SkinView> skinViews = mSkinViews.get(skinChangeListener);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }

            skinChangeListener.changeSkin(mSkinResource);
        }
    }

    private void saveSkinState(String path) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(path);
    }

    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        // 判断当前有没有皮肤
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath)) {
            return SkinConfig.SKIN_STATE_NO_CHANGE;
        }

        String skinPath = mContext.getPackageResourcePath();
        mSkinResource = new SkinResource(mContext, skinPath);

        changeSkin();

        SkinPreUtils.getInstance(mContext).clearSkinInfo();

        return SkinConfig.SKIN_STATE_SUCCESS;
    }

    public List<SkinView> getSkinViews(ISkinChangeListener skinChangeListener) {
        return mSkinViews.get(skinChangeListener);
    }

    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    public void checkChangeSkin(SkinView skinView) {
        // 如果保存了皮肤，则换肤
        if (!TextUtils.isEmpty(SkinPreUtils.getInstance(mContext).getSkinPath())) {
            skinView.skin();
        }
    }
}
