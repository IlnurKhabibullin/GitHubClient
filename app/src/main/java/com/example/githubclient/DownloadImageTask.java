package com.example.githubclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    Bitmap bmImage;
    public ProgressDialog dialog;
    Activity activity;
    String avatar_owner;

    public DownloadImageTask(Activity activity, String owner) {
        this.activity = activity;
        avatar_owner = owner;
    }

    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Loading images");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    protected Bitmap doInBackground(String... params) {
        String urldisplay = params[0];
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmImage;
    }

    protected void onPostExecute(Bitmap result) {
        dialog.dismiss();
        RepositoryContent.addAVATAR(avatar_owner, result);
        ((MainActivity)activity).callReposFragment();
    }

        @SafeVarargs
        @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
        public static <T> void executeAsyncTask(AsyncTask<T, ?, ?> asyncTask, T... params) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            else
                asyncTask.execute(params);
        }
}