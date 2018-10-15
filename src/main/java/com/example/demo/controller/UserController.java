package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.model.exceptions.DuplicateDataException;
import com.example.demo.model.exceptions.InternalErrorException;
import com.example.demo.model.exceptions.NotFoundException;
import com.example.demo.model.exceptions.TeapotException;
import com.example.demo.service.UserService;
import com.example.demo.util.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(UrlConstants.USERS_CONTROLLER_URL)
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity goverifay() {
        return ResponseEntity.ok().body("message");
    }




    @PutMapping
    public ResponseEntity add(@Valid @RequestBody User user) {

        try {

            userService.add(user);

        } catch (DuplicateDataException e) {
            return ResponseEntity.status(409).body(Collections.singletonMap("message", e.getMessage()));

        } catch (InternalErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

        return ResponseEntity.ok().body(user);

    }





    @PostMapping(UrlConstants.USERS_CONTROLLER_URL_VERIFY)
    public ResponseEntity verifay(@RequestParam String email, @RequestParam String code) throws TeapotException {

        try {

            userService.verify(email, code);

        } catch (IllegalAccessError e) {

            return ResponseEntity.status(409).body(Collections.singletonMap("message", e.getMessage()));
        } catch (InternalErrorException e) {

            return ResponseEntity.status(500).body(e.getMessage());
        }
        return ResponseEntity.ok().body("you have scssesful passed  the varification");
    }






    @PostMapping(UrlConstants.USERS_CONTROLLER_SENDRECOVERINGCODE)
    public ResponseEntity sendRecoveringCode(@RequestParam String email) {

        try {

            userService.sendRecoveringCode(email);
            return ResponseEntity.ok().body("See Your Recovering Code in your  Email");


        } catch (IllegalAccessError e) {
            return ResponseEntity.status(409).body(Collections.singletonMap("message", e.getMessage()));

        } catch (InternalErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }





    @PostMapping(UrlConstants.USERS_CONTROLLER_CHANGPASSWORD)
    public ResponseEntity changpassword(@RequestParam String email, @RequestParam String password, @RequestParam String code) {

        try {

            userService.changePassword(email, password, code);
            return ResponseEntity.ok().body("you have scssesful cahne the password");


        } catch (IllegalAccessError e) {
            return ResponseEntity.status(409).body(Collections.singletonMap("message", e.getMessage()));

        } catch (InternalErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }




    @PostMapping(UrlConstants.USERS_CONTROLLER_RESENDVERIFICATIONCOD)
    public ResponseEntity resendVerificationCode(@RequestParam String email) throws NotFoundException, InternalErrorException {
        userService.resendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

}