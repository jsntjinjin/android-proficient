package com.fastaoe.framelibrary.http;

import android.content.Context;
import android.util.Log;

import com.fastaoe.baselibrary.base.BaseApplication;
import com.fastaoe.baselibrary.db.DaoSupportFactory;
import com.fastaoe.baselibrary.db.IDaoSupport;
import com.fastaoe.framelibrary.MD5Util;

import java.util.List;

/**
 * Created by jinjin on 17/6/13.
 * description:
 */

public class CacheDataUtil {
    /**
     * 获取数据
     */
    public static String getCacheResultJson(String finalUrl) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory(BaseApplication.sContext).getDao(CacheData.class);
        // 需要缓存，从数据库拿缓存，问题又来了，OkHttpEngine  BaseLibrary
        // 数据库缓存在 FrameLibrary
        List<CacheData> cacheDatas = dataDaoSupport.querySupport()
                // finalUrl http:w 报错  finalUrl -> MD5处理
                .selection("mUrlKey = ?").selectionArgs(MD5Util.md5Encode(finalUrl)).query();

        if (cacheDatas.size() != 0) {
            // 代表有数据
            CacheData cacheData = cacheDatas.get(0);
            String resultJson = cacheData.getResultJson();

            return resultJson;
        }
        return null;
    }

    /**
     * 缓存数据
     */
    public static long cacheData(String finalUrl, String resultJson) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory(BaseApplication.sContext).
                getDao(CacheData.class);
        dataDaoSupport.delete("mUrlKey=?", MD5Util.md5Encode(finalUrl));
        long number = dataDaoSupport.insert(new CacheData(MD5Util.md5Encode(finalUrl), resultJson));
        Log.e("TAG", "number --> " + number);
        return number;
    }
}
