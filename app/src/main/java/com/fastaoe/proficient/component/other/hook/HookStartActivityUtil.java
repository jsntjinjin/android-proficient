package com.fastaoe.proficient.component.other.hook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jinjin on 17/6/16.
 * description:
 */

public class HookStartActivityUtil {

    private static final String EXTRA_ORIGIN_INTENT = "EXTRA_ORIGIN_INTENT";

    private Context mContext;
    private Class<?> mProxyClazz;

    public HookStartActivityUtil(Context context, Class<?> clazz) {
        this.mContext = context.getApplicationContext();
        this.mProxyClazz = clazz;
    }

    public void hookLaunchActivity() throws Exception {
        // 获取ActivityThread实例
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Field sCurrentActivityThreadField = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
        sCurrentActivityThreadField.setAccessible(true);
        Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);

        // 获取ActivityThread中的mH(handler)
        Field mHandleField = activityThreadClazz.getDeclaredField("mH");
        mHandleField.setAccessible(true);
        Object mHandler = mHandleField.get(sCurrentActivityThread);

        // hook handleLaunchActivity
        // 设置mHandler的callback
        Class<?> handlerClazz = Class.forName("android.os.Handler");
        Field mCallbackField = handlerClazz.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(mHandler,new HandlerCallback());
    }

    private class HandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100) {
                handleLaunchActivity(msg);
            }
            return false;
        }

        /**
         * hook Activity拦截
         * @param msg
         */
        private void handleLaunchActivity(Message msg) {
            try {
                // 从msg中获取ActivityClientRecord
                Object record = msg.obj;
                // 从record中获取Proxy的intent
                Field intentField = record.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent safeIntent = (Intent) intentField.get(record);
                // 从Proxy的safeIntent中获取原来的intent
                Intent originIntent = safeIntent.getParcelableExtra(EXTRA_ORIGIN_INTENT);
                // 重新将intent赋值回去
                if (originIntent!= null) {
                    intentField.set(record, originIntent);
                }

                // 兼容AppCompatActivity报错问题
                Class<?> forName = Class.forName("android.app.ActivityThread");
                Field field = forName.getDeclaredField("sCurrentActivityThread");
                field.setAccessible(true);
                Object activityThread = field.get(null);
                // 我自己执行一次那么就会创建PackageManager，系统再获取的时候就是下面的iPackageManager
                Method getPackageManager = activityThread.getClass().getDeclaredMethod("getPackageManager");
                Object iPackageManager = getPackageManager.invoke(activityThread);

                PackageManagerHandler handler = new PackageManagerHandler(iPackageManager);
                Class<?> iPackageManagerIntercept = Class.forName("android.content.pm.IPackageManager");
                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{iPackageManagerIntercept}, handler);

                // 获取 sPackageManager 属性
                Field iPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
                iPackageManagerField.setAccessible(true);
                iPackageManagerField.set(activityThread, proxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class PackageManagerHandler implements InvocationHandler {
        private Object mActivityManagerObject;

        public PackageManagerHandler(Object iActivityManagerObject) {
            this.mActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Log.e("TAG", "methodName = " + method.getName());
            if (method.getName().startsWith("getActivityInfo")) {
                ComponentName componentName = new ComponentName(mContext, mProxyClazz);
                args[0] = componentName;
            }
            return method.invoke(mActivityManagerObject, args);
        }
    }

    public void hookStartActivity() throws Exception {
        // 获取ActivityManagerNative中的gDefault
        Class<?> amnClazz = Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField = amnClazz.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);

        // 获取gDefault中的mInstance属性
        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iamInstance = mInstanceField.get(gDefault);

        Class<?> iamClazz = Class.forName("android.app.IActivityManager");
        iamInstance = Proxy.newProxyInstance(
                HookStartActivityUtil.class.getClassLoader(),
                new Class[]{iamClazz},
                new StartActivityInvocationHandler(iamInstance));

        // 重新指定
        mInstanceField.set(gDefault, iamInstance);
    }

    private class StartActivityInvocationHandler implements InvocationHandler {

        private Object mObject;

        public StartActivityInvocationHandler(Object object) {
            this.mObject = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // LogUtil.d("HookStartActivityUtil", method.getName());
            if (method.getName().equals("startActivity")) {
                // 获取原来的intent
                Intent intent = (Intent) args[2];
                // 替换代理的intent
                Intent safeIntent = new Intent(mContext, mProxyClazz);
                args[2] = safeIntent;

                // 绑定原来的intent
                safeIntent.putExtra(EXTRA_ORIGIN_INTENT, intent);
            }
            return method.invoke(mObject, args);
        }
    }
}
