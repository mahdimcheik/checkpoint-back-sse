package com.inspire.com.checkpoint2.user_app;

import com.inspire.com.checkpoint2.util.JwtService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.Consumer;

@RestController
@Data
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public UserApp register(@RequestBody UserDTO newUser) {
         return userService.createUser(newUser);
    }

    @PostMapping("/login")
    public ResponseAuth login(@RequestBody UserDTO newUser) {
        return userService.findUserByEmail(newUser.getEmail(),newUser.getPassword());
    }
    @GetMapping("/get/{email}")
    public UserApp getUser(@PathVariable String email) {
        return userService.test(email);
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build());
    }
}
