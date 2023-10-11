package com.ashishkhatiwada.chat.service;

import com.ashishkhatiwada.chat.dto.ChatUserDTO;
import com.ashishkhatiwada.chat.entity.ChatUser;

import java.util.List;

public interface ChatUserService {
    ChatUserDTO save(ChatUserDTO chatUserDTO);

    ChatUserDTO update(ChatUserDTO chatUserDTO);

    ChatUserDTO getById(String id);

    Object getUserRoom(String id);

    List<ChatUser> findAll();
}
