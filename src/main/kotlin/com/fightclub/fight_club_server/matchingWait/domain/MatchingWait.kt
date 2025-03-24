package com.fightclub.fight_club_server.matchingWait.domain

import com.fightclub.fight_club_server.meta.enums.WeightClass
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "matching_wait")
class MatchingWait (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long = 0L,

    val weight: Double = 0.0,

    val weightClass: WeightClass,

    val createdAt: LocalDateTime = LocalDateTime.now()
)