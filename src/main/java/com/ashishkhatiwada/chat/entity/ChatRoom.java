package com.ashishkhatiwada.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "chat_room_users",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_user_id")
    )
    private Set<ChatUser> users = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom")
    @JsonIgnore
    private List<ChatLine> chatLineList = new ArrayList<>();

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



