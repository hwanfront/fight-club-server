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

    var weight: Double = 0.0,

    var weightClass: WeightClass,

    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateMatchingWait(weight: Double, weightClass: WeightClass) {
        this.weight = weight
        this.weightClass = weightClass
        this.createdAt = LocalDateTime.now()
    }

}