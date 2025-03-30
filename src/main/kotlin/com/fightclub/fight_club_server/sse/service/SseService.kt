package com.fightclub.fight_club_server.sse.service

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.sse.SseEmitterStore
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class SseService(
    private val sseEmitterStore: SseEmitterStore,
    private val notificationService: NotificationService
) {
    fun connect(user: User): SseEmitter {
        val userId = user.id!!
        val emitter = SseEmitter(60 * 1000L)

        sseEmitterStore.add(userId, emitter)

        emitter.onCompletion { sseEmitterStore.remove(userId) }
        emitter.onTimeout { sseEmitterStore.remove(userId) }

        notificationService.sendAllActiveToastNotifications(user, emitter)

        return emitter
    }
}