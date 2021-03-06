package com.example.demo.service;

import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;


import java.nio.file.AccessDeniedException;
import java.util.List;

public interface NotificationService {


    void add(Notification notification, User user) throws InternalErrorException;

    void delete(int id, User user) throws InternalErrorException, NotFoundException, AccessDeniedException;

    List<Notification> getUserNotification(int userId);

    Notification geOneNotification(int nid, int userId);

  void  update(Notification notification,User user) throws InternalErrorException, NotFoundException;




}
