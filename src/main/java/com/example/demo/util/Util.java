package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

public class Util {


    public static String generateRandomChars() {
        int length = 6;
        String candidateChars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }


    public static String getEncoded(String value) {
        return new BCryptPasswordEncoder().encode(value);
    }


    public static String getRecoveringMessage(String name,String recoveringCode){
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return "Hello dear " + name + " ,your  password recovering code is '" +
                recoveringCode  + "'\n"+ "With best regards NotificationService support team";
    }

    public static  String getVerificationMessage(String name,String verificationCode){
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return "Hello dear " + name + " ,your verification code is '" +
                verificationCode  + "'\n"+ "With best regards NotificationService support team";

    }

}
