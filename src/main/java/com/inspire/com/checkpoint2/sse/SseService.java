package com.inspire.com.checkpoint2.sse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<SseEmitter>();
    private final Map<String, SseEmitter> emittersMap = new HashMap<String, SseEmitter>();

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> {
            System.out.println("complete");
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> emitters.remove(emitter));
    }
    public void addEmitter(String email,SseEmitter emitter) {

        emittersMap.remove(email);
        emittersMap.put(email, emitter);
        emitter.onCompletion(() -> {
            System.out.println("complete");
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    // @Scheduled(fixedRate = 1000)
    public void sendEvents() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send("lol " + System.currentTimeMillis());
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }

    public void sendEvents(String email) {
            try {
                emittersMap.get(email).send("lol " + System.currentTimeMillis());
            } catch (IOException e) {
                emittersMap.get(email).complete();
                emittersMap.remove(email);
            }

    }
}
