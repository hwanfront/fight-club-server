package com.fightclub.fight_club_server.matchingWait.mapper

import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import com.fightclub.fight_club_server.matchingWait.dto.MatchingCandidateProjection
import com.fightclub.fight_club_server.matchingWait.dto.MatchingCandidateResponse
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitResponse
import com.fightclub.fight_club_server.meta.enums.WeightClass
import org.springframework.stereotype.Component

@Component
class MatchingWaitMapper {
    fun toResponse(matchingWait: MatchingWait): MatchingWaitResponse {
        return MatchingWaitResponse(
            weight = matchingWait.weight,
            weightClass = matchingWait.weightClass,
            createdAt = matchingWait.createdAt,
        )
    }

    fun toCandidateResponse(candidateProjection: MatchingCandidateProjection): MatchingCandidateResponse {
        return MatchingCandidateResponse(
            userId = candidateProjection.getUserId(),
            nickname = candidateProjection.getNickname(),
            weight = candidateProjection.getWeight(),
            weightClass = WeightClass.fromName(candidateProjection.getWeightClass()),
        )
    }
}