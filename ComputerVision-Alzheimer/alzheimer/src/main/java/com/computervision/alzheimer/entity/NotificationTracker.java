package com.computervision.alzheimer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "notification_tracker")
public class NotificationTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    public NotificationTracker() {
    }

    public NotificationTracker(Timestamp timestamp, Notification notification) {
        this.timestamp = timestamp;
        this.notification = notification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "NotificationTracker{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", notification=" + notification +
                '}';
    }
}
