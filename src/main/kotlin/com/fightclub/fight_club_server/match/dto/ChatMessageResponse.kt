package com.fightclub.fight_club_server.match.dto

import com.fightclub.fight_club_server.match.domain.ChatMessageType
import java.time.LocalDateTime

data class ChatMessageResponse(
    val id: Long?,
    val senderId: Long?,
    val senderNickname: String?,
    val content: String,
    val messageType: ChatMessageType,
    val sentAt: LocalDateTime,
)
