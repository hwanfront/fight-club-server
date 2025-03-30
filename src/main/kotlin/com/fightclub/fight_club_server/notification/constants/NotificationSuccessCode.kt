package com.fightclub.fight_club_server.notification.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class NotificationSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    DELETE_TOAST_NOTIFICATIONS_SUCCESS(HttpStatus.OK,"TNS001", "토스트 알림을 삭제했습니다."),
}