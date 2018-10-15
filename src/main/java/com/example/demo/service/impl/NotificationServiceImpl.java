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


import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailSenderClient mailSenderClient;

    @Transactional
    @Override
    public void add(Notification notification, User user) throws InternalErrorException {
        try {
            notification.setUser(user);
            notificationRepository.save(notification);
        } catch (RuntimeException e) {
            throw new InternalErrorException("Something went wrong, please try later");
        }
    }


    @Transactional
    @Override
    public void deleteNotifay(int id, User user) throws InternalErrorException, NotFoundException {

        try {
            Notification notification = notificationRepository.findOne(id);
//            if (notification.getUser().equals(user.getId())) {

            notification.setDeleted(true);
            notification.setNotifyStatus(NotifyStatus.NotNotify);
            notificationRepository.save(notification);
//            } else {
//                throw new NotFoundException("Not accses delete");
//            }

        } catch (RuntimeException e) {
            throw new InternalErrorException("Something went wrong, please try later");
        }

    }


    @Override
    public void update(Notification notification) {

    }


    @Override
    public List<Notification> getUserNotification(int userId) {
        return notificationRepository.getByUser(userId);
    }


    @Transactional
    @Scheduled(fixedRate = 1000 * 10)
    @Override
    public void sendRemembers() {
        try {
            Date curentData = new Date();
            List<Notification> notification = notificationRepository.getAllCurentlyNotification(curentData);

            for (Notification notifications : notification) {
                System.out.println(notifications);
                notifications.setNotified(false);
                User user = userRepository.findOne(notifications.getUser().getId());



                if (notifications.isSendEmail()) {
                    mailSenderClient.send(user.getEmail(), "Notification", Util.Notification(user.getName(), notifications.getMessage(), notifications.getReminedData()));
                    System.out.println(Util.Notification(user.getName(), notifications.getMessage(), notifications.getReminedData()));
                }


            }
            System.out.println(curentData);
        } catch (RuntimeException e) {
            mailSenderClient.send("Admin@admin.admin", "Notification Project ", "There are problems");
        }
    }
}
