package com.fightclub.fight_club_server.matchProposal.domain

import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "match_proposal",
    uniqueConstraints = [UniqueConstraint(columnNames = ["sender_id", "receiver_id"])]
)
class MatchProposal(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val sender: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val receiver: User,

    var senderWeight: Double,

    var receiverWeight: Double,

    @Enumerated(EnumType.STRING)
    var weightClass: WeightClass,

    val requestedAt: LocalDateTime = LocalDateTime.now()
) {
}