package com.fightclub.fight_club_server.notification.domain

import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @Enumerated(EnumType.STRING)
    val type: NotificationType,

    val message: String,

    var isRead: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

}
