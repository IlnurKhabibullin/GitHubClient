package com.example.githubclient;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ильнур on 17.08.2015.
 */
public class RepoViewAdapter extends RecyclerView.Adapter<RepoViewAdapter.ReposViewHolder>{
    List<RepositoryContent.Repository> repos;
    ReposViewHolder.RepoCallBackListener fragment;

    RepoViewAdapter(List<RepositoryContent.Repository> repos,
                    ReposViewHolder.RepoCallBackListener fragment){
        this.repos = repos;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repo_card, viewGroup, false);
        final ReposViewHolder pvh = new ReposViewHolder(v, fragment);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ReposViewHolder rVH, int i) {
        RepositoryContent.Repository repo = repos.get(i);
        rVH.author_name.setText(repo.owner);
        rVH.avatar.setImageBitmap(RepositoryContent.AVATAR_MAP.get(repo.owner));
        TextView name = rVH.repo_name;
        name.setText(repo.name);
        name.setCompoundDrawablePadding(4);
        name.setCompoundDrawablesWithIntrinsicBounds(null, null, repo.privacyIcon, null);
        rVH.repo_desc.setText(repo.description);
        rVH.stars.setText(String.valueOf(repo.stargazers));
        rVH.forks.setText(String.valueOf(repo.forks));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ReposViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        RepoCallBackListener repoFragment;
        CardView cv;
        TextView author_name;
        ImageView avatar;
        TextView repo_name;
        TextView repo_desc;
        TextView stars;
        TextView forks;

        ReposViewHolder(View view, RepoCallBackListener fragment) {
            super(view);
            repoFragment = fragment;
            cv = (CardView)view.findViewById(R.id.repo_card);
            author_name = (TextView)view.findViewById(R.id.author_name);
            avatar = (ImageView)view.findViewById(R.id.avatar);
            repo_name = (TextView)view.findViewById(R.id.repo_name);
            repo_desc = (TextView)view.findViewById(R.id.repo_desc);
            stars = (TextView)view.findViewById(R.id.stars);
            forks = (TextView)view.findViewById(R.id.forks);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            repoFragment.onRepoItemClicked(RepositoryContent.REPOS.get(getPosition()));
        }

        public interface RepoCallBackListener {
            void onRepoItemClicked(RepositoryContent.Repository repo);
        }
    }
}

