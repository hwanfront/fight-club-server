package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchRepository: JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.user1.id = :userId OR m.user2.id = :userId")
    fun findMatchesByUserId(@Param("userId") userId: Long): List<Match>
}