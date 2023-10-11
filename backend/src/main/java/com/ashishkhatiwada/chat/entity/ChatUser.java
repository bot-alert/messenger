package com.ashishkhatiwada.chat.entity;

import com.ashishkhatiwada.chat.dto.ChatUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chat_user")
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String vendorId;

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<ChatRoom> chatRooms = new HashSet<>();

    @OneToMany(mappedBy = "chatUser")
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


    public static ChatUser fromChatUserDTO(ChatUserDTO chatUserDTO) {
        ChatUser chatUser = new ChatUser();
        chatUser.setName(chatUserDTO.getName());
        chatUser.setVendorId(chatUserDTO.getVendorId());
        return chatUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatUser chatUser = (ChatUser) o;

        if (!Objects.equals(name, chatUser.name)) return false;
        return Objects.equals(vendorId, chatUser.vendorId);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (vendorId != null ? vendorId.hashCode() : 0);
        return result;
    }
}



