package com.example.githubclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReposFragment extends Fragment {
    private ListView reposList;
    private OnFragmentInteractionListener mListener;

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

        ArrayAdapter<RepositoryContent.Repository> adapter =
                new ArrayAdapter<RepositoryContent.Repository>(getActivity().getApplicationContext(),
                        R.layout.repo_item, R.id.repo_name, RepositoryContent.REPOS) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        RepositoryContent.Repository repo = getItem(position);
                        ((TextView)view.findViewById(R.id.author_name)).setText(repo.owner);
                        ((ImageView)view.findViewById(R.id.avatar)).setImageBitmap(repo.avatar);
                        ((TextView)view.findViewById(R.id.repo_name)).setText(repo.name);
                        ((TextView)view.findViewById(R.id.repo_desc)).setText(repo.description);
                        ((TextView)view.findViewById(R.id.stars)).setText(String.valueOf(repo.watches));
                        ((TextView)view.findViewById(R.id.forks)).setText(String.valueOf(repo.forks));

                        return view;
                    }
                };
        reposList = ((ListView) v.findViewById(R.id.repos_list));
        reposList.setAdapter(adapter);
        reposList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mListener) {
                    System.out.println("button clicked");
                    RepositoryContent.COMMITS.clear();
                    mListener.onFragmentInteraction(RepositoryContent.REPOS.get(position));
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(RepositoryContent.Repository repo);
    }

}
