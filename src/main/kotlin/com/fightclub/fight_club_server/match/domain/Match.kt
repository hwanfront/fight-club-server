package com.fightclub.fight_club_server.match.domain

import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
class Match(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val user1: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val user2: User,

    @Enumerated(EnumType.STRING)
    var status: MatchStatus = MatchStatus.CHATTING,

    @Enumerated(EnumType.STRING)
    var readyStatus: MatchReadyStatus = MatchReadyStatus.NONE,

    @Enumerated(EnumType.STRING)
    val weightClass: WeightClass,

    val user1Weight: Double,

    val user2Weight: Double,

    val matchedAt: LocalDateTime = LocalDateTime.now(),
)