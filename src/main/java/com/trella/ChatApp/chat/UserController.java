package com.trella.ChatApp.chat;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/api/username")
    public String getUsername(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }
}
