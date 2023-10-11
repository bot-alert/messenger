package com.ashishkhatiwada.chat.service.impl;

import com.ashishkhatiwada.chat.dto.ChatUserDTO;
import com.ashishkhatiwada.chat.entity.ChatUser;
import com.ashishkhatiwada.chat.repository.ChatRoomRepository;
import com.ashishkhatiwada.chat.repository.ChatUserRepository;
import com.ashishkhatiwada.chat.service.ChatUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatUserServiceImpl implements ChatUserService {
    private final ChatUserRepository chatUserRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatUserDTO save(ChatUserDTO chatUserDTO) {
        ChatUser chatUser = ChatUser.fromChatUserDTO(chatUserDTO);
        chatUserRepository.save(chatUser);
        chatUserDTO.setId(String.valueOf(chatUser.getId()));
        return chatUserDTO;
    }

    @Override
    public ChatUserDTO update(ChatUserDTO chatUserDTO) {
        ChatUser chatUserFromDb = chatUserRepository.findById(UUID.fromString(chatUserDTO.getId()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        chatUserFromDb.setName(chatUserDTO.getName());
        chatUserFromDb.setVendorId(chatUserDTO.getVendorId());
        chatUserRepository.save(chatUserFromDb);
        return chatUserDTO;
    }

    @Override
    public ChatUserDTO getById(String id) {
        ChatUser chatUser = chatUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ChatUserDTO.builder()
                .name(chatUser.getName())
                .vendorId(chatUser.getVendorId())
                .id(id)
                .build();
    }

    @Override
    public Object getUserRoom(String id) {
        ChatUser chatUser = chatUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatRoomRepository.findAllByChatUser(chatUser.getId());
    }

    @Override
    public List<ChatUser> findAll() {
        return chatUserRepository.findAll();
    }
}
