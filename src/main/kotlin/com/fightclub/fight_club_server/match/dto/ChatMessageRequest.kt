package com.fightclub.fight_club_server.match.dto

import com.fightclub.fight_club_server.match.domain.ChatMessageType

data class ChatMessageRequest(
    val matchId: Long,
    val content: String,
    val messageType: ChatMessageType,
)