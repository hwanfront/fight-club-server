package com.fightclub.fight_club_server.notification.dto

import com.fightclub.fight_club_server.notification.domain.NotificationType
import java.time.LocalDateTime

data class ToastNotificationPayload(
    val type: NotificationType,
    val message: String,
    val updateAt: LocalDateTime
)
