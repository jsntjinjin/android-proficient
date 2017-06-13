package com.fastaoe.framelibrary.http;

/**
 * Created by jinjin on 17/6/13.
 * description:
 */

public class CacheData {
    // 请求链接
    private String mUrlKey;

    // 后台返回的Json
    private String mResultJson;

    public CacheData() {

    }

    public CacheData(String urlKey, String resultJson) {
        this.mUrlKey = urlKey;
        this.mResultJson = resultJson;
    }

    public String getResultJson() {
        return mResultJson;
    }
}
