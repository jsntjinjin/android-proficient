package com.fastaoe.baselibrary.http;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by jinjin on 17/5/14.
 */

public class HttpUtils {

    private static final int POST_TYPE = 0x01;
    private static final int GET_TYPE = 0x02;

    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    private String mUrl;
    private int mType = GET_TYPE;
    private Context mContext;
    private Map<String, Object> mParams;

    private HttpUtils(Context context) {
        this.mContext = context;
        this.mParams = new ArrayMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils post(String url) {
        this.mType = POST_TYPE;
        this.mUrl = url;
        return this;
    }

    public HttpUtils get(String url) {
        this.mType = GET_TYPE;
        this.mUrl = url;
        return this;
    }

    public HttpUtils addParam(String key, Object value) {
        this.mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }

    public void execute(EngineCallback callback) {

        if (callback == null) {
            callback = EngineCallback.DEFAULT_CALL_BACK;
        }

        callback.onPreExecute(mContext, mParams);

        if (mType == POST_TYPE) {
            post(mUrl, mParams, callback);
        } else if (mType == GET_TYPE) {
            get(mUrl, mParams, callback);
        }
    }

    public void execute() {
        execute(null);
    }

    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    private void get(String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.get(url, params, callback);
    }

    private void post(String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.post(url, params, callback);
    }

    public static Class<?> analysisClazzInfo(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
