package com.fightclub.fight_club_server.matchingWait.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내 매칭 대기 상태 조회 성공 응답")
data class MyMatchingWaitSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchingWaitConstants.MyMatchingWaitSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchingWaitConstants.MyMatchingWaitSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchingWaitConstants.MyMatchingWaitSuccess.MESSAGE)
    override val message: String,

    override val data: MatchingWaitResponse
): ApiResult<MatchingWaitResponse>
