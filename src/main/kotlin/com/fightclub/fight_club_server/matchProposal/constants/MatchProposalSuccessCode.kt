package com.fightclub.fight_club_server.matchProposal.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchProposalSuccessCode (
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    GET_RECEIVED_PROPOSAL_LIST_SUCCESS(HttpStatus.OK, MatchProposalConstants.GetReceivedProposalList.CODE, MatchProposalConstants.GetReceivedProposalList.MESSAGE),
    GET_SENT_PROPOSAL_LIST_SUCCESS(HttpStatus.OK, MatchProposalConstants.GetSentProposalList.CODE, MatchProposalConstants.GetSentProposalList.MESSAGE),
    ACCEPT_PROPOSAL_SUCCESS(HttpStatus.OK, MatchProposalConstants.AcceptProposalSuccess.CODE, MatchProposalConstants.AcceptProposalSuccess.MESSAGE),
    REJECT_PROPOSAL_SUCCESS(HttpStatus.OK, MatchProposalConstants.RejectProposalSuccess.CODE, MatchProposalConstants.RejectProposalSuccess.MESSAGE),
    CANCEL_MY_PROPOSAL_SUCCESS(HttpStatus.OK, MatchProposalConstants.CancelMyProposalSuccess.CODE, MatchProposalConstants.CancelMyProposalSuccess.MESSAGE),
}
