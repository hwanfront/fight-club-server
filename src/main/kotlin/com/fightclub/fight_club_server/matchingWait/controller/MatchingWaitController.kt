package com.fightclub.fight_club_server.matchingWait.controller

import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitSuccessCode
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitRequest
import com.fightclub.fight_club_server.matchingWait.dto.SendMatchRequest
import com.fightclub.fight_club_server.matchingWait.dto.docs.*
import com.fightclub.fight_club_server.matchingWait.service.MatchingWaitService
import com.fightclub.fight_club_server.user.domain.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "Matching Wait", description = "Matching Wait API")
@RestController
@RequestMapping("/api/matching-wait")
class MatchingWaitController(
    private val matchingWaitService: MatchingWaitService
) {
    @Operation(
        summary = "내 매칭 대기 상태 조회",
        responses = [
            ApiResponse(
                responseCode = MatchingWaitConstants.MyMatchingWaitSuccess.STATUS_CODE,
                description = "[${MatchingWaitConstants.MyMatchingWaitSuccess.CODE}] ${MatchingWaitConstants.MyMatchingWaitSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = MyMatchingWaitSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchingWaitConstants.MatchingWaitNotFound.STATUS_CODE,
                description = "[${MatchingWaitConstants.MatchingWaitNotFound.CODE}] ${MatchingWaitConstants.MatchingWaitNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchingWaitNotFoundResponse::class))]
            ),
        ]
    )
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun myMatchingWait(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchingWaitSuccessCode.GET_MY_MATCHING_WAIT_SUCCESS, matchingWaitService.getMyMatchingWait(user))

    @Operation(
        summary = "매칭 대기 등록",
        responses = [
            ApiResponse(
                responseCode = MatchingWaitConstants.CreateMatchingWaitSuccess.STATUS_CODE,
                description = "[${MatchingWaitConstants.CreateMatchingWaitSuccess.CODE}] ${MatchingWaitConstants.CreateMatchingWaitSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = CreateMatchingWaitSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchingWaitConstants.MatchingWaitAlreadyExists.STATUS_CODE,
                description = "[${MatchingWaitConstants.MatchingWaitAlreadyExists.CODE}] ${MatchingWaitConstants.MatchingWaitAlreadyExists.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchingWaitAlreadyExistsResponse::class))]
            ),
        ]
    )
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun enrollMatchingWait(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: MatchingWaitRequest
    ) = BaseResponse.success(MatchingWaitSuccessCode.CREATE_MATCHING_WAIT_SUCCESS, matchingWaitService.createMatchingWait(user, request))

    @Operation(
        summary = "매칭 대기 취소",
        responses = [
            ApiResponse(
                responseCode = MatchingWaitConstants.DeleteMatchingWaitSuccess.STATUS_CODE,
                description = "[${MatchingWaitConstants.DeleteMatchingWaitSuccess.CODE}] ${MatchingWaitConstants.DeleteMatchingWaitSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = DeleteMatchingWaitSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchingWaitConstants.MatchingWaitNotFound.STATUS_CODE,
                description = "[${MatchingWaitConstants.MatchingWaitNotFound.CODE}] ${MatchingWaitConstants.MatchingWaitNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchingWaitNotFoundResponse::class))]
            ),
        ]
    )
    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    fun deleteMatchingWait(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchingWaitSuccessCode.DELETE_MATCHING_WAIT_SUCCESS, matchingWaitService.cancelMatchingWait(user))

    @Operation(
        summary = "매칭 대기 조건 수정",
        responses = [
            ApiResponse(
                responseCode = MatchingWaitConstants.UpdateMatchingWaitSuccess.STATUS_CODE,
                description = "[${MatchingWaitConstants.UpdateMatchingWaitSuccess.CODE}] ${MatchingWaitConstants.UpdateMatchingWaitSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = UpdateMatchingWaitSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchingWaitConstants.MatchingWaitNotFound.STATUS_CODE,
                description = "[${MatchingWaitConstants.MatchingWaitNotFound.CODE}] ${MatchingWaitConstants.MatchingWaitNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchingWaitNotFoundResponse::class))]
            ),
        ]
    )
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    fun updateMatchingWait(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: MatchingWaitRequest
    ) = BaseResponse.success(MatchingWaitSuccessCode.UPDATE_MATCHING_WAIT_SUCCESS, matchingWaitService.updateMatchingWait(user, request))

    @Operation(
        summary = "매칭 대기 리스트 조회",
        responses = [
            ApiResponse(
                responseCode = MatchingWaitConstants.RandomCandidateSuccess.STATUS_CODE,
                description = "[${MatchingWaitConstants.RandomCandidateSuccess.CODE}] ${MatchingWaitConstants.RandomCandidateSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = RandomCandidateSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchingWaitConstants.MatchingWaitNotFound.STATUS_CODE,
                description = "[${MatchingWaitConstants.MatchingWaitNotFound.CODE}] ${MatchingWaitConstants.MatchingWaitNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchingWaitNotFoundResponse::class))]
            ),
        ]
    )
    @GetMapping("/candidate")
    @PreAuthorize("isAuthenticated()")
    fun randomCandidate(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchingWaitSuccessCode.GET_CANDIDATE_LIST_SUCCESS, matchingWaitService.getCandidateList(user))

    @Operation(
        summary = "매치 요청 보내기",
        responses = [
            ApiResponse(
                responseCode = MatchingWaitConstants.CreateSendMatchProposalSuccess.STATUS_CODE,
                description = "[${MatchingWaitConstants.CreateSendMatchProposalSuccess.CODE}] ${MatchingWaitConstants.CreateSendMatchProposalSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = CreateSendMatchProposalResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchingWaitConstants.MatchingWaitNotFound.STATUS_CODE,
                description = "[${MatchingWaitConstants.MatchingWaitNotFound.CODE}] ${MatchingWaitConstants.MatchingWaitNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchingWaitNotFoundResponse::class))]
            ),
        ]
    )
    @PostMapping("/send")
    @PreAuthorize("isAuthenticated()")
    fun createSendMatchProposal(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: SendMatchRequest
    ) = BaseResponse.success(MatchingWaitSuccessCode.SEND_MATCH_PROPOSAL_SUCCESS, matchingWaitService.sendMatchProposal(user, request))
}