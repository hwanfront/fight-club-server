package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.domain.MatchReadyStatus
import com.fightclub.fight_club_server.match.domain.MatchStatus
import com.fightclub.fight_club_server.match.dto.*
import com.fightclub.fight_club_server.match.mapper.MatchMapper
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import java.util.*

@ExtendWith(MockitoExtension::class)
class MatchSocketServiceTest {
    @Mock lateinit var matchRepository: MatchRepository
    private var matchMapper = MatchMapper()

    private lateinit var matchSocketService: MatchSocketService

    @BeforeEach
    fun setUp() {
        matchSocketService = MatchSocketService(
            matchRepository = matchRepository,
            matchMapper = matchMapper,
        )
    }

    @Test
    fun `updateReadyStatus success - NONE to USER1_READY`() {
        // given
        val userId = 1L
        val otherId = 2L
        val matchId = 1L

        val user = User(id = userId, nickname = "user")
        val other = User(id = otherId, nickname = "other")
        val readyRequest = ReadyRequest(matchId = matchId)

        val match = Match(
            id = matchId,
            user1 = user,
            user2 = other,
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.NONE,
            weightClass = WeightClass.BANTAM,
            user1Weight = 55.0,
            user2Weight = 54.0,
        )
        val matchReadyResponse = MatchReadyResponse(
            matchId = matchId,
            isMeReady = true,
            isOpponentReady = false,
            matchStatus = MatchStatus.CHATTING
        )

        val captor = argumentCaptor<Match>()
        given(matchRepository.findByIdWithLock(matchId)).willReturn(Optional.of(match))

        // when
        val result = matchSocketService.updateReadyStatus(user, readyRequest)

        // then
        verify(matchRepository).save(captor.capture())

        val savedMatch = captor.firstValue

        assertThat(savedMatch.readyStatus).isEqualTo(MatchReadyStatus.USER1_READY)
        assertThat(savedMatch.status).isEqualTo(MatchStatus.CHATTING)
        assertThat(result).isEqualTo(matchReadyResponse)
    }

    @Test
    fun `updateReadyStatus success - USER1_READY to USER1_READY`() {
        // given
        val userId = 1L
        val otherId = 2L
        val matchId = 1L

        val user = User(id = userId, nickname = "user")
        val other = User(id = otherId, nickname = "other")
        val readyRequest = ReadyRequest(matchId = matchId)

        val match = Match(
            id = matchId,
            user1 = user,
            user2 = other,
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 55.0,
            user2Weight = 54.0,
        )
        val matchReadyResponse = MatchReadyResponse(
            matchId = matchId,
            isMeReady = true,
            isOpponentReady = false,
            matchStatus = MatchStatus.CHATTING
        )

        val captor = argumentCaptor<Match>()
        given(matchRepository.findByIdWithLock(matchId)).willReturn(Optional.of(match))

        // when
        val result = matchSocketService.updateReadyStatus(user, readyRequest)

        // then
        verify(matchRepository).save(captor.capture())

        val savedMatch = captor.firstValue

        assertThat(savedMatch.readyStatus).isEqualTo(MatchReadyStatus.USER1_READY)
        assertThat(savedMatch.status).isEqualTo(MatchStatus.CHATTING)
        assertThat(result).isEqualTo(matchReadyResponse)
    }

    @Test
    fun `updateReadyStatus success - USER2_READY to ALL_READY`() {
        // given
        val userId = 1L
        val otherId = 2L
        val matchId = 1L

        val user = User(id = userId, nickname = "user")
        val other = User(id = otherId, nickname = "other")
        val readyRequest = ReadyRequest(matchId = matchId)

        val match = Match(
            id = matchId,
            user1 = user,
            user2 = other,
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER2_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 55.0,
            user2Weight = 54.0,
        )
        val matchReadyResponse = MatchReadyResponse(
            matchId = matchId,
            isMeReady = true,
            isOpponentReady = true,
            matchStatus = MatchStatus.READY_TO_STREAM
        )

        val captor = argumentCaptor<Match>()
        given(matchRepository.findByIdWithLock(matchId)).willReturn(Optional.of(match))

        // when
        val result = matchSocketService.updateReadyStatus(user, readyRequest)

        // then
        verify(matchRepository).save(captor.capture())

        val savedMatch = captor.firstValue

        assertThat(savedMatch.readyStatus).isEqualTo(MatchReadyStatus.ALL_READY)
        assertThat(savedMatch.status).isEqualTo(MatchStatus.READY_TO_STREAM)
        assertThat(result).isEqualTo(matchReadyResponse)
    }

    @Test
    fun `declineMatch success`() {
        // given
        val userId = 1L
        val otherId = 2L
        val matchId = 1L

        val user = User(id = userId, nickname = "user")
        val other = User(id = otherId, nickname = "other")
        val declineRequest = DeclineRequest(matchId = matchId)

        val match = Match(
            id = matchId,
            user1 = user,
            user2 = other,
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 55.0,
            user2Weight = 54.0,
        )

        val declineMatchResponse = DeclineMatchResponse(
            matchId = matchId,
            declinedBy = user.nickname,
        )

        val captor = argumentCaptor<Match>()
        given(matchRepository.findByIdWithLock(matchId)).willReturn(Optional.of(match))

        // when
        val result = matchSocketService.declineMatch(user, declineRequest)

        // then
        verify(matchRepository).save(captor.capture())

        val savedMatch = captor.firstValue

        assertThat(savedMatch.readyStatus).isEqualTo(MatchReadyStatus.NONE)
        assertThat(savedMatch.status).isEqualTo(MatchStatus.DECLINED)
        assertThat(result).isEqualTo(declineMatchResponse)

    }
}