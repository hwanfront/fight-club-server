package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.*
import com.fightclub.fight_club_server.match.dto.ReadChatMessageRequest
import com.fightclub.fight_club_server.match.exception.MatchNotFoundSocketException
import com.fightclub.fight_club_server.match.exception.UnnecessaryReadUpdateSocketException
import com.fightclub.fight_club_server.match.exception.UserIsNotParticipantSocketException
import com.fightclub.fight_club_server.match.repository.MatchReadStatusRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import java.util.*

@ExtendWith(MockitoExtension::class)
class MatchReadStatusServiceTest {

    @Mock lateinit var matchReadStatusRepository: MatchReadStatusRepository
    @Mock lateinit var matchRepository: MatchRepository

    private lateinit var matchReadStatusService: MatchReadStatusService

    @BeforeEach
    fun setUp() {
        matchReadStatusService = MatchReadStatusService(
            matchReadStatusRepository = matchReadStatusRepository,
            matchRepository = matchRepository,
        )
    }

    @Test
    fun `# updateLastReadMessage success`() {
        // given
        val matchId = 1L
        val newMessageId = 40L
        val readChatMessageRequest = ReadChatMessageRequest(matchId, newMessageId)

        val user = User(id = 1L, nickname = "user")
        val match = Match(
            id = matchId,
            user1 = user,
            user2 = User(id = 2L, nickname = "opponent1"),
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )
        val matchReadStatusKey = MatchReadStatusId(matchId, user.id!!)
        val matchReadStatus = MatchReadStatus(
            id = matchReadStatusKey,
            match = match,
            user = user,
            lastReadMessageId = 20L
        )
        val captor = argumentCaptor<MatchReadStatus>()

        given(matchRepository.findById(readChatMessageRequest.matchId)).willReturn(Optional.of(match))
        given(matchReadStatusRepository.findById(matchReadStatusKey)).willReturn(Optional.of(matchReadStatus))

        // when
        val result = matchReadStatusService.updateLastReadMessage(readChatMessageRequest, user)

        // then
        verify(matchReadStatusRepository).save(captor.capture())

        val savedMatchReadStatus = captor.firstValue

        assertThat(savedMatchReadStatus.id).isEqualTo(matchReadStatusKey)
        assertThat(savedMatchReadStatus.lastReadMessageId).isEqualTo(newMessageId)
        assertThat(result.lastReadMessageId).isEqualTo(newMessageId)
    }

    @Test
    fun `# updateLastReadMessage success - init`() {
        // given
        val matchId = 1L
        val newMessageId = 20L
        val readChatMessageRequest = ReadChatMessageRequest(matchId, newMessageId)

        val user = User(id = 1L, nickname = "user")
        val match = Match(
            id = matchId,
            user1 = user,
            user2 = User(id = 2L, nickname = "opponent1"),
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )
        val matchReadStatusKey = MatchReadStatusId(matchId, user.id!!)
        val captor = argumentCaptor<MatchReadStatus>()

        given(matchRepository.findById(readChatMessageRequest.matchId)).willReturn(Optional.of(match))
        given(matchReadStatusRepository.findById(matchReadStatusKey)).willReturn(Optional.empty())

        // when
        val result = matchReadStatusService.updateLastReadMessage(readChatMessageRequest, user)

        // then
        verify(matchReadStatusRepository).save(captor.capture())

        val savedMatchReadStatus = captor.firstValue

        assertThat(savedMatchReadStatus.id).isEqualTo(matchReadStatusKey)
        assertThat(savedMatchReadStatus.lastReadMessageId).isEqualTo(newMessageId)
        assertThat(result.lastReadMessageId).isEqualTo(newMessageId)
    }

    @Test
    fun `# updateLastReadMessage failed - UnnecessaryReadUpdateException`() {
        // given
        val matchId = 1L
        val newMessageId = 20L
        val readChatMessageRequest = ReadChatMessageRequest(matchId, newMessageId)

        val user = User(id = 1L, nickname = "user")
        val match = Match(
            id = matchId,
            user1 = user,
            user2 = User(id = 2L, nickname = "opponent1"),
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )
        val matchReadStatusKey = MatchReadStatusId(matchId, user.id!!)
        val matchReadStatus = MatchReadStatus(
            id = matchReadStatusKey,
            match = match,
            user = user,
            lastReadMessageId = 40L
        )

        given(matchRepository.findById(readChatMessageRequest.matchId)).willReturn(Optional.of(match))
        given(matchReadStatusRepository.findById(matchReadStatusKey)).willReturn(Optional.of(matchReadStatus))

        // when
        // then
        assertThrows(UnnecessaryReadUpdateSocketException::class.java) {
            matchReadStatusService.updateLastReadMessage(readChatMessageRequest, user)
        }
    }

    @Test
    fun `# updateLastReadMessage failed - UserIsNotParticipantsException`() {
        // given
        val matchId = 1L
        val newMessageId = 20L
        val readChatMessageRequest = ReadChatMessageRequest(matchId, newMessageId)

        val user = User(id = 1L, nickname = "user")
        val match = Match(
            id = matchId,
            user1 = User(id = 3L, nickname = "opponent2"),
            user2 = User(id = 2L, nickname = "opponent1"),
            status = MatchStatus.CHATTING,
            readyStatus = MatchReadyStatus.USER1_READY,
            weightClass = WeightClass.BANTAM,
            user1Weight = 54.0,
            user2Weight = 55.0,
        )
        given(matchRepository.findById(readChatMessageRequest.matchId)).willReturn(Optional.of(match))

        // when
        // then
        assertThrows(UserIsNotParticipantSocketException::class.java) {
            matchReadStatusService.updateLastReadMessage(readChatMessageRequest, user)
        }
    }


    @Test
    fun `# updateLastReadMessage failed - MatchNotFoundSocketException`() {
        // given
        val matchId = 1L
        val newMessageId = 20L
        val readChatMessageRequest = ReadChatMessageRequest(matchId, newMessageId)

        val user = User(id = 1L, nickname = "user")
        given(matchRepository.findById(readChatMessageRequest.matchId)).willReturn(Optional.empty())

        // when
        // then
        assertThrows(MatchNotFoundSocketException::class.java) {
            matchReadStatusService.updateLastReadMessage(readChatMessageRequest, user)
        }
    }
}