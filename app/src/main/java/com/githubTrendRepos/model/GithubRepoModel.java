package com.githubTrendRepos.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GithubRepoModel implements Serializable {

    private String username;
    private String url;
    private String repositoryName;
    private String description;
    private String language;
    private String since;
    private int totalStars;
    private int forks;
    private int rank;
    private int starsSince;
    private ArrayList<UserModel> builtBy;
    private boolean isSelected;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStarsSince() {
        return starsSince;
    }

    public void setStarsSince(int starsSince) {
        this.starsSince = starsSince;
    }

    public ArrayList<UserModel> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(ArrayList<UserModel> builtBy) {
        this.builtBy = builtBy;
    }
}
