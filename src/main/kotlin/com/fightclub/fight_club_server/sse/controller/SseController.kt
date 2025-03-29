package com.fightclub.fight_club_server.sse.controller

import com.fightclub.fight_club_server.sse.service.SseService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/sse")
class SseController(
    private val sseService: SseService
) {
    @GetMapping("/connect")
    fun connect(): SseEmitter {
        return sseService.connect()
    }
}