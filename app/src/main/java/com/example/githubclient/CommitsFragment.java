package com.example.githubclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommitsFragment extends Fragment {

    public static CommitsFragment newInstance() {
        return new CommitsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_commits, container, false);
        ((TextView)v.findViewById(R.id.repo_name)).setText(getArguments().getString("repo_name"));
        try {
            Slide returnSlide = new Slide();
            returnSlide.setDuration(1000);
            returnSlide.setSlideEdge(Gravity.BOTTOM);
            setReturnTransition(returnSlide);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(new CommitViewAdapter(RepositoryContent.COMMITS));

        return v;
    }
}
