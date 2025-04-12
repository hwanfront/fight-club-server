package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.constants.MatchConstants
import com.fightclub.fight_club_server.match.dto.MatchInfoResponse
import com.fightclub.fight_club_server.match.dto.MatchResponse
import com.fightclub.fight_club_server.match.exception.MatchNotFoundException
import com.fightclub.fight_club_server.match.exception.UserIsNotParticipantException
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
        val match = matchRepository.findById(matchId).orElseThrow { MatchNotFoundException() }

        if(match.user1.id != user.id && match.user2.id != user.id) {
            throw UserIsNotParticipantException()
        }

        return matchMapper.toInfoResponse(match, user)
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
