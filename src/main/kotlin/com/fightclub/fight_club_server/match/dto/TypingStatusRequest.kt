package com.fightclub.fight_club_server.match.dto

data class TypingStatusRequest(
    val matchId: Long,
    val isTyping: Boolean
)
