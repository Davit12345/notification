package com.example.demo.service.impl;

import com.example.demo.model.ScheduledNotification;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.repository.ScheduledNotificationRepository;
import com.example.demo.service.ScheduledNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledNotificationServiceImpl implements ScheduledNotificationService {


    @Autowired
    private ScheduledNotificationRepository scheduledNotificationRepository;


    @Override
    public void add(ScheduledNotification scheduledNotification) throws InternalErrorException {
        try{
            scheduledNotificationRepository.save(scheduledNotification);

        }catch (RuntimeException e){
            throw  new InternalErrorException();
        }
    }



    @Override
    public List<ScheduledNotification> get(String username) throws InternalErrorException {
        try{
          return   scheduledNotificationRepository.getByUsername(username);
        }catch (RuntimeException e){
            throw  new InternalErrorException();
        }
    }
}
