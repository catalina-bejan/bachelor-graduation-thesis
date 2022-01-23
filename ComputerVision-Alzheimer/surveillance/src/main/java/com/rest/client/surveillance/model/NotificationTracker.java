package com.rest.client.surveillance.model;

import java.sql.Timestamp;

public class NotificationTracker {
    private Integer id;

    private Timestamp timestamp;

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
