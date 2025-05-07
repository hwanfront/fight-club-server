package com.fightclub.fight_club_server.security.jwt.repository

import com.fightclub.fight_club_server.security.jwt.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByUserId(userId: Long): RefreshToken?
    fun deleteByUserId(userId: Long)
}