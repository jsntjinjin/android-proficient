package com.fastaoe.proficient.guardService;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.List;

/**
 * Created by jinjin on 2017/6/6.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    private static final int JOB_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        JobInfo.Builder jobBuilder =
                new JobInfo.Builder(JOB_ID, new ComponentName(this, JobWakeUpService.class));
        jobBuilder.setPeriodic(2000);

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        // 开启定时任务，定时轮询，查看MessageService有没有被杀死
        // 如果杀死，则启动

        boolean isAlive = serviceAlive(MessageService.class.getName());
        if (!isAlive) {
            startService(new Intent(this, MessageService.class));
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean serviceAlive( String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
