package com.fightclub.fight_club_server.matchProposal.contorller

import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalSuccessCode
import com.fightclub.fight_club_server.matchProposal.service.MatchProposalService
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/match-proposal")
class MatchProposalController(
    private val matchProposalService: MatchProposalService
) {

    @GetMapping("/received")
    @PreAuthorize("isAuthenticated()")
    fun getReceivedProposalList(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchProposalSuccessCode.GET_RECEIVED_PROPOSAL_LIST_SUCCESS, matchProposalService.getReceivedMatchProposalList(user))

    @GetMapping("/sent")
    @PreAuthorize("isAuthenticated()")
    fun getSentProposalList(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(MatchProposalSuccessCode.GET_SENT_PROPOSAL_LIST_SUCCESS, matchProposalService.getSentMatchProposalList(user))

    @PostMapping("/{matchProposalId}/accept")
    @PreAuthorize("isAuthenticated()")
    fun acceptProposal(
        @PathVariable matchProposalId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchProposalSuccessCode.ACCEPT_PROPOSAL_SUCCESS, matchProposalService.acceptProposal(matchProposalId, user))

    @PostMapping("/{matchProposalId}/reject")
    @PreAuthorize("isAuthenticated()")
    fun rejectProposal(
        @PathVariable matchProposalId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchProposalSuccessCode.REJECT_PROPOSAL_SUCCESS, matchProposalService.rejectProposal(matchProposalId, user))

    @DeleteMapping("/{matchProposalId}")
    fun cancelMyProposal(
        @PathVariable matchProposalId: Long,
        @AuthenticationPrincipal user: User,
    ) = BaseResponse.success(MatchProposalSuccessCode.CANCEL_MY_PROPOSAL_SUCCESS, matchProposalService.cancelMyProposal(matchProposalId, user))
}