package com.rest.client.surveillance.model;

import java.sql.Timestamp;

public class Video {

    private Integer id;
    private String footage;
    private Timestamp timestamp;
    private SearchedActivity searchedActivity;
    private Integer score;

    public Video() {
    }

    public Video(String footage, Timestamp timestamp, SearchedActivity searchedActivity, Integer score) {
        this.footage = footage;
        this.timestamp = timestamp;
        this.searchedActivity = searchedActivity;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFootage() {
        return footage;
    }

    public void setFootage(String footage) {
        this.footage = footage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public SearchedActivity getSearchedActivity() {
        return searchedActivity;
    }

    public void setSearchedActivity(SearchedActivity searchedActivity) {
        this.searchedActivity = searchedActivity;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", footage='" + footage + '\'' +
                ", timestamp=" + timestamp +
                ", searchedActivity=" + searchedActivity +
                ", score='" + score + '\'' +
                '}';
    }

}
