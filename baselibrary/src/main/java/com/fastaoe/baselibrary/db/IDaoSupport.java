package com.fastaoe.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by jinjin on 17/5/15.
 */

public interface IDaoSupport<T> {

    void init(SQLiteDatabase mdatabase, Class<T> clazz);

    long insert(T t);

    long insert(List<T> datas);

    List<T> queryAll();

    List<T> query();

    long delete();

    long update();
}
