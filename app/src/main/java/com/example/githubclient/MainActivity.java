package com.example.githubclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity
        implements AuthFragment.OnAuthButtonListener, ReposFragment.OnFragmentInteractionListener {
    String credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String login = "appvshare@gmail.com";
//        String pass = "playerN5027";
//        new APICall().execute("https://api.github.com/authorizations");
        credentials = "tz3:playerN5027";
//        String credentials = "IlnurKhabibullin:Bkmyeh818878";
        credentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//        new APICall(this).execute("https://api.github.com/user/repos");
//        new APICall().execute("https://api.github.com/user/repos?access_token=d03298a0243752724c5725e2bf0be97431d3bc7581bb2020f8d4f398bc215d07");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, AuthFragment.newInstance()
                        , "AUTH_FRAGMENT")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(String credentials) {
        APICall apiCall = new APICall(this);
        apiCall.execute("https://api.github.com/user/repos");
    }

    public void callReposFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, ReposFragment.newInstance()
                        , "REPOS_FRAGMENT")
                .commit();
    }

    public void onFragmentInteraction(String id) {

    }

    public String getCredentials() {
        return credentials;
    }
}
