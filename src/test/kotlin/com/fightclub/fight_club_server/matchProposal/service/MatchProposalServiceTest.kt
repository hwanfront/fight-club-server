package com.fightclub.fight_club_server.matchProposal.service

import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.matchProposal.domain.MatchProposalStatus
import com.fightclub.fight_club_server.matchProposal.dto.ReceivedMatchProposalResponse
import com.fightclub.fight_club_server.matchProposal.mapper.MatchProposalMapper
import com.fightclub.fight_club_server.matchProposal.repository.MatchProposalRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.BDDMockito.given
import org.mockito.Mock
import java.time.LocalDateTime

class MatchProposalServiceTest {
    @Mock lateinit var matchProposalRepository: MatchProposalRepository
    @Mock lateinit var matchRepository: MatchRepository
    @Mock lateinit var matchProposalMapper: MatchProposalMapper
    @Mock lateinit var notificationService: NotificationService

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
                status = MatchProposalStatus.PENDING,
                requestedAt = requestedAt
            ),
            MatchProposal(
                id = 2L,
                sender = sender2,
                senderWeight = sender2Weight,
                receiver = user,
                receiverWeight = userWeight,
                weightClass = weightClass,
                status = MatchProposalStatus.PENDING,
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

        given(matchProposalRepository.findAllByReceiverAndStatus(user, MatchProposalStatus.PENDING)).willReturn(proposalList)

        // when
        val result = matchProposalService.getReceivedMatchProposalList(user)

        // then
        assertThat(result).isEqualTo(mappedProposalList)
    }

    @Test
    fun `# getReceivedMatchProposalList success - empty`() {
        // given
        val user = User(id = 1L, nickname = "user", email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchProposalRepository.findAllByReceiverAndStatus(user, MatchProposalStatus.PENDING)).willReturn(emptyList())

        // when
        val result = matchProposalService.getReceivedMatchProposalList(user)

        // then
        assertThat(result).isEmpty()
    }

}