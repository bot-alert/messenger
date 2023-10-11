package com.ashishkhatiwada.chat.service;

import com.ashishkhatiwada.chat.dto.ChatMessage;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface ChatService {
    ChatMessage send( ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor);
}
