package com.example.demo.repository;

import java.util.Date;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {


    @Query(nativeQuery = true , value = "SELECT * FROM notification WHERE user_id = :userId AND deleted != true " )
    List<Notification> getByUser(@Param("userId") int userId);



    @Query(nativeQuery = true , value = "SELECT * FROM notification WHERE notified!=false  AND deleted != true AND remined_data BETWEEN NOW()-1 AND NOW()+90" )
    List<Notification> getAllCurrentlyNotification();



    @Query(nativeQuery = true , value = "SELECT * FROM notification WHERE user_id = :userId AND deleted != true AND id=:nid " )
    Notification getByIdAndUser(@Param("nid") int id,@Param("userId") int userId);

}
