package com.fastaoe.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by jinjin on 17/5/15.
 */

public class DaoSupportFactory {

    private SQLiteDatabase database;
    private static DaoSupportFactory mFactory;

    private DaoSupportFactory() {
        // 将数据库放到内存卡中
        // TODO: 17/5/15 1.判断是否有存储卡 2.6.0动态申请存储卡权限
        File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "haha" + File.separator + "database");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File dbFile = new File(filePath, "haha.db");
        // 持有外部数据库引用
        // 创建或者打开数据库
        database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DaoSupportFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory();
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
