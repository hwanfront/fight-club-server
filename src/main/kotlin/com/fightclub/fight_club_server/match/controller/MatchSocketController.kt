package com.fightclub.fight_club_server.match.controller

import com.fightclub.fight_club_server.common.dto.SocketResponse
import com.fightclub.fight_club_server.common.exception.SocketCodeException
import com.fightclub.fight_club_server.match.constants.MatchSocketSuccessCode
import com.fightclub.fight_club_server.match.dto.ChatMessageRequest
import com.fightclub.fight_club_server.match.dto.DeclineRequest
import com.fightclub.fight_club_server.match.dto.ReadyRequest
import com.fightclub.fight_club_server.match.service.ChatMessageService
import com.fightclub.fight_club_server.match.service.MatchService
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class MatchSocketController(
    private val matchService: MatchService,
    private val chatMessageService: ChatMessageService,
    private val userRepository: UserRepository,
    private val messagingTemplate: SimpMessagingTemplate
) {

    @MessageMapping("/match.ready")
    fun ready(@Payload readyRequest: ReadyRequest, principal: Principal) {
        try {
            val user = userRepository.findByEmail(principal.name) ?: throw UserNotFoundException()
            val data = matchService.updateReadyStatus(user, readyRequest)
            messagingTemplate.convertAndSend(
                "/ws/sub/match/room/${readyRequest.matchId}",
                SocketResponse.success(MatchSocketSuccessCode.MATCH_READY_UPDATED_SUCCESS, data)
            )
        } catch (e: Exception) {
            if (e is SocketCodeException) {
                messagingTemplate.convertAndSend(
                    "/ws/sub/match/room/${readyRequest.matchId}",
                    SocketResponse.error(e.socketResponseCode)
                )
            }
        }
    }

    @MessageMapping("/match.decline")
    fun decline(@Payload declineRequest: DeclineRequest, principal: Principal) {
        try {
            val user = userRepository.findByEmail(principal.name) ?: throw UserNotFoundException()
            val data = matchService.declineMatch(user, declineRequest)
            messagingTemplate.convertAndSend(
                "/ws/sub/match/room/${declineRequest.matchId}",
                SocketResponse.success(MatchSocketSuccessCode.DECLINE_MATCH_SUCCESS, data)
            )
        } catch (e: Exception) {
            if (e is SocketCodeException) {
                messagingTemplate.convertAndSend(
                    "/ws/sub/match/room/${declineRequest.matchId}",
                    SocketResponse.error(e.socketResponseCode)
                )
            }
        }
    }

    @MessageMapping("/match.chat")
    fun sendChatMessage(@Payload chatMessageRequest: ChatMessageRequest, principal: Principal) {
        try {
            val user = userRepository.findByEmail(principal.name) ?: throw UserNotFoundException()
            val data = chatMessageService.saveChatMessage(user, chatMessageRequest)
            messagingTemplate.convertAndSend(
                "/ws/sub/match/room/${chatMessageRequest.matchId}",
                SocketResponse.success(MatchSocketSuccessCode.NEW_CHAT_MESSAGE_RECEIVED_SUCCESS, data)
            )
        } catch (e: Exception) {
            if (e is SocketCodeException) {
                messagingTemplate.convertAndSend(
                    "/ws/sub/match/room/${chatMessageRequest.matchId}",
                    SocketResponse.error(e.socketResponseCode)
                )
            }
        }
    }
}