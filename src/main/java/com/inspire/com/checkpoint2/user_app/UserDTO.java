package com.inspire.com.checkpoint2.user_app;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
@Builder
public class UserDTO {
    private String email;
    private String password;

    public static UserDTO fromEntity(UserApp user ) {
        return UserDTO.builder()
                .email(user.getEmail())
                .build();
    }
    public static UserApp toEntity(UserDTO dto) {
        return UserApp.builder()
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();
    }
}
