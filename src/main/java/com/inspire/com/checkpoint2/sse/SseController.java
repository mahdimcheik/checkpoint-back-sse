package com.inspire.com.checkpoint2.sse;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@Data
public class SseController {
    private final SseService sseService;
    private SseEmitter emitter;
    private Long lastId = 0L;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(600000L);
        try {
            System.out.println("emitter started " + emitter.toString());
            emitter.send(SseEmitter.event()
                    .name("message")
                    .id("" + lastId++)
                    .data("connexion"));
            sseService.addEmitter(emitter);
            return emitter;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/subscribe/{email}")
    public SseEmitter subscribe(@PathVariable String email) {
        SseEmitter emitter = new SseEmitter(6000000L);
        try {
            System.out.println("emitter started " + emitter.toString());
            emitter.send(SseEmitter.event()
                    .name("message")
                    .id("" + lastId++)
                    .data("connexion"));
            sseService.addEmitter(email,emitter);
            return emitter;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/invoke")
    public String invokeAll() {
        sseService.sendEvents();
        return "Started";
    }

    @GetMapping("/invoke/{email}")
    public String invokeAll(@PathVariable String email) {
        sseService.sendEvents(email);
        return "Started";
    }
}
