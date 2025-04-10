package com.fightclub.fight_club_server.matchProposal.contorller

import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalConstants
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalSuccessCode
import com.fightclub.fight_club_server.matchProposal.dto.docs.*
import com.fightclub.fight_club_server.matchProposal.service.MatchProposalService
import com.fightclub.fight_club_server.user.domain.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "Match Proposal", description = "Match Proposal API")
@RestController
@RequestMapping("/api/match-proposal")
class MatchProposalController(
    private val matchProposalService: MatchProposalService
) {
    @Operation(
        summary = "내가 받은 매치요청 목록 조회",
        responses = [
            ApiResponse(
                responseCode = MatchProposalConstants.GetReceivedProposalList.STATUS_CODE,
                description = "[${MatchProposalConstants.GetReceivedProposalList.CODE}] ${MatchProposalConstants.GetReceivedProposalList.MESSAGE}",
                content = [Content(schema = Schema(implementation = GetReceivedProposalListResponse::class))]
            ),
        ]
    )
    @GetMapping("/received")
    @PreAuthorize("isAuthenticated()")
    fun getReceivedProposalList(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchProposalSuccessCode.GET_RECEIVED_PROPOSAL_LIST_SUCCESS, matchProposalService.getReceivedMatchProposalList(user))

    @Operation(
        summary = "내가 보낸 매치요청 목록 조회",
        responses = [
            ApiResponse(
                responseCode = MatchProposalConstants.GetSentProposalList.STATUS_CODE,
                description = "[${MatchProposalConstants.GetSentProposalList.CODE}] ${MatchProposalConstants.GetSentProposalList.MESSAGE}",
                content = [Content(schema = Schema(implementation = GetSentProposalListResponse::class))]
            ),
        ]
    )
    @GetMapping("/sent")
    @PreAuthorize("isAuthenticated()")
    fun getSentProposalList(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchProposalSuccessCode.GET_SENT_PROPOSAL_LIST_SUCCESS, matchProposalService.getSentMatchProposalList(user))

    @Operation(
        summary = "매치요청 수락",
        responses = [
            ApiResponse(
                responseCode = MatchProposalConstants.AcceptProposalSuccess.STATUS_CODE,
                description = "[${MatchProposalConstants.AcceptProposalSuccess.CODE}] ${MatchProposalConstants.AcceptProposalSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = AcceptProposalResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchProposalConstants.UserIsNotReceiver.STATUS_CODE,
                description = "[${MatchProposalConstants.UserIsNotReceiver.CODE}] ${MatchProposalConstants.UserIsNotReceiver.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserIsNotReceiverResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchProposalConstants.MatchProposalNotFound.STATUS_CODE,
                description = "[${MatchProposalConstants.MatchProposalNotFound.CODE}] ${MatchProposalConstants.MatchProposalNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchProposalNotFoundResponse::class))]
            ),
        ]
    )
    @PostMapping("/{matchProposalId}/accept")
    @PreAuthorize("isAuthenticated()")
    fun acceptProposal(
        @PathVariable matchProposalId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchProposalSuccessCode.ACCEPT_PROPOSAL_SUCCESS, matchProposalService.acceptProposal(matchProposalId, user))

    @Operation(
        summary = "매치요청 거절",
        responses = [
            ApiResponse(
                responseCode = MatchProposalConstants.RejectProposalSuccess.STATUS_CODE,
                description = "[${MatchProposalConstants.RejectProposalSuccess.CODE}] ${MatchProposalConstants.RejectProposalSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserIsNotSenderResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchProposalConstants.UserIsNotReceiver.STATUS_CODE,
                description = "[${MatchProposalConstants.UserIsNotReceiver.CODE}] ${MatchProposalConstants.UserIsNotReceiver.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserIsNotReceiverResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchProposalConstants.MatchProposalNotFound.STATUS_CODE,
                description = "[${MatchProposalConstants.MatchProposalNotFound.CODE}] ${MatchProposalConstants.MatchProposalNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchProposalNotFoundResponse::class))]
            ),
        ]
    )
    @PostMapping("/{matchProposalId}/reject")
    @PreAuthorize("isAuthenticated()")
    fun rejectProposal(
        @PathVariable matchProposalId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchProposalSuccessCode.REJECT_PROPOSAL_SUCCESS, matchProposalService.rejectProposal(matchProposalId, user))

    @Operation(
        summary = "내가 보낸 매치요청 취소",
        responses = [
            ApiResponse(
                responseCode = MatchProposalConstants.CancelMyProposalSuccess.STATUS_CODE,
                description = "[${MatchProposalConstants.CancelMyProposalSuccess.CODE}] ${MatchProposalConstants.CancelMyProposalSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = CancelMyProposalResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchProposalConstants.MatchProposalNotFound.STATUS_CODE,
                description = "[${MatchProposalConstants.MatchProposalNotFound.CODE}] ${MatchProposalConstants.MatchProposalNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = MatchProposalNotFoundResponse::class))]
            ),
            ApiResponse(
                responseCode = MatchProposalConstants.UserIsNotSender.STATUS_CODE,
                description = "[${MatchProposalConstants.UserIsNotSender.CODE}] ${MatchProposalConstants.UserIsNotSender.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserIsNotSenderResponse::class))]
            ),
        ]
    )
    @DeleteMapping("/{matchProposalId}")
    fun cancelMyProposal(
        @PathVariable matchProposalId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchProposalSuccessCode.CANCEL_MY_PROPOSAL_SUCCESS, matchProposalService.cancelMyProposal(matchProposalId, user))
}