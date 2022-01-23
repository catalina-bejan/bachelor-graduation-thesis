package com.computervision.alzheimer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //@Lob
    @Column(name = "footage")
    private String footage;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private SearchedActivity searchedActivity;

    @Column(name = "score")
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
