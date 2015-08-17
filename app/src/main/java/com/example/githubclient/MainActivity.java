package com.example.githubclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity
        implements AuthFragment.OnAuthButtonListener, ReposFragment.OnFragmentInteractionListener {
    String credentials;
    DownloadImageTask dit;
    Toolbar toolbar;
    SharedPreferences sPref;

    private final String CREDS = "credentials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.Base_TextAppearance_AppCompat_Large);
        setSupportActionBar(toolbar);
        sPref = getPreferences(MODE_PRIVATE);
        credentials = sPref.getString(CREDS, "");
        if (!"".equals(credentials)) {
            if (RepositoryContent.REPOS.isEmpty())
                onSignIn(credentials);
            else
                toolbar.setTitle("Repositories");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, ReposFragment.newInstance()
                                , "REPOS_FRAGMENT")
                        .commit();
        } else {
            toolbar.setTitle("Authorization");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, AuthFragment.newInstance()
                            , "AUTH_FRAGMENT")
                    .commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onSignIn(String cred) {
        credentials = cred;
        sPref.edit().putString(CREDS, credentials).commit();
        Context context = this.getApplicationContext();
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.
                getSystemService(context.CONNECTIVITY_SERVICE));
        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected()) {
            APICall apiCall = new APICall(this, "REPO");
            apiCall.execute("https://api.github.com/user/repos");
        } else {
            Toast.makeText(this,
                    "Check your internet connection",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void callReposFragment(JSONArray result, String responseTag) {
        if ("wrong_request".equals(responseTag)) {
            Toast.makeText(this,
                    "Wrong login or password",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (result != null) {
            for (int i = 0; i < result.length(); i++) {
                try {
                    JSONObject repo = result.getJSONObject(i);
                    String owner = repo.getJSONObject("owner").getString("login");
                    String commit_url = repo.getString("commits_url");
                    if (!RepositoryContent.AVATAR_MAP.containsKey(owner)) {
                        dit = new DownloadImageTask();
                        dit.executeAsyncTask(dit, repo.getJSONObject("owner")
                                .getString("avatar_url"));
                        try {
                            RepositoryContent.addAVATAR(owner, dit.get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    Bitmap avatar = RepositoryContent.AVATAR_MAP.get(owner);
                    int iconId = repo.getBoolean("private")?R.drawable.ic_lock_outline_white_18dp:
                            R.drawable.ic_lock_open_white_18dp;
                    Drawable privacy_icon = getDrawable(iconId);
                    RepositoryContent.addRepo(new RepositoryContent.Repository(
                            repo.getString("id"),
                            repo.getString("name"),
                            repo.getString("description"),
                            owner,
                            privacy_icon,
                            commit_url.substring(0, commit_url.length() - 6),
                            avatar,
                            repo.getInt("stargazers_count"),
                            repo.getInt("forks_count")
                    ));
                } catch (JSONException e) {
                    System.out.println("repos filling issues: " + e.getMessage());
                }
            }
            toolbar.setTitle("Repositories");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, ReposFragment.newInstance()
                            , "REPOS_FRAGMENT")
                    .commit();
        } else {
            Toast.makeText(this,
                    "There is no repositories yet",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onFragmentInteraction(RepositoryContent.Repository repo) {
        Context context = this.getApplicationContext();
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.
                getSystemService(context.CONNECTIVITY_SERVICE));
        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected()) {
            APICall apiCall = new APICall(this, "COMMIT");
            apiCall.execute(repo.commits_url);
        } else {
            Toast.makeText(this,
                    "Check your internet connection",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void callCommitsFragment(JSONArray commits) {
        RepositoryContent.COMMITS.clear();
        if (commits != null) {
            for (int i = 0; i < commits.length(); i++) {
                try {
                    JSONObject commit = commits.getJSONObject(i);
                    String date = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
                    try {
                        date = DateFormat.getDateTimeInstance().format(sdf.parse(commit.
                                getJSONObject("commit").getJSONObject("author").getString("date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    RepositoryContent.addCommit(new RepositoryContent.Commit(
                            commit.getString("sha"),
                            commit.getJSONObject("commit").getString("message"),
                            commit.getJSONObject("author").getString("login"),
                            date
                    ));
                } catch (JSONException e) {
                    System.out.println("commits filling issues: " + e.getMessage());
                }
            }
            toolbar.setTitle("Commits");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, CommitsFragment.newInstance()
                            , "COMMITS_FRAGMENT")
                    .addToBackStack(null).commit();
        } else {
            Toast.makeText(this,
                    "There is no commits yet",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout_button) {
            credentials = "";
            sPref.edit().putString(CREDS, credentials).commit();
            toolbar.setTitle("Authorization");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, AuthFragment.newInstance()
                            , "AUTH_FRAGMENT")
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getCredentials() {
        return credentials;
    }
}
