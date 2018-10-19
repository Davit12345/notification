package com.example.demo.service;

import com.example.demo.model.ScheduledNotification;
import com.example.demo.model.exceptions.InternalErrorException;

import java.util.List;

public interface ScheduledNotificationService {



    void add(ScheduledNotification scheduledNotification) throws InternalErrorException;




    List<ScheduledNotification> get(String username) throws InternalErrorException;
}
