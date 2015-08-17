package com.example.githubclient;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ильнур on 10.08.2015.
 */
public class APICall extends AsyncTask<String, String, JSONArray> {
    private MainActivity activity;
    String TAG;

    public APICall(MainActivity activity, String tag) {
        this.activity = activity;
        TAG = tag;
    }

    @Override
    public JSONArray doInBackground(String... params) {
        InputStream in;

        try {
            URL url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + activity.getCredentials());

            in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            in.close();
            JSONArray result = new JSONArray(responseStrBuilder.toString());
            return result;
        } catch (Exception e ) {
//            Toast.makeText(activity,
//                    "Attempt failed",
//                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        if ("REPO".equals(TAG))
            activity.callReposFragment(result);
        else if ("COMMIT".equals(TAG)) {
            activity.callCommitsFragment(result);
        }
    }
}
