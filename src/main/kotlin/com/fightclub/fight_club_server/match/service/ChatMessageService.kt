package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.ChatMessage
import com.fightclub.fight_club_server.match.dto.ChatMessageRequest
import com.fightclub.fight_club_server.match.dto.ChatMessageResponse
import com.fightclub.fight_club_server.match.exception.MatchNotFoundSocketException
import com.fightclub.fight_club_server.match.exception.UserIsNotParticipantSocketException
import com.fightclub.fight_club_server.match.mapper.ChatMessageMapper
import com.fightclub.fight_club_server.match.repository.ChatMessageRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val chatMessageMapper: ChatMessageMapper,
    private val matchRepository: MatchRepository
) {

    fun getLatestMessageList(matchId: Long, size: Int): List<ChatMessageResponse> {
        val chatMessages = chatMessageRepository.findByMatchIdOrderByIdDesc(matchId, PageRequest.of(0, size))
        return chatMessages.map(chatMessageMapper::toResponse)
    }

    fun getBeforeMessageList(matchId: Long, beforeMessageId: Long, size: Int): List<ChatMessageResponse> {
        val chatMessages = chatMessageRepository.findByMatchIdAndIdLessThanOrderByIdDesc(matchId, beforeMessageId, PageRequest.of(0, size))
        return chatMessages.map(chatMessageMapper::toResponse)
    }

    fun saveChatMessage(user: User, chatMessageRequest: ChatMessageRequest): ChatMessageResponse {
        val match = matchRepository.findById(chatMessageRequest.matchId).orElseThrow { throw MatchNotFoundSocketException() }

        if (!match.isParticipant(user)) {
            throw UserIsNotParticipantSocketException()
        }

        val chatMessage = chatMessageRepository.save(
            ChatMessage(
                match = match,
                sender = user,
                content = chatMessageRequest.content,
                messageType = chatMessageRequest.messageType,
            )
        )

        return chatMessageMapper.toResponse(chatMessage)
    }
}
