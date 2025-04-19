package com.fightclub.fight_club_server.match.mapper

import com.fightclub.fight_club_server.match.domain.ChatMessage
import com.fightclub.fight_club_server.match.dto.ChatMessageResponse
import org.springframework.stereotype.Component

@Component
class ChatMessageMapper {
    fun toResponse(chatMessage: ChatMessage): ChatMessageResponse {
        return ChatMessageResponse(
            id = chatMessage.id,
            senderId = chatMessage.sender.id,
            senderNickname = chatMessage.sender.nickname,
            content = chatMessage.content,
            messageType = chatMessage.messageType,
            sentAt = chatMessage.sentAt
        )
    }
}