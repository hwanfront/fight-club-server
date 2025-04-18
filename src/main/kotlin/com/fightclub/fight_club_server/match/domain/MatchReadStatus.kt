package com.fightclub.fight_club_server.match.domain

import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*

@Entity
class MatchReadStatus(

    @EmbeddedId
    val id: MatchReadStatusId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("matchId")
    val match: Match,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    val user: User,

    var lastReadMessageId: Long?
) {
    fun updateLastReadMessage(messageId: Long) {
        this.lastReadMessageId = messageId
    }
}