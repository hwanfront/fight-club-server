package com.fightclub.fight_club_server.match.dto

data class TypingStatusResponse(
    val userId: Long,
    val isTyping: Boolean
)
