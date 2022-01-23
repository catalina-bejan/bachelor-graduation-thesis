package com.computervision.alzheimer.repository;

import com.computervision.alzheimer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
