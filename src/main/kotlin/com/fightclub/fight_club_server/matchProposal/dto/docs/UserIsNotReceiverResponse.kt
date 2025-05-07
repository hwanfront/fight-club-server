package com.fightclub.fight_club_server.matchProposal.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자가 수신자 receiver 가 아님")
data class UserIsNotReceiverResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchProposalConstants.UserIsNotReceiver.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchProposalConstants.UserIsNotReceiver.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchProposalConstants.UserIsNotReceiver.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>
