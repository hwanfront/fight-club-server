package com.fightclub.fight_club_server.matchingWait.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "매칭 대기 조건 수정 성공 응답")
data class UpdateMatchingWaitSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchingWaitConstants.UpdateMatchingWaitSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchingWaitConstants.UpdateMatchingWaitSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchingWaitConstants.UpdateMatchingWaitSuccess.MESSAGE)
    override val message: String,

    override val data: MatchingWaitResponse
): ApiResult<MatchingWaitResponse>
