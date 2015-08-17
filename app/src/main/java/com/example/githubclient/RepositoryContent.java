package com.example.githubclient;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryContent {

    public static List<Repository> REPOS = new ArrayList<>();

    public static List<Commit> COMMITS = new ArrayList<>();

    public static Map<String, Bitmap> AVATAR_MAP = new HashMap<>();

    public static void addRepo(Repository repo) {
        REPOS.add(repo);
    }

    public static void addCommit(Commit commit) {
        COMMITS.add(commit);
    }

    public static void addAVATAR(String name, Bitmap avatar) {
        AVATAR_MAP.put(name, avatar);
    }

    public static class Repository {
        public String id;
        public String name;
        public String description;
        public String owner;
        public String commits_url;
        public Bitmap avatar;
        public int watches;//todo note, that there is no watchers count
        // in repos table, even in github.com. There is only stargazers and forks
        public int forks;

        public Repository(String id, String name, String description,
                          String owner, String commits_url, Bitmap avatar, int watches, int forks) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.owner = owner;
            this.commits_url = commits_url;
            this.avatar = avatar;
            this.watches = watches;
            this.forks = forks;
        }
    }

    public static class Commit {
        public String hash;
        public String desc;
        public String author;
        public String date;

        public Commit(String hash, String desc, String author, String date) {
            this.hash = hash;
            this.desc = desc;
            this.author = author;
            this.date = date;
        }
    }
}
