package com.example.demo.service;

import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;


import java.util.List;

public interface NotificationService {


    void add(Notification notification, User user) throws InternalErrorException;

    void deleteNotifay(int id, User user) throws InternalErrorException, NotFoundException;

    void update(Notification notification);

    List<Notification> getUserNotification(int userId);

    Notification geOneNotification(int nid, int userId);

  void  updateNotification(Notification notification,User user) throws InternalErrorException, NotFoundException;


    void sendRemembers();

}
