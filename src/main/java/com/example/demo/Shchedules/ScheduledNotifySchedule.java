package com.example.demo.Shchedules;


import com.example.demo.model.Notification;
import com.example.demo.model.ScheduledNotification;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.ScheduledNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledNotifySchedule {

    @Autowired
    private NotificationRepository notificationService;

    @Autowired
    private ScheduledNotificationRepository scheduledNotificationRepository;


    @Scheduled(fixedDelay = 90*1000)
    @Transactional
    public void add() {


        List<ScheduledNotification> scheduledNotifications = scheduledNotificationRepository.getReady();

        for (ScheduledNotification scheduledNotification : scheduledNotifications) {

            switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {

                case Calendar.SUNDAY:
                    if (scheduledNotification.isSunday()) {
                        addNotification(scheduledNotification);
                    }
                    break;
                case Calendar.MONDAY:
                    if (scheduledNotification.isMonday()) {
                        addNotification(scheduledNotification);

                    }
                    break;
                case Calendar.TUESDAY:
                    if (scheduledNotification.isTuesday()) {
                        addNotification(scheduledNotification);

                    }
                    break;
                case Calendar.WEDNESDAY:
                    if (scheduledNotification.isWednesday()) {
                        addNotification(scheduledNotification);

                    }
                    break;
                case Calendar.THURSDAY:
                    if (scheduledNotification.isThursday()) {
                        addNotification(scheduledNotification);

                    }
                    break;
                case Calendar.FRIDAY:
                    if (scheduledNotification.isFriday()) {
                        addNotification(scheduledNotification);

                    }
                    break;
                case Calendar.SATURDAY:
                    if (scheduledNotification.isSaturday()) {
                        addNotification(scheduledNotification);

                    }
                    break;
            }
        }


    }


    public void addNotification(ScheduledNotification scheduledNotification) {


        Notification notification = new Notification();
        notification.setUser(scheduledNotification.getUser());
        notification.setMessage(scheduledNotification.getMessage());
        notification.setSendEmail(scheduledNotification.isSendEmail());
        notification.setSendSms(scheduledNotification.isSendSms());
        notification.setSendPush(scheduledNotification.isSendPush());
        notification.setNotificationDate(getSTartOfDay() + scheduledNotification.getNotificationDate());
        notification.setReminedData(new Date(getSTartOfDay() + scheduledNotification.getNotificationDate()));
        notificationService.save(notification);
        scheduledNotification.setCreationDate(new Date(scheduledNotification.getCreationDate().getTime() + 86400000));

    }


    private long getSTartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTimeInMillis();
    }
}
