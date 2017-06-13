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

    private static final int POST_TYPE = 0x0001;
    private static final int GET_TYPE = 0x0002;

    private static IHttpEngine mHttpEngine = null;

    private String mUrl;

    private int mType = GET_TYPE;

    private Context mContext;

    private Map<String, Object> mParams;

    // 是否读取缓存
    private boolean mCache = false;

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

    // 是否配置缓存
    public HttpUtils cache(boolean isCache) {
        mCache = isCache;
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

    /**
     * 初始化引擎
     *
     * @param httpEngine
     */
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    /**
     * 每次请求可以自己切换引擎
     *
     * @param httpEngine
     */
    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    private void get(String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.get(mCache, mContext, url, params, callback);
    }

    private void post(String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.post(mCache, mContext, url, params, callback);
    }

    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
