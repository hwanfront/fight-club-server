package com.fightclub.fight_club_server.match.domain

import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "matches")
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

    val matchedAt: LocalDateTime = LocalDateTime.now(),
)