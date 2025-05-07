package com.fightclub.fight_club_server.matchingWait.domain

import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "matching_wait")
class MatchingWait (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    var weight: Double = 0.0,

    @Enumerated(EnumType.STRING)
    var weightClass: WeightClass,

    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateMatchingWait(weight: Double, weightClass: WeightClass) {
        this.weight = weight
        this.weightClass = weightClass
        this.createdAt = LocalDateTime.now()
    }
}