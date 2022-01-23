package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.SearchedActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchedActivityRepository extends JpaRepository<SearchedActivity,Integer> {

    @Query(value = "SELECT sa.activity_name " +
            "FROM searched_activity sa, " +
            "general_action ga " +
            "WHERE sa.general_action_id = ga.id " +
            "AND ga.type = :generalActionType",
            nativeQuery = true)
    List<String> getActivityNameByType(@Param("generalActionType") String generalActionType);

    @Query(value = "SELECT sa.activityName " +
            "FROM SearchedActivity sa")
    List<String> getAllActivityName();

    @Query(value = "SELECT sa.id " +
            "FROM searched_activity sa " +
            "WHERE sa.activity_name = :activityName",
            nativeQuery = true)
    Integer getSearchedActivityIdByName(@Param("activityName") String activityName);




}
