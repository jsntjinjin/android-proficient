package com.fastaoe.baselibrary.ioc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fastaoe.baselibrary.utils.NetStateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jinjin on 17/5/13.
 */

public class ViewInjectorImpl implements ViewInject {

    private ViewInjectorImpl() {
    }

    public static ViewInjectorImpl mInstance;

    public static ViewInjectorImpl getInstance() {
        if (mInstance == null) {
            synchronized (ViewInjectorImpl.class) {
                if (mInstance == null) {
                    mInstance = new ViewInjectorImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void inject(View view) {
        injectObject(view, view.getClass(), new ViewFinder(view));
    }

    @Override
    public void inject(Object handler, View view) {
        injectObject(handler, view.getClass(), new ViewFinder(view));
    }

    @Override
    public void inject(Activity activity) {
        Class<?> clazz = activity.getClass();
        // activity设置布局
        try {
            ContentView contentView = findContentView(clazz);
            if (contentView != null) {
                int layoutId = contentView.value();
                if (layoutId > 0) {
                    Method setContentView = clazz.getMethod("setContentView", int.class);
                    setContentView.invoke(activity, layoutId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        injectObject(activity, clazz, new ViewFinder(activity));
    }

    @Override
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        Class<?> clazz = fragment.getClass();
        // fragment设置布局
        View view = null;
        ContentView contentView = findContentView(clazz);
        if (contentView != null) {
            int layoutId = contentView.value();
            if (layoutId > 0) {
                view = inflater.inflate(layoutId, container, false);
            }
        }
        injectObject(fragment, clazz, new ViewFinder(view));
        return view;
    }

    /**
     * 从类中获取ContentView注解
     *
     * @param clazz
     * @return
     */
    private static ContentView findContentView(Class<?> clazz) {
        return clazz != null ? clazz.getAnnotation(ContentView.class) : null;
    }

    public static void injectObject(Object handler, Class<?> clazz, ViewFinder finder) {
        try {
            injectView(handler, clazz, finder);
            injectEvent(handler, clazz, finder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置findViewById
     *
     * @param handler
     * @param clazz
     * @param finder
     */
    private static void injectView(Object handler, Class<?> clazz, ViewFinder finder) throws Exception {
        // 获取class的所有属性
        Field[] fields = clazz.getDeclaredFields();

        // 遍历并找到所有的ViewById注解的属性
        for (Field field : fields) {
            Bind viewById = field.getAnnotation(Bind.class);
            if (viewById != null) {
                // 获取ViewById的值
                int viewId = viewById.value();
                if (viewId > 0) {
                    // 获取View
                    View view = finder.findViewById(viewId);
                    if (view != null) {
                        // 反射注入view
                        field.setAccessible(true);
                        field.set(handler, view);
                    } else {
                        throw new Exception("Invalid @Bind for "
                                + clazz.getSimpleName() + "." + field.getName());
                    }
                }
            }

        }
    }

    /**
     * 设置Event
     *
     * @param handler
     * @param clazz
     * @param finder
     */
    private static void injectEvent(Object handler, Class<?> clazz, ViewFinder finder) {
        // 获取class所有的方法
        Method[] methods = clazz.getDeclaredMethods();

        // 遍历找到onClick注解的方法
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            boolean CheckNet = method.getAnnotation(CheckNet.class) != null;
            if (onClick != null) {
                // 获取注解中的value值
                int[] views = onClick.value();
                if (views.length > 0) {
                    for (int viewId : views) {
                        // findViewById找到View
                        View view = finder.findViewById(viewId);
                        if (view != null) {
                            // 设置setOnClickListener反射注入方法
                            view.setOnClickListener(new MyOnClickListener(method, handler, CheckNet));
                        }
                    }
                }
            }
        }
    }

    private static class MyOnClickListener implements View.OnClickListener {
        private Method method;
        private Object handler;
        private boolean checkNet;

        public MyOnClickListener(Method method, Object handler, boolean checkNet) {
            this.method = method;
            this.handler = handler;
            this.checkNet = checkNet;
        }

        @Override
        public void onClick(View v) {
            if (checkNet && !NetStateUtil.isNetworkConnected(v.getContext())) {
                Toast.makeText(v.getContext(), "网络错误!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 注入方法
            try {
                method.setAccessible(true);
                method.invoke(handler, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
