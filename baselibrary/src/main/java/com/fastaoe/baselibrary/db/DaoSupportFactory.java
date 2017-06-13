package com.fastaoe.baselibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.fastaoe.baselibrary.base.BaseApplication;

import java.io.File;

/**
 * Created by jinjin on 17/5/15.
 */

public class DaoSupportFactory {

    private SQLiteDatabase database;
    private static DaoSupportFactory mFactory;

    private DaoSupportFactory(Context context) {
        // 将数据库放到内存卡中
        // TODO: 17/5/15 1.判断是否有存储卡 2.6.0动态申请存储卡权限
        File filePath = null;
        if (!DBConfig.SAVE_TO_CACHE && Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + DBConfig.DB_DIR);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
        } else {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
                filePath = context.getExternalCacheDir();
            } else {
                filePath = context.getCacheDir();
            }
        }

        File dbFile = new File(filePath, DBConfig.DB_NAME);
        // 持有外部数据库引用
        // 创建或者打开数据库
        database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DaoSupportFactory getFactory(Context context) {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory(context);
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport();
        daoSupport.init(database, clazz);
        return daoSupport;
    }
}
