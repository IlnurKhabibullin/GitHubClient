package com.example.githubclient;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ильнур on 17.08.2015.
 */
public class CommitViewAdapter extends RecyclerView.Adapter<CommitViewAdapter.CommitsViewHolder>{
    List<RepositoryContent.Commit> commits;

    CommitViewAdapter(List<RepositoryContent.Commit> commits){
        this.commits = commits;
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    @Override
    public CommitsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commit_card, viewGroup, false);
        final CommitsViewHolder pvh = new CommitsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CommitsViewHolder rVH, int i) {
        rVH.commit_desc.setText(commits.get(i).desc);
        rVH.commit_author.setText(commits.get(i).author);
        rVH.commit_date.setText(commits.get(i).date);
        rVH.commit_hash.setText("Hash: " + commits.get(i).hash);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CommitsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView commit_desc;
        TextView commit_author;
        TextView commit_date;
        TextView commit_hash;


        CommitsViewHolder(View view) {
            super(view);
            cv = (CardView)view.findViewById(R.id.repo_card);
            commit_desc = (TextView)view.findViewById(R.id.commit_desc);
            commit_author = (TextView)view.findViewById(R.id.commit_author);
            commit_date = (TextView)view.findViewById(R.id.commit_date);
            commit_hash = (TextView)view.findViewById(R.id.commit_hash);
        }
    }
}
