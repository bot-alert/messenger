package com.ashishkhatiwada.chat.repository;

import com.ashishkhatiwada.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, UUID> {
    @Query("SELECT DISTINCT cu FROM ChatUser cu LEFT JOIN FETCH cu.chatRooms cr WHERE cr.id = :roomId")
    List<ChatUser> findAllByChatRoom(@Param("roomId") UUID roomId);
}
