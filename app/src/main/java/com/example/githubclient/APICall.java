package com.example.githubclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public APICall(MainActivity activity) {
        this.activity = activity;
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
            for (int i = 0; i < result.length(); i++) {
                try {
                    JSONObject repo = result.getJSONObject(i);
                    Bitmap avatar = null;
                    try {
                        in = new java.net.URL(repo.getJSONObject("owner")
                                .getString("avatar_url")).openStream();
                        avatar = BitmapFactory.decodeStream(in);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RepositoryContent.addItem(new RepositoryContent.Repository(
                            repo.getString("id"),
                            repo.getString("name"),
                            repo.getString("description"),
                            repo.getJSONObject("owner").getString("login"),
                            avatar,
                            repo.getInt("stargazers_count"),
                            repo.getInt("forks_count")
                    ));
                } catch (JSONException e) {
                    System.out.println("repos filling issues: " + e.getMessage());
                }
            }
            return result;

        } catch (Exception e ) {
            Toast.makeText(activity,
                    "Attempt failed",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray result) {

        activity.callReposFragment();
    }
}
