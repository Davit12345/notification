package com.example.demo.Shchedules;

import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.MailSenderClient;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Shchedul {


    @Autowired
    private NotificationRepository notificationRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderClient mailSenderClient;

    @Scheduled(fixedRate = 1000 * 15)
    @Transactional
    public void sendRemembers() {
        try {

            List<Notification> notification = notificationRepository.getAllCurrentlyNotification();

            for (Notification notifications : notification) {

                User user = userRepository.findOne(notifications.getUser().getId());


                if (notifications.isSendEmail()) {
                    mailSenderClient.send(user.getEmail(), "Notification", Util.Notification(user.getName(), notifications.getMessage(), notifications.getReminedData()));
                    System.out.println(Util.Notification(user.getName(), notifications.getMessage(), notifications.getReminedData()));
                }
                if (notifications.isSendSms()) {
                    System.out.println("Send SMS");
                }
                if (notifications.isSendPush()) {
                    System.out.println("Send Push");
                }

                notifications.setNotified(false);
            }
        } catch (RuntimeException e) {
            mailSenderClient.send("Admin@admin.admin", "Notification Project ", "There are problems");
        }
    }
}
