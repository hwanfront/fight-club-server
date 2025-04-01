package com.fightclub.fight_club_server.matchingWait.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "이미 존재하는 매칭 에러 응답")
data class MatchingWaitNotFoundResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchingWaitConstants.MatchingWaitNotFound.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchingWaitConstants.MatchingWaitNotFound.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchingWaitConstants.MatchingWaitNotFound.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>
