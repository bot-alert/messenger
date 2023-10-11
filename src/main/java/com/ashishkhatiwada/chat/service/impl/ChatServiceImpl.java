package com.ashishkhatiwada.chat.service.impl;

import com.ashishkhatiwada.chat.dto.ChatMessage;
import com.ashishkhatiwada.chat.dto.MessageResponseDTO;
import com.ashishkhatiwada.chat.entity.ChatLine;
import com.ashishkhatiwada.chat.entity.ChatRoom;
import com.ashishkhatiwada.chat.entity.ChatUser;
import com.ashishkhatiwada.chat.repository.ChatLineRepository;
import com.ashishkhatiwada.chat.repository.ChatRoomRepository;
import com.ashishkhatiwada.chat.repository.ChatUserRepository;
import com.ashishkhatiwada.chat.service.ChatService;
import com.ashishkhatiwada.chat.util.UserCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatServiceImpl implements ChatService {
    private final ChatLineRepository chatLineRepository;
    private final ChatUserRepository chatUserRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate messagingTemplate;


    public ChatMessage send(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Validate input parameters
        validateChatMessage(chatMessage);
        validateHeaderAccessor(headerAccessor);

        String senderId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");

        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        // Validate user and room IDs
        validateUserIdAndRoomId(senderId, roomId);

        ChatUser sender = findChatUserById(senderId);
        ChatRoom chatRoom = findChatRoomById(roomId);

        // Save the chat message to the database
        saveChatMessage(chatMessage, sender, chatRoom);


        MessageResponseDTO messageResponseDTO = new MessageResponseDTO(roomId, senderId, chatMessage.getContent());

        List<ChatUser> chatUserList = chatUserRepository.findAllByChatRoom(UUID.fromString(roomId));

        chatUserList.forEach(chatUser -> {
            String chatUserId = chatUser.getId().toString();
            if (UserCacheUtil.containsUserId(chatUserId)) {
                log.info("Sending message to {} user ", chatUserId);
                messagingTemplate.convertAndSendToUser(chatUserId,
                        "/topic/chat", messageResponseDTO);
            }
        });


        return chatMessage;
    }

    private void validateChatMessage(ChatMessage chatMessage) {
        Assert.hasText(chatMessage.getContent(), "Message cannot be empty");
    }

    private void validateHeaderAccessor(SimpMessageHeaderAccessor headerAccessor) {
        Assert.notNull(headerAccessor.getSessionAttributes(), "SimpMessageHeaderAccessor attributes are null");
    }

    private void validateUserIdAndRoomId(String userId, String roomId) {
        Assert.hasText(userId, "You need to register to send chat");
        Assert.hasText(roomId, "You need to register to send chat");
    }

    private ChatUser findChatUserById(String userId) {
        return chatUserRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("Sender not found"));
    }

    private ChatRoom findChatRoomById(String roomId) {
        return chatRoomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new RuntimeException("No such chat room is available"));
    }

    private void saveChatMessage(ChatMessage chatMessage, ChatUser sender, ChatRoom chatRoom) {
        ChatLine chatLine = new ChatLine();
        chatLine.setChatRoom(chatRoom);
        chatLine.setChatUser(sender);
        chatLine.setText(chatMessage.getContent());
        chatLineRepository.save(chatLine);
    }
}
