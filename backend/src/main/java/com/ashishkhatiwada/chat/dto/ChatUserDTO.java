package com.ashishkhatiwada.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUserDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String vendorId;
    private String id;
    private List<String> chatRooms;
}
