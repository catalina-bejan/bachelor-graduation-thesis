package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.NotificationTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface NotificationTrackerRepository extends JpaRepository<NotificationTracker,Integer> {
    @Query(value = "SELECT max(timestamp) " +
            "FROM notification_tracker " +
            "WHERE notification_id=:id",
            nativeQuery = true)
    Timestamp getLastNotificationTimeByNotification(@Param("id") Integer id);

    @Query(value = "SELECT * " +
            "FROM notification_tracker " +
            "WHERE timestamp < CAST(:lastDate AS DATETIME)",
            nativeQuery = true)
    List<NotificationTracker> getListOfOlderData(@Param("lastDate") Timestamp lastDate);

//    @Modifying
//    @Query(value = "DELETE FROM notification_tracker WHERE timestamp < CAST(:lastDate AS DATETIME)",
//            nativeQuery = true)
//    void deleteOlderNotifications(@Param("lastDate") Timestamp lastDate);
}
