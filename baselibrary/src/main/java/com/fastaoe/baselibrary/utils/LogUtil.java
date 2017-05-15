package com.fastaoe.baselibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import com.fastaoe.baselibrary.base.BaseApplication;

/**
 * Created by jinjin on 17/5/15.
 */

public class LogUtil {

    private static final int MAX_LENGTH = 500;
    private static final String EMPTY = "---empty log---";

    public static void i(String tag, String msg) {
        if (BaseApplication.DEBUG) {
            if (TextUtils.isEmpty(msg)) {
                msg = EMPTY;
            }
            while (msg.length() > MAX_LENGTH) {
                Log.i(tag, msg.substring(0, MAX_LENGTH));
                msg = msg.substring(MAX_LENGTH);
            }
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (BaseApplication.DEBUG) {
            if (TextUtils.isEmpty(msg)) {
                msg = EMPTY;
            }
            while (msg.length() > MAX_LENGTH) {
                Log.v(tag, msg.substring(0, MAX_LENGTH));
                msg = msg.substring(MAX_LENGTH);
            }
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BaseApplication.DEBUG) {
            if (TextUtils.isEmpty(msg)) {
                msg = EMPTY;
            }
            while (msg.length() > MAX_LENGTH) {
                Log.d(tag, msg.substring(0, MAX_LENGTH));
                msg = msg.substring(MAX_LENGTH);
            }
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BaseApplication.DEBUG) {
            if (TextUtils.isEmpty(msg)) {
                msg = EMPTY;
            }
            while (msg.length() > MAX_LENGTH) {
                Log.e(tag, msg.substring(0, MAX_LENGTH));
                msg = msg.substring(MAX_LENGTH);
            }
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BaseApplication.DEBUG) {
            if (TextUtils.isEmpty(msg)) {
                msg = EMPTY;
            }
            while (msg.length() > MAX_LENGTH) {
                Log.w(tag, msg.substring(0, MAX_LENGTH));
                msg = msg.substring(MAX_LENGTH);
            }
            Log.w(tag, msg);
        }
    }
}