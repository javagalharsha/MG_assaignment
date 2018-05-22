package com.harsha.mastglobal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harsha on 5/21/18.
 */

public class UserRepoResponse {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("html_url")
    @Expose
    private String url;

    @SerializedName("size")
    @Expose
    private int size;

    @SerializedName("watchers")
    @Expose
    private int watchers;

    @SerializedName("open_issues_count")
    @Expose
    private int openIssues;

    @SerializedName("description")
    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getSize() {
        return size;
    }

    public int getWatchers() {
        return watchers;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public String getDescription() {
        return description;
    }
}
