package com.ashishkhatiwada.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chat_line")
public class ChatLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private ChatUser chatUser;

    private String text;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long updatedAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) this.createdAt = new Date().getTime();
        if (this.updatedAt == null) this.updatedAt = new Date().getTime();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date().getTime();
    }
}
