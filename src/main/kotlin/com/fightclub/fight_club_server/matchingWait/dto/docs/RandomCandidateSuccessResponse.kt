package com.fightclub.fight_club_server.matchingWait.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import com.fightclub.fight_club_server.matchingWait.dto.MatchingCandidateResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "매칭 대기 리스트 조회 성공 응답")
data class RandomCandidateSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchingWaitConstants.RandomCandidateSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchingWaitConstants.RandomCandidateSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchingWaitConstants.RandomCandidateSuccess.MESSAGE)
    override val message: String,

    override val data: List<MatchingCandidateResponse>
): ApiResult<List<MatchingCandidateResponse>>
