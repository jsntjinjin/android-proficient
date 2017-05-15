package com.fastaoe.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by jinjin on 17/5/14.
 */

public interface EngineCallback {

    void onPreExecute(Context context, Map<String, Object> params);

    void onSuccess(String result);

    void onError(Exception e);

    EngineCallback DEFAULT_CALL_BACK = new EngineCallback() {

        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onError(Exception e) {

        }
    };
}
