package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Integer> {

    @Query(value = "SELECT * FROM video WHERE timestamp = (SELECT max(v.timestamp) " +
            "FROM general_action ga, searched_activity sa, video v " +
            "WHERE sa.general_action_id = ga.id " +
            "AND v.label_id = sa.id " +
            "AND ga.type=:type)",
            nativeQuery = true)
    List<Video> getLastActionTimeOfType(@Param("type") String type);

    @Query(value = "SELECT * FROM video WHERE timestamp = (SELECT max(v.timestamp) " +
            "FROM general_action ga, searched_activity sa, video v " +
            "WHERE sa.general_action_id = ga.id " +
            "AND v.label_id = sa.id " +
            "AND ga.id=:id)",
            nativeQuery = true)
    List<Video> getLastActionTimeOfGeneralId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM video WHERE timestamp = (SELECT max(v.timestamp) " +
            "FROM general_action ga, searched_activity sa, video v " +
            "WHERE sa.general_action_id = ga.id " +
            "AND v.label_id = sa.id " +
            "AND ga.type=:type " +
            "AND v.timestamp < CAST(:lastTime AS DATETIME))",
            nativeQuery = true)
    List<Video> getLastPreviousActionTimeOfType(@Param("type") String type, @Param("lastTime") Timestamp lastTime);

    @Query(value = "SELECT * FROM video WHERE timestamp = (SELECT max(v.timestamp) " +
            "FROM general_action ga, searched_activity sa, video v " +
            "WHERE sa.general_action_id = ga.id " +
            "AND v.label_id = sa.id " +
            "AND ga.id=:id " +
            "AND v.timestamp < CAST(:lastTime AS DATETIME))",
            nativeQuery = true)
    List<Video> getLastPreviousActionTimeOfGeneralId(@Param("id") Integer id, @Param("lastTime") Timestamp lastTime);

    @Query(value = "SELECT * " +
            "FROM video " +
            "WHERE timestamp < CAST(:lastDate AS DATETIME)",
            nativeQuery = true)
    List<Video> getListOfOlderData(@Param("lastDate") Timestamp lastDate);

//    @Query(value = "DELETE FROM video WHERE timestamp < CAST(:lastDate AS DATETIME)",
//            nativeQuery = true)
//    void deleteOlderVideos(@Param("lastDate") Timestamp lastDate);
}
