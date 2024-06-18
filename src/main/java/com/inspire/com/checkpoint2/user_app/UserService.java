package com.inspire.com.checkpoint2.user_app;

import com.inspire.com.checkpoint2.util.CustomPasswordEncoder;
import com.inspire.com.checkpoint2.util.JwtService;
import lombok.Data;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserApp createUser(UserDTO userDTO) {
        String hashedPassword = CustomPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);
        return userRepository.save(UserDTO.toEntity(userDTO));
    }

    public ResponseAuth findUserByEmail(String email, String password) {
        UserApp user = userRepository.findByEmail(email).get();
        if( CustomPasswordEncoder.matches(password , user.getPassword())){
            UserDTO userDTO = UserDTO.fromEntity(user);
            Map<String, Object> extra = new HashMap<>();
            extra.put("email", email);
            String token = jwtService.generateToken(extra, user);
            return ResponseAuth.builder()
                    .token(token)
                    .message("Connected successfully")
                    .userDTO(userDTO)
                    .build();
        }
        return ResponseAuth.builder()
                .token("")
                .message("Not Connected successfully")
                .userDTO(null)
                .build();
    }

    public UserApp test(String email) {
        return userRepository.findByEmail(email).get();
    }

}
