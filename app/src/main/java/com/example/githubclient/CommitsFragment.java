package com.example.githubclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommitsFragment extends Fragment {
//    private RepositoryContent.Repository repository;
    private ListView commitsList;

    public static CommitsFragment newInstance() {
        return new CommitsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_commits, container, false);
//        ((TextView)v.findViewById(R.id.repo_owner)).setText(repository.owner);
//        ((TextView)v.findViewById(R.id.repository)).setText(repository.name);
        ArrayAdapter<RepositoryContent.Commit> adapter =
                new ArrayAdapter<RepositoryContent.Commit>(getActivity().getApplicationContext(),
                        R.layout.commit_item, R.id.commit_desc, RepositoryContent.COMMITS) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        RepositoryContent.Commit commit = getItem(position);
                        ((TextView)view.findViewById(R.id.commit_author)).setText("by: " + commit.author);
                        ((TextView)view.findViewById(R.id.commit_desc)).setText(commit.desc);
                        ((TextView)view.findViewById(R.id.commit_date)).setText(commit.date);
                        ((TextView)view.findViewById(R.id.commit_hash)).setText(commit.hash);

                        return view;
                    }
                };
        commitsList = (ListView)v.findViewById(R.id.commits_list);
        commitsList.setAdapter(adapter);

        return v;
    }

//    private CommitsFragment setRepo(RepositoryContent.Repository repo) {
//        repository = repo;
//        return this;
//    }
}
