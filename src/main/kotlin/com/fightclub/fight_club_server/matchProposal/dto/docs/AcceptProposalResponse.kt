package com.fightclub.fight_club_server.matchProposal.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalConstants
import com.fightclub.fight_club_server.matchProposal.dto.AcceptResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "매치요청 보내기 성공 응답")
data class AcceptProposalResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchProposalConstants.AcceptProposalSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchProposalConstants.AcceptProposalSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchProposalConstants.AcceptProposalSuccess.MESSAGE)
    override val message: String,

    override val data: AcceptResponse
): ApiResult<AcceptResponse>
