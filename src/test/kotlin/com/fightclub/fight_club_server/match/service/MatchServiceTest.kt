package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.domain.MatchReadyStatus
import com.fightclub.fight_club_server.match.domain.MatchStatus
import com.fightclub.fight_club_server.match.dto.MatchResponse
import com.fightclub.fight_club_server.match.mapper.MatchMapper
import com.fightclub.fight_club_server.match.repository.MatchMessageRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MatchServiceTest {
    @Mock lateinit var matchRepository: MatchRepository
    @Mock lateinit var matchMessageRepository: MatchMessageRepository
    @Mock lateinit var notificationService: NotificationService
    private var matchMapper = MatchMapper()

    private lateinit var matchService: MatchService

    @BeforeEach
    fun setUp() {
        matchService = MatchService(
            matchRepository = matchRepository,
            matchMessageRepository = matchMessageRepository,
            matchMapper = matchMapper,
        )
    }

    @Test
    fun getMatchList() {
        // given
        val userId = 1L
        val user = User(id = userId, nickname = "user")
        val match1 = Match(
            id = 1L,
            user1 = user,
            user2 = User(id = 2L, nickname = "opponent1"),
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )
        val match2 = Match(
            id = 2L,
            user1 = User(id = 3L, nickname = "opponent2"),
            user2 = user,
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.NONE,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )
        val match3 = Match(
            id = 3L,
            user1 = user,
            user2 = User(id = 4L, nickname = "opponent3"),
            status = MatchStatus.READY_TO_STREAM,
            readyStatus = MatchReadyStatus.ALL_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )

        val matchResponse1 = MatchResponse(
            matchId = 1L,
            opponentNickname = "opponent1",
            opponentWeight = 55.0,
            weightClass = WeightClass.BANTAM,
            unreadMessageCount = 0,
            isMeReady = true,
            isOpponentReady = false
        )
        val matchResponse2 = MatchResponse(
            matchId = 2L,
            opponentNickname = "opponent2",
            opponentWeight = 54.0,
            weightClass = WeightClass.BANTAM,
            unreadMessageCount = 0,
            isMeReady = false,
            isOpponentReady = false
        )
        val matchResponse3 = MatchResponse(
            matchId = 3L,
            opponentNickname = "opponent3",
            opponentWeight = 55.0,
            weightClass = WeightClass.BANTAM,
            unreadMessageCount = 0,
            isMeReady = true,
            isOpponentReady = true
        )

        val matchList = listOf(match1, match2, match3)
        val mappedMatchList = listOf(matchResponse1, matchResponse2, matchResponse3)

        given(matchRepository.findMatchesByUserId(userId)).willReturn(matchList)

        // when
        val result = matchService.getMatchList(user)

        // then
        assertThat(result).containsExactlyInAnyOrder(matchResponse1, matchResponse2, matchResponse3)
    }
}