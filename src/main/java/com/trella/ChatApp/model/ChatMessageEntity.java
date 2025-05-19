package com.trella.ChatApp.model;


import com.trella.ChatApp.chat.MessageType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="chat_entities")
@Getter
@Setter
@NoArgsConstructor
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    private LocalDateTime time;

    public ChatMessageEntity(String sender, String content, MessageType type, LocalDateTime time) {
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.time = time;
    }


}
