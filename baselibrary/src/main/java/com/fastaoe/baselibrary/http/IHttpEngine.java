package com.fastaoe.baselibrary.http;

import java.util.Map;

/**
 * Created by jinjin on 17/5/14.
 */

public interface IHttpEngine {

    /**
     * get
     */
    void get(String url, Map<String, Object> params, EngineCallback callback);

    /**
     * post
     */
    void post(String url, Map<String, Object> params, EngineCallback callback);

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
