package com.fightclub.fight_club_server.match.dto

data class ChatMessageRequest(
    val matchId: Long,
    val senderId: Long,
    val content: String,
)