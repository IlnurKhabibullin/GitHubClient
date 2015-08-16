package com.example.githubclient;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    Bitmap bmImage;

    public DownloadImageTask() {
//        bmImage = image;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmImage;
    }

    protected void onPostExecute(Bitmap result) {
//        bmImage = result;
    }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
        public static <T> void executeAsyncTask(AsyncTask<T, ?, ?> asyncTask, T... params) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            else
                asyncTask.execute(params);
        }
}