package com.fightclub.fight_club_server.auth.dto.docs

import com.fightclub.fight_club_server.auth.constants.AuthConstants
import com.fightclub.fight_club_server.common.dto.ApiResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 갱신 성공 응답")
data class RefreshTokenNotFoundResponse(

    @Schema(description = "HTTP 상태 코드", example = AuthConstants.RefreshTokenNotFound.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = AuthConstants.RefreshTokenNotFound.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = AuthConstants.RefreshTokenNotFound.MESSAGE)
    override val message: String,

    override val data: Nothing
): ApiResult<Nothing>
