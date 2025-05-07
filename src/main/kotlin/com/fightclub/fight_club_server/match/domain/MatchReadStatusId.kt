package com.fightclub.fight_club_server.match.domain

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class MatchReadStatusId(
    val matchId: Long,
    val userId: Long,
): Serializable
