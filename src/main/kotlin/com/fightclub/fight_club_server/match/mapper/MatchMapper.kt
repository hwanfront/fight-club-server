package com.fightclub.fight_club_server.match.mapper

import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.domain.MatchReadyStatus
import com.fightclub.fight_club_server.match.dto.MatchInfoResponse
import com.fightclub.fight_club_server.match.dto.MatchReadyResponse
import com.fightclub.fight_club_server.match.dto.MatchResponse
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.stereotype.Component

@Component
class MatchMapper {
    fun toResponse(match: Match, user: User): MatchResponse {
        val (opponentNickname, opponentWeight) = if (match.user1.id == user.id) {
            match.user2.nickname to match.user2Weight
        } else {
            match.user1.nickname to match.user1Weight
        }

        val (isMeReady, isOpponentReady) = getReadyStatus(match, user)

        return MatchResponse(
            matchId = match.id,
            opponentNickname = opponentNickname,
            opponentWeight = opponentWeight,
            weightClass = match.weightClass,

//            recentMessage = TODO(),
            unreadMessageCount = 0,
//            lastMessageTime = TODO(),

            isMeReady = isMeReady,
            isOpponentReady = isOpponentReady,
        )
    }

    fun toInfoResponse(match: Match, user: User): MatchInfoResponse {
        val (myWeight, opponentNickname, opponentWeight) = if (match.user1.id == user.id) {
            Triple(match.user1Weight, match.user2.nickname, match.user2Weight)
        } else {
            Triple(match.user2Weight, match.user1.nickname, match.user1Weight)
        }

        val (isMeReady, isOpponentReady) = getReadyStatus(match, user)

        return MatchInfoResponse(
            matchId = match.id,
            myNickname = user.nickname,
            myWeight = myWeight,
            opponentNickname = opponentNickname,
            opponentWeight = opponentWeight,
            weightClass = match.weightClass,
            matchStatus = match.status,
            isMeReady = isMeReady,
            isOpponentReady = isOpponentReady,
            matchedAt = match.matchedAt,
        )
    }

    fun toMatchReadyResponse(match: Match, user: User): MatchReadyResponse {
        val (isMeReady, isOpponentReady) = getReadyStatus(match, user)

        return MatchReadyResponse(
            matchId = match.id!!,
            isMeReady = isMeReady,
            isOpponentReady = isOpponentReady,
        )
    }

    private fun getReadyStatus(match: Match, user: User): Pair<Boolean, Boolean> {
        return when {
            match.user1.id == user.id -> {
                when (match.readyStatus) {
                    MatchReadyStatus.NONE -> false to false
                    MatchReadyStatus.USER1_READY -> true to false
                    MatchReadyStatus.USER2_READY -> false to true
                    MatchReadyStatus.ALL_READY -> true to true
                }
            }
            else -> {
                when (match.readyStatus) {
                    MatchReadyStatus.NONE -> false to false
                    MatchReadyStatus.USER1_READY -> false to true
                    MatchReadyStatus.USER2_READY -> true to false
                    MatchReadyStatus.ALL_READY -> true to true
                }
            }
        }
    }
}