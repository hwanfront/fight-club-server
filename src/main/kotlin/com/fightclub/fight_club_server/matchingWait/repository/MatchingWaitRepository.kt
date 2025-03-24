package com.fightclub.fight_club_server.matchingWait.repository

import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import org.springframework.data.jpa.repository.JpaRepository

interface MatchingWaitRepository: JpaRepository<MatchingWait, Long> {
    fun findByUserId(userId: Long): MatchingWait?
    fun existsByUserId(userId: Long): Boolean
}