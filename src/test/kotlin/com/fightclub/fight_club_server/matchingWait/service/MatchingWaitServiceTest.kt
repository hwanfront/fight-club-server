package com.fightclub.fight_club_server.matchingWait.service

import com.fightclub.fight_club_server.matchProposal.repository.MatchProposalRepository
import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitResponse
import com.fightclub.fight_club_server.matchingWait.exception.MatchingWaitNotFoundException
import com.fightclub.fight_club_server.matchingWait.mapper.MatchingWaitMapper
import com.fightclub.fight_club_server.matchingWait.repository.MatchingWaitRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class MatchingWaitServiceTest {
    @Mock lateinit var matchingWaitRepository: MatchingWaitRepository
    @Mock lateinit var matchProposalRepository: MatchProposalRepository
    @Mock lateinit var notificationService: NotificationService
    @Mock lateinit var matchingWaitMapper: MatchingWaitMapper

    private lateinit var matchingWaitService: MatchingWaitService

    @BeforeEach
    fun setUp() {
        matchingWaitService = MatchingWaitService(
            matchingWaitRepository = matchingWaitRepository,
            matchProposalRepository = matchProposalRepository,
            notificationService = notificationService,
            matchingWaitMapper = matchingWaitMapper
        )
    }

    @Test
    fun `# getMyMatchingWait success`() {
        // given
        val weight = 55.0
        val weightClass = WeightClass.BANTAM
        val createdAt = LocalDateTime.of(2025, 4, 1, 12, 0)
        val user = User(id = 1L, email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val matchingWait = MatchingWait(
            id = 1L,
            user = user,
            weight = weight,
            weightClass = weightClass,
            createdAt = createdAt,
        )
        val matchingWaitResponse = MatchingWaitResponse(
            weight = weight,
            weightClass = weightClass,
            createdAt = createdAt
        )

        given(matchingWaitRepository.findByUser(user)).willReturn(matchingWait)
        given(matchingWaitMapper.toResponse(matchingWait)).willReturn(matchingWaitResponse)

        // when
        val result = matchingWaitService.getMyMatchingWait(user)

        // then
        assertThat(result).isEqualTo(matchingWaitResponse)
    }

    @Test
    fun `# getMyMatchingWait failed - 매칭 대기중인 내 상태 불러올 수 없음 MatchingWaitNotFoundException`() {
        // given
        val user = User(id = 1L, email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)

        given(matchingWaitRepository.findByUser(user)).willReturn(null)

        // when
        // then
        assertThrows(MatchingWaitNotFoundException::class.java) {
            matchingWaitService.getMyMatchingWait(user)
        }
    }
}