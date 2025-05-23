package com.fightclub.fight_club_server.matchProposal.service

import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.domain.MatchStatus
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.matchProposal.dto.AcceptResponse
import com.fightclub.fight_club_server.matchProposal.dto.ReceivedMatchProposalResponse
import com.fightclub.fight_club_server.matchProposal.dto.SentMatchProposalResponse
import com.fightclub.fight_club_server.matchProposal.exception.MatchProposalNotFoundException
import com.fightclub.fight_club_server.matchProposal.exception.UserIsNotReceiverException
import com.fightclub.fight_club_server.matchProposal.exception.UserIsNotSenderException
import com.fightclub.fight_club_server.matchProposal.mapper.MatchProposalMapper
import com.fightclub.fight_club_server.matchProposal.repository.MatchProposalRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class MatchProposalServiceTest {
    @Mock lateinit var matchProposalRepository: MatchProposalRepository
    @Mock lateinit var matchRepository: MatchRepository
    @Mock lateinit var notificationService: NotificationService
    private val matchProposalMapper = MatchProposalMapper()

    private lateinit var matchProposalService: MatchProposalService

    @BeforeEach
    fun setUp() {
        matchProposalService = MatchProposalService(
            matchRepository = matchRepository,
            matchProposalRepository = matchProposalRepository,
            matchProposalMapper = matchProposalMapper,
            notificationService = notificationService,
        )
    }

    @Test
    fun `# getReceivedMatchProposalList success`() {
        // given
        val userWeight = 55.0
        val weightClass = WeightClass.BANTAM
        val requestedAt = LocalDateTime.of(2025, 4, 1, 12, 0)
        val sender1Weight = 54.5
        val sender2Weight = 54.0
        val sender1Nickname = "sender1"
        val sender2Nickname = "sender2"

        val user = User(id = 1L, nickname = "user", email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender1 = User(id = 2L, nickname = sender1Nickname, email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender2 = User(id = 3L, nickname = sender2Nickname, email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val proposalList = listOf(
            MatchProposal(
                id = 1L,
                sender = sender1,
                senderWeight = sender1Weight,
                receiver = user,
                receiverWeight = userWeight,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
            MatchProposal(
                id = 2L,
                sender = sender2,
                senderWeight = sender2Weight,
                receiver = user,
                receiverWeight = userWeight,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
        )
        val mappedProposalList = listOf(
            ReceivedMatchProposalResponse(
                id = 1L,
                senderNickname = sender1Nickname,
                senderWeight = sender1Weight,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
            ReceivedMatchProposalResponse(
                id = 2L,
                senderNickname = sender2Nickname,
                senderWeight = sender2Weight,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
        )

        given(matchProposalRepository.findAllByReceiver(user)).willReturn(proposalList)

        // when
        val result = matchProposalService.getReceivedMatchProposalList(user)

        // then
        assertThat(result).isEqualTo(mappedProposalList)
    }

    @Test
    fun `# getReceivedMatchProposalList success - empty`() {
        // given
        val user = User(id = 1L, nickname = "user", email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchProposalRepository.findAllByReceiver(user)).willReturn(emptyList())

        // when
        val result = matchProposalService.getReceivedMatchProposalList(user)

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `# getSentMatchProposalList success`() {
        // given
        val userWeight = 55.0
        val weightClass = WeightClass.BANTAM
        val requestedAt = LocalDateTime.of(2025, 4, 1, 12, 0)
        val receiver1Weight = 54.5
        val receiver2Weight = 54.0
        val receiver1Nickname = "receiver1"
        val receiver2Nickname = "receiver2"

        val user = User(id = 1L, nickname = "user", email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val receiver1 = User(id = 2L, nickname = receiver1Nickname, email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val receiver2 = User(id = 3L, nickname = receiver2Nickname, email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val proposalList = listOf(
            MatchProposal(
                id = 1L,
                sender = user,
                senderWeight = userWeight,
                receiver = receiver1,
                receiverWeight = receiver1Weight,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
            MatchProposal(
                id = 2L,
                sender = user,
                senderWeight = userWeight,
                receiver = receiver2,
                receiverWeight = receiver2Weight,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
        )
        val mappedProposalList = listOf(
            SentMatchProposalResponse(
                id = 1L,
                senderWeight = userWeight,
                receiverWeight = receiver1Weight,
                receiverNickname = receiver1Nickname,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
            SentMatchProposalResponse(
                id = 2L,
                senderWeight = userWeight,
                receiverWeight = receiver2Weight,
                receiverNickname = receiver2Nickname,
                weightClass = weightClass,
                requestedAt = requestedAt
            ),
        )

        given(matchProposalRepository.findAllBySender(user)).willReturn(proposalList)

        // when
        val result = matchProposalService.getSentMatchProposalList(user)

        // then
        assertThat(result).isEqualTo(mappedProposalList)
    }

    @Test
    fun `# getSentMatchProposalList success - empty`() {
        // given
        val user = User(id = 1L, nickname = "user", email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchProposalRepository.findAllBySender(user)).willReturn(emptyList())

        // when
        val result = matchProposalService.getSentMatchProposalList(user)

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `# acceptProposal success`() {
        // given
        val matchProposalId = 1L
        val senderWeight = 54.0
        val receiverWeight = 55.0
        val weightClass = WeightClass.BANTAM
        val matchId = 1L

        val user = User(id = 1L, nickname = "receiver", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender = User(id = 2L, nickname = "sender", email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val matchProposal = MatchProposal(
            id = matchProposalId,
            sender = sender,
            senderWeight = senderWeight,
            receiver = user,
            receiverWeight = receiverWeight,
            weightClass = weightClass,
        )

        val match = Match(
            id = matchId,
            user1 = user,
            user2 = sender,
            status = MatchStatus.CHATTING,
            weightClass = weightClass,
            user1Weight = receiverWeight,
            user2Weight = senderWeight,
        )

        val acceptResponse = AcceptResponse(
            matchId = matchId
        )

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.of(matchProposal))
        given(matchRepository.save(any())).willReturn(match)

        // when
        val result = matchProposalService.acceptProposal(matchProposalId, user)

        // then
        verify(matchProposalRepository).delete(matchProposal)
        verify(matchRepository).save(any())

        assertThat(result.matchId).isEqualTo(matchId)

        val captor = argumentCaptor<MatchProposal>()
        verify(notificationService).notifyMatchAccepted(captor.capture())

        val capturedProposal = captor.firstValue
        assertThat(capturedProposal.sender).isEqualTo(sender)
        assertThat(capturedProposal.receiver).isEqualTo(user)
        assertThat(capturedProposal.senderWeight).isEqualTo(senderWeight)
        assertThat(capturedProposal.weightClass).isEqualTo(weightClass)
    }

    @Test
    fun `# acceptProposal failed - 사용자가 수신자 receiver 가 아님 UserIsNotReceiverException`() {
        // given
        val matchProposalId = 1L

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender = User(id = 2L, nickname = "sender", email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val receiver = User(id = 3L, nickname = "receiver", email = "test3@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        val matchProposal = MatchProposal(
            id = matchProposalId,
            sender = sender,
            senderWeight = 54.0,
            receiver = receiver,
            receiverWeight = 55.0,
            weightClass = WeightClass.BANTAM,
        )

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.of(matchProposal))

        // when
        // then
        assertThrows(UserIsNotReceiverException::class.java) {
            matchProposalService.acceptProposal(matchProposalId, user)
        }
    }

    @Test
    fun `# acceptProposal failed - matchProposal 찾을 수 없음 MatchProposalNotFoundException`() {
        // given
        val matchProposalId = 1L
        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.empty())

        // when
        // then
        assertThrows(MatchProposalNotFoundException::class.java) {
            matchProposalService.acceptProposal(matchProposalId, user)
        }
    }

    @Test
    fun `# rejectProposal success`() {
        // given
        val matchProposalId = 1L

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender = User(id = 2L, nickname = "sender", email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val matchProposal = MatchProposal(
            id = matchProposalId,
            sender = sender,
            senderWeight = 54.0,
            receiver = user,
            receiverWeight = 55.0,
            weightClass = WeightClass.BANTAM,
        )

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.of(matchProposal))

        // when
        matchProposalService.rejectProposal(matchProposalId, user)

        // then
        verify(matchProposalRepository).delete(matchProposal)
    }

    @Test
    fun `# rejectProposal failed - 사용자가 수신자 receiver 가 아님 UserIsNotReceiverException`() {
        // given
        val matchProposalId = 1L

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender = User(id = 2L, nickname = "sender", email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val receiver = User(id = 3L, nickname = "receiver", email = "test3@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        val matchProposal = MatchProposal(
            id = matchProposalId,
            sender = sender,
            senderWeight = 54.0,
            receiver = receiver,
            receiverWeight = 55.0,
            weightClass = WeightClass.BANTAM,
        )

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.of(matchProposal))

        // when
        // then
        assertThrows(UserIsNotReceiverException::class.java) {
            matchProposalService.rejectProposal(matchProposalId, user)
        }
    }

    @Test
    fun `# rejectProposal failed - matchProposal 찾을 수 없음 MatchProposalNotFoundException`() {
        // given
        val matchProposalId = 1L

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.empty())

        // when
        // then
        assertThrows(MatchProposalNotFoundException::class.java) {
            matchProposalService.rejectProposal(matchProposalId, user)
        }
    }

    @Test
    fun `# cancelMyProposal success`() {
        // given
        val matchProposalId = 1L
        val senderWeight = 54.0
        val receiverWeight = 55.0
        val weightClass = WeightClass.BANTAM

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val receiver = User(id = 2L, nickname = "receiver", email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val matchProposal = MatchProposal(
            id = matchProposalId,
            sender = user,
            senderWeight = senderWeight,
            receiver = receiver,
            receiverWeight = receiverWeight,
            weightClass = weightClass,
        )

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.of(matchProposal))

        // when
        matchProposalService.cancelMyProposal(matchProposalId, user)

        // then
        verify(matchProposalRepository).delete(matchProposal)
        val captor = argumentCaptor<MatchProposal>()
        verify(notificationService).notifyMatchProposalCanceled(captor.capture())

        val capturedProposal = captor.firstValue
        assertThat(capturedProposal.sender).isEqualTo(user)
        assertThat(capturedProposal.receiver).isEqualTo(receiver)
        assertThat(capturedProposal.senderWeight).isEqualTo(senderWeight)
        assertThat(capturedProposal.weightClass).isEqualTo(weightClass)
    }

    @Test
    fun `# cancelMyProposal failed - `() {
        // given
        val matchProposalId = 1L

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val receiver = User(id = 2L, nickname = "receiver", email = "test2@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val sender = User(id = 3L, nickname = "sender", email = "test3@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        val matchProposal = MatchProposal(
            id = matchProposalId,
            sender = sender,
            senderWeight = 54.0,
            receiver = receiver,
            receiverWeight = 55.0,
            weightClass = WeightClass.BANTAM,
        )

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.of(matchProposal))

        // when
        // then
        assertThrows(UserIsNotSenderException::class.java) {
            matchProposalService.cancelMyProposal(matchProposalId, user)
        }
    }

    @Test
    fun `# cancelMyProposal failed -  `() {
        // given
        val matchProposalId = 1L

        val user = User(id = 1L, nickname = "user", email = "test1@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchProposalRepository.findByIdWithLock(matchProposalId)).willReturn(Optional.empty())

        // when
        // then
        assertThrows(MatchProposalNotFoundException::class.java) {
            matchProposalService.cancelMyProposal(matchProposalId, user)
        }
    }
}