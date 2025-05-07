package com.fightclub.fight_club_server.notification.mapper

import com.fightclub.fight_club_server.notification.domain.ToastNotification
import com.fightclub.fight_club_server.notification.dto.ToastNotificationPayload
import org.springframework.stereotype.Component

@Component
class ToastNotificationMapper {
    fun toPayload(toastNotification: ToastNotification): ToastNotificationPayload {
        return ToastNotificationPayload(
            type = toastNotification.type,
            message = toastNotification.message,
            updateAt = toastNotification.updateAt
        )
    }
}