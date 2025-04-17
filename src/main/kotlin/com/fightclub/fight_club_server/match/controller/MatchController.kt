package com.fightclub.fight_club_server.match.controller

import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.match.constants.MatchSuccessCode
import com.fightclub.fight_club_server.match.service.ChatMessageService
import com.fightclub.fight_club_server.match.service.MatchService
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/match")
class MatchController(
    private val matchService: MatchService,
    private val chatMessageService: ChatMessageService,
) {
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    fun myMatchList(
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_MY_MATCH_LIST_SUCCESS, matchService.getMatchList(user))

    @GetMapping("/{matchId}")
    @PreAuthorize("isAuthenticated()")
    fun matchInfo(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_MATCH_INFO_SUCCESS, matchService.getMatchInfo(matchId, user))

    @GetMapping("/{matchId}/messages/latest")
    fun getLatestMessages(
        @PathVariable matchId: Long,
        @RequestParam(defaultValue = "30") size: Int,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_CHAT_MESSAGE_LIST_SUCCESS, chatMessageService.getLatestMessageList(matchId, user, size))

    @GetMapping("/{matchId}/messages/before/{beforeMessageId}")
    fun getBeforeMessages(
        @PathVariable matchId: Long,
        @PathVariable beforeMessageId: Long,
        @RequestParam(defaultValue = "30") size: Int,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchSuccessCode.GET_CHAT_MESSAGE_LIST_SUCCESS, chatMessageService.getBeforeMessageList(matchId, beforeMessageId, user, size))

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