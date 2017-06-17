package com.fastaoe.proficient.weight.notification;

import android.content.Context;
import android.os.Build;

import static android.R.string.no;

/**
 * Created by jinjin on 2017/6/17.
 * description:
 */

public class NotifyProxy extends Notify {

    private Notify notification;

    public NotifyProxy(Context context, Class<?> clazz) {
        super(context, clazz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification = new NotifyHeadsUp(context,clazz);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            notification = new NotifyBig(context,clazz);
        } else {
            notification = new NotifyNormal(context,clazz);
        }
    }

    @Override
    public void send() {
        notification.send();
    }

    @Override
    public void cancel() {
        notification.cancel();
    }
}
