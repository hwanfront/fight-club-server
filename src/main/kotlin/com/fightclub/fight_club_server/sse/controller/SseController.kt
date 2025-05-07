package com.fightclub.fight_club_server.sse.controller

import com.fightclub.fight_club_server.sse.service.SseService
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/sse")
class SseController(
    private val sseService: SseService
) {

    @GetMapping("/connect")
    @PreAuthorize("isAuthenticated()")
    fun connect(@AuthenticationPrincipal user: User): SseEmitter {
        return sseService.connect(user)
    }
}