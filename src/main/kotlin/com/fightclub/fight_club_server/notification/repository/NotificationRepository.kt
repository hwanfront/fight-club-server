package com.fightclub.fight_club_server.notification.repository

import com.fightclub.fight_club_server.notification.domain.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long> {
}
