package com.fightclub.fight_club_server.notification.service

import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.notification.domain.Notification
import com.fightclub.fight_club_server.notification.domain.NotificationType
import com.fightclub.fight_club_server.notification.domain.ToastNotification
import com.fightclub.fight_club_server.notification.mapper.ToastNotificationMapper
import com.fightclub.fight_club_server.notification.repository.NotificationRepository
import com.fightclub.fight_club_server.notification.repository.ToastNotificationRepository
import com.fightclub.fight_club_server.sse.SseEmitterStore
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.given
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class NotificationServiceTest {
    @Mock lateinit var notificationRepository: NotificationRepository
    @Mock lateinit var sseEmitterStore: SseEmitterStore
    @Mock lateinit var toastNotificationRepository: ToastNotificationRepository
    @Mock lateinit var toastNotificationMapper: ToastNotificationMapper

    private lateinit var notificationService: NotificationService

    @BeforeEach
    fun setUp() {
        notificationService = NotificationService(
            notificationRepository = notificationRepository,
            sseEmitterStore = sseEmitterStore,
            toastNotificationRepository = toastNotificationRepository,
            toastNotificationMapper = toastNotificationMapper,
        )
    }

    @Test
    fun `# notifyMatchProposal success`() {
        // given
        val sender = User(id = 1L, nickname = "nickname1", email = "test1@gmail.com", status = UserStatus.REGISTERED)
        val receiver = User(id = 2L, nickname = "nickname2", email = "test2@gmail.com", status = UserStatus.REGISTERED)

        val matchProposal = MatchProposal(
            id = 1L,
            sender = sender,
            receiver = receiver,
        )

        given(toastNotificationRepository.findByUserAndType(receiver, NotificationType.MATCH_PROPOSAL)).willReturn(null)

        val toastNotification = ToastNotification(
            user = receiver,
            type = NotificationType.MATCH_PROPOSAL,
            message = "스파링 제안이 도착했습니다."
        )

        val notification = Notification(
            user = receiver,
            type = NotificationType.MATCH_PROPOSAL,
            message = "${sender.nickname} 님의 스파링 제안이 도착했습니다."
        )

        given(toastNotificationRepository.save(any<ToastNotification>())).willReturn(toastNotification)
        given(notificationRepository.save(any<Notification>())).willReturn(notification)

        // when
        notificationService.notifyMatchProposal(matchProposal)

        // then
        verify(toastNotificationRepository).findByUserAndType(receiver, NotificationType.MATCH_PROPOSAL)
        verify(toastNotificationRepository).save(any())
        verify(notificationRepository).save(any())
    }

    @Test
    fun `# notifyMatchProposal success - 이미 존재하는 toastNotification`() {
        // given
        val sender = User(id = 1L, nickname = "nickname1", email = "test1@gmail.com", status = UserStatus.REGISTERED)
        val receiver = User(id = 2L, nickname = "nickname2", email = "test2@gmail.com", status = UserStatus.REGISTERED)

        val matchProposal = MatchProposal(
            id = 1L,
            sender = sender,
            receiver = receiver,
        )

        val existsToastNotification = ToastNotification(
            user = receiver,
            type = NotificationType.MATCH_PROPOSAL,
            message = "이전 메시지"
        )

        given(toastNotificationRepository.findByUserAndType(receiver, NotificationType.MATCH_PROPOSAL)).willReturn(existsToastNotification)

        val notification = Notification(
            user = receiver,
            type = NotificationType.MATCH_PROPOSAL,
            message = "${sender.nickname} 님의 스파링 제안이 도착했습니다."
        )

        given(toastNotificationRepository.save(any<ToastNotification>())).willReturn(existsToastNotification)
        given(notificationRepository.save(any<Notification>())).willReturn(notification)

        val message = "스파링 제안이 도착했습니다."
        // when
        notificationService.notifyMatchProposal(matchProposal)

        // then
        val captor = argumentCaptor<ToastNotification>()
        verify(toastNotificationRepository).save(captor.capture())

        val newMessage = captor.firstValue.message
        assertThat(newMessage).isEqualTo(message)

        verify(toastNotificationRepository).findByUserAndType(receiver, NotificationType.MATCH_PROPOSAL)
        verify(notificationRepository).save(any())
    }
}