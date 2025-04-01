package com.fightclub.fight_club_server.auth.dto.docs

import com.fightclub.fight_club_server.auth.constants.AuthConstants
import com.fightclub.fight_club_server.common.dto.ApiResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "비밀번호 불일치")
data class InvalidRefreshTokenResponse(
    @Schema(description = "HTTP 상태 코드", example = AuthConstants.InvalidRefreshToken.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = AuthConstants.InvalidRefreshToken.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = AuthConstants.InvalidRefreshToken.MESSAGE)
    override val message: String,

    override val data: Nothing,
): ApiResult<Nothing>