package com.fightclub.fight_club_server.matchingWait.controller

import com.fightclub.fight_club_server.common.dto.ApiResponse
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitSuccessCode
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitRequest
import com.fightclub.fight_club_server.matchingWait.dto.SendMatchRequest
import com.fightclub.fight_club_server.matchingWait.service.MatchingWaitService
import com.fightclub.fight_club_server.user.domain.User
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/matching-wait")
class MatchingWaitController(
    private val matchingWaitService: MatchingWaitService
) {
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun getMyMatchingWait(
        @AuthenticationPrincipal user: User
    ) = ApiResponse.success(MatchingWaitSuccessCode.GET_MY_MATCHING_WAIT_SUCCESS, matchingWaitService.getMyMatchingWait(user))

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun createMatchingWait(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: MatchingWaitRequest
    ) = ApiResponse.success(MatchingWaitSuccessCode.CREATE_MATCHING_WAIT_SUCCESS, matchingWaitService.enrollMatchingWait(user, request))

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    fun deleteMatchingWait(
        @AuthenticationPrincipal user: User
    ) = ApiResponse.success(MatchingWaitSuccessCode.DELETE_MATCHING_WAIT_SUCCESS, matchingWaitService.cancelMatchingWait(user))

    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    fun updateMatchingWait(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: MatchingWaitRequest
    ) = ApiResponse.success(MatchingWaitSuccessCode.UPDATE_MATCHING_WAIT_SUCCESS, matchingWaitService.updateMatchingWait(user, request))

    @GetMapping("/candidate")
    @PreAuthorize("isAuthenticated()")
    fun getRandomCandidateList(
        @AuthenticationPrincipal user: User
    ) = ApiResponse.success(MatchingWaitSuccessCode.GET_CANDIDATE_LIST_SUCCESS, matchingWaitService.getCandidateList(user))

    @PostMapping("/send")
    @PreAuthorize("isAuthenticated()")
    fun createSendMatchProposal(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: SendMatchRequest
    ) = ApiResponse.success(MatchingWaitSuccessCode.SEND_MATCH_PROPOSAL_SUCCESS, matchingWaitService.sendMatchProposal(user, request))
}