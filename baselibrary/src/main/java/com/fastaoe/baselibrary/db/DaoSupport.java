package com.fastaoe.baselibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;

import com.fastaoe.baselibrary.utils.DaoUtil;
import com.fastaoe.baselibrary.utils.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * Created by jinjin on 17/5/15.
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";

    private static final Object[] sPutMethodArgs = new Object[2];
    private static final Map<String, Method> sPutMethods = new ArrayMap<>();

    private SQLiteDatabase mDatabase;
    private Class<T> mClazz;

    @Override
    public void init(SQLiteDatabase database, Class<T> clazz) {
        this.mDatabase = database;
        this.mClazz = clazz;

        // 创建表
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(clazz))
                .append(" (id integer primary key autoincrement, ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int modify = field.getModifiers();
            if (Modifier.isStatic(modify) || Modifier.isTransient(modify)) {
                continue;
            }
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
    public long insert(T o) {
        ContentValues values = getContentValues(o);
        return mDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    @Override
    public long insert(List<T> datas) {
        long count = 0;
        mDatabase.beginTransaction();
        for (T data : datas) {
            long number = insert(data);
            count += number;
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        return count;
    }

    @Override
    public List<T> queryAll() {
//        mDatabase.query()
        return null;
    }

    @Override
    public List<T> query() {
        return null;
    }

    @Override
    public long delete() {
        return 0;
    }

    @Override
    public long update() {
        return 0;
    }

    private ContentValues getContentValues(T o) {
        ContentValues values = new ContentValues();
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                int modify = field.getModifiers();
                if (Modifier.isStatic(modify) || Modifier.isTransient(modify)) {
                    continue;
                }
                sPutMethodArgs[0] = field.getName();
                sPutMethodArgs[1] = field.get(o);

                String fieldTypeName = field.getType().getName();
                Method putMethod = sPutMethods.get(fieldTypeName);
                if (putMethod == null) {
                    putMethod = values.getClass()
                            .getDeclaredMethod("put", String.class, sPutMethodArgs[1].getClass());
                    sPutMethods.put(fieldTypeName, putMethod);
                }
                putMethod.invoke(values, sPutMethodArgs);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sPutMethodArgs[0] = null;
                sPutMethodArgs[1] = null;
            }
        }
        return values;
    }

}
