package com.example.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) throws IOException{

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied you need halp?");

    }
}