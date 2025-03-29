package com.fightclub.fight_club_server.jwt.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var tokenValue: String,

    var createdAt: LocalDateTime = LocalDateTime.now()
)