package com.example.demo.service.impl;

import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.model.enums.NotifyStatus;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.NotificationService;
import com.example.demo.util.MailSenderClient;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {



    @Autowired
    private NotificationRepository notificationRepository;




    @Override
    @Transactional
    public void add(Notification notification, User user) throws InternalErrorException {
        try {
            notification.setUser(user);
            notificationRepository.save(notification);
        } catch (RuntimeException e) {
            throw new InternalErrorException("Something went wrong, please try later");
        }
    }


    @Override
    @Transactional
    public void delete(int id, User user) throws InternalErrorException, NotFoundException, AccessDeniedException {

        try {
            Notification notification = notificationRepository.findOne(id);
            if (notification.getUser().getId() != (user.getId())) {
                throw new AccessDeniedException("Not access this action");
            }
            if (notification.isDeleted() == true) {
                throw new NotFoundException("Not access this action");
            }

            notification.setDeleted(true);
            notification.setNotifyStatus(NotifyStatus.NotNotify);
            notificationRepository.save(notification);


        } catch (RuntimeException e) {
            throw new InternalErrorException("Something went wrong, please try later");
        }

    }


    @Override
    public List<Notification> getUserNotification(int userId) {
        return notificationRepository.getByUser(userId);
    }


    @Override
    public Notification geOneNotification(int nid, int userId) {
        return notificationRepository.getByIdAndUser(nid, userId);
    }



    @Override
    @Transactional
    public void update(Notification notification, User user) throws InternalErrorException, NotFoundException {
        try {


            Date date = new Date();
            Notification notification1 = notificationRepository.getOne(notification.getId());

            if (notification1.getUser().getId() != user.getId()) {
                throw new NotFoundException("Not acsess this action");

            }
            notification1 = notification;
            notification1.setUser(user);
            notification1.setCreationDate(date);

            notificationRepository.save(notification1);
        } catch (RuntimeException e) {
            throw new InternalErrorException("Something went wrong, please try later");
        }
    }

}
