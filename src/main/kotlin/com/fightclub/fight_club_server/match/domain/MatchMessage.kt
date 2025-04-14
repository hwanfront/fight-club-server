package com.fightclub.fight_club_server.match.domain

import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "match_message")
class MatchMessage (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val match: Match,

    @ManyToOne(fetch = FetchType.LAZY)
    val sender: User,

    val content: String,

    val sentAt: LocalDateTime = LocalDateTime.now()
)