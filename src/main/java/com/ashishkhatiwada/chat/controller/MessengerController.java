package com.ashishkhatiwada.chat.controller;

import com.ashishkhatiwada.chat.dto.ChatMessage;
import com.ashishkhatiwada.chat.dto.RoomRegister;
import com.ashishkhatiwada.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
@Log4j2
public class MessengerController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    public ChatMessage send(@Payload ChatMessage chatMessage,
                            SimpMessageHeaderAccessor headerAccessor) {
        return chatService.send(chatMessage, headerAccessor);
    }

    @MessageMapping("/register")
    @SendToUser("/topic/register")
    public String subscribe(@Payload RoomRegister chatRegister,
                            Principal principal,
                            SimpMessageHeaderAccessor headerAccessor) {

        Assert.notNull(headerAccessor.getSessionAttributes(), "SimpMessageHeaderAccessor is attributes is null");
        Assert.hasText(chatRegister.getRoom(), "Room is missing");

        headerAccessor.getSessionAttributes().put("roomId", chatRegister.getRoom());
        log.info("User {} registered to chat with in room {}", principal.getName(), chatRegister.getRoom());

        log.info(headerAccessor.getSessionId());

        return "Registered to room " + chatRegister.getRoom();
    }
}