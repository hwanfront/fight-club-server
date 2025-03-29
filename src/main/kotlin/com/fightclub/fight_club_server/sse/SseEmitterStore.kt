package com.fightclub.fight_club_server.sse

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Component
class SseEmitterStore {
    private val emitters: MutableMap<Long, SseEmitter> = ConcurrentHashMap()

    fun add(userId: Long, emitter: SseEmitter) {
        emitters[userId] = emitter
    }

    fun get(userId: Long): SseEmitter? = emitters[userId]

    fun remove(userId: Long) {
        emitters.remove(userId)
    }
}