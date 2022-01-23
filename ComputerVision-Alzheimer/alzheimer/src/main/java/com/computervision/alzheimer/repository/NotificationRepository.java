package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
