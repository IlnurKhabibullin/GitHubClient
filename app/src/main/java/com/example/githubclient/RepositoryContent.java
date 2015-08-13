package com.example.githubclient;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryContent {

    public static List<Repository> ITEMS = new ArrayList<>();

    public static Map<String, Repository> ITEM_MAP = new HashMap<>();

    static {
        // Add 3 sample
    }

    public static void addItem(Repository item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Repository {
        public String id;
        public String name;
        public String description;
        public String owner;
        public Bitmap avatar;
        public int watches;//todo note, that there is no watchers count
        // in repos table, even in github.com. There is only stargazers and forks
        public int forks;

        public Repository(String id, String name, String description,
                          String owner, Bitmap avatar, int watches, int forks) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.owner = owner;
            this.avatar = avatar;
            this.watches = watches;
            this.forks = forks;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
