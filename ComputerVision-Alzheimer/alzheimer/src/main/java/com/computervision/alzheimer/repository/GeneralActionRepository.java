package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.GeneralAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface GeneralActionRepository extends JpaRepository<GeneralAction,Integer> {
    @Query(value = "SELECT ga.* " +
            "FROM general_action ga, searched_activity sa, video v " +
            "WHERE sa.general_action_id = ga.id " +
            "AND v.label_id = sa.id " +
            "AND v.timestamp between CAST(:startDateTime AS DATETIME) and CAST(:endDateTime AS DATETIME)",
            nativeQuery = true)
    List<GeneralAction> getByDateBetween(@Param("startDateTime") Timestamp startDateTime, @Param("endDateTime") Timestamp endDateTime);

    @Query(value = "SELECT ga.* " +
            "FROM general_action ga, searched_activity sa " +
            "WHERE ga.id=sa.general_action_id " +
            "AND sa.activity_name=:activity",
            nativeQuery = true)
    GeneralAction getBySearchedActivityName(@Param("activity") String activity);
}
