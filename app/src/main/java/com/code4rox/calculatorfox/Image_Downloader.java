package com.code4rox.calculatorfox;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.Integer.valueOf;

public class Image_Downloader extends AppCompatActivity {

    private static String file_url = "https://api.androidhive.info/progressdialog/hive.jpg";
    private Button btn_download_image;
    private ProgressBar progressBar;
    private ImageView imageView;
    private String Tag = "IDownload";
    private int progressbar_type = 0;
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;
    private int notificationId=101;
    private Snackbar snkbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__downloader);
        btn_download_image = (Button) findViewById(R.id.btn_download_image);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        imageView = (ImageView) findViewById(R.id.downloaded_image);
         snkbar=Snackbar.make(findViewById(R.id.coordinatorLayout),R.string.downloaded,Snackbar.LENGTH_SHORT);
        snkbar.setAction(R.string.undo_string, new MyUndoListener());
        btn_download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (isConnectingToInternet()) {
                    new DownloadImage_AsyncTask().execute(file_url);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Required Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(){

        String CHANNEL_ID="Download_channel";
         notificationManager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, "CHANNEL_ID");
        builder.setContentTitle("Picture Download")
                .setSmallIcon(R.drawable.ic_download)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        int PROGRESS_MAX=100;
        int PROGRESS_CURRENT=0;
// Issue the initial notification with zero progress
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true);
        notificationManager.notify(notificationId, builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager builder = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel("CHANNEL_ID", "chanel_1", builder.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
        }

// Do the job here that tracks the progress.
// Usually, this should be in a
// worker thread
// To show progress, update PROGRESS_CURRENT and update the notification with:
// builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
// notificationManager.notify(notificationId, builder.build());

// When done, update the notification one more time to remove the progress bar

    }

    class DownloadImage_AsyncTask extends AsyncTask<String, Integer, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.isIndeterminate();
            progressBar.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotification();
            }
        }
        @Override
        protected String doInBackground(String... F_url) {
            int count;
            try {
                URL url = new URL(F_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthoffile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File directory = getFilesDir();
                File file = new File(directory, "downloaded_file.jpg");
                OutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(0 + (int) ((total * 100) / lengthoffile));
                    Log.d(Tag, "" + 0 + (int) ((total * 100) / lengthoffile));
                    // writing data to file
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressBar.setProgress(progress[0]);
            Log.d(Tag," progress "+progress[0]);
            update_Notification(progress[0],"Download in Progress");

        }
        private void update_Notification(int progress,String Status){
            builder.setContentText(Status).setProgress(80,progress,false);
            notificationManager.notify(notificationId, builder.build());
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_SHORT).show();
            Image_Downloader.this.snkbar.show();
            String imagePath = getFilesDir() + "/downloaded_file.jpg";
            imageView.setImageDrawable(Drawable.createFromPath(imagePath));
            builder.setContentText("Download Complete").setProgress(0,0,false);
            notificationManager.notify(notificationId, builder.build());
        }
    }
    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // Code to undo the user's last action
        }
    }

}
