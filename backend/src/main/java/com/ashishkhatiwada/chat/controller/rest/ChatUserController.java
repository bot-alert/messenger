package com.ashishkhatiwada.chat.controller.rest;

import com.ashishkhatiwada.chat.dto.ChatUserDTO;
import com.ashishkhatiwada.chat.entity.ChatUser;
import com.ashishkhatiwada.chat.service.ChatUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-user")
@RequiredArgsConstructor
public class ChatUserController {
    private final ChatUserService chatUserService;

    @PostMapping
    public ResponseEntity<ChatUserDTO> save(@RequestBody ChatUserDTO chatUserDTO) throws URISyntaxException {
        ChatUserDTO savedUser = chatUserService.save(chatUserDTO);
        return ResponseEntity.created(new URI("/api/v1/chat-user/" + savedUser.getId()))
                .body(savedUser);
    }

    @PutMapping
    public ResponseEntity<ChatUserDTO> update(@RequestBody ChatUserDTO chatUserDTO) {
        return ResponseEntity.ok(chatUserService.update(chatUserDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatUserDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(chatUserService.getById(id));
    }

    @GetMapping
    public List<ChatUser> getAll() {
        return chatUserService.findAll();
    }

    @GetMapping("/{id}/room")
    public Object getUserRoom(@PathVariable String id) {
        return chatUserService.getUserRoom(id);
    }
}
