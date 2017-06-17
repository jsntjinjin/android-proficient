package com.fastaoe.proficient.weight.notification;

import android.app.Notification;
import android.content.Context;
import android.widget.RemoteViews;

import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/6/17.
 * description:
 */

public class NotifyBig extends Notify {

    public NotifyBig(Context context, Class<?> clazz) {
        super(context, clazz);
    }

    @Override
    public void send() {
        Notification notification = builder.build();
        notification.contentView = new RemoteViews(context.getPackageName(),
                R.layout.remote_notify_proxy_normal);
        notification.bigContentView = new RemoteViews(context.getPackageName(),
                R.layout.remote_notify_proxy_big);
        nm.notify(0, notification);
    }

    @Override
    public void cancel() {
        nm.cancel(0);
    }
}
