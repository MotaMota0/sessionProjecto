package com.trella.ChatApp.service;

import com.trella.ChatApp.model.User;
import com.trella.ChatApp.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*@Service
public class CustomUserDetailsService implements UserDetailsService {
    *//*private final PersonRepository personRepository;

  *//**//*  public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
*//**//*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
    }*/

