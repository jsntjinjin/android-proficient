package com.fastaoe.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fastaoe.baselibrary.utils.DaoUtil;
import com.fastaoe.baselibrary.utils.LogUtil;

import java.lang.reflect.Field;

/**
 * Created by jinjin on 17/5/15.
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";

    private SQLiteDatabase mDatabase;
    private Class<T> mClazz;

    @Override
    public void init(SQLiteDatabase database, Class<T> clazz) {
        this.mDatabase = database;
        this.mClazz = clazz;

        // 创建表
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ")
                .append(DaoUtil.getClassName(clazz))
                .append(" (id integer primary key autoincrement, ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getSimpleName();
            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ")");

        String createTableSql = sb.toString();

        LogUtil.d(TAG, "table sql --> " + createTableSql);

        mDatabase.execSQL(createTableSql);

    }

    @Override
    public int insert(T o) {


        return 0;
    }

}
