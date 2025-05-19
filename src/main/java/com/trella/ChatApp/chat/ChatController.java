package com.trella.ChatApp.chat;


import com.trella.ChatApp.model.ChatMessageEntity;
import com.trella.ChatApp.repository.ChatRepository;
import org.springframework.dao.DataAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class ChatController {

    private final ChatRepository repository;

    public ChatController(ChatRepository repository) {
        this.repository = repository;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload  ChatMessage chatMessage) {
        log.info("Received message from {}: {}", chatMessage.getSender(), chatMessage.getContent());
        try {
            ChatMessageEntity entity = new ChatMessageEntity(chatMessage.getSender(),chatMessage.getContent(),chatMessage.getType(), LocalDateTime.now());

            repository.save(entity);
        } catch (DataAccessException e) {
            log.error("Failed to save message: {}", e.getMessage());
            throw new RuntimeException("Failed to save message");
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload  ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor,
                               @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        if (chatMessage.getSender() == null || chatMessage.getSender().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }
        if (chatMessage.getType() != MessageType.JOIN) {
            throw new IllegalArgumentException("Expected JOIN message type");
        }

        log.info("User joined: {}", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setSender(username);
        // Сохранение события JOIN (опционально)
        try {
            ChatMessageEntity entity = new ChatMessageEntity(chatMessage.getSender(),chatMessage.getContent(),chatMessage.getType(), LocalDateTime.now());

            repository.save(entity);
        } catch (DataAccessException e) {
            log.error("Failed to save JOIN event for user {}: {}", chatMessage.getSender(), e.getMessage());
            throw new RuntimeException("Failed to save JOIN event");
        }

        return chatMessage;
    }
}
