package com.fightclub.fight_club_server.match.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.match.constants.MatchConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "매치 정보 조회 실패")
data class MatchNotFoundResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchConstants.GetMatchInfoSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchConstants.GetMatchInfoSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchConstants.GetMatchInfoSuccess.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>
