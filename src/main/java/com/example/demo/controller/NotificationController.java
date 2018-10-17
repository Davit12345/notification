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
import java.nio.file.AccessDeniedException;
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
        return ResponseEntity.ok("success add notification");

    }


    @GetMapping
    public ResponseEntity AllOneUserNotificationsNotDeleted(Principal principal) {

        User user = userService.getByEmail(principal.getName());
        List<Notification> notification = notificationService.getUserNotification(user.getId());

        return ResponseEntity.ok().body(notification);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleted(@PathVariable int id, Principal principal) throws NotFoundException, InternalErrorException, AccessDeniedException {

        User user = userService.getByEmail(principal.getName());
        notificationService.delete(id, user);

        return ResponseEntity.ok("success delete one notification");

    }


    @GetMapping(value = "/{id}")
    public ResponseEntity GetOneNotification(@PathVariable int id, Principal principal) {

        User user = userService.getByEmail(principal.getName());

        return ResponseEntity.ok().body(notificationService.geOneNotification(id, user.getId()));
    }



    @PatchMapping
    public ResponseEntity updateNotification(@RequestBody Notification notification, Principal principal) throws InternalErrorException, NotFoundException {

        User user = userService.getByEmail(principal.getName());
        notificationService.update(notification,user);
        return ResponseEntity.ok("success update Notification");

    }

}
