package com.trella.ChatApp.repository;

import com.trella.ChatApp.model.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends JpaRepository<ChatMessageEntity,Long>{
}
