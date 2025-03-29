package com.fightclub.fight_club_server.sse.service

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.sse.SseEmitterStore
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class SseService(
    private val sseEmitterStore: SseEmitterStore,
) {
    fun connect(): SseEmitter {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()
        val userId = user.id!!
        val emitter = SseEmitter(60 * 1000L)

        sseEmitterStore.add(userId, emitter)

        emitter.onCompletion { sseEmitterStore.remove(userId) }
        emitter.onTimeout { sseEmitterStore.remove(userId) }

        return emitter
    }
}