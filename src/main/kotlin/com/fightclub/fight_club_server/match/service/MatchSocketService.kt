package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.dto.DeclineMatchResponse
import com.fightclub.fight_club_server.match.dto.DeclineRequest
import com.fightclub.fight_club_server.match.dto.MatchReadyResponse
import com.fightclub.fight_club_server.match.dto.ReadyRequest
import com.fightclub.fight_club_server.match.exception.MatchNotFoundException
import com.fightclub.fight_club_server.match.exception.UserIsNotParticipantException
import com.fightclub.fight_club_server.match.mapper.MatchMapper
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.user.domain.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MatchSocketService(
    private val matchRepository: MatchRepository,
    private val matchMapper: MatchMapper
) {
    @Transactional
    fun updateReadyStatus(user: User, readyRequest: ReadyRequest): MatchReadyResponse {
        val match = matchRepository.findByIdWithLock(readyRequest.matchId).orElseThrow { throw MatchNotFoundException() }

        if (!match.isParticipant(user)) {
            throw UserIsNotParticipantException()
        }

        match.updateReadyStatus(user)
        matchRepository.save(match)

        return matchMapper.toMatchReadyResponse(match, user)
    }

    @Transactional
    fun declineMatch(user: User, declineRequest: DeclineRequest): DeclineMatchResponse {
        val match = matchRepository.findByIdWithLock(declineRequest.matchId).orElseThrow { throw MatchNotFoundException() }

        if (!match.isParticipant(user)) {
            throw UserIsNotParticipantException()
        }

        match.declineMatch()
        matchRepository.save(match)

        return matchMapper.toDeclineMatchResponse(match, user)
    }
}