package com.fightclub.fight_club_server.matchingWait.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "매칭 대기 취소 성공 응답")
data class DeleteMatchingWaitSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchingWaitConstants.DeleteMatchingWaitSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchingWaitConstants.DeleteMatchingWaitSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchingWaitConstants.DeleteMatchingWaitSuccess.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>