package com.fightclub.fight_club_server.matchProposal.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchProposalErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    MATCH_PROPOSAL_NOT_FOUND(HttpStatus.NOT_FOUND, MatchProposalConstants.MatchProposalNotFound.CODE, MatchProposalConstants.MatchProposalNotFound.MESSAGE),
    USER_IS_NOT_RECEIVER(HttpStatus.FORBIDDEN, MatchProposalConstants.UserIsNotReceiver.MESSAGE, MatchProposalConstants.UserIsNotReceiver.MESSAGE),
    USER_IS_NOT_SENDER(HttpStatus.FORBIDDEN, MatchProposalConstants.UserIsNotSender.MESSAGE, MatchProposalConstants.UserIsNotSender.MESSAGE),
}