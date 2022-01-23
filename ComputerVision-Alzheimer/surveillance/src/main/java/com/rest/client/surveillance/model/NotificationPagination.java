package com.rest.client.surveillance.model;

import java.util.List;

public class NotificationPagination {
    private List<NotificationTracker> notifications;
    private Integer totalElements;
    private Integer totalPages;
    private Integer pageNumber;
    private Integer pageSize;

    public List<NotificationTracker> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationTracker> notifications) {
        this.notifications = notifications;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "NotificationPagination{" +
                "notifications=" + notifications +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
