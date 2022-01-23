package com.computervision.alzheimer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "scheduler")
public class Scheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "activity_scan_seconds")
    private Integer activityScanSeconds;

    @Column(name = "sleeping_from")
    private Time sleepingFrom;

    @Column(name = "sleeping_to")
    private Time sleepingTo;

    @Column(name = "min_hours_between_meals")
    private Integer minHoursBetweenMeals;

    @Column(name = "max_hours_between_meals")
    private Integer maxHoursBetweenMeals;

    @Column(name = "eating_minutes")
    private Integer eatingMinutes;

    @Column(name = "minutes_between_drinks")
    private Integer minutesBetweenDrinks;

    @Column(name = "minutes_between_eat_notifications")
    private Integer minutesBetweenEatNotifications;

    @Column(name = "minutes_between_drink_notifications")
    private Integer minutesBetweenDrinkNotifications;

    @Column(name = "storedDataHours")
    private Integer storedDataHours;

    public Scheduler() {
    }

    public Scheduler(Integer activityScanSeconds, Time sleepingFrom, Time sleepingTo, Integer minHoursBetweenMeals, Integer maxHoursBetweenMeals, Integer eatingMinutes, Integer minutesBetweenDrinks, Integer minutesBetweenEatNotifications, Integer minutesBetweenDrinkNotifications, Integer storedDataHours) {
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

    public Time getSleepingFrom() {
        return sleepingFrom;
    }

    public void setSleepingFrom(Time sleepingFrom) {
        this.sleepingFrom = sleepingFrom;
    }

    public Time getSleepingTo() {
        return sleepingTo;
    }

    public void setSleepingTo(Time sleepingTo) {
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
