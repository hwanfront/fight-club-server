package com.fightclub.fight_club_server.notification.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class NotificationErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    TOAST_NOTIFICATIONS_NOT_FOUND(HttpStatus.NOT_FOUND,"TNE001", "토스트 알림이 존재하지 않습니다."),
}