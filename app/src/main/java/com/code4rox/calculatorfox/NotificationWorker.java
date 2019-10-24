package com.code4rox.calculatorfox;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";
    private static final String PROGRESS = "PROGRESS";
    NotificationCompat.Builder builder;
    NotificationManager manager;
    Data outputData;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        setProgressAsync(new Data.Builder().putInt(PROGRESS, 0).build());

    }

    @NonNull
    @Override
    public Result doWork() {


                // Doing work.

                Data taskData = getInputData();
                String taskDataString = taskData.getString(workermanager.MESSAGE_STATUS);
                Log.d("Task data", "" + taskData.getString(workermanager.MESSAGE_STATUS));
                outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
                showNotification("WorkManager", taskDataString != null ? taskDataString : "Message has been Sent");
                setProgressAsync(new Data.Builder().putInt(PROGRESS, 0).build());
        return Result.success(outputData);
    }

    private void showNotification(String task, String desc) {

        manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);
        builder.setProgress(100, 0, false);
        manager.notify(1, builder.build());

    }



}
