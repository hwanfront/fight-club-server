package com.fightclub.fight_club_server.matchProposal.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalConstants
import com.fightclub.fight_club_server.matchProposal.dto.ReceivedMatchProposalResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "매치요청 목록 조회")
data class GetReceivedProposalListResponse(

    @Schema(description = "HTTP 상태 코드", example = MatchProposalConstants.GetReceivedProposalList.STATUS_CODE)
    override val status: Int,


    @Schema(description = "응답 코드", example = MatchProposalConstants.GetReceivedProposalList.CODE)
    override val code: String,


    @Schema(description = "응답 메시지", example = MatchProposalConstants.GetReceivedProposalList.MESSAGE)
    override val message: String,

    override val data: List<ReceivedMatchProposalResponse>
): ApiResult<List<ReceivedMatchProposalResponse>>
