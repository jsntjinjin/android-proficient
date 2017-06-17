package com.fastaoe.proficient.weight.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/6/17.
 * description:
 */

public abstract class Notify {

    protected Context context;
    protected final NotificationManager nm;
    protected final NotificationCompat.Builder builder;

    public Notify(Context context, Class<?> clazz) {
        this.context = context;

        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, clazz),
                        PendingIntent.FLAG_UPDATE_CURRENT));
    }

    // 发送一条通知
    public abstract void send();

    // 取消一条通知
    public abstract void cancel();
}
