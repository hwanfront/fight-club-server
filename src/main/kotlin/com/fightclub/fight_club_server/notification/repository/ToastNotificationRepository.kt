package com.fightclub.fight_club_server.notification.repository

import com.fightclub.fight_club_server.notification.domain.NotificationType
import com.fightclub.fight_club_server.notification.domain.ToastNotification
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface ToastNotificationRepository: JpaRepository<ToastNotification, Long> {
    fun findByUserAndType(user: User, type: NotificationType): ToastNotification?
}