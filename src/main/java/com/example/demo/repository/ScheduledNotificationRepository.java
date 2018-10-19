package com.example.demo.repository;


import com.example.demo.model.ScheduledNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledNotificationRepository extends JpaRepository<ScheduledNotification, Integer> {


    @Query(nativeQuery = true, value = "SELECT * FROM scheduled_notification WHERE creation_date < NOW()")
    List<ScheduledNotification> getReady();

    @Query(nativeQuery = true, value = "SELECT * FROM scheduled_notification WHERE user_id = (SELECT id FROM user WHERE email= :username) ")
    List<ScheduledNotification> getByUsername(@Param("username") String username);
}
