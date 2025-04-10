package com.fightclub.fight_club_server.matchProposal.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchProposal.constants.MatchProposalConstants
import com.fightclub.fight_club_server.matchProposal.dto.SentMatchProposalResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내가 보낸 매치요청 목록 조회")
data class GetSentProposalListResponse(

    @Schema(description = "HTTP 상태 코드", example = MatchProposalConstants.GetSentProposalList.STATUS_CODE)
    override val status: Int,


    @Schema(description = "응답 코드", example = MatchProposalConstants.GetSentProposalList.CODE)
    override val code: String,


    @Schema(description = "응답 메시지", example = MatchProposalConstants.GetSentProposalList.MESSAGE)
    override val message: String,

    override val data: List<SentMatchProposalResponse>
): ApiResult<List<SentMatchProposalResponse>>
