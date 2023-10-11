package com.ashishkhatiwada.chat.controller.rest;

import com.ashishkhatiwada.chat.entity.ChatRoom;
import com.ashishkhatiwada.chat.entity.ChatUser;
import com.ashishkhatiwada.chat.repository.ChatRoomRepository;
import com.ashishkhatiwada.chat.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;

    @PostMapping("/generate")
    public ResponseEntity<Object> getById(@RequestParam List<String> userList) {
        ChatRoom chatRoom = new ChatRoom();
        List<ChatUser> chatUserList = chatUserRepository.findAllById(userList.stream().map(UUID::fromString).toList());
        chatRoom.setUsers(new HashSet<>(chatUserList));
        return ResponseEntity.ok(chatRoomRepository.save(chatRoom));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<Object> getAll(@PathVariable String id) {
        ChatRoom chatRoom = chatRoomRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ChatUser> chatUserList = chatUserRepository.findAllByChatRoom(chatRoom.getId());
        return ResponseEntity.ok(chatUserList);
    }
}
