package com.fightclub.fight_club_server.match.controller

import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.match.constants.MatchConstants
import com.fightclub.fight_club_server.match.constants.MatchSuccessCode
import com.fightclub.fight_club_server.match.dto.docs.*
import com.fightclub.fight_club_server.match.service.ChatMessageService
import com.fightclub.fight_club_server.match.service.MatchService
import com.fightclub.fight_club_server.user.domain.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@Tag(name = "Match", description = "Match API")
@RestController
@RequestMapping("/api/match")
class MatchController(
    private val matchService: MatchService,
    private val chatMessageService: ChatMessageService,
) {
    @Operation(
        summary = "내 매치 리스트 조회",
        responses = [
            ApiResponse(
                responseCode = MatchConstants.GetMyMatchListSuccess.STATUS_CODE,
                description = "[${MatchConstants.GetMyMatchListSuccess.CODE}] ${MatchConstants.GetMyMatchListSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = GetMyMatchListResponse::class))]
            ),
        ]
    )
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    fun myMatchList(
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_MY_MATCH_LIST_SUCCESS, matchService.getMatchList(user))

    @Operation(
        summary = "매치 정보 조회",
        responses = [
            ApiResponse(
                responseCode = MatchConstants.GetMatchInfoSuccess.STATUS_CODE,
                description = "[${MatchConstants.GetMatchInfoSuccess.CODE}] ${MatchConstants.GetMatchInfoSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = GetMatchInfoResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchConstants.MatchNotFound.STATUS_CODE,
                description = "[${MatchConstants.MatchNotFound.CODE}] ${MatchConstants.MatchNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchNotFoundResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchConstants.UserIsNotParticipant.STATUS_CODE,
                description = "[${MatchConstants.UserIsNotParticipant.CODE}] ${MatchConstants.UserIsNotParticipant.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserIsNotParticipantResponse::class))]
            ),
        ]
    )
    @GetMapping("/{matchId}")
    @PreAuthorize("isAuthenticated()")
    fun matchInfo(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_MATCH_INFO_SUCCESS, matchService.getMatchInfo(matchId, user))

    @Operation(
        summary = "최근 채팅 메시지 조회",
        responses = [
            ApiResponse(
                responseCode = MatchConstants.GetChatMessageListSuccess.STATUS_CODE,
                description = "[${MatchConstants.GetChatMessageListSuccess.CODE}] ${MatchConstants.GetChatMessageListSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = GetChatMessagesResponse::class))]
            ),
        ]
    )
    @GetMapping("/{matchId}/messages/latest")
    fun getLatestMessages(
        @PathVariable matchId: Long,
        @RequestParam(defaultValue = "30") size: Int,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_CHAT_MESSAGE_LIST_SUCCESS, chatMessageService.getLatestMessageList(matchId, size))

    @Operation(
        summary = "이전 채팅 메지시 조회",
        responses = [
            ApiResponse(
                responseCode = MatchConstants.GetChatMessageListSuccess.STATUS_CODE,
                description = "[${MatchConstants.GetChatMessageListSuccess.CODE}] ${MatchConstants.GetChatMessageListSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = GetChatMessagesResponse::class))]
            ),
        ]
    )
    @GetMapping("/{matchId}/messages/before/{beforeMessageId}")
    fun getBeforeMessages(
        @PathVariable matchId: Long,
        @PathVariable beforeMessageId: Long,
        @RequestParam(defaultValue = "30") size: Int,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_CHAT_MESSAGE_LIST_SUCCESS, chatMessageService.getBeforeMessageList(matchId, beforeMessageId, size))

    @PostMapping("/{matchId}/start")
    @PreAuthorize("isAuthenticated()")
    fun startStreaming(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.START_MATCH_STREAMING_SUCCESS, matchService.startMatchStreaming(matchId, user))

    @PostMapping("/{matchId}/pause")
    @PreAuthorize("isAuthenticated()")
    fun pauseStreaming(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.PAUSE_MATCH_STREAMING_SUCCESS, matchService.pauseMatchStreaming(matchId, user))

    @PostMapping("/{matchId}/resume")
    @PreAuthorize("isAuthenticated()")
    fun resumeStreaming(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.RESUME_MATCH_STREAMING_SUCCESS, matchService.resumeMatchStreaming(matchId, user))

    @PostMapping("/{matchId}/end")
    @PreAuthorize("isAuthenticated()")
    fun endStreaming(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.END_MATCH_STREAMING_SUCCESS, matchService.endMatchStreaming(matchId, user))
}