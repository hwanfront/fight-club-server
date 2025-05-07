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
) {
    fun isParticipant(user: User): Boolean {
        return listOfNotNull(user1.id, user2.id).contains(user.id)
    }

    fun updateReadyStatus(user: User) {
        val isUser1 = this.user1.id == user.id

        this.readyStatus = when(this.readyStatus) {
            MatchReadyStatus.NONE -> if (isUser1) MatchReadyStatus.USER1_READY else MatchReadyStatus.USER2_READY
            MatchReadyStatus.USER1_READY -> if (isUser1) MatchReadyStatus.USER1_READY else MatchReadyStatus.ALL_READY
            MatchReadyStatus.USER2_READY -> if (isUser1) MatchReadyStatus.ALL_READY else MatchReadyStatus.USER2_READY
            MatchReadyStatus.ALL_READY -> MatchReadyStatus.ALL_READY
        }

        if(this.readyStatus == MatchReadyStatus.ALL_READY) {
            this.status = MatchStatus.READY_TO_STREAM
        } else {
            this.status = MatchStatus.CHATTING
        }
    }

    fun declineMatch() {
        this.readyStatus = MatchReadyStatus.NONE
        this.status = MatchStatus.DECLINED
    }
}