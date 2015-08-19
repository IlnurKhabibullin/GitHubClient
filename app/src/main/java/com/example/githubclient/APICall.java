package com.example.githubclient;

import android.app.ProgressDialog;
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
    String tag;
    String responseTag;
    public ProgressDialog dialog;
    String repo_name;

    public APICall(MainActivity activity, String tag) {
        this.activity = activity;
        this.tag = tag;
    }

    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Requesting data");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public JSONArray doInBackground(String... params) {
        InputStream in;

        try {

            if ("COMMIT".equals(tag)) {
                repo_name = params[1];
            }
            responseTag = "wrong_request";
            URL url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + activity.getCredentials());

            in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            in.close();
            JSONArray result = new JSONArray(responseStrBuilder.toString());
            responseTag = "correct_request";
            return result;
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        dialog.dismiss();
        if ("REPO".equals(tag))
            activity.handleResponse(result, responseTag);
        else if ("COMMIT".equals(tag)) {
            activity.callCommitsFragment(result, repo_name);
        }
    }
}
