package com.inspire.com.checkpoint2.user_app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAuth {
    private UserDTO userDTO;
    private String token;
    private String message;
}
