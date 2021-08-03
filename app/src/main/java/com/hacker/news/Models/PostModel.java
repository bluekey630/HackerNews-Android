package com.hacker.news.Models;
import com.google.gson.annotations.SerializedName;

public class PostModel {

    @SerializedName("id")
    private int id;

    @SerializedName("by")
    private String by;

    @SerializedName("descendants")
    private int descendants;

    @SerializedName("score")
    private int score;

    @SerializedName("time")
    private long time;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    public PostModel(int id, String by, int descendants, int score, long time, String title, String url) {
        this.id = id;
        this.by = by;
        this.descendants = descendants;
        this.score = score;
        this.time = time;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

