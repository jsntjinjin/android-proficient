package com.fastaoe.framelibrary;

import android.content.Context;

import com.fastaoe.baselibrary.http.EngineCallback;
import com.fastaoe.baselibrary.http.HttpUtils;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by jinjin on 17/5/15.
 */

public abstract class HttpCallback<T> implements EngineCallback {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        // 做一些请求前的准备(公用参数设置等等)

        onPreExecute();
    }

    public void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T resObject = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(resObject);
    }

    public abstract void onSuccess(T t);
}
