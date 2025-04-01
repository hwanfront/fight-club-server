package com.fightclub.fight_club_server.matchingWait.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchingWaitSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    GET_MY_MATCHING_WAIT_SUCCESS(HttpStatus.OK, MatchingWaitConstants.MyMatchingWaitSuccess.CODE, MatchingWaitConstants.MyMatchingWaitSuccess.MESSAGE),
    CREATE_MATCHING_WAIT_SUCCESS(HttpStatus.OK, MatchingWaitConstants.CreateMatchingWaitSuccess.CODE, MatchingWaitConstants.CreateMatchingWaitSuccess.MESSAGE),
    DELETE_MATCHING_WAIT_SUCCESS(HttpStatus.OK, MatchingWaitConstants.DeleteMatchingWaitSuccess.CODE, MatchingWaitConstants.DeleteMatchingWaitSuccess.MESSAGE),
    UPDATE_MATCHING_WAIT_SUCCESS(HttpStatus.OK, MatchingWaitConstants.UpdateMatchingWaitSuccess.CODE, MatchingWaitConstants.UpdateMatchingWaitSuccess.MESSAGE),
    GET_CANDIDATE_LIST_SUCCESS(HttpStatus.OK, MatchingWaitConstants.RandomCandidateSuccess.CODE, MatchingWaitConstants.RandomCandidateSuccess.MESSAGE),
    SEND_MATCH_PROPOSAL_SUCCESS(HttpStatus.OK,  MatchingWaitConstants.CreateSendMatchProposalSuccess.CODE, MatchingWaitConstants.CreateSendMatchProposalSuccess.MESSAGE),
}
