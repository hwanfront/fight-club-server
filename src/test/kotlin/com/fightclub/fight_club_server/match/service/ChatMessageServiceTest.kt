package com.fightclub.fight_club_server.match.service

import com.fightclub.fight_club_server.match.domain.ChatMessage
import com.fightclub.fight_club_server.match.domain.ChatMessageType
import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.dto.ChatMessageRequest
import com.fightclub.fight_club_server.match.dto.ChatMessageResponse
import com.fightclub.fight_club_server.match.exception.MatchNotFoundSocketException
import com.fightclub.fight_club_server.match.exception.UserIsNotParticipantSocketException
import com.fightclub.fight_club_server.match.mapper.ChatMessageMapper
import com.fightclub.fight_club_server.match.repository.ChatMessageRepository
import com.fightclub.fight_club_server.match.repository.MatchRepository
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
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class ChatMessageServiceTest {


    @Mock lateinit var chatMessageRepository: ChatMessageRepository
    @Mock lateinit var matchRepository: MatchRepository

    private var chatMessageMapper = ChatMessageMapper()

    private lateinit var chatMessageService: ChatMessageService

    @BeforeEach
    fun setUp() {
        chatMessageService = ChatMessageService(
            chatMessageRepository = chatMessageRepository,
            chatMessageMapper = chatMessageMapper,
            matchRepository = matchRepository,
        )
    }

    @Test
    fun `# getLatestMessageList success`() {
        // given
        val matchId = 1L
        val size = 20

        val user1 = User(id = 1L, nickname = "user1")
        val user2 = User(id = 2L, nickname = "user2")
        val match = Match(id = matchId, user1 = user1, user2 = user2, weightClass = WeightClass.BANTAM, user1Weight = 55.0, user2Weight = 54.0)
        val chatMessage1 = ChatMessage(
            id = 1L,
            match = match,
            sender = user1,
            content = "message1",
            messageType = ChatMessageType.TEXT,
        )
        val chatMessage2 = ChatMessage(
            id = 2L,
            match = match,
            sender = user2,
            content = "message2",
            messageType = ChatMessageType.TEXT,
        )
        val messageList = listOf(chatMessage2, chatMessage1)

        given(chatMessageRepository.findByMatchIdOrderByIdDesc(matchId, PageRequest.of(0, size))).willReturn(messageList)

        // when
        val result = chatMessageService.getLatestMessageList(matchId, size)

        // then
        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo(2L)
        assertThat(result[1].id).isEqualTo(1L)
    }

    @Test
    fun `# getBeforeMessageList success`() {
        // given
        val matchId = 1L
        val beforeMessageId = 20L
        val size = 20

        val user1 = User(id = 1L, nickname = "user1")
        val user2 = User(id = 2L, nickname = "user2")
        val match = Match(id = matchId, user1 = user1, user2 = user2, weightClass = WeightClass.BANTAM, user1Weight = 55.0, user2Weight = 54.0)
        val chatMessage1 = ChatMessage(
            id = 17L,
            match = match, // 간단히 맞춤
            sender = user1,
            content = "message1",
            messageType = ChatMessageType.TEXT,
        )
        val chatMessage2 = ChatMessage(
            id = 18L,
            match = match,
            sender = user2,
            content = "message2",
            messageType = ChatMessageType.TEXT,
        )
        val messageList = listOf(chatMessage2, chatMessage1)

        given(chatMessageRepository.findByMatchIdAndIdLessThanOrderByIdDesc(matchId, beforeMessageId, PageRequest.of(0, size))).willReturn(messageList)

        // when
        val result = chatMessageService.getBeforeMessageList(matchId, beforeMessageId, size)

        // then
        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo(18L)
        assertThat(result[1].id).isEqualTo(17L)
    }

    @Test
    fun `# saveChatMessage success`() {
        // given
        val matchId = 1L
        val content = "message1"
        val messageType = ChatMessageType.TEXT

        val chatMessageRequest = ChatMessageRequest(
            matchId = matchId,
            content = content,
            messageType = messageType
        )

        val user1 = User(id = 1L, nickname = "user1")
        val user2 = User(id = 2L, nickname = "user2")
        val match = Match(id = matchId, user1 = user1, user2 = user2, weightClass = WeightClass.BANTAM, user1Weight = 55.0, user2Weight = 54.0)

        val chatMessageId = 1L
        val chatMessage = ChatMessage(
            id = chatMessageId,
            match = match,
            sender = user1,
            content = content,
            messageType = messageType,
        )

        given(matchRepository.findById(matchId)).willReturn(Optional.of(match))

        val captor = argumentCaptor<ChatMessage>()
        given(chatMessageRepository.save(captor.capture())).willReturn(chatMessage)

        // when
        val result = chatMessageService.saveChatMessage(user1, chatMessageRequest)

        // then
        val savedChatMessage = captor.firstValue
        assertThat(savedChatMessage.content).isEqualTo(content)
        assertThat(savedChatMessage.match).isEqualTo(match)
        assertThat(savedChatMessage.messageType).isEqualTo(messageType)
        assertThat(savedChatMessage.content).isEqualTo(content)
        assertThat(savedChatMessage.sender).isEqualTo(user1)

        assertThat(result.content).isEqualTo(content)
        assertThat(result.senderId).isEqualTo(user1.id)
        assertThat(result.senderNickname).isEqualTo(user1.nickname)
        assertThat(result.messageType).isEqualTo(messageType)
    }

    @Test
    fun `# saveChatMessage failed - UserIsNotParticipantSocketException`() {
        // given
        val matchId = 1L
        val content = "message1"
        val messageType = ChatMessageType.TEXT

        val chatMessageRequest = ChatMessageRequest(
            matchId = matchId,
            content = content,
            messageType = messageType
        )

        val user1 = User(id = 1L, nickname = "user1")
        val user2 = User(id = 2L, nickname = "user2")
        val user3 = User(id = 2L, nickname = "user2")
        val match = Match(id = matchId, user1 = user3, user2 = user2, weightClass = WeightClass.BANTAM, user1Weight = 55.0, user2Weight = 54.0)

        given(matchRepository.findById(matchId)).willReturn(Optional.of(match))

        // when
        // then
        assertThrows(UserIsNotParticipantSocketException::class.java) {
            chatMessageService.saveChatMessage(user1, chatMessageRequest)
        }
    }

    @Test
    fun `# saveChatMessage failed - MatchNotFoundSocketException`() {
        // given
        val matchId = 1L
        val content = "message1"
        val messageType = ChatMessageType.TEXT

        val chatMessageRequest = ChatMessageRequest(
            matchId = matchId,
            content = content,
            messageType = messageType
        )

        val user1 = User(id = 1L, nickname = "user1")

        given(matchRepository.findById(matchId)).willReturn(Optional.empty())

        // when
        // then
        assertThrows(MatchNotFoundSocketException::class.java) {
            chatMessageService.saveChatMessage(user1, chatMessageRequest)
        }
    }
}