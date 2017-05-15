package com.fastaoe.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jinjin on 17/5/15.
 */

public interface IDaoSupport<T> {

    void init(SQLiteDatabase mdatabase, Class<T> clazz);

    int insert(T t);

}
