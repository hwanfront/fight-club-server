package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.MatchMessage
import com.fightclub.fight_club_server.match.dto.MatchInfoResponse
import com.fightclub.fight_club_server.match.dto.MatchResponse
import com.fightclub.fight_club_server.match.mapper.MatchMapper
import com.fightclub.fight_club_server.match.repository.MatchMessageRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.stereotype.Service

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val matchMessageRepository: MatchMessageRepository,
    private val matchMapper: MatchMapper,
) {

    fun getMatchList(user: User): List<MatchResponse> {
        val userId = user.id!!
        val matchList = matchRepository.findMatchesByUserId(userId)
        return matchList.map { match -> matchMapper.toResponse(match, user) }
    }

    fun getMatchInfo(matchId: Long, user: User): MatchInfoResponse {
        return MatchInfoResponse(
            matchId = TODO(),
            myNickname = TODO(),
            myWeight = TODO(),
            opponentNickname = TODO(),
            opponentWeight = TODO(),
            weightClass = TODO(),
            isMeReady = TODO(),
            isOpponentReady = TODO(),
            matchedAt = TODO()
        )
    }

    fun readyToStream(matchId: Long, user: User): Unit {

    }

    fun declineMatch(matchId: Long, user: User): Unit {

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
