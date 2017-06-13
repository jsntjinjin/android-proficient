package com.fastaoe.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;

import com.fastaoe.baselibrary.db.crud.QuerySupport;

import java.util.List;

/**
 * Created by jinjin on 17/5/15.
 */

public interface IDaoSupport<T> {

    void init(SQLiteDatabase mdatabase, Class<T> clazz);

    long insert(T t);

    long insert(List<T> datas);

    // 获取专门查询的支持类
    QuerySupport<T> querySupport();

    int delete(String whereClause, String... whereArgs);

    int update(T obj, String whereClause, String... whereArgs);
}
