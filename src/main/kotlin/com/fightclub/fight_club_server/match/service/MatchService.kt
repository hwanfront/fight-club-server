package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.MatchStatus
import com.fightclub.fight_club_server.match.dto.*
import com.fightclub.fight_club_server.match.exception.MatchNotFoundException
import com.fightclub.fight_club_server.match.exception.MatchNotFoundSocketException
import com.fightclub.fight_club_server.match.exception.UserIsNotParticipantSocketException
import com.fightclub.fight_club_server.match.mapper.MatchMapper
import com.fightclub.fight_club_server.match.repository.ChatMessageRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.user.domain.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val matchMapper: MatchMapper,
) {

    fun getMatchList(user: User): List<MatchResponse> {
        val userId = user.id!!
        val matchList = matchRepository.findMatchesByUserId(userId)
        return matchList.map { match -> matchMapper.toResponse(match, user) }
    }

    fun getMatchInfo(matchId: Long, user: User): MatchInfoResponse {
        val match = matchRepository.findById(matchId).orElseThrow { MatchNotFoundException() }

        if(!match.isParticipant(user)) {
            throw UserIsNotParticipantSocketException()
        }

        return matchMapper.toInfoResponse(match, user)
    }

    @Transactional
    fun updateReadyStatus(user: User, readyRequest: ReadyRequest): MatchReadyResponse {
        val match = matchRepository.findByIdWithLock(readyRequest.matchId).orElseThrow { throw MatchNotFoundSocketException() }

        if (!match.isParticipant(user)) {
            throw UserIsNotParticipantSocketException()
        }

        match.updateReadyStatus(user)
        matchRepository.save(match)

        return matchMapper.toMatchReadyResponse(match, user)
    }

    @Transactional
    fun declineMatch(user: User, declineRequest: DeclineRequest): DeclineMatchResponse {
        val match = matchRepository.findByIdWithLock(declineRequest.matchId).orElseThrow { throw MatchNotFoundSocketException() }

        if (!match.isParticipant(user)) {
            throw UserIsNotParticipantSocketException()
        }

        if (match.status == MatchStatus.DECLINED) {
            throw MatchNotFoundSocketException()
        }

        match.declineMatch()
        matchRepository.save(match)

        return matchMapper.toDeclineMatchResponse(match, user)
    }

    fun startMatchStreaming(matchId: Long, user: User): Unit {

    }

    fun pauseMatchStreaming(matchId: Long, user: User): Unit {

    }

    fun resumeMatchStreaming(matchId: Long, user: User): Unit {

    }

    fun endMatchStreaming(matchId: Long, user: User): Unit {

    }

}
