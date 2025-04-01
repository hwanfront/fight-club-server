package com.fightclub.fight_club_server.user.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.user.constants.UserConstants
import io.swagger.v3.oas.annotations.media.Schema

data class UserNotFoundResponse(
    @Schema(description = "HTTP 상태 코드", example = UserConstants.UserNotFound.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = UserConstants.UserNotFound.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = UserConstants.UserNotFound.MESSAGE)
    override val message: String,

    @Schema(description = "응답 데이터")
    override val data: Nothing
): ApiResult<Nothing>
