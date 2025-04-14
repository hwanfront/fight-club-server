package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.Match
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface MatchRepository: JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE (m.user1 = :user OR m.user2 = :user) AND m.status <> 'DECLINED'")
    fun findMatchesByUserId(@Param("userId") userId: Long): List<Match>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Match m WHERE m.id = :id")
    fun findByIdWithLock(@Param("id") id: Long): Optional<Match>
}