package com.fastaoe.baselibrary.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinjin on 17/5/14.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    private static CrashHandler mInstance;

    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        // 获取系统默认的处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 让该处理器作为默认的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // 获取手机相关信息
        collectDeviceInfo(mContext);
        // 保存奔溃信息
        saveCrashInfo2File(e);

        // 让系统默认处理异常
        mDefaultHandler.uncaughtException(t, e);
    }

    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info");
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info");
            }
        }
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        FileOutputStream fos = null;
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new java.util.Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = mContext.getExternalCacheDir();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fos = new FileOutputStream(new File(dir.getPath(), fileName));
                fos.write(sb.toString().getBytes());
            }
            Log.d(TAG, "捕获到异常到 --> " + mContext.getExternalCacheDir().getPath() + File.separator + fileName);
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
