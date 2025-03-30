package com.fightclub.fight_club_server.notification.controller

import com.fightclub.fight_club_server.common.dto.ApiResponse
import com.fightclub.fight_club_server.notification.constants.NotificationSuccessCode
import com.fightclub.fight_club_server.notification.domain.NotificationType
import com.fightclub.fight_club_server.notification.service.NotificationService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/notifications")
class NotificationController(
    private val notificationService: NotificationService
) {
    @DeleteMapping("/toast")
    fun deleteToastNotifications(@RequestParam type: NotificationType) = ApiResponse.success(NotificationSuccessCode.DELETE_TOAST_NOTIFICATIONS_SUCCESS, notificationService.deleteToastNotification(type))
}