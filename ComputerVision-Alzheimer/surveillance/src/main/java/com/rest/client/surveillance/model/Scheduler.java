package com.rest.client.surveillance.model;

import java.sql.Time;

public class Scheduler {
    private Integer id;
    private Integer activityScanSeconds;
    private String sleepingFrom;
    private String sleepingTo;
    private Integer minHoursBetweenMeals;
    private Integer maxHoursBetweenMeals;
    private Integer eatingMinutes;
    private Integer minutesBetweenDrinks;
    private Integer minutesBetweenEatNotifications;
    private Integer minutesBetweenDrinkNotifications;
    private Integer storedDataHours;

    public Scheduler() {
    }

    public Scheduler(Integer activityScanSeconds, String sleepingFrom, String sleepingTo, Integer minHoursBetweenMeals, Integer maxHoursBetweenMeals, Integer eatingMinutes, Integer minutesBetweenDrinks, Integer minutesBetweenEatNotifications, Integer minutesBetweenDrinkNotifications, Integer storedDataHours) {
        this.activityScanSeconds = activityScanSeconds;
        this.sleepingFrom = sleepingFrom;
        this.sleepingTo = sleepingTo;
        this.minHoursBetweenMeals = minHoursBetweenMeals;
        this.maxHoursBetweenMeals = maxHoursBetweenMeals;
        this.eatingMinutes = eatingMinutes;
        this.minutesBetweenDrinks = minutesBetweenDrinks;
        this.minutesBetweenEatNotifications = minutesBetweenEatNotifications;
        this.minutesBetweenDrinkNotifications = minutesBetweenDrinkNotifications;
        this.storedDataHours = storedDataHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityScanSeconds() {
        return activityScanSeconds;
    }

    public void setActivityScanSeconds(Integer activityScanSeconds) {
        this.activityScanSeconds = activityScanSeconds;
    }

    public String getSleepingFrom() {
        return sleepingFrom;
    }

    public void setSleepingFrom(String sleepingFrom) {
        this.sleepingFrom = sleepingFrom;
    }

    public String getSleepingTo() {
        return sleepingTo;
    }

    public void setSleepingTo(String sleepingTo) {
        this.sleepingTo = sleepingTo;
    }

    public Integer getMinHoursBetweenMeals() {
        return minHoursBetweenMeals;
    }

    public void setMinHoursBetweenMeals(Integer minHoursBetweenMeals) {
        this.minHoursBetweenMeals = minHoursBetweenMeals;
    }

    public Integer getMaxHoursBetweenMeals() {
        return maxHoursBetweenMeals;
    }

    public void setMaxHoursBetweenMeals(Integer maxHoursBetweenMeals) {
        this.maxHoursBetweenMeals = maxHoursBetweenMeals;
    }

    public Integer getEatingMinutes() {
        return eatingMinutes;
    }

    public void setEatingMinutes(Integer eatingMinutes) {
        this.eatingMinutes = eatingMinutes;
    }

    public Integer getMinutesBetweenDrinks() {
        return minutesBetweenDrinks;
    }

    public void setMinutesBetweenDrinks(Integer minutesBetweenDrinks) {
        this.minutesBetweenDrinks = minutesBetweenDrinks;
    }

    public Integer getMinutesBetweenEatNotifications() {
        return minutesBetweenEatNotifications;
    }

    public void setMinutesBetweenEatNotifications(Integer minutesBetweenEatNotifications) {
        this.minutesBetweenEatNotifications = minutesBetweenEatNotifications;
    }

    public Integer getMinutesBetweenDrinkNotifications() {
        return minutesBetweenDrinkNotifications;
    }

    public void setMinutesBetweenDrinkNotifications(Integer minutesBetweenDrinkNotifications) {
        this.minutesBetweenDrinkNotifications = minutesBetweenDrinkNotifications;
    }

    public Integer getStoredDataHours() {
        return storedDataHours;
    }

    public void setStoredDataHours(Integer storedDataHours) {
        this.storedDataHours = storedDataHours;
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "id=" + id +
                ", activityScanSeconds=" + activityScanSeconds +
                ", sleepingFrom=" + sleepingFrom +
                ", sleepingTo=" + sleepingTo +
                ", minHoursBetweenMeals=" + minHoursBetweenMeals +
                ", maxHoursBetweenMeals=" + maxHoursBetweenMeals +
                ", eatingMinutes=" + eatingMinutes +
                ", minutesBetweenDrinks=" + minutesBetweenDrinks +
                ", minutesBetweenEatNotifications=" + minutesBetweenEatNotifications +
                ", minutesBetweenDrinkNotifications=" + minutesBetweenDrinkNotifications +
                ", storedDataHours=" + storedDataHours +
                '}';
    }
}
