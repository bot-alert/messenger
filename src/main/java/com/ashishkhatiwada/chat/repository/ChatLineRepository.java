package com.ashishkhatiwada.chat.repository;

import com.ashishkhatiwada.chat.entity.ChatLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLineRepository extends JpaRepository<ChatLine, Long> {
}

