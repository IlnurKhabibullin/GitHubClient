package com.example.githubclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommitsFragment extends Fragment {

    public static CommitsFragment newInstance() {
        return new CommitsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_commits, container, false);

        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(new CommitViewAdapter(RepositoryContent.COMMITS));

        /*ArrayAdapter<RepositoryContent.Commit> adapter =
                new ArrayAdapter<RepositoryContent.Commit>(getActivity().getApplicationContext(),
                        R.layout.commit_item, R.id.commit_desc, RepositoryContent.COMMITS) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        RepositoryContent.Commit commit = getItem(position);
                        ((TextView)view.findViewById(R.id.commit_author)).setText(commit.author);
                        ((TextView)view.findViewById(R.id.commit_desc)).setText(commit.desc);
                        ((TextView)view.findViewById(R.id.commit_date)).setText(commit.date);
                        ((TextView)view.findViewById(R.id.commit_hash)).setText("Hash: " + commit.hash);

                        return view;
                    }
                };
        commitsList = (ListView)v.findViewById(R.id.commits_list);
        commitsList.setAdapter(adapter);*/

        return v;
    }
}
