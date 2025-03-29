package com.fightclub.fight_club_server.notification.service

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.notification.domain.Notification
import com.fightclub.fight_club_server.notification.domain.NotificationType
import com.fightclub.fight_club_server.notification.domain.ToastNotification
import com.fightclub.fight_club_server.notification.exception.ToastNotificationNotFoundException
import com.fightclub.fight_club_server.notification.mapper.ToastNotificationMapper
import com.fightclub.fight_club_server.notification.repository.NotificationRepository
import com.fightclub.fight_club_server.notification.repository.ToastNotificationRepository
import com.fightclub.fight_club_server.sse.SseEmitterStore
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val sseEmitterStore: SseEmitterStore,
    private val toastNotificationRepository: ToastNotificationRepository,
    private val toastNotificationMapper: ToastNotificationMapper
) {

    fun notifyMatchProposal(matchProposal: MatchProposal) {
        sendToast(matchProposal.receiver, NotificationType.MATCH_PROPOSAL, "스파링 제안이 도착했습니다.")
        saveDetail(matchProposal.receiver, NotificationType.MATCH_PROPOSAL, "${matchProposal.sender.nickname} 님의 스파링 제안이 도착했습니다.")
    }

    fun sendAllActiveToastNotifications(user: User, emitter: SseEmitter) {
        val activeToastNotifications = toastNotificationRepository.findByUser(user)

        activeToastNotifications.forEach { toastNotification ->
            try {
                emitter.send(
                    SseEmitter.event()
                        .id("toast-${toastNotification.type.name}")
                        .name("notification")
                        .data(toastNotificationMapper.toPayload(toastNotification))
                )
            } catch (ex: Exception) {
                sseEmitterStore.remove(user.id!!)
            }

        }
    }

//    fun notifyMatchAccepted(matchProposal: MatchProposal) {
//        sendToast(matchProposal.sender, NotificationType.MATCH_ACCEPTED, "스파링 제안이 수락되었습니다.")
//        saveDetail(matchProposal.sender, NotificationType.MATCH_ACCEPTED, "${matchProposal.receiver.nickname} 님의 스파링 제안이 수락되었습니다.")
//    }

    fun deleteToastNotification(type: NotificationType) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()

        val toastNotification = toastNotificationRepository.findByUserAndType(user, type)
            ?: throw ToastNotificationNotFoundException()

        toastNotificationRepository.delete(toastNotification)
    }

    private fun sendToast(user: User, type: NotificationType, message: String) {
        var toastNotification = toastNotificationRepository.findByUserAndType(user, type)

        if(toastNotification == null) {
            toastNotification = toastNotificationRepository.save(
                ToastNotification(
                    user = user,
                    type = type,
                    message = message
                )
            )
        } else {
            toastNotification.updateMessage(message)
            toastNotificationRepository.save(toastNotification)
        }

        sseEmitterStore.get(user.id!!)?.let { emitter ->
            try {
                emitter.send(
                    SseEmitter.event()
                        .id("toast-${type.name}")
                        .name("notification")
                        .data(toastNotificationMapper.toPayload(toastNotification))
                )
            } catch (e: Exception) {
                sseEmitterStore.remove(user.id)
            }
        }
    }

    private fun saveDetail(user: User, type: NotificationType, message: String): Notification {
        val notification = notificationRepository.save(
            Notification(
                user = user,
                type = type,
                message = message
            )
        )

        return notification
    }
}