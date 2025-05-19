package com.trella.ChatApp.repository;

import com.trella.ChatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
