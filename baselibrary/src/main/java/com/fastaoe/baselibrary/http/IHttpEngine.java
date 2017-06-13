package com.fastaoe.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by jinjin on 17/5/14.
 */

public interface IHttpEngine {

    /**
     * get
     */
    void get(boolean cache, Context context, String url, Map<String, Object> params, EngineCallback callback);

    /**
     * post
     */
    void post(boolean cache, Context context, String url, Map<String, Object> params, EngineCallback callback);

    /**
     * download file
     */

    /**
     * upload file
     */

    /**
     * https add certificate
     */
}
