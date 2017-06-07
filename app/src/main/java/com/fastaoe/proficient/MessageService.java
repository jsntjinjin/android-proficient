package com.fastaoe.proficient;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.fastaoe.baselibrary.utils.LogUtil;

/**
 * Created by jinjin on 2017/6/6.
 */

public class MessageService extends Service {

    private static final String TAG = "MessageService";
    private static final int MESSAGE_ID = 1;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 连接上
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 断开连接,重新启动，重新绑定
            startService(new Intent(MessageService.this, GuardService.class));
            bindService(new Intent(MessageService.this, GuardService.class), conn, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            while (true) {
                LogUtil.d(TAG, "等待接收消息");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 提高服务的优先级
        //        startForeground(MESSAGE_ID, new Notification());

        // 绑定建立连接
        bindService(new Intent(this, MessageService.class), conn, Context.BIND_IMPORTANT);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }
        };
    }
}
