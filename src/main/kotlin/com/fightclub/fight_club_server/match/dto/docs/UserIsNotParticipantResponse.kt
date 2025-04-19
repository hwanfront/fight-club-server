package com.fightclub.fight_club_server.match.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.match.constants.MatchConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자가 매치의 참가자가 아님")
data class UserIsNotParticipantResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchConstants.UserIsNotParticipant.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchConstants.UserIsNotParticipant.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchConstants.UserIsNotParticipant.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>
