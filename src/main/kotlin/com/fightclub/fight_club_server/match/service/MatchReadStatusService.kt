package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.MatchReadStatus
import com.fightclub.fight_club_server.match.domain.MatchReadStatusId
import com.fightclub.fight_club_server.match.dto.ReadChatMessageRequest
import com.fightclub.fight_club_server.match.dto.ReadChatMessageResponse
import com.fightclub.fight_club_server.match.exception.*
import com.fightclub.fight_club_server.match.repository.MatchReadStatusRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.user.domain.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MatchReadStatusService(
    private val matchReadStatusRepository: MatchReadStatusRepository,
    private val matchRepository: MatchRepository
) {

    @Transactional
    fun updateLastReadMessage(readChatMessageRequest: ReadChatMessageRequest, user: User): ReadChatMessageResponse {
        val match = matchRepository.findById(readChatMessageRequest.matchId).orElseThrow { throw MatchNotFoundSocketException() }

        if (!match.isParticipant(user)) {
            throw UserIsNotParticipantSocketException()
        }

        val userId = user.id!!
        val key = MatchReadStatusId(readChatMessageRequest.matchId, userId)
        val matchReadStatus = matchReadStatusRepository.findById(key).orElse(null)

        if (matchReadStatus == null) {
            matchReadStatusRepository.save(
                MatchReadStatus(
                    id = key,
                    match = match,
                    user = user,
                    lastReadMessageId = readChatMessageRequest.messageId,
                )
            )

            return ReadChatMessageResponse(
                lastReadMessageId = readChatMessageRequest.messageId
            )
        }

        if (matchReadStatus.lastReadMessageId != null && matchReadStatus.lastReadMessageId!! >= readChatMessageRequest.messageId) {
            throw UnnecessaryReadUpdateSocketException()
        }

        matchReadStatus.updateLastReadMessage(readChatMessageRequest.messageId)

        matchReadStatusRepository.save(matchReadStatus)

        return ReadChatMessageResponse(
            lastReadMessageId = readChatMessageRequest.messageId
        )
    }
}