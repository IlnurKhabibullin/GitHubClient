package com.example.githubclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthFragment extends Fragment {
    private EditText mLoginField;
    private EditText mPasswordField;
    private Button mAuthButton;
    private OnAuthButtonListener mCallBack;

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    public interface OnAuthButtonListener {
            void onButtonPressed(String credentials);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auth, container, false);

        mLoginField = (EditText) v.findViewById(R.id.loginField);
        mPasswordField = (EditText) v.findViewById(R.id.passwordField);
        mAuthButton = (Button) v.findViewById(R.id.authButton);
        mAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginField.getText().toString();
                String pass = mPasswordField.getText().toString();
                if (!login.isEmpty() && !pass.isEmpty()) {
                    String credentials = login + ":" + pass;
                    credentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    RepositoryContent.REPOS.clear();
                    RepositoryContent.AVATAR_MAP.clear();
                    mCallBack.onButtonPressed(credentials);
                } else {
                    Toast.makeText(getActivity(),
                            "login or password can not be empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (OnAuthButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
