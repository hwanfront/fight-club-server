package com.fightclub.fight_club_server.notification.domain

import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "toast_notification",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "type"])]
)
class ToastNotification (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    val type: NotificationType,

    var message: String,

    var updateAt: LocalDateTime = LocalDateTime.now(),
) {
    fun updateMessage(message: String) {
        this.message = message
        this.updateAt = LocalDateTime.now()
    }
}