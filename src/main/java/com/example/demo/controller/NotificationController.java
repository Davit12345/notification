package com.example.demo.controller;


import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;
import com.example.demo.service.NotificationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.example.demo.util.UrlConstants.NOTIFICATION_CONTROLLER_URL;

@RestController
@RequestMapping(NOTIFICATION_CONTROLLER_URL)
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity addNotification(@Valid @RequestBody Notification notification
            , Principal principal) throws InternalErrorException {

        User user = userService.getByEmail(principal.getName());
        notificationService.add(notification, user);
        return ResponseEntity.ok("sucsses add notification");

    }


    @GetMapping
    public ResponseEntity AllMyNotifications(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        List<Notification> notification = notificationService.getUserNotification(user.getId());
        return ResponseEntity.ok().body(notification);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable int id, Principal principal) throws NotFoundException, InternalErrorException {

        User user = userService.getByEmail(principal.getName());


        notificationService.deleteNotifay(id, user);

        return ResponseEntity.ok("sucsess delete one notification");

    }


}
