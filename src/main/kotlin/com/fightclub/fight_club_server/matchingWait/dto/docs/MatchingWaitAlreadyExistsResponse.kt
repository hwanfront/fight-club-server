package com.fightclub.fight_club_server.matchingWait.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "중복 매칭 에러 응답")
data class MatchingWaitAlreadyExistsResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchingWaitConstants.MatchingWaitAlreadyExists.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchingWaitConstants.MatchingWaitAlreadyExists.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchingWaitConstants.MatchingWaitAlreadyExists.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>
