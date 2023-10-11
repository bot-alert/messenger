package com.ashishkhatiwada.chat.repository;

import com.ashishkhatiwada.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    @Query("SELECT DISTINCT cr FROM ChatRoom cr JOIN FETCH cr.users WHERE cr IN (SELECT c FROM ChatRoom c JOIN c.users u WHERE u.id = :userId)")
    List<ChatRoom> findAllByChatUser(@Param("userId") UUID userId);
}

