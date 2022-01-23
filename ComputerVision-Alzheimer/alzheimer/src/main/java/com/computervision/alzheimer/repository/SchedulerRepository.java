package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerRepository extends JpaRepository<Scheduler,Integer> {
}
