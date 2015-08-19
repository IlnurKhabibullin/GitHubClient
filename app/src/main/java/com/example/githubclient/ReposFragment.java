package com.example.githubclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReposFragment extends Fragment implements RepoViewAdapter.ReposViewHolder.RepoCallBackListener{
    private RepoFragmentListener mListener;

    public static ReposFragment newInstance() {
        return new ReposFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_repos, container, false);
        try {
            Fade exitFade = new Fade();
            exitFade.setDuration(200);
            setExitTransition(exitFade);
            Slide reenterSlide = new Slide();
            reenterSlide.setSlideEdge(Gravity.TOP);
            reenterSlide.setDuration(1000);
            setReenterTransition(reenterSlide);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(new RepoViewAdapter(RepositoryContent.REPOS, this));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RepoFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RepoFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRepoItemClicked(RepositoryContent.Repository repo) {
        if (null != mListener) {
            RepositoryContent.COMMITS.clear();
            mListener.onRepoSelected(repo);
        }
    }

    public interface RepoFragmentListener {
        void onRepoSelected(RepositoryContent.Repository repo);
    }

}
