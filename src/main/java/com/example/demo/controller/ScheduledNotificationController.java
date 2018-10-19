package com.example.demo.controller;


import com.example.demo.model.Notification;
import com.example.demo.model.ScheduledNotification;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.service.ScheduledNotificationService;
import com.example.demo.service.UserService;
import com.example.demo.util.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(UrlConstants.SCHEDULED_NOTIFICATION_CONTROLLER_URL)
public class ScheduledNotificationController {


    @Autowired
    private ScheduledNotificationService scheduledNotificationService;

    @Autowired
    private UserService userService;




    @PutMapping
    public ResponseEntity addScheduledNotification(@Valid @RequestBody ScheduledNotification scheduledNotification, Principal principal) throws InternalErrorException {
        User user = userService.getByEmail(principal.getName());
        scheduledNotification.setUser(user);
        scheduledNotificationService.add(scheduledNotification);
        return ResponseEntity.ok("success add scheduled notification");

    }



    @GetMapping
    public ResponseEntity AllOneUserNotificationsNotDeleted(Principal principal) throws InternalErrorException {

        List<ScheduledNotification> scheduledNotifications = scheduledNotificationService.get(principal.getName());
        return ResponseEntity.ok().body(scheduledNotifications);

    }


}
